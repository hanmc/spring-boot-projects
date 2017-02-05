package com.hmc.service;

import org.springframework.data.jpa.domain.Specification;

import com.hmc.base.BaseResultInfo;
import com.hmc.domain.BaseDomain;
import com.hmc.event.IBaseRequestEvent;

/**
 * 数据库更新操作组件的基类<br>
 * 默认通过请求参数{id}的值或者通过子类覆盖的getSpec()方法来查询数据库对象<br>
 * 若数据库对象存在则update，不存在则执行save<br>
 * update和save的数据库对象是通过 拷贝RequestEvent中的同名字段来获取的<br>
 * 子类可通过重写executing()方法自定义逻辑<br>
 *
 * @author hmc
 *
 * @param <I>
 * @param <D>
 */
public  class BaseDomainUpdateService<I extends IBaseRequestEvent , D extends BaseDomain> extends BaseService<I>{

	
	//TODO
	protected BaseResultInfo executing(IBaseRequestEvent request){
		BaseResultInfo response = BaseResultInfo.getBaseResult(true);
		if(request.getId()!=null){
		}
		
		return response;
		
	}
	
	/**
	 * 子类覆盖此方法用来构建查询条件 若没有覆盖，则默认查询全部
	 * @param request
	 * @return
	 */
	protected Specification<D> getSpec(IBaseRequestEvent request) {
		return null;
	}

}
