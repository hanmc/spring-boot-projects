package com.hmc.scheduler.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DemoTask {

	@Scheduled(cron="0 * * * * ?")
	public void taskDemo(){
		System.out.println("taskDemo running.......");
	}
}
