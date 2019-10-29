package com.my.xunwu.service;
/**
 * 服务接口通用结构
 * @author hcz
 * @param <T>
 * @date 2019.10.29
 */
public class ServiceResult<T> {
	private boolean success;
	private String message;
	private T result;
	public ServiceResult(boolean success) {
		super();
		this.success = success;
	}
	
	public ServiceResult(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}

	public ServiceResult(boolean success, String message, T result) {
		super();
		this.success = success;
		this.message = message;
		this.result = result;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
	
}
