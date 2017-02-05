package com.hmc.cache.redis;

import java.util.HashSet;
import java.util.Set;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis配置类<p>
 * 如果没有定义对应的属性，则使用默认配置
 * 
 * @author hanmc
 * @date 2016-3-27
 * 
 */
@Configuration
@EnableCaching
public class RedisConfiguration {

	@Bean
	public JedisPoolConfig poolConfig() {
		JedisPoolConfig config = new JedisPoolConfig();
		return config;
	}

	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setPoolConfig(poolConfig());
		return factory;
	}

	// 指定key序列使用String的RedisTemplate
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public RedisTemplate redisTemplate() {
		RedisTemplate redisTemplate = new RedisTemplate();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		return redisTemplate;
	}

	// 使用默认序列（JdkSerializationRedisSerializer）的RedisTemplate
	@SuppressWarnings("rawtypes")
	@Bean
	public RedisTemplate defuaultRedisTemplate() {
		RedisTemplate redisTemplate = new RedisTemplate();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		return redisTemplate;
	}

	@SuppressWarnings("unchecked")
	@Bean
	public CacheManager redisCacheManager() {
		
		/**
		 * Cache对象底层是通过RedisTemplate来操作数据的
		 * 所以每个Cache实例必须使用不同的RedisTemplate实例
		 * 如果两个Cache对象使用同一个RedisTemplate，则两个Cache实际上是同一个
		 */
		Set<RedisCache> caches = new HashSet<RedisCache>();
		RedisCache cache1 = new RedisCache("cache1", null, redisTemplate(), 0);
		RedisCache cache2 = new RedisCache("cache2", null, defuaultRedisTemplate(), 0);
		caches.add(cache1);
		caches.add(cache2);
		
		SimpleCacheManager cacheManager = new SimpleCacheManager();
		cacheManager.setCaches(caches);
		
		return cacheManager;
	}
}
