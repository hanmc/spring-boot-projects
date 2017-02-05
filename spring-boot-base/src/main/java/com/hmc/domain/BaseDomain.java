package com.hmc.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 数据库实体基类 <br>
 * 可自动生成数据库主键id和创建时间
 * @author hmc
 *
 */
@MappedSuperclass
public class BaseDomain implements IBaseDomain{
	
	/**
	 * 数据库主键
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="id_strategy")
	@TableGenerator(name = "id_strategy",
					initialValue=10000,
					allocationSize=1,
					pkColumnName="domain_id",
					pkColumnValue="id_value_now",
					table="hmc_id_generator",
					valueColumnName="id_value_now")
	private Long id;
	
	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime = new Date();
	
	/**
	 * 最后更新时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	
}
