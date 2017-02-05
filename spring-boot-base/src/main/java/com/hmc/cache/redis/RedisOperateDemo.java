package com.hmc.cache.redis;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.hmc.base.BaseConstants;

public class RedisOperateDemo {
	
	@Autowired
	private RedisTemplate<String,Object> redisTemplate;
	
	@Autowired
	private CacheManager cacheManager;
	
	@Test
	public void redisTemplateTest(){
		ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
		opsForValue.set("aa", "bb");
		
		ValueOperations<String, Object> opsForValue2 = redisTemplate.opsForValue();
		Object object = opsForValue2.get("aa");
		System.out.println(object.toString());
		System.out.println(BaseConstants.LOG_FLAG_END.getDesc());
	}
	
	@Test
	public void cacheManagerTest(){
		Cache cache1 = cacheManager.getCache("cache1");
		Cache cache2 = cacheManager.getCache("cache2");
		cache1.put("key", "value");
		System.out.println(cache1.getName() + "--------------->" + cache1.get("key", String.class));
		System.out.println(cache2.getName() + "--------------->" + cache2.get("key", String.class));
		System.out.println(BaseConstants.LOG_FLAG_END.getDesc());

	}
	
	
}
