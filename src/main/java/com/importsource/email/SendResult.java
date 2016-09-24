package com.importsource.email;

import java.util.List;


/**
 * 发送结果
 * @author Hezf
 */
public class SendResult {
	
	private List<String> success;
	private List<String> fail;

	/**
	 * 发送成功列表
	 * @return 发送成功列表
	 */
	public List<String> getSuccess() {
		return success;
	}

	public void setSuccess(List<String> success) {
		this.success = success;
	}

	/**
	 * 发送失败列表
	 * @return 发送失败列表
	 */
	public List<String> getFail() {
		return fail;
	}

	public void setFail(List<String> fail) {
		this.fail = fail;
	}

}
