package com.hmc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import com.hmc.base.BaseResultInfo;
import com.hmc.component.BeanOperateComponent;
import com.hmc.domain.BaseDomain;
import com.hmc.event.IBaseRequestEvent;
import com.hmc.repository.IBaseJpaRepository;

/**
 * 数据库删除操作组件的基类<br>
 * 存在事务控制<br>
 * 默认通过请求参数中的id来进行单条数据删除<br>
 * 如果请求参数中id为null  则通过获得子类重写的getSpec()方法来进行条件查询并删除<br>
 * 子类可通过重写executing()方法自定义逻辑
 * @author hmc
 *
 * @param <I>
 * @param <D>
 */
public class BaseDomainDeleteService<I extends IBaseRequestEvent, D extends BaseDomain> extends BaseService<I>{
	
	@Autowired
	private BeanOperateComponent beanOperateComponent;
	
	@Override
	protected BaseResultInfo executing(IBaseRequestEvent request) {
		
		IBaseJpaRepository<D> domainRepository =beanOperateComponent.getRepository(getClass());
		
		BaseResultInfo response = BaseResultInfo.getBaseResult(true);
		
		if(request.getId()!=null){
			domainRepository.delete(request.getId());
		}else{
			Specification<D> spec = getSpec(request);
			if(spec!=null){
				List<D> list = domainRepository.findAll(spec);
				for (D d : list) {
					domainRepository.delete(d);
				} 
			}else{
				response = BaseResultInfo.getBaseResult(false,"系统异常，请重写getSpec()或者加入{id}请求参数");
				return response;
			}
		}
		
		return response;
	}

	protected Specification<D> getSpec(IBaseRequestEvent request) {
		return null;
	}

}
