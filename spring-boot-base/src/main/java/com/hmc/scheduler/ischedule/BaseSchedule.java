package com.hmc.scheduler.ischedule;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.hmc.base.BaseConstants;
import com.hmc.base.BaseException;
import com.hmc.base.BaseLogger;
import com.hmc.domain.IScheduleDomain;
import com.hmc.event.enums.ScheduleType;
import com.hmc.repository.IScheduleRepository;
import com.hmc.utils.BaseDateUtils;

/**
 * 抽象的定时任务，所有的定时任务需要继承此类<p>
 * <pre>
 * 定时任务表达式最小单位支持到分钟，不支持秒
 * 表达式格式：
 *   1.固定时间：HHmm(如：16：00为每天下午4点触发)
 *   2.固定周期：分钟数(如：60为距上次执行开始时间每隔60分钟执行一次)
 * <pre>
 * @author hanmc
 * @date 2016-5-15
 */
public abstract class BaseSchedule{
	
	@Autowired
	protected IScheduleRepository repository;
	
	protected void scheduleRegist(IScheduleDomain domain){
		try{
			IScheduleDomain iScheduleDomain = repository.findByBeanName(domain.getBeanName());
			if(iScheduleDomain==null){
				BaseLogger.debug(this, "定时任务{}未注册，开始注册",domain.getScheduleName());
				repository.save(domain);
			}else{
				BaseLogger.debug(this, "定时任务{}已注册，开始更新",iScheduleDomain.getBeanName());
				repository.delete(iScheduleDomain);
				repository.save(domain);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	protected abstract void scheduleInit();
	
	protected abstract void excute();
	
	protected void start(IScheduleDomain domain){
		try{
			Date now = new Date();
			
			String scheduleName = domain.getScheduleName();
			
			timeCheck(domain, now);
			
			statusCheck(domain);
			
			BaseLogger.info(this, "定时任务:{}开始",scheduleName);
			
			domain.setIsRunning(true);
			domain.setStartTime(now);
			repository.save(domain);
			excute();
			domain.setIsRunning(false);
			domain.setEndTime(new Date());
			repository.save(domain);
			
			BaseLogger.info(this, "定时任务:{}结束",scheduleName);
			BaseLogger.info(this, BaseConstants.LOG_FLAG_END.getDesc());
			
		}catch(Exception e){
			if(e instanceof BaseException){
			}else{
				BaseLogger.error(this,"定时任务执行系统异常：" + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	

	/**
	 * 检查定时任务到达执行时间
	 * @param domain
	 * @param now
	 * @throws BaseException
	 */
	private void timeCheck(IScheduleDomain domain, Date now) throws BaseException {
		
		String scheduleTime = domain.getScheduleTime();
		ScheduleType type = domain.getScheduleType();
		String name = domain.getScheduleName();
		Date startTime = domain.getStartTime();
		
		if(type.compareTo(ScheduleType.FIX)==0){
			String nowStr = BaseDateUtils.getString(now, "HHmm");
			if(!nowStr.equals(scheduleTime)){
				BaseLogger.debug(this, "定时任务：{}未到达执行时间，跳过。现在时间：{},执行时间：{}",name,nowStr,scheduleTime);
				throw new BaseException();
			}
		}else if(type.compareTo(ScheduleType.CYCLE)==0){
			long mills = startTime.getTime();
			long nowMills = now.getTime();
			
			long secendsDiff = (nowMills-mills)/(1000*60);
			if(secendsDiff==0){
				//不足一分钟
				throw new BaseException();
			}
			
			if(secendsDiff % (Integer.parseInt(scheduleTime))!=0){
				BaseLogger.debug(this, "现在时间：{},上次执行时间：{},定时任务执行周期：{}分钟",now,startTime,scheduleTime);
				BaseLogger.debug(this, "定时任务：{}未到达执行时间，跳过。", name);
				throw new BaseException();
			}
		}else{
			BaseLogger.warn(this,"定时任务:{}任务类型[{}]不正确，已跳过",name,type.getDesc());
			throw new BaseException();
		}
	}

	/**
	 * 检查定时任务是否处于可执行状态
	 * @param domain
	 * @throws BaseException
	 */
	private void statusCheck(IScheduleDomain domain) throws BaseException {
		Boolean isEnable = domain.getIsEnable();
		Boolean isRunning = domain.getIsRunning();
		
		if(!isEnable){
			BaseLogger.info(this, "定时任务:{}状态为不可用，已跳过",domain.getBeanName());
			throw new BaseException();
		}
		
		if(isRunning){
			BaseLogger.info(this, "定时任务:{}正在运行，已跳过",domain.getBeanName());
			throw new BaseException();
		}
	}
	
}
