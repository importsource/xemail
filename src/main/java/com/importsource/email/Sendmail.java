package com.importsource.email;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.importsource.conf.PropertiesTools;

/**
 * 发送邮件
 * @author Hezf
 *
 */
public class Sendmail {
	private  String smtp = "smtp.189.cn";

	private  String pwd = "";

	private  String from = "";
	
	private String storePath="/";
	// sendText("text","13308362652@189.cn");
	
	private Properties properties;
	private Session session;
	private Transport ts ;
	private  void configure(){
		properties = new Properties();
		properties.setProperty("mail.host", smtp);
		properties.setProperty("mail.transport.protocol", "smtp");
		properties.setProperty("mail.smtp.auth", "true");
		com.importsource.conf.Properties p=Configuration.newPropertiesInstance();
		from=PropertiesTools.get(p, "xemail.from", "");
		pwd=PropertiesTools.get(p, "xemail.pwd", "");
		smtp=PropertiesTools.get(p, "xemail.smtp", "");
	}
	public  void sendText(String text,String receiver) throws Exception{
		configure();
		setSession();
		setDebug(true);
		setTransport();
		connect();
		Message msg=createSimpleMail(text,receiver);
		send1(msg);
	    close();
	}
	
	
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
	
	 private  MimeMessage createSimpleMail(String text,String receiver)
	            throws Exception {
	        //创建邮件对象
	        MimeMessage message = new MimeMessage(session);
	        //指明邮件的发件人
	        message.setFrom(new InternetAddress(from));
	        //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
	        message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
	        //邮件的标题
	        message.setSubject(text);
	        //邮件的文本内容
	        message.setContent(text, "text/html;charset=UTF-8");
	        //返回创建好的邮件对象
	        return message;
	    }
	
	 /**
	    * @Method: createAttachMail
	    * @Description: 创建一封带附件的邮件
	    * @Anthor:孤傲苍狼
	    *
	    * @param session
	    * @return
	    * @throws Exception
	    */ 
	    private  MimeMessage createAttachMail(String text,FileDataSource fileDataSource,String receiver) throws Exception{
	        MimeMessage message = new MimeMessage(session);
	        
	        //设置邮件的基本信息
	        //发件人
	        message.setFrom(new InternetAddress(from));
	        //收件人
	        message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
	        //邮件标题
	        message.setSubject("JavaMail邮件发送测试");
	        
	        //创建邮件正文，为了避免邮件正文中文乱码问题，需要使用charset=UTF-8指明字符编码
	        MimeBodyPart mimeBodyPart = new MimeBodyPart();
	        mimeBodyPart.setContent(text, "text/html;charset=UTF-8");
	        
	        //创建邮件附件
	        MimeBodyPart attach = new MimeBodyPart();
	        DataHandler dh = new DataHandler(fileDataSource);
	        attach.setDataHandler(dh);
	        attach.setFileName(dh.getName());  //
	        
	        //创建容器描述数据关系
	        MimeMultipart mp = new MimeMultipart();
	        mp.addBodyPart(mimeBodyPart);
	        mp.addBodyPart(attach);
	        mp.setSubType("mixed");
	        
	        message.setContent(mp);
	        message.saveChanges();
	        //将创建的Email写入到E盘存储
	        store(message);
	        //返回生成的邮件
	        return message;
	    }
	    
	    
	   
	     private  MimeMessage createImageMail(String subject,String content,FileDataSource fileDataSource,String receiver) throws Exception {
	         //创建邮件
	         MimeMessage message = new MimeMessage(session);
	         // 设置邮件的基本信息
	         //发件人
	         message.setFrom(new InternetAddress(from));
	         //收件人
	         message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
	         //邮件标题
	         message.setSubject(subject);

	         // 准备邮件数据
	         // 准备邮件正文数据
	         MimeBodyPart mimeBodyPart = new MimeBodyPart();
	         mimeBodyPart.setContent(content,"text/html;charset=UTF-8");
	         // 准备图片数据
	         MimeBodyPart image = new MimeBodyPart();
	         DataHandler dh = new DataHandler(fileDataSource);
	         image.setDataHandler(dh);
	         image.setContentID(fileDataSource.getFile().getPath());
	         // 描述数据关系
	         MimeMultipart mm = new MimeMultipart();
	         mm.addBodyPart(mimeBodyPart);
	         mm.addBodyPart(image);
	         mm.setSubType("related");

	         message.setContent(mm);
	         message.saveChanges();
	         //将创建好的邮件写入到E盘以文件的形式进行保存
	         store(message);
	         //返回创建好的邮件
	         return message;
	     }    
	private void store(MimeMessage message) throws IOException, MessagingException, FileNotFoundException {
		message.writeTo(new FileOutputStream(storePath));
	}

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
       Sendmail sendmail=new Sendmail();
       sendmail.sendText("sdfsdf", "meixixizf24@gmail.com");
    }
    
   
}