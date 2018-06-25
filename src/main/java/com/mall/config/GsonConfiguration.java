package com.mall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * ******************  类说明  *********************
 * class       :  GsonConfiguration
 * @author     :  hejinyun@umpay.com
 * @version    :  1.0  
 * description :  初始化Gson对象
 * @see        :                        
 * ***********************************************
 */
@Configuration
public class GsonConfiguration {
	
	private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
	
	@Bean
	public Gson buildGsonObject(){
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat(DATE_FORMAT_PATTERN);
		return builder.create();
	}
	
}
