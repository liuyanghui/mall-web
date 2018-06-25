package com.mall.service;

import java.util.concurrent.TimeUnit;


/** 
 * ******************  类说明  *********************
 * class       :  IRedisService
 * @author     :  xuhuafeng
 * @version    :  1.0  
 * description :  
 * ************************************************/
public interface IRedisService {
	
	
	public String get(String key);
	
	public String getAndSet(String key, String value);
	
	public void set(String key, String value);
	
	public void set(String key, String value, long offset);
	
	public void set(String key, String value, long timeout, TimeUnit unit);
	
}
