package com.hmc.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 事务切面  所有符合切入点规则的方法都会加入事务控制
 * @author hmc
 *
 */

@Aspect
@Component
public class TransacationAspect {
	
	@Autowired
	private PlatformTransactionManager transacationManager;
	
	//定义事务切入点
	@Pointcut(value="execution(* com.hmc.service.IBaseService.excute(..) )")
	public void transacationPoint(){}
	
	
	private Logger ilog = LoggerFactory.getLogger(getClass());
	
	
	@Around(value="transacationPoint()")
	public Object process(final ProceedingJoinPoint pjp) throws Throwable{
		long startTime = System.currentTimeMillis();
		String serviceClassName = pjp.getTarget().getClass().getName();
		ilog.info("调用" + serviceClassName + "服务开始");
		TransactionStatus transaction = transacationManager.getTransaction(new DefaultTransactionDefinition());
		
		Object obj = new Object();
		try{
			obj = pjp.proceed();
		}catch(Exception e){
			ilog.error("程序异常");
			e.printStackTrace();
			transacationManager.rollback(transaction);
			ilog.error("事务回滚");
			return obj;
		}
		
		transacationManager.commit(transaction);
		
		long time = System.currentTimeMillis() - startTime;

		ilog.info("调用{}服务结束,耗时{}毫秒", serviceClassName, time);
		
		return obj;
		
	}
	
}
