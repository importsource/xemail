package com.importsource.email.client;

import java.util.ArrayList;
import java.util.List;

import javax.activation.FileDataSource;

import com.importsource.email.AbstractEmail;
import com.importsource.email.AttachEmail;
import com.importsource.email.ImageEmail;
import com.importsource.email.SimpleEmail;

/**
 * 邮件工具类
 * @author Hezf
 *
 */
public class MailTool {
	
	/**
	 * 测试邮件发送是否正常。发送纯文本(标题和正文一样)
	 * @param text 标题
	 * @param content 内容
	 * @param receiver 接收人
	 */
	public static void test() {
		AbstractEmail mail = new SimpleEmail();
		String testMsg="importsource.xemail test";
		/*try {
			Properties p=Configuration.newPropertiesInstance();
			String from=PropertiesTools.get(p, "xemail.from", "");
			mail.send(testMsg, testMsg, null, from);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	
	/**
	 * 发送纯文本(标题和正文一样)
	 * @param subject 标题
	 * @param content 内容
	 * @param receiver 接收人
	 */
	public static void sendText(String text, String receiver) {
		AbstractEmail mail = new SimpleEmail();
		try {
			mail.send(text, text, null, receiver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送纯文本
	 * @param subject 标题
	 * @param content 内容
	 * @param receiver 接收人
	 */
	public static void sendText(String subject, String content, String receiver) {
		AbstractEmail mail = new SimpleEmail();
		try {
			mail.send(subject, content, null, receiver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 发送带附件的（单个附件）
	 * @param subject 标题
	 * @param content 内容
	 * @param fileDataSource 附件
	 * @param receiver 接收人
	 */
	public static void sendAttach(String subject, String content, FileDataSource fileDataSource, String receiver) {
		AbstractEmail mail = new AttachEmail();
		
		List<FileDataSource> fileDataSources=new ArrayList<FileDataSource>();
		fileDataSources.add(fileDataSource);
		try {
			mail.send(subject, content, fileDataSources, receiver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送带附件的（多个附件）
	 * @param subject 标题
	 * @param content 内容
	 * @param fileDataSources 多个附件
	 * @param receiver 接收人
	 */
	public static void sendAttach(String subject, String content, List<FileDataSource> fileDataSources, String receiver) {
		AbstractEmail mail = new AttachEmail();
		try {
			mail.send(subject, content, fileDataSources, receiver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
	/**
	 * 发送正文带图片的(单张图片)
	 * @param subject 标题
	 * @param content 内容
	 * @param fileDataSource 图片
	 * @param receiver 接收人
	 */
	public static void sendImage(String subject, String content, FileDataSource fileDataSource, String receiver) {
		AbstractEmail mail = new ImageEmail();
		
		List<FileDataSource> fileDataSources=new ArrayList<FileDataSource>();
		fileDataSources.add(fileDataSource);
		try {
			mail.send(subject, content, fileDataSources, receiver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送正文带图片的(多张图片)
	 * @param subject 标题
	 * @param content 内容
	 * @param fileDataSources 多个图片
	 * @param receiver 接收人
	 */
	public static void sendImage(String subject, String content, List<FileDataSource> fileDataSources, String receiver) {
		AbstractEmail mail = new ImageEmail();
		try {
			mail.send(subject, content, fileDataSources, receiver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**************************************************/
	/**
	 * 发送纯文本(标题和正文一样)
	 * @param subject 标题
	 * @param content 内容
	 * @param receivers 接收人
	 */
	public static void sendText(String text, List<String> receivers) {
		AbstractEmail mail = new SimpleEmail();
		try {
			mail.send(text, text, null, receivers);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送纯文本
	 * @param subject 标题
	 * @param content 内容
	 * @param receivers 接收人
	 */
	public static void sendText(String subject, String content, List<String> receivers) {
		AbstractEmail mail = new SimpleEmail();
		try {
			mail.send(subject, content, null, receivers);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 发送带附件的（单个附件）
	 * @param subject 标题
	 * @param content 内容
	 * @param fileDataSource 附件
	 * @param receivers 接收人
	 */
	public static void sendAttach(String subject, String content, FileDataSource fileDataSource, List<String> receivers) {
		AbstractEmail mail = new AttachEmail();
		
		List<FileDataSource> fileDataSources=new ArrayList<FileDataSource>();
		fileDataSources.add(fileDataSource);
		try {
			mail.send(subject, content, fileDataSources, receivers);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送带附件的（多个附件）
	 * @param subject 标题
	 * @param content 内容
	 * @param fileDataSources 多个附件
	 * @param receivers 接收人
	 */
	public static void sendAttach(String subject, String content, List<FileDataSource> fileDataSources, List<String> receivers) {
		AbstractEmail mail = new AttachEmail();
		try {
			mail.send(subject, content, fileDataSources, receivers);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
	/**
	 * 发送正文带图片的(单张图片)
	 * @param subject 标题
	 * @param content 内容
	 * @param fileDataSource 图片
	 * @param receivers 接收人
	 */
	public static void sendImage(String subject, String content, FileDataSource fileDataSource, List<String> receivers) {
		AbstractEmail mail = new ImageEmail();
		
		List<FileDataSource> fileDataSources=new ArrayList<FileDataSource>();
		fileDataSources.add(fileDataSource);
		try {
			mail.send(subject, content, fileDataSources, receivers);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送正文带图片的(多张图片)
	 * @param subject 标题
	 * @param content 内容
	 * @param fileDataSources 多个图片
	 * @param receivers 接收人
	 */
	public static void sendImage(String subject, String content, List<FileDataSource> fileDataSources, List<String> receivers) {
		AbstractEmail mail = new ImageEmail();
		try {
			mail.send(subject, content, fileDataSources, receivers);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	

}
