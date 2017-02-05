package com.hmc.test;

import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import com.hmc.base.BaseConstants;

public class CaheTest extends DemoApplicationTests{
	
	//@Autowired
	//private RedisTemplate<String,Object> redisTemplate;
	
	@Autowired
	private CacheManager cacheManager;
	
	/*@Test
	public void redisTemplateTest(){
		ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
		opsForValue.set("aa", "bb");
		
		ValueOperations<String, Object> opsForValue2 = redisTemplate.opsForValue();
		Object object = opsForValue2.get("aa");
		System.out.println(object.toString());
		System.out.println(BaseConstants.LOG_FLAG_END.getDesc());
	}*/
	
	@Test
	public void cacheManagertest(){
		Collection<String> cacheNames = cacheManager.getCacheNames();
		for (String cacheName : cacheNames) {
			System.out.println("cacheName--------------->" + cacheName);
			Cache cache = cacheManager.getCache(cacheName);
			cache.put(cacheName+"key", cacheName+"value");
			System.out.println(cache.get(cacheName + "key", String.class));
		}
		System.out.println(BaseConstants.LOG_FLAG_END.getDesc());

	}

}
