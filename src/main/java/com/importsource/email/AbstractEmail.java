package com.importsource.email;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import com.importsource.conf.PropertiesTools;

/**
 * 邮件基类
 * @author Hezf
 *
 */
public abstract class AbstractEmail {
	protected  String smtp = "smtp.189.cn";

	protected  String pwd = "";

	protected  String from = "";
	
	protected String storePath="/";
	
	protected Properties properties;
	protected Session session;
	protected Transport ts ;
	protected  void configure(){
		properties = new Properties();
		properties.setProperty("mail.host", smtp);
		properties.setProperty("mail.transport.protocol", "smtp");
		properties.setProperty("mail.smtp.auth", "true");
		com.importsource.conf.Properties p=Configuration.newPropertiesInstance();
		from=PropertiesTools.get(p, "xemail.from", "");
		pwd=PropertiesTools.get(p, "xemail.pwd", "");
		smtp=PropertiesTools.get(p, "xemail.smtp", "");
	}
	
	
	/**
	 * 发送含有附件的邮件
	 * @param text 文本信息和主题
	 * @param fileDataSource 附件
	 * @param receiver 接收人
	 * @throws Exception
	 */
	public  void send(String subject,String content,FileDataSource fileDataSource,String receiver) throws Exception{
		configure();
		setSession();
		setDebug(true);
		setTransport();
		connect();
		Message msg=createMail(subject,content,fileDataSource,receiver);
		send1(msg);
	    close();
	}
	
	
	protected abstract Message createMail(String subject,String content, FileDataSource fileDataSource, String receiver) throws Exception;

	private void send1(Message msg) throws MessagingException {
		ts.sendMessage(msg, msg.getAllRecipients());
	}
	private void close() throws MessagingException {
		ts.close();
	}
	
	private void setSession(){
		session = Session.getInstance(properties);
	}
	
	private void setDebug(boolean debug){
		session.setDebug(true);
	}
	
	private void setTransport() throws NoSuchProviderException{
		ts = session.getTransport();
	}
	
	private void connect() throws MessagingException{
		ts.connect(smtp, from, pwd);
	}
	
	    
	protected void store(MimeMessage message) throws IOException, MessagingException, FileNotFoundException {
		message.writeTo(new FileOutputStream(storePath));
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