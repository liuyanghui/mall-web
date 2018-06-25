package com.mall.interceptor;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * ******************  类说明  *********************
 * class       :  BindingValidInterceptor
 * @author     :  hejinyun@umpay.com
 * @version    :  1.0  
 * description :  "手机号绑定"鉴权
 * @see        :                        
 * ***********************************************
 */
@Aspect
@Component
@Order(value=2)
public class BindingValidInterceptor {

}
