package com.hmc.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hmc.base.BaseResultInfo;
import com.hmc.event.XmlRootEvent;

@RestController
public class XMLViewController {

	@RequestMapping(value="/service/xmlTest",method=RequestMethod.POST)
	public BaseResultInfo xmlTest(@RequestBody XmlRootEvent body , HttpServletRequest servletRequest){
		
		
		return null;
	}
	
}
