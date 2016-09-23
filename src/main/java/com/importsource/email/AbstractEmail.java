package com.importsource.email;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.importsource.email.client.EmailProperties;

/**
 * 邮件基类
 * 
 * @author Hezf
 *
 */
public abstract class AbstractEmail {
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

	public AbstractEmail(boolean builtIn, String from, String pwd, String protocol, String host) {
		if (!builtIn) {
			this.from = from;
			this.protocol = protocol;
			this.pwd = pwd;
			this.host = host;
			this.builtIn=builtIn;
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
	public SendResult send(String subject, String content, List<FileDataSource> fileDataSources, String receiver)
			throws Exception {
		configure();
		setSession();
		setDebug(true);
		setTransport();
		connect();
		Message msg = createMail(subject, content, fileDataSources, receiver);
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
		close();
		return result;
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
	public SendResult send(String subject, String content, List<FileDataSource> fileDataSources, List<String> receivers)
			throws Exception {
		configure();
		setSession();
		setDebug(true);
		setTransport();
		connect();
		SendResult result = new SendResult();
		List<String> success = new ArrayList<String>();
		List<String> fail = new ArrayList<String>();
		for (int i = 0; i < receivers.size(); i++) {
			String receiver = receivers.get(i);
			Message msg = createMail(subject, content, fileDataSources, receiver);
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
		close();
		return result;
	}

	protected abstract Message createMail(String subject, String content, List<FileDataSource> fileDataSources,
			String receiver) throws Exception;

	private void send1(Message msg) throws MessagingException {
		ts.sendMessage(msg, msg.getAllRecipients());
	}

	private void close() throws MessagingException {
		ts.close();
	}

	private void setSession() {
		properties.put("mail.transport.protocol", protocol);
		session = Session.getDefaultInstance(properties);
	}

	private void setDebug(boolean debug) {
		session.setDebug(true);
	}

	private void setTransport() throws NoSuchProviderException {
		ts = session.getTransport();
	}

	private void connect() throws MessagingException {
		ts.connect(host, from, pwd);
	}

	protected void store(MimeMessage message) throws IOException, MessagingException, FileNotFoundException {
		message.writeTo(new FileOutputStream(storePath + getLocalFileName(message.getSubject())));
	}

	protected String getLocalFileName(String subject) {
		return subject + ".eml";
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