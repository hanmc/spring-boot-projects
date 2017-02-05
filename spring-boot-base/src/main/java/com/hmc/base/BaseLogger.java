package com.hmc.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具类<p>
 * 使用固定name的Logger，简化输出格式及日志配置文件
 *
 * @author hanmc
 * @date 2016-5-16
 */
public class BaseLogger {

	private final static Logger ilog = LoggerFactory.getLogger(BaseLogger.class.getSimpleName());
	
	private final static String FLAG = " - ";
	
	public static void debug(Object clazz, String msg){
		ilog.debug(clazz.getClass().getSimpleName() + FLAG + msg);
	}
	
	public static void debug(Object clazz, String format, Object... arguments){
		ilog.debug(clazz.getClass().getSimpleName() + FLAG + format, arguments);
	}
	
	public static void info(Object clazz, String msg){
		ilog.info(clazz.getClass().getSimpleName() + FLAG + msg);
	}
	
	public static void info(Object clazz, String format, Object... arguments){
		ilog.info(clazz.getClass().getSimpleName() + FLAG + format, arguments);
	}
	
	public static void warn(Object clazz, String msg){
		ilog.warn(clazz.getClass().getSimpleName() + FLAG + msg);
	}
	
	public static void warn(Object clazz, String format, Object... arguments){
		ilog.warn(clazz.getClass().getSimpleName() + FLAG + format, arguments);
	}
	
	public static void error(Object clazz, String msg){
		ilog.error(clazz.getClass().getSimpleName() + FLAG + msg);
	}
	
	public static void error(Object clazz, String format, Object... arguments){
		ilog.error(clazz.getClass().getSimpleName() + FLAG + format, arguments);
	}
	
}
