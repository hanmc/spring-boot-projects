package com.hmc.base;

import java.io.Serializable;

/**
 * 基本响应信息<p>
 * <pre>
 * 包括处理结果、响应码、响应信息、返回数据
 * 四个字段不是必须的,可自由组合
 * 对于只需要处理结果的服务，只需要设置result字段,系统将返回默认统一的标准响应
 * 对于有复杂业务逻辑的服务，需要分情况处理的可分别设置returnCode和returnMsg，以便区分处理
 * 对于需要返回简单数据的情况，可使用obj字段
 * <pre>
 * @author hanmc
 *
 */
public class BaseResultInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 处理结果
	 */
	private Boolean result;
	
	/**
	 *响应码
	 */
	private String returnCode;
	
	/**
	 * 响应信息
	 */
	private String returnMsg;
	
	/**
	 * 返回数据
	 */
	private Object data;

	public BaseResultInfo() {
		super();
	}

	private BaseResultInfo(Boolean result, String returnCode, String returnMsg) {
		super();
		this.result = result;
		this.returnCode = returnCode;
		this.returnMsg = returnMsg;
	}

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}


	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}


	/**
	 * 返回默认响应结果，当需要返回错误信息时，不建议使用此方法
	 * @param result
	 * @return
	 */
	public static BaseResultInfo getBaseResult(Boolean result){
		if(result){
			return getBaseResult(result,BaseConstants.SYS_SUCCESS.getCode(),BaseConstants.SYS_SUCCESS.getDesc());
		}else{
			return getBaseResult(result,BaseConstants.SYS_ERROR.getCode(),BaseConstants.SYS_ERROR.getDesc());
		}
	}
	
	/**
	 * 当需要返回错误信息时，建议使用此方法（返回标准的统一响应码）
	 * @param result
	 * @param msg
	 * @return
	 */
	public static BaseResultInfo getBaseResult(Boolean result, String returnMsg){
		String code = result==true?BaseConstants.SYS_SUCCESS.getCode():BaseConstants.SYS_ERROR.getCode();
		return getBaseResult(result,code,returnMsg);
	}

	/**
	 * 当需要返回错误信息时，建议使用此方法（返回具体响应码）
	 * @param code
	 * @param msg
	 * @return
	 */
	public static BaseResultInfo getBaseResult(String returnCode, String returnMsg){
		Boolean result = BaseConstants.SYS_SUCCESS.getCode().equals(returnCode)?true:false;
		return getBaseResult(result,returnCode,returnMsg);
	}
	
	public static BaseResultInfo getBaseResult(Boolean result, String returnCode, String returnMsg){
		return new BaseResultInfo(result,returnCode,returnMsg);
	}
	

	@Override
	public String toString() {
		return "BaseResultInfo [result=" + result + ", returnCode=" + returnCode + ", returnMsg="
				+ returnMsg + ", data=" + data + "]";
	}

}
