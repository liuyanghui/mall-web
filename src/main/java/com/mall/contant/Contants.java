package com.mall.contant;
/**
 * ******************  类说明  *********************
 * class       :  Contants
 * @author     :  liuyanghui@umpay.com
 * @version    :  1.0  
 * description :  常量类
 * @see        :                        
 * ***********************************************
 */
public class Contants {
	
	/**
	 * 站点域名
	 */
	public static final String DOMAIN = "domain";
	/**
	 * 静态资源域名
	 */
	public static final String RESOURCE = "resource";
	
	
	/**
	 * 手机号
	 */
	public static final String MEMBER_MOBILEID  = "mobileid";
	/**
	 * 密码
	 */
	public static final String MEMBER_PASSWORD  = "password";
	
	/**
	 * 登陆token
	 */
	public static final String LOGIN_TOKEN  = "login_token";
	
	/**
	 * 登陆userid
	 */
	public static final String USERID  = "userid";
	
	/**
	 * cookie存储时间 保存一周
	 */
	public static final int COOKIE_TIME  = 3600 * 24 * 7;
	/**
	 * 支付方式-积分兑换
	 */
	public static final String PAYTYPE_POINTS = "points";
	/**
	 * 支付方式-现金支付
	 */
	public static final String PAYTYPE_CASH = "cash";
	/**
	 * 支付方式-组合支付
	 */
	public static final String PAYTYPE_COMB = "comb";
	
	/**
	 * utf-8字符集
	 */
	public static final String CHARSET_UTF_8 = "utf-8";
	/**
	 * 请求微信存储手机号
	 */
	public static final String WX_MOBILEID = "wx_mobileid.";
	/**
	 * 请求微信存储密码
	 */
	public static final String WX_PASSWORD = "wx_password.";
	/**
	 * 请求微信存储验证码
	 */
	public static final String WX_CODE= "wx_code.";
	
	public static final String ORDER_SOURCE_PAY = "pay";
	
	public static final String ORDER_SOURCE_LOTTERY = "lottery";
	
	/**
	 * 微信用户openid
	 */
	public static final String WX_OPENID = "wx_openid.";
	/**
	 * 微信用户
	 */
	public static final String WX_UNIONID = "wx_unionid.";
	
	/**
	 * 特殊符号"="
	 */
	public static final String SYMBOL_EQUAL = "=";
	/**
	 * 特殊符号"&"
	 */
	public static final String SYMBOL_AND = "&";
	
	/**
	 * 微信后台通知-返回状态码
	 */
	public static final String WX_PAY_NOTIFY_RETURN_CODE = "return_code";
	/**
	 * 微信后台通知-返回信息
	 */
	public static final String WX_PAY_NOTIFY_RETURN_MSG = "return_msg";
	/**
	 * 微信后台通知-业务结果
	 */
	public static final String WX_PAY_NOTIFY_RESULT_CODE = "result_code";
	/**
	 * 注册时发送验证码的redise前缀
	 */
	public static final String SEND_REGISTER_AUTH_CODE = "REGISTER_VALIDCODE_";
	/**
	 * 微信后台通知-返回信息
	 */
	public static final String REDIRCT = "redirct.";
	
	/**
	 * 绑定手机号-返回信息
	 */
	public static final String BINDREDIRCT = "bindredirct.";
	
	/**
	 * HTTP-POST方式
	 */
	public static final String POST = "POST";
	
}
