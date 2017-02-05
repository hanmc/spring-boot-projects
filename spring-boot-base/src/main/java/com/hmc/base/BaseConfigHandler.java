package com.hmc.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

/**
 * 基础的配置处理器<p>
 * 每个配置加载器可集成此抽象类去完成不同组的配置的读取
 * @author hanmc
 *
 */
public abstract class BaseConfigHandler implements InitializingBean{

	@Autowired
	private Environment environment;
	
	private Map<String,String> config = new HashMap<String, String>();
	
	private final static String PFOFILE_BASE = "application.properties";

	
	public Map<String,String> getAllSetting(){
		return config;
	}
	
	public int  getInt(String key){
		return getSetting(key, Integer.class);
	}
	
	public String getString(String key){
		return getSetting(key, String.class);
	}
	
	public Long getLong(String key){
		return getSetting(key, Long.class);
	}
	
	public Boolean getBoolean(String key){
		String value =  getSetting(key, String.class);
		Boolean b = "1".equals(value) || Boolean.parseBoolean("true");
		return b;
	}
	
	@SuppressWarnings("unchecked")
	public <V> V getSetting(String key, Class<V> v){
		return (V)config.get(key);
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		String[] proFiles = getPropertiesFiles();
		for (String proFile : proFiles) {
			ClassPathResource resource = new ClassPathResource(proFile);
			Properties properties = new Properties();
			properties.load(resource.getInputStream());
			readSettings(properties);
		}
	}
	
	private void readSettings(Properties properties) {
		Set<Object> set = properties.keySet();
		for (Object key : set) {
			config.put((String)key, (String)properties.get(key));
		}
	}

	/**
	 * <pre>
	 * 根据当前环境读取相应的.properties文件和扩展的.properties文件
	 * 比如：当前的环境是dev,扩展的配置为redis,则读取application.properties,application-dev.properties及redis-dev.properties
 	 * 配置文件优先级依次升高,优先级高的配置文件会覆盖优先级低的配置文件中的同名配置
	 * <pre>
	 * @return
	 */
	private String[] getPropertiesFiles() {
		if(environment != null) {
			String[] profiles = environment.getActiveProfiles();
			if(!ArrayUtils.isEmpty(profiles)){
				String activeProFile = "application-" + profiles[0] + ".properties";
				if(StringUtils.isNotBlank(getSettingFile())){
					return new String[]{PFOFILE_BASE, activeProFile, getSettingFile()+ "-" + profiles[0] + ".properties"};
				}
				return new String[]{PFOFILE_BASE, activeProFile};
			}
			
		}
		return new String[]{PFOFILE_BASE};
	}

	/**
	 * <pre>
	 * 获取扩展配置文件名
	 * 建议此方法返回空，只使用application配置文件
	 * 子类可覆盖此方法来读取扩展配置文件
	 * <pre>
	 * @return
	 */
	protected String getSettingFile(){
		return null;
	}
	
	/**
	 * <pre>
	 * 是否使用自定义配置
	 * 如果配置文件中不存在对应的配置，则使用默认配置
	 * <pre>
	 * @return
	 */
	protected Boolean useDefaultConfig(){
		String settingPre = getSettingPre();
		Set<String> keySet = config.keySet();
		for (String key : keySet) {
			if(key.startsWith(settingPre)){
				return false;
			}
		}
		return true;
	}
	
	public abstract String getSettingPre();
}
