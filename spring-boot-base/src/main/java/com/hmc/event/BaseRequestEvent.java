package com.hmc.event;

import java.util.Date;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

/**
 * 请求数据基类<br>
 * 要求所有请求类全部继承此类，并且类命名和请求url对应<br>
 * 如请求url为：localhsot:8080/hmc/service/tamplateService<br>
 * 则此请求所对应的组件的请求数据类名字应为TamplateServiceRequestEvent<br>
 * 
 * @author hmc
 *
 */
public class BaseRequestEvent implements IBaseRequestEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 数据库主键id
	 */
	private Long id;

	/**
	 * 请求时间
	 */
	private Date requestTime = new Date();
	
	private Direction direction = Direction.DESC;
	
	private String[] sortProperties = {"createTime"};
	
	@SuppressWarnings("unused")
	private Sort sort;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public String[] getSortProperties() {
		return sortProperties;
	}

	public void setSortProperties(String[] sortProperties) {
		this.sortProperties = sortProperties;
	}

	public Sort getSort() {
		return new Sort(direction,sortProperties);
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}
	
}
