package com.hmc.scheduler.ischedule;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.hmc.base.BaseLogger;
import com.hmc.domain.IScheduleDomain;
import com.hmc.event.enums.ScheduleType;

@Component("iScheduleDemo")
public class IScheduleDemo extends BaseSchedule{

	@PostConstruct
	@Override
	protected void scheduleInit() {
		IScheduleDomain domain = new IScheduleDomain();
		domain.setBeanName("iScheduleDemo");
		domain.setScheduleName("定时任务测试");
		domain.setScheduleTime("2");
		domain.setScheduleType(ScheduleType.CYCLE);
		scheduleRegist(domain);
	}

	@Override
	protected void excute() {
		BaseLogger.info(this,"定时任务执行了了了了了了了了了了");
	}

}
