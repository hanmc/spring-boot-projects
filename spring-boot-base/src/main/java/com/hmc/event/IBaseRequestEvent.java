package com.hmc.event;

import java.util.Date;

import org.springframework.data.domain.Sort;


/**
 * 请求
 * @author hmc
 *
 */
public interface IBaseRequestEvent extends IBaseEvent{
	
	Date getRequestTime();
	
	Long getId();
	
	Sort getSort();
}
