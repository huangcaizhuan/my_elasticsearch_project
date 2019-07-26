package com.my.xunwu.base;

/**
 * api格式封装
 * @author hcz
 *	201.7.26
 */
public class ApiResponse {
	/**
	 * 返回代码
	 */
	private int code;
	/**
	 * 信息
	 */
	private String message;
	/**
	 * 返回的数据
	 */
	private Object data;
	/**
	 * 其他
	 */
	private Boolean more;
	
	public ApiResponse(int code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	public ApiResponse() {
		this.code = Status.SUCCESS.getCode();
		this.message = Status.SUCCESS.getStandardMessage();
	}

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Boolean getMore() {
		return more;
	}
	public void setMore(Boolean more) {
		this.more = more;
	}
	/**
	 * 返回状态码和消息
	 * @param code
	 * @param message
	 * @return
	 */
	public static ApiResponse ofMessage(int code ,String message) {
		return new ApiResponse(code,message,null);
	}
	/**
	 * 返回成功消息
	 * @param data
	 * @return
	 */
	public static ApiResponse ofSuccess(Object data) {
		return new ApiResponse(Status.SUCCESS.getCode(),Status.SUCCESS.getStandardMessage(),data);
	}
	/**
	 * 按状态返回
	 * @param status
	 * @return
	 */
	public static ApiResponse ofStatus(Status status) {
		return new ApiResponse(status.code,status.standardMessage,null);
	}
	
	/**
	 * 状态码枚举类型
	 * @author hcz
	 *
	 */
	public enum Status{
		SUCCESS(200,"OK"),
		BAD_REQUEST(400,"Bad Request"),
		NOT_FOUND(404,"Not Found"),
		INTERNAL_SERVICE_ERROR(500,"Unknown Internal Error"),
		NOT_VALID_PARAMS(40005,"Not Valid Params"),
		NOT_SUPPORTED_OPERATION(40006,"Operation Not Supported"),
		NOT_LOGIN(5000,"Not Login");
		
		private int code;
		private String standardMessage;
		
		Status(int code, String standardMessage) {
			this.code = code;
			this.standardMessage = standardMessage;
		}
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public String getStandardMessage() {
			return standardMessage;
		}
		public void setStandardMessage(String standardMessage) {
			this.standardMessage = standardMessage;
		}
	}
}
