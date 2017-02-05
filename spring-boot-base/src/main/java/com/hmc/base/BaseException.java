package com.hmc.base;


/**
 * 自定义Exception<p>
 * 当业务逻辑异常时手动抛出<br>
 * 主要用于区分业务逻辑异常和系统异常
 * @author hanmc
 *
 */
public class BaseException extends Exception{

	private static final long serialVersionUID = -979947296887977905L;
	
	private String code;
	
	private String message;

	public BaseException() {
		super();
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
		dealMessage();
	}

	public BaseException(String code,String message) {
		super(message);
		this.code = code;
		this.message = message;
		dealMessage();
	}
	
	public BaseException(String message) {
		super(message);
		this.message = message;
		dealMessage();
	}

	public BaseException(Throwable cause) {
		super(cause);
	}
	
	private void dealMessage() {
		if(message==null || "".equals(message.trim())){
			message = "系统异常";
		}
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
