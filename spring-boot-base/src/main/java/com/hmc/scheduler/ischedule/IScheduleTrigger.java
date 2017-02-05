package com.hmc.scheduler.ischedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hmc.base.BaseLogger;
import com.hmc.domain.IScheduleDomain;
import com.hmc.repository.IScheduleRepository;

/**
 * 定时任务触发器<p>
 * 每分钟去检查是否有满足条件的定时任务，有则执行，没有则跳过
 *
 * @author hanmc
 * @date 2016-5-16
 */
@Component
public class IScheduleTrigger extends BaseLogger{ 
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private IScheduleRepository iScheduleRepository;
	
	@Value("${base.schedule.host:true}")
	private Boolean scheduleHost;
	
	@Scheduled(cron="0 * * * * ?")
	public void scheduleStart(){
		if(!scheduleHost){
			BaseLogger.debug(this,"不是定时任务执行的主机，已跳过");
			return;
		}
		
		List<IScheduleDomain> schedules = iScheduleRepository.findAll();
		for (IScheduleDomain domain : schedules) {
			BaseLogger.debug(this,"定时任务：{}触发",domain.getScheduleName());
			asyncScheduleDeal(domain);
		}
	}
	
	@Async
	private void asyncScheduleDeal(IScheduleDomain domain) {
		String beanName = domain.getBeanName();
		BaseSchedule schedule = applicationContext.getBean(beanName,BaseSchedule.class);
		schedule.start(domain);
	}

}
