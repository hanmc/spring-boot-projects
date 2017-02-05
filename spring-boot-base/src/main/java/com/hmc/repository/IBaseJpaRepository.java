package com.hmc.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.hmc.domain.BaseDomain;

/**
 * Repository顶级接口 <br>
 * 
 * 继承JpaSpecificationExecutor接口，支持JPA2.0的Criteria查询
 * 
 * @author hmc
 *
 * @param <T> Entity
 */
@NoRepositoryBean
public interface IBaseJpaRepository<T extends BaseDomain> extends JpaRepository<T, Serializable>,JpaSpecificationExecutor<T>{
}
