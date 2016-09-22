/**
 * 
 */
package com.importsource.email;

import java.util.List;


/**
 * 发送结果
 * @author Hezf
 */
public class SendResult {
	private List<String> success;
	private List<String> fail;

	public List<String> getSuccess() {
		return success;
	}

	public void setSuccess(List<String> success) {
		this.success = success;
	}

	public List<String> getFail() {
		return fail;
	}

	public void setFail(List<String> fail) {
		this.fail = fail;
	}

}
