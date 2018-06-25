package com.mall.controller.common;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;
import com.mall.util.IPUtil;

/**
 * ******************  类说明  *********************
 * class       :  GlobalInterceptor
 * @author     :  hejinyun@umpay.com
 * @version    :  1.0  
 * description :  平台统一拦截器
 * @see        :                        
 * ***********************************************
 */
//@Aspect
//@Component
public class GlobalInterceptor {
	
	private static final Logger LOG = LoggerFactory.getLogger(GlobalInterceptor.class);
	@Autowired
	private Gson gson;
	
	/**
	 * ********************************************
	 * method name   : requestMappingPointcut 
	 * description   : 定义拦截规则
	 * @return       : void
	 * @param        : 
	 * modified      : hejinyun@umpay.com ,  2016-11-21  下午5:35:28
	 * @see          : 
	 * *******************************************
	 */
	@Pointcut("execution(* com.mall.controller..*(..)) and @annotation(org.springframework.web.bind.annotation.RequestMapping)")
	//@Pointcut("execution(* com.mall.controller.web.*(..))")
	public void requestMappingPointcut(){
		
	}
	
	@Around("requestMappingPointcut()")
	public Object interceptor(ProceedingJoinPoint pjp){
		try {
			long stime = System.currentTimeMillis();
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	        HttpServletRequest request = attributes.getRequest();
	        String ip = IPUtil.getRemoteAddress(request);
	        String uri = request.getRequestURI();
		    MethodSignature signature = (MethodSignature) pjp.getSignature();
		    Method method = signature.getMethod();
		    String methodName = method.getName(); //获取被拦截的方法名
		    Object[] args = pjp.getArgs();
		    LOG.debug("#interceptor() Method:{}==>请求ip:{}", methodName, ip);
	        LOG.debug("#interceptor() Method:{}==>请求uri:{}", methodName, uri);
//		    LOG.debug("#interceptor() Method:{}==>请求参数:{}", methodName, gson.toJson(args));
			Object result = pjp.proceed(args);
			LOG.debug("#interceptor() Method:{}==>响应参数:{}", methodName, gson.toJson(result));
			LOG.info("#interceptor() 接口调用耗时:{}", (System.currentTimeMillis()-stime));
			return result;
		} catch (Throwable e){
			LOG.error("#interceptor 平台异常", e);
		}
		
		return  "{\"ret\":\"error\", \"msg\": \"System Error!\"}";
	}
	
}
