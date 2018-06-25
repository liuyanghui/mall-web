package com.mall.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * ******************  类说明  *********************
 * class       :  BindingValid
 * @author     :  hejinyun@umpay.com
 * @version    :  1.0  
 * description :  手机号绑定鉴权
 * @see        :                        
 * ***********************************************
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindingValid {
	
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
