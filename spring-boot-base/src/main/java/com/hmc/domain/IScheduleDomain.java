package com.hmc.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.hmc.event.enums.ScheduleType;

@Entity
public class IScheduleDomain extends BaseDomain{

	private String scheduleName;
	
	private String scheduleTime;
	
	private Date startTime = new Date();
	
	private Date endTime = new Date();
	
	private Boolean isRunning = false;
	
	private Boolean isEnable = true;

	private ScheduleType scheduleType;

	private String beanName;

	public String getScheduleName() {
		return scheduleName;
	}

	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public String getScheduleTime() {
		return scheduleTime;
	}

	public void setScheduleTime(String scheduleTime) {
		this.scheduleTime = scheduleTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Boolean getIsRunning() {
		return isRunning;
	}

	public void setIsRunning(Boolean isRunning) {
		this.isRunning = isRunning;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public ScheduleType getScheduleType() {
		return scheduleType;
	}

	public void setScheduleType(ScheduleType scheduleType) {
		this.scheduleType = scheduleType;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	//HMC
	@Override
	public String toString() {
		return "IScheduleDomain [scheduleName=" + scheduleName + ", scheduleTime=" + scheduleTime
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", isRunning=" + isRunning
				+ ", isEnable=" + isEnable + ", scheduleType=" + scheduleType + "]";
	}
}
