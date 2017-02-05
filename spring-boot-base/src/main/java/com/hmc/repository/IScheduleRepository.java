package com.hmc.repository;

import org.springframework.stereotype.Repository;

import com.hmc.domain.IScheduleDomain;

@Repository
public interface IScheduleRepository extends IBaseJpaRepository<IScheduleDomain>{

	IScheduleDomain findByBeanName(String beanName);

	
	
}
