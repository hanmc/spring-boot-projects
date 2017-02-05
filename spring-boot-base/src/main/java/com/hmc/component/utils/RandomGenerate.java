package com.hmc.component.utils;

import java.util.Date;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.hmc.utils.BaseDateUtils;

/**
 * 随机数生成器<p>
 *
 * @author hanmc
 * @date 2016-4-26
 */
@Component
public class RandomGenerate {
	
	private int sufIntValue = 0;
	
	private static final int DEFAULT_LENGTH = 20;
	
	private static final String DEFAULT_SEQUENCE = "default_sequence";

	
	private static final String FORMAT_DATE = "yyyyMMdd";
	private static final String FORMAT_DATETIME = "yyyyMMddHHmmss";
	
	private String preDate = BaseDateUtils.getString(new Date(), FORMAT_DATE);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 获取默认的序列号
	 * @param count
	 * @return
	 */
	public String randomSequence(){
		return randomSequence(DEFAULT_LENGTH);
	}
	
	/**
	 * 获取序列号
	 * @param count
	 * @return
	 */
	public String randomSequence(int count){
		someInit();
		
		Assert.isTrue(count>=16, "count must not be less than 16");
		
		String dateTimePre = dateTimePre();
		
		String randomMid = randomMid();
		
		String intSuf = intSuf(count, dateTimePre, randomMid, nextSequenceValue(DEFAULT_SEQUENCE));
		
		return dateTimePre + randomMid + intSuf;
	}
	
	private String nextSequenceValue(String defaultSequence) {
		String sql = "select " + defaultSequence + ".nextval from dual" ;
		return jdbcTemplate.queryForObject(sql, String.class);
	}

	/**
	 * 获取默认位数的随机数（20位）
	 * <pre>
	 * 随机数随着日期变化而变化
	 * 格式为：12位时间 + 2位随机数 + 从0开始的序列（每天重置）
	 * <pre>
	 * 
	 * @return
	 */
	public String randomDay(){
		return randomDay(DEFAULT_LENGTH);
	}
	
	
	/**
	 * 获取指定位数的随机数(需大于16位)
	 * <pre>
	 * 随机数随着日期变化而变化
	 * 格式为：12位时间 + 2位随机数 + 从0开始的序列（每天重置）
	 * <pre>
	 * 
	 * @param count
	 * @return
	 */
	public String randomDay(int count){
		someInit();
		
		Assert.isTrue(count>=16, "count must not be less than 16");
		
		String dateTimePre = dateTimePre();
		String randomMid = randomMid();
		sufIntValue ++; 
		String intSuf = intSuf(count, dateTimePre, randomMid, String.valueOf(sufIntValue));
		
		return dateTimePre + randomMid + intSuf;
	}


	private String intSuf(int count, String dateTimePre, String randomMid, String intSuf) {
		int intSufSize =  count - dateTimePre.length() - randomMid.length();
		return StringUtils.leftPad(intSuf, intSufSize, "0");
	}
	


	private String randomMid() {
		return String.valueOf(RandomUtils.nextInt(100));
	}


	private String dateTimePre() {
		return BaseDateUtils.getString(FORMAT_DATETIME);
	}
	
	/**
	 * 检查是否为新的一天，如果是，则重置所有数据
	 */
	private void someInit() {
		String nowDate = BaseDateUtils.getString(FORMAT_DATE);
		if(!StringUtils.equals(nowDate, preDate)){
			//新的一天 重置所有值
			sufIntValue = 0;
			preDate =  nowDate;
		}		
	}

	public String randomStringAndNum(int length){
		return RandomStringUtils.randomAlphanumeric(length);
	}
	
	public String randomStringAndNum(){
		return randomStringAndNum(DEFAULT_LENGTH);
	}
	
	public String randomString(int length){
		return RandomStringUtils.randomAlphabetic(length);
	}
	
	public String randomString(){
		return randomString(DEFAULT_LENGTH);
	}
	
}
