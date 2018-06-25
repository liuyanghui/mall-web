package com.mall.interceptor;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.mall.contant.Contants;
import com.mall.util.PropertiesUtils;
import com.mall.util.PublicKeyMap;
import com.mall.util.RSAUtils;
import com.mall.util.RedisPool;

/**
 * ******************  类说明  ********************* class       :  LoginValidInterceptor
 *
 * @author :  hejinyun@umpay.com
 * @version :  1.0 description :  "用户登录"鉴权
 * @see : ***********************************************
 */
@Aspect
@Component
@Order(value = 1)
public class LoginValidInterceptor {

  private static final Logger LOG = LoggerFactory.getLogger(LoginValidInterceptor.class);
  private static String domain = PropertiesUtils.getProperty("platform.resource.domain");

  @Pointcut("@annotation(com.mall.annotation.LoginValid)")
  public void loginValidPointcut() {

  }

  /**
   * ******************************************** method name   : interceptor description   :
   * 用户未登录拦截器
   *
   * @param : @param pjp
   * @param : @return
   * @param : @throws Throwable modified      : liuyanghui@umpay.com ,  2017年4月13日  下午10:03:45
   * @return : Object
   * @see : *******************************************
   */
  @Around("loginValidPointcut()")
  public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
    /**
     * 内部协定：
     * 凡使用@LoginValid注解的方法，必须包含HttpServletRequest、HttpServletResponse参数
     */
    long stime = System.currentTimeMillis();
    LOG.debug("#interceptor() 开始本次登录信息鉴权");

    // 获取被注解的方法参数
    Object[] args = pjp.getArgs();
    HttpServletRequest request = null;
    HttpServletResponse response = null;
    for (Object arg : args) {
      if (arg instanceof HttpServletRequest) {
        request = (HttpServletRequest) arg;
        continue;
      }
      if (arg instanceof HttpServletResponse) {
        response = (HttpServletResponse) arg;
        continue;
      }
    }
    // 打印用户环境信息：User-Agent、请求uri
    String ua = request.getHeader("User-Agent");
    LOG.debug("#interceptor() ua:{}", ua);
    String uri = request.getRequestURI();
    LOG.info("#interceptor() 用户请求uri:{}", uri);
    //记录地址用于登录后返回地址
    String login_token = getLoginToken(request, response);
    RedisPool.set(Contants.REDIRCT + login_token, 60 * 60, uri);
    LOG.debug("#interceptor() LOGIN_TOKEN:{}", login_token);
    request.setAttribute(Contants.LOGIN_TOKEN, login_token);
    // LOGIN_TOKEN为空：视为未登录，重定向到登录界面
    if (StringUtils.isBlank(login_token)) {
      LOG.debug("#interceptor() cookies未获取到LOGIN_TOKEN信息,重定向到登录页面");
      LOG.debug("#interceptor() 本次用户登录鉴权结束,耗时:{}ms", (System.currentTimeMillis() - stime));
      return "redirect:" + domain + "/login/index.htm";
    }

    // 判断缓存中是否有用户登录信息，已过期或不存在，重定向到登录界面
    String memberinfo = RedisPool.get(login_token);
    LOG.debug("#interceptor() 会员信息:{}", memberinfo);
    if (StringUtils.isBlank(memberinfo)) {
      LOG.debug("#interceptor() 缓存中没有用户信息,重定向到登录页面");
      LOG.debug("#interceptor() 本次用户登录鉴权结束,耗时:{}ms", (System.currentTimeMillis() - stime));
      return "redirect:" + domain + "/login/index.htm";
    }

    JSONObject memjson = JSONObject.fromObject(memberinfo);
    request.setAttribute(Contants.USERID, memjson.get(Contants.USERID));
    request.setAttribute(Contants.LOGIN_TOKEN, memjson);
    String requrl = request.getRequestURI();
    String filterUrl = PropertiesUtils.getProperty("filterUrl");
    String filterUrls[] = filterUrl.split(",");
    List<String> filterList = Arrays.asList(filterUrls);
    //获取用户手机号
    if (filterList.contains(requrl) || requrl.startsWith("/cashier/confirmorder")) {
      String mobileid = memjson.optString("mobileid");
      if (mobileid == null || "".equals(mobileid)) {
        RedisPool.set(Contants.BINDREDIRCT + login_token, 60 * 60, uri);
        LOG.debug("#interceptor() 缓存中没有用户手机号信息,重定向到绑定手机号页面");
        LOG.debug("#interceptor() 本次用户登录鉴权结束,耗时:{}ms", (System.currentTimeMillis() - stime));
        return "redirect:" + domain + "/member/bindmobile.htm";
      }
    }
    // 执行目标方法
    Object result = pjp.proceed(args);
    LOG.info("#interceptor() 本次用户登录鉴权结束,耗时:{}ms", (System.currentTimeMillis() - stime));
    return result;

  }


  /**
   * ******************************************** method name   : getLoginToken description   :
   * 获取登录token (uuid)
   *
   * @param : @param request
   * @param : @param response
   * @param : @return modified      : liuyanghui@umpay.com  ,  2017-4-14  上午11:59:19
   * @return : String
   * @see : *******************************************
   */
  private String getLoginToken(HttpServletRequest request, HttpServletResponse response) {

    Cookie[] cookies = request.getCookies();
    String login_token = "";
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(Contants.LOGIN_TOKEN)) {
          login_token = cookie.getValue();
        }
      }
    }

    //如果cookie 没有uuid 则生成一个uuid 并存入cookie中
    if (StringUtils.isEmpty(login_token)) {
      login_token = UUID.randomUUID().toString();
      Cookie cookie = new Cookie(Contants.LOGIN_TOKEN, login_token);
      cookie.setPath("/");
      response.addCookie(cookie);
      cookie.setMaxAge(Contants.COOKIE_TIME);
    }
    return login_token;
  }
}
