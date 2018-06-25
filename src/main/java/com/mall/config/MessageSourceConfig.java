package com.mall.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * ******************  类说明  *********************
 * class       :  MessageSourceConfig
 * @author     :  hejinyun@umpay.com
 * @version    :  1.0  
 * description :  MessageSource配置
 * @see        :                        
 * ***********************************************
 */
@Configuration
@EnableCaching //启用缓存，这个注解很重要；
public class MessageSourceConfig extends CachingConfigurerSupport {
	
	// 需要自动加载的配置文件
	@Value("${platform.properties.basenames}")
	private String basenames;
	// 配置文件自动刷新时间
	@Value("${platform.properties.cacheseconds}")
	private int cacheSeconds;
	
	@Value("${spring.redis.host}")
	private String host;
	@Value("${spring.redis.port}")
	private int port;
	@Value("${spring.redis.timeout}")
	private int timeout;
//	@Value("${spring.redis.pool.max-idle}")
//	private int maxIdle;
//	@Value("${spring.redis.pool.max-wait}")
//	private long maxWaitMillis;
	@Value("$spring.redis.password")
	private String password;
	
	@Bean  
    public ResourceBundleMessageSource messageSource() {  
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();  
        //不需要classpath和.properties后缀
        String[] properties = basenames.split(",");
        source.addBasenames(properties);
        source.setUseCodeAsDefaultMessage(true);
        source.setCacheSeconds(cacheSeconds);
        return source;  
    }
	
	@Bean
    public CacheManager cacheManager(RedisTemplate<String, String> redisTemplate) {
           CacheManager cacheManager = new RedisCacheManager(redisTemplate);
           return cacheManager;
    }
	
	@Bean
	public JedisConnectionFactory redisConnectionFactory(){
		 JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
		 redisConnectionFactory.setHostName(host);
		 redisConnectionFactory.setPort(port);
		 redisConnectionFactory.setTimeout(timeout);
		 redisConnectionFactory.setPassword(password);
		 return redisConnectionFactory;
	}
	
	@Bean
	public RedisTemplate<String, String> redisTemplate(
			RedisConnectionFactory factory){
		StringRedisTemplate template = new StringRedisTemplate(factory);
		// 设置redis的序列化方式-统一进行string序列化
		StringRedisSerializer stringSerializer = new StringRedisSerializer();
		template.setKeySerializer(stringSerializer);
		template.setValueSerializer(stringSerializer);
		template.afterPropertiesSet();
		return template;
	}
}
