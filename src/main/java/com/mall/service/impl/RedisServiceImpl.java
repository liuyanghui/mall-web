package com.mall.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.mall.service.IRedisService;

/**
 * ******************  类说明  *********************
 * class       :  RedisServiceImpl
 * @author     :  hejinyun@umpay.com
 * @version    :  1.0  
 * description :  redis服务层接口实现
 * @see        :                        
 * ***********************************************
 */
@Service("redisService")
public class RedisServiceImpl implements IRedisService {

	
	@Autowired
    private RedisTemplate<String,String> redisTemplate;
	
	@Override
	public String get(String key) {
		ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
		return valueOperations.get(key);
	}

	@Override
	public String getAndSet(String key, String value) {
		ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
		return valueOperations.getAndSet(key, value);
	}

	@Override
	public void set(String key, String value) {
		ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
		valueOperations.set(key, value);

	}

	@Override
	public void set(String key, String value, long offset) {
		ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
		valueOperations.set(key, value, offset);
	}

	@Override
	public void set(String key, String value, long timeout, TimeUnit unit) {
		ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
		valueOperations.set(key, value, timeout, unit);
	}

}
