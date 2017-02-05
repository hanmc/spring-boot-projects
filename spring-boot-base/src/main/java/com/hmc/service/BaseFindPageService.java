package com.hmc.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hmc.base.BaseConstants;
import com.hmc.base.BaseResultInfo;
import com.hmc.component.BeanOperateComponent;
import com.hmc.domain.BaseDomain;
import com.hmc.event.BaseFindPageRequestEvent;
import com.hmc.repository.IBaseJpaRepository;

/**
 * 分页查询Service基类
 * @author hmc
 *
 * @param <D>
 */
public class BaseFindPageService<I extends BaseFindPageRequestEvent,D extends BaseDomain> extends BaseDomainSearchService<I,D> {

	@Autowired
	private BeanOperateComponent beanOperateComponent;
	
	private Logger ilog = LoggerFactory.getLogger(getClass().getSimpleName());
	
	@Override
	public BaseResultInfo executing(I request) throws Exception {
		IBaseJpaRepository<D> domainRepository = beanOperateComponent.getRepository(getClass());
		
		BaseResultInfo response = BaseResultInfo.getBaseResult(true);
		Map<String, Object> content = new HashMap<String, Object>();
		
		Page<D> page;
		
		if(getSpec(request)!=null){
			page = domainRepository.findAll(getSpec(request), getPageable(request));
		}else{
			page = domainRepository.findAll(getPageable(request));
		}
		
		content.put("totalElement",page.getTotalElements());
		content.put("totalPage",page.getTotalPages());
		content.put(BaseConstants.ITEMS.getCode(), transformer(page.getContent()));
		
		ilog.info("分页查询记录总数：" + page.getTotalElements());
		ilog.info("分页查询页码总数：" + page.getTotalPages());
		ilog.info("分页查询当前页记录数：" + page.getContent().size());
		
		
		response.setData(content);
		
		return response;
	}

	private Pageable getPageable(BaseFindPageRequestEvent request) {
		
		return request.getPageable();
	}
}
