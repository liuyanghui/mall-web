package com.mall.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mall.util.PropertiesUtils;

/**
 * ******************  类说明  *********************
 * class       :  WebFilter
 * @author     :  hejinyun@umpay.com
 * @version    :  1.0  
 * description :  自定义Filter，用于加载一些共用的配置
 * @see        :                        
 * ***********************************************
 */
@Deprecated
public class WebFilter implements Filter {

	private static final Logger LOG = LoggerFactory.getLogger(WebFilter.class);
	private String resource = null; // 静态资源域名
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOG.debug("#init() init webFilter...");
		resource = PropertiesUtils.getProperty("platform.resource.domain");
		LOG.debug("#init() Resource:{}", resource);

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		// 设置静态资源访问域名
//		HttpServletRequest request = (HttpServletRequest)servletRequest;
//		HttpServletResponse response = (HttpServletResponse)servletResponse;
//		HttpSession session = request.getSession();
//		session.setAttribute(Contants.RESOURCE, resource);
		chain.doFilter(servletRequest, servletResponse);

	}

	@Override
	public void destroy() {
		LOG.debug("#init() destroy webFilter...");
	}

}
