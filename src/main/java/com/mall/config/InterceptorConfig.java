package com.mall.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.mall.interceptor.WebHandlerInterceptor;

/**
 * ******************  类说明  *********************
 * class       :  InterceptorConfig
 * @author     :  hejinyun@umpay.com
 * @version    :  1.0  
 * description :  拦截器注册，设置一些页面需要的信息
 * @see        :                        
 * ***********************************************
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter{
	
	private final Logger LOG = LoggerFactory.getLogger(InterceptorConfig.class);
	
	@Value("${platform.webfilter.urlpatterns}")
	private String urlPattern;
	
	public void addInterceptors(InterceptorRegistry registry) {
		LOG.debug("#addInterceptors() 拦截器拦截的urlPattern:{}", urlPattern);
		registry.addInterceptor(new WebHandlerInterceptor()).addPathPatterns(urlPattern);
		super.addInterceptors(registry);
	}
}
