package com.hmc.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.hmc.base.BaseConstants;
import com.hmc.base.BaseException;
import com.hmc.base.BaseResultInfo;
import com.hmc.event.IBaseRequestEvent;
import com.hmc.service.IBaseService;

/**
 * 请求入口<br>
 * 拦截符合/service/{serviceName}规则的请求<br>
 * 根据serviceName调用相应的service组件<br>
 * 要求serviceName和service组件定义的 BaseRequestEvent 子类名字对应
 * 如请求url为：localhsot:8080/hmc/service/userLogin<br>
 * 则此请求所对应的组件的请求数据类名字应为UserLoginRequestEvent<br>
 * 
 * @author hmc
 *
 */
@RestController
public class BaseController {
	
	Logger ilog = LoggerFactory.getLogger(getClass());
	
	@Value("${hmc.event.basePackage:com.hmc.event}")
	private String baseEventPackage;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	
	@RequestMapping(value="/service/{serviceName}",method=RequestMethod.POST)
	public BaseResultInfo service(HttpServletRequest httpRequest, @PathVariable String serviceName, @RequestBody String param, @RequestParam(defaultValue = "") String eventPackage, @RequestParam(defaultValue = "") String servicePackage) throws IOException{
		
		ilog.info("请求处理开始");
		ilog.info("请求url：{}",httpRequest.getRequestURL());
		ilog.info("请求参数：\r" + param);
		long startTime = System.currentTimeMillis();
		
		IBaseRequestEvent request = null;
		BaseResultInfo response = BaseResultInfo.getBaseResult(true);
		IBaseService service;
		
		try {
			param = getJsonParam(param);
			request = getRunTimeRequest(serviceName,eventPackage,param);
			service = getServiceBean(serviceName);
			response = service.excute(request);
		} catch (Exception e) {
			
			if(e instanceof BaseException){
				ilog.error("系统错误：" + e.getMessage());
				response = BaseResultInfo.getBaseResult(false);
				response.setReturnMsg(e.getMessage());
			}else{
				ilog.error("系统错误：" + e.getMessage());
				e.printStackTrace();
				response = BaseResultInfo.getBaseResult(false);
				response.setReturnMsg("系统错误，请稍候重试");
			}
		}
		
		ilog.info("请求响应信息：" + response.toString());
		ilog.info("请求{}处理结束，耗时{}毫秒",StringUtils.capitalize(serviceName),System.currentTimeMillis()-startTime);
		ilog.info(BaseConstants.LOG_FLAG_END.getDesc());
		return response;
		
	}

	/**
	 * 请求报文处理<br>
	 * 将json格式或者key-value的请求字段统一处理为json格式
	 * @param param
	 * @return
	 */
	private String getJsonParam(String param) {
		JSONObject jsonObj = new JSONObject();
		
		if(StringUtils.startsWith(param, "{") && StringUtils.endsWith(param, "}")){
			//json格式请求体
			return param;
		}else{
			//key value的请求体
			String[] params = param.split("&");
			for (String kv : params) {
				//考虑到value值中存在等号的情况  限定切割后数组的最大长度为2  
				String[] keyValues = StringUtils.split(kv,"=",2);
				String value = "";
				if(keyValues.length>1){
					value = keyValues[1];
				}
				jsonObj.put(keyValues[0], value);
			}
		}
		String jsonString = jsonObj.toString();
		return jsonString;
	}

	/**
	 * 获取请求对应的Service组件
	 * @param serviceName
	 * @return
	 * @throws BaseException 
	 */
	private IBaseService getServiceBean(
			String serviceName) throws BaseException {
		String serviceBeanName = serviceName + "Service";
		IBaseService service;
		
		try{
			service = (IBaseService)applicationContext.getBean(serviceBeanName);
		}catch (Exception e) {
			throw new BaseException("Service:"+StringUtils.capitalize(serviceBeanName)+"不存在",e);
		}
		
		return service;
	}


	/**
	 * 实例化请求对象，组装请求参数
	 * @param serviceName
	 * @param eventPackage
	 * @param param
	 * @return
	 * @throws BaseException 
	 */
	@SuppressWarnings({ "unchecked" })
	private IBaseRequestEvent getRunTimeRequest(String serviceName, String eventPackage, String param) throws BaseException{
		
		ilog.info("开始组装请求参数");
		
		if(StringUtils.isNotBlank(eventPackage)){
			baseEventPackage = baseEventPackage + "." + eventPackage;
		}
		
		String className = baseEventPackage + "." + StringUtils.capitalize(serviceName) + "RequestEvent";
		
		Class<IBaseRequestEvent> requestClass = null;
		IBaseRequestEvent requestEvent = null;
		try {
			requestClass = (Class<IBaseRequestEvent>) Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new BaseException(className + "不存在",e);
		}
		
		ObjectMapper objMapper = new ObjectMapper();
		try {
			requestEvent = objMapper.readValue(param, requestClass);
		} catch (UnrecognizedPropertyException e) {
			throw new BaseException("参数转换异常!参数" +e.getPropertyName() + "不存在",e);
		} catch (IOException e) {
			throw new BaseException("参数转换异常!",e);
		}
		
		ilog.info("请求参数{}组装完成", className);
		return requestEvent;
		
	}

}
