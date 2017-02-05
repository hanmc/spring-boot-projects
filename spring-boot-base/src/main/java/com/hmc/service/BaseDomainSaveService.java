package com.hmc.service;

import com.hmc.base.BaseException;
import com.hmc.base.BaseResultInfo;
import com.hmc.domain.BaseDomain;
import com.hmc.event.IBaseRequestEvent;

/**
 * 数据库保存操作组件的基类<p>
 * 默认尝试自动转换请求对象到数据库实体对象
 * 若请求对象和数据库实体对象字段不一致或者字段类型尝试转换失败，则抛出异常<br>
 * 子类可通过重写executing()方法自定义逻辑
 * 
 * @author hanmc
 *
 * @param <I>
 * @param <D>
 */
public class BaseDomainSaveService<I extends IBaseRequestEvent, D extends BaseDomain> extends BaseService<I>{

	@Override
	protected BaseResultInfo executing(I request) throws BaseException {
		
		
		
		return null;
	}

}
