package com.mall.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ******************  类说明  *********************
 * class       :  LoginValid
 * @author     :  hejinyun@umpay.com
 * @version    :  1.0  
 * description :  登录鉴权注解
 * @see        :                        
 * ***********************************************
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginValid {
	
	
	/**
	 * ********************************************
	 * method name   : value 
	 * description   : 是否需要验证
	 * @return       : boolean
	 * @param        : @return
	 * modified      : hejinyun@umpay.com ,  2015-8-7  上午10:11:08
	 * @see          : 
	 * *******************************************
	 */
	boolean value() default true;
}
