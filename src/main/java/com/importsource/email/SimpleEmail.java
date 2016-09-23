package com.importsource.email;

import java.util.List;

import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 纯文本
 * @author Hezf
 *
 */
public class SimpleEmail extends AbstractEmail {

	public SimpleEmail(boolean builtIn, String from, String pwd, String protocol, String host) {
		super(builtIn, from, pwd, protocol, host);
	}
	
	public SimpleEmail() {
		super();
	}

	@Override
	protected Message createMail(String subject, String content, List<FileDataSource> fileDataSources, String receiver)
			throws Exception {
		return this.createSimpleMail(subject,content, receiver);
	}

	private MimeMessage createSimpleMail(String text, String content,String receiver) throws Exception {
		// 创建邮件对象
		MimeMessage message = new MimeMessage(session);
		// 指明邮件的发件人
		message.setFrom(new InternetAddress(from));
		// 指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
		//message.setRecipients(Message.RecipientType.TO, receiver);
		// 邮件的标题
		message.setSubject(text);
		// 邮件的文本内容
		message.setContent(content, "text/html;charset=UTF-8");
		// 返回创建好的邮件对象
		return message;
	}
}
