/** *****************  JAVA头文件说明  ****************
 * file name  :  TestCtl.java
 * owner      :  xu
 * copyright  :  UMPAY
 * description:  
 * modified   :  2017-4-6
 * *************************************************/ 

package com.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mall.service.IRedisService;


/** ******************  类说明  *********************
 * class       :  TestCtl
 * @author     :  xuhuafeng
 * @version    :  1.0  
 * description :  
 * ************************************************/
@RestController
@RequestMapping("/web/test")
public class Test5Ctl {
	@Autowired
	private IRedisService redisService;
	
	@RequestMapping(value="/getpic", method=RequestMethod.GET)
	public ModelAndView getPic(ModelMap modelMap) throws Exception{
		modelMap.addAttribute("test", "hello world");
        System.out.println(redisService.getAndSet("test", "mykey4"));
		return new ModelAndView("index");

	}
	
}
