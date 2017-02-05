package com.hmc.web.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hmc.base.BaseConstants;
import com.hmc.event.UserLoginRequestEvent;

@Controller
public class TestController {

	@RequestMapping(value="/test/test",method=RequestMethod.POST)
	public void test(@RequestBody UserLoginRequestEvent user){
		System.out.println(user.toString());
		System.out.println(BaseConstants.LOG_FLAG_END.getDesc());
	}
	
}
