package com.hmc.web.filter;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringUtils;
/**
 * 普通请求包装
 * @author wuxiaozeng
 *
 */
public class MirageRequestWrapper extends HttpServletRequestWrapper {

	public MirageRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	/**
	 * 对密码控件域传输的值不进行XSS过滤 过滤参数 pcData|pwd|pwd_confirm|pwd_old
	 */
	public String[] getParameterValues(String name) {
		String[] values = super.getParameterValues(name);
		if (!"_".equals(name)) {
			
			if (values != null) {
				for (int i = 0; i < values.length; i++) {			
						values[i] = cleanXSS(values[i]);
				}
				
			}
		}
		return values;

	}

	@Override
	public String getParameter(String name) {
		String value = super.getParameter(name);
		if (StringUtils.isNotBlank(value)) {
			return  cleanXSS(value);
		}
		return value;
	}
	
	
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> paramMap = super.getParameterMap();
		Iterator<String> it = paramMap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next().toString();
			if (!"_".equals(key)) {
				String[] values = paramMap.get(key);
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						values[i] = cleanXSS(values[i]);
					}
				}
			}
		}
		return paramMap;
	}
	
//	@Override
//	public Object getAttribute(String name) {
//		// 处理app的跨站脚本攻击
//		if ("param".equals(name)) {
//			return cleanXSS((String)super.getAttribute(name));
//		}
//		return super.getAttribute(name);
//	}

	/**
	 * 跨站脚本过滤，注意：如果该方法有改动，PasswordHelper.restoreXSS方法也要随之改动
	 * @param value
	 * @return
	 */
	private String cleanXSS(String value) {
		//value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
		value = value.replaceAll("'", "&#39;");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"<a>\\\'][\\s]*javascript:(.*)[\\\"\\\'</a>]", "\"\"");
		value = value.replaceAll("script", "");
		return value;
	}
}
