package com.importsource.email;

import java.util.List;

import javax.activation.FileDataSource;
import javax.mail.internet.AddressException;

import com.importsource.email.exc.SendEmailException;

/**
 * 发送接口
 * @author Hezf
 *
 */
public interface SendEmailable {
	/**
	 * 发送多人
	 * @param subject  主题（标题）
	 * @param content 内容
	 * @param fileDataSources 附件
	 * @param receiver 接收者
	 * @return 发送结果
	 * @throws SendEmailException 
	 */
	public SendResult send(String subject, String content, List<FileDataSource> fileDataSources, String receiver)
			throws SendEmailException ;
    /**
     * 发送单人
     * @param subject 主题（标题）
     * @param content 内容
     * @param fileDataSources 附件
     * @param receivers 接收者
     * @return 发送结果
     * @throws SendEmailException
     */
	public SendResult send(String subject, String content, List<FileDataSource> fileDataSources, List<String> receivers)
			throws SendEmailException;
	
	/**
	 * 设置抄送人
	 * @param cc 抄送人
	 * @throws AddressException
	 */
	public void setCC(List<String> cc) throws AddressException;
	
	/**
	 * 设置密送人
	 * @param cc 密送人
	 * @throws AddressException
	 */
	public void setBCC(List<String> bcc) throws AddressException;
	
}
