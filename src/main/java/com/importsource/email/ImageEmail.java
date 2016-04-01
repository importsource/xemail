package com.importsource.email;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 带图片的
 * @author Hezf
 *
 */
public class ImageEmail extends AbstractEmail {

	@Override
	protected Message createMail(String subject, String content, FileDataSource fileDataSource, String receiver)
			throws Exception {
		return this.createImageMail(subject, content, fileDataSource, receiver);
	}

	private MimeMessage createImageMail(String subject, String content, FileDataSource fileDataSource, String receiver)
			throws Exception {
		// 创建邮件
		MimeMessage message = new MimeMessage(session);
		// 设置邮件的基本信息
		// 发件人
		message.setFrom(new InternetAddress(from));
		// 收件人
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
		// 邮件标题
		message.setSubject(subject);

		// 准备邮件数据
		// 准备邮件正文数据
		MimeBodyPart mimeBodyPart = new MimeBodyPart();
		mimeBodyPart.setContent(content, "text/html;charset=UTF-8");
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
		// 将创建好的邮件写入到E盘以文件的形式进行保存
		store(message);
		// 返回创建好的邮件
		return message;
	}

}
