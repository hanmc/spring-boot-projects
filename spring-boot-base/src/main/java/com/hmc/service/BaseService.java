package com.hmc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hmc.base.BaseConstants;
import com.hmc.base.BaseException;
import com.hmc.base.BaseResultInfo;
import com.hmc.event.IBaseRequestEvent;

/**
 * 抽象的service 实现了基本响应的获取
 * @author hmc
 *
 * @param <I>
 */
public abstract class BaseService<I extends IBaseRequestEvent> implements IBaseService {

	//HMC
	protected Logger ilog = LoggerFactory.getLogger(getClass().getSimpleName());
	
	@SuppressWarnings("unchecked")
	@Override
	public BaseResultInfo excute(IBaseRequestEvent request) {
		BaseResultInfo response = BaseResultInfo.getBaseResult(false);
		try{
			response =  executing((I)request);
		}catch(Exception e){
			if(e instanceof BaseException){
				ilog.warn("服务被终止：" +e.getMessage());
				response.setReturnCode(BaseConstants.COMMON_ERROR.getCode());
				response.setReturnMsg(e.getMessage());
			}else{
				ilog.error("系统错误:" + e.getMessage());
				response.setReturnMsg("系统错误，请稍后重试");
				e.printStackTrace();
			}
		}
		ilog.info("服务处理完成：" + response.toString());
		return response;
		
	}
	
	/**
	 * 子类具体逻辑实现入口
	 * @param request
	 * @return
	 * @throws BaseException 
	 * @throws Exception 
	 */
	protected abstract BaseResultInfo executing(I request) throws Exception;
	
	
	
}
