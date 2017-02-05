package com.hmc.service;

import com.hmc.base.BaseResultInfo;
import com.hmc.event.IBaseRequestEvent;
/**
 * 业务service定级接口<br>
 * 
 * @author hmc
 *
 * @param <I>
 * @param <O>
 */
public interface IBaseService {
	
	/**
	 * service调用入口
	 * @param request
	 * @return
	 */
	BaseResultInfo excute(IBaseRequestEvent request);
	
	
}
