package com.hmc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期工具类<p>
 * @author hanmc
 *
 */
public class BaseDateUtils {
	
	private static Logger ilog = LoggerFactory.getLogger(BaseDateUtils.class.getSimpleName());
	
	
	/**
	 * 获取默认的当前日期字符串
	 * 默认格式为：yyyy-MM-dd hh:mm:s
	 * @return
	 */
	public static String getString(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return dateFormat.format(new Date());
	}
	
	/**
	 * 获取指定格式的的当前日期的字符串
	 * 
	 * @return
	 */
	public static String getString(String format){
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(new Date());
	}
	
	/**
	 * 根据传入的指定格式和日期 返回字符串日期
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getString(Date date,String format){
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	/**
	 *  根据传入字符串日期和字符串对应的格式,返回Date类型日期
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date getDate(String date,String format){
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date  date2 = null;
		try {
			 date2 = dateFormat.parse(date);
		} catch (ParseException e) {
			ilog.info("日期转换异常：" + e.getMessage());
		}
		return date2;
	}
	
	/**
	 * 字符串日期的格式的转化方法
	 * 根据传入的字符串格式原日期和原日期的格式  返回新的字符串格式日期
	 * @param date 原字符串日期
	 * @param sourceFormat 原字符串日期格式
	 * @param targetFormat 要转换的字符串日期格式
	 * @return 要转换的字符串日期
	 */
	public static String getString(String date,String sourceFormat,String targetFormat){
		
		return getString(getDate(date, sourceFormat), targetFormat);
	}
	
	
	/**
	 * 将当前date的时间置为当天的结束，即23:59:59
	 * @param date
	 * @return
	 */
	public static Date setDateEnd(Date date){
		Calendar c = Calendar.getInstance(); 
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY,23);
		c.set(Calendar.MINUTE,59);
		c.set(Calendar.SECOND,59);
		return c.getTime();
	}
	
	/**
	 * 将当前date的时间置为当天的开始，即00:00:00
	 * @param date
	 * @return
	 */
	public static Date setDateBegin(Date date){
		Calendar c = Calendar.getInstance(); 
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND,0);
		return c.getTime();
	}

}
