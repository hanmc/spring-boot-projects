package com.hmc.cache.ehcache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ehcache配置类<p>
 * 如果没有配置文件，则使用默认配置
 * 
 * @author hanmc
 * @date 2016-3-27
 * 
 */
//@Configuration
//@EnableCaching
public class EhCacheConfiguration {

	@Bean
	public CacheManager ehCacheCacheManager() {
        net.sf.ehcache.CacheManager ehCacheManager = net.sf.ehcache.CacheManager.newInstance();
        ehCacheManager.addCache("defaultCache");
		EhCacheCacheManager cacheManager = new EhCacheCacheManager(ehCacheManager);
		return cacheManager;
	}
}
