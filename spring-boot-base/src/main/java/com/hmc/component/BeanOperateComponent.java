package com.hmc.component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.hmc.domain.BaseDomain;
import com.hmc.repository.IBaseJpaRepository;
import com.hmc.service.IBaseService;

/**
 * Bean查找操作组件<p>
 * @author hanmc
 *
 */
@Component
public class BeanOperateComponent {
	
	
	@Autowired
	private ApplicationContext applicationContext;
	
	public IBaseService getService(String serviceName){
		
		String serviceBeanName = serviceName + "Service";
		
		IBaseService bean = (IBaseService) applicationContext.getBean(serviceBeanName);
		
		return bean;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <D extends BaseDomain> IBaseJpaRepository<D> getRepository(Class clazz){
		Class<D> domainClass = getDomainClass(clazz);
		
		String domainName = StringUtils.uncapitalize(domainClass.getSimpleName());
		return (IBaseJpaRepository<D>) applicationContext.getBean(domainName+"Repository");
		
	}
	

	/**
	 * 获取指定Type的泛型参数的类型
	 * @param parameterIndex
	 * @param genType
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static final Class getDomainClass(Class clazz) {
		Type genType = clazz.getGenericSuperclass();
		int parameterIndex = 1;
		
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (parameterIndex >= params.length || parameterIndex < 0) {
			throw new RuntimeException("Index outof bounds");
		}
		if (!(params[parameterIndex] instanceof Class)) {
			return Object.class;
		}
		return (Class) params[parameterIndex];
	}
}
