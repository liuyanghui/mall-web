package com.mall.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mall.contant.Contants;
import com.mall.util.PropertiesUtils;

/**
 * ******************  类说明  *********************
 * class       :  WebHandlerInterceptor
 * @author     :  hejinyun@umpay.com
 * @version    :  1.0  
 * description :  应用统一拦截器
 * @see        :                        
 * ***********************************************
 */
public class WebHandlerInterceptor implements HandlerInterceptor {
	
	private static final Logger LOG = LoggerFactory.getLogger(WebHandlerInterceptor.class);
	
	// 返回true,才会执行postHandle
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
 HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		/**
		 * 内部协定：设置一些页面上需要共用的参数值
		 */
		if (null != modelAndView) {
			String domain = PropertiesUtils.getProperty("platform.site.domain");
			String resource = PropertiesUtils.getProperty("platform.resource.domain");
			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute(Contants.DOMAIN, domain); // 平台访问域名
			modelMap.addAttribute(Contants.RESOURCE, resource); // 静态文件域名
			LOG.debug("#postHandle() 公共参数:{}", modelMap);

			modelAndView.addAllObjects(modelMap);
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
