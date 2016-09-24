package com.importsource.email;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.importsource.email.client.EmailProperties;
import com.importsource.email.exc.SendEmailException;

/**
 * 邮件基类
 * 
 * @author Hezf
 *
 */
public abstract class AbstractEmail implements SendEmailable {
	protected static Log log = LogFactory.getLog(AbstractEmail.class);

	protected String host = "smtp.189.cn";

	protected String pwd = "";

	protected String from = "";

	protected String protocol = "smtp";

	protected String storePath = "";

	protected boolean builtIn = true;

	/**
	 * 发送邮件的props文件
	 */
	protected final transient Properties properties = System.getProperties();
	protected Session session;
	protected Transport ts;
	
	
	protected List<InternetAddress> cc;
	protected List<InternetAddress> bcc;

	public AbstractEmail(boolean builtIn, String from, String pwd, String protocol, String host) {
		if (!builtIn) {
			this.from = from;
			this.protocol = protocol;
			this.pwd = pwd;
			this.host = host;
			this.builtIn = builtIn;
		}
	}

	public AbstractEmail() {
	}

	protected void configure() {
		if (builtIn) {
			from = EmailProperties.getFrom();
			pwd = EmailProperties.getPwd();
			host = EmailProperties.getHost();
			storePath = EmailProperties.getStorePath();
			protocol = EmailProperties.getProtocol();
		}
	}

	/**
	 * 发送含有附件的邮件
	 * 
	 * @param text
	 *            文本信息和主题
	 * @param fileDataSource
	 *            附件
	 * @param receiver
	 *            接收人
	 * @throws Exception
	 */
	public synchronized SendResult send(String subject, String content, List<FileDataSource> fileDataSources,
			String receiver) throws SendEmailException {
		configure();
		setSession();
		setDebug(true);
		setTransport();
		connect();
		SendResult result = sendSingle(subject, content, fileDataSources, receiver);
		close();
		return result;
	}

	private SendResult sendSingle(String subject, String content, List<FileDataSource> fileDataSources, String receiver)
			throws SendEmailException {
		Message msg;
		try {
			msg = createMail(subject, content, fileDataSources, receiver);
			setBCCAndCC(msg);
		} catch (Exception e1) {
			throw new SendEmailException(e1);
		}
		SendResult result = new SendResult();
		List<String> success = new ArrayList<String>();
		List<String> fail = new ArrayList<String>();
		try {
			send1(msg);
			success.add(receiver);
		} catch (MessagingException e) {
			log.error(e);
			fail.add(receiver);
		}
		result.setSuccess(success);
		result.setFail(fail);
		return result;
	}

	private void setBCCAndCC(Message msg) throws MessagingException {
		if(cc!=null&& cc.size()>0){
			msg.setRecipients(Message.RecipientType.CC, cc.toArray(new InternetAddress[]{}));
		}
		if(bcc!=null){
			msg.setRecipients(Message.RecipientType.BCC, bcc.toArray(new InternetAddress[]{}));
		}
	}

	/**
	 * 发送含有附件的邮件
	 * 
	 * @param text
	 *            文本信息和主题
	 * @param fileDataSource
	 *            附件
	 * @param receivers
	 *            多个接收人
	 * @throws Exception
	 */
	public synchronized SendResult send(String subject, String content, List<FileDataSource> fileDataSources,
			List<String> receivers) throws SendEmailException {
		configure();
		setSession();
		setDebug(true);
		setTransport();
		connect();
		SendResult result;
		try {
			result = sendBatch(subject, content, fileDataSources, receivers);
		} catch (Exception e1) {
			throw new SendEmailException(e1);
		}
		close();
		return result;
	}

	private SendResult sendBatch(String subject, String content, List<FileDataSource> fileDataSources,
			List<String> receivers) throws Exception {
		SendResult result = new SendResult();
		List<String> success = new ArrayList<String>();
		List<String> fail = new ArrayList<String>();
		for (int i = 0; i < receivers.size(); i++) {
			String receiver = receivers.get(i);
			Message msg = createMail(subject, content, fileDataSources, receiver);
			setBCCAndCC(msg);
			try {
				send1(msg);
				success.add(receiver);
			} catch (MessagingException e) {
				log.error(e);
				fail.add(receiver);
				continue;
			}
		}
		result.setSuccess(success);
		result.setFail(fail);
		return result;
	}

	protected abstract Message createMail(String subject, String content, List<FileDataSource> fileDataSources,
			String receiver) throws Exception;

	private void send1(Message msg) throws MessagingException {
		ts.sendMessage(msg, msg.getAllRecipients());
		
	}

	private void close() throws SendEmailException {
		try {
			ts.close();
		} catch (MessagingException e) {
			throw new SendEmailException("messaging Exception", e);
		}

	}

	private void setSession() {
		properties.put("mail.transport.protocol", protocol);
		session = Session.getDefaultInstance(properties);
	}

	private void setDebug(boolean debug) {
		session.setDebug(true);
	}

	private void setTransport() throws SendEmailException {
		try {
			ts = session.getTransport();
		} catch (NoSuchProviderException e) {
			throw new SendEmailException("No such provider", e);
		}
	}

	private void connect() throws SendEmailException {
		try {
			ts.connect(host, from, pwd);
		} catch (MessagingException e) {
			throw new SendEmailException("messaging Exception", e);
		}

	}

	protected void store(MimeMessage message) throws IOException, MessagingException, FileNotFoundException {
		message.writeTo(new FileOutputStream(storePath + getLocalFileName(message.getSubject())));
	}

	protected String getLocalFileName(String subject) {
		return subject + ".eml";
	}
	
	public void setCC(List<String> cc) throws AddressException {
		
		if(cc!=null&& cc.size()>0){
			List<InternetAddress> ccList=new ArrayList<InternetAddress>();
			for(int i=0;i<cc.size();i++){
				String ccTmp=cc.get(i);
				ccList.add(new InternetAddress(ccTmp));
			}
			this.cc=ccList;
		}
		
		
	}

	public void setBCC(List<String> bcc) throws AddressException {
		if(bcc!=null&& bcc.size()>0){
			List<InternetAddress> bccList=new ArrayList<InternetAddress>();
			for(int i=0;i<bcc.size();i++){
				String bccTmp=bcc.get(i);
				bccList.add(new InternetAddress(bccTmp));
			}
			this.bcc=bccList;
		}
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// Sendmail sendmail=new Sendmail();
		// sendmail.sendText("sdfsdf", "meixixizf24@gmail.com");
	}

}