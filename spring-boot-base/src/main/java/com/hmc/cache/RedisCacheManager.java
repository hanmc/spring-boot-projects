package com.hmc.cache;

import java.util.Collection;
import java.util.Collections;

import org.springframework.cache.Cache;
import org.springframework.data.redis.core.RedisOperations;

public class RedisCacheManager extends org.springframework.data.redis.cache.RedisCacheManager{

	@SuppressWarnings("rawtypes")
	public RedisCacheManager(RedisOperations redisOperations) {
		this(redisOperations, Collections.<String> emptyList());
	}

	@SuppressWarnings("rawtypes")
	public RedisCacheManager(RedisOperations redisOperations, Collection<String> cacheNames) {
		super(redisOperations, cacheNames);
	}
	
	@Override
	public Cache getCache(String name) {
		return super.getCache(name);
	}
	
}
