package com.hmc.cache.redis;

import com.hmc.base.BaseConfigHandler;

/**
 * redis配置控制器<p>
 * 加载前缀为base.redis.的配置项
 * @author hanmc
 *
 */
public class RedisConfigHandler extends BaseConfigHandler{

	private final String CONFIG_PRE_REDIS = "base.redis.";
	
	@Override
	public String getSettingPre() {
		return CONFIG_PRE_REDIS;
	}
	
}
