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

	@Override
	protected Message createMail(String subject, String content, List<FileDataSource> fileDataSources, String receiver)
			throws Exception {
		return this.createSimpleMail(subject, receiver);
	}

	private MimeMessage createSimpleMail(String text, String receiver) throws Exception {
		// 创建邮件对象
		MimeMessage message = new MimeMessage(session);
		// 指明邮件的发件人
		message.setFrom(new InternetAddress(from));
		// 指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
		// 邮件的标题
		message.setSubject(text);
		// 邮件的文本内容
		message.setContent(text, "text/html;charset=UTF-8");
		// 返回创建好的邮件对象
		return message;
	}
}
