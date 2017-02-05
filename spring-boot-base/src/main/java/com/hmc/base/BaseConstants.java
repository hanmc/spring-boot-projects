package com.hmc.base;

import org.apache.commons.lang3.StringUtils;

/**
 * 系统常量<p>
 * @author hanmc
 *
 */
public enum BaseConstants {

	/**
	 * UTF-8
	 */
	UTF_8("UTF-8","UTF-8"),

	/**
	 * GBK
	 */
	GBK("UTF-8","UTF-8"),
	
	/**
	 * 系统正常
	 */
	SYS_SUCCESS("0000","success"),
	
	/**
	 * 系统错误
	 */
	SYS_ERROR("9999","error"),
	
	/**
	 * 通用错误（没有指定错误码和错误信息）
	 */
	COMMON_ERROR("9001","fail"),
	
	/**
	 * 日志标记
	 */
	LOG_FLAG_END("LOG_FLAG_END",StringUtils.leftPad("", 100, "=")),
	
	ITEMS("items","items")
	;
	
	private String code;
	
	private String desc;
	
	private BaseConstants(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	
	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}



	public String getDesc() {
		return desc;
	}



	public void setDesc(String desc) {
		this.desc = desc;
	}

}
