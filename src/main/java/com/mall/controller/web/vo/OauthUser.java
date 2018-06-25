/** *****************  JAVA头文件说明  ****************
 * file name  :  UserLotteryControl.java
 * owner      :  dmm
 * copyright  :  UMPAY
 * description:  
 * modified   :  2017-4-6
 * *************************************************/

package com.mall.controller.web.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * ****************** 类说明 ********************* class : UserLotteryControl
 * 
 * @author : dmm
 * @version : 1.0 description : 用户抽奖概率控制表
 * @see :
 * ************************************************/
public class OauthUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5322059318352607661L;
	// `user_type` pk
	private String id;// 用户ID
	private String oauth_user_id;// 关联第三方平台ID
	private String unionid;// 第三方平台用户唯一ID
	private String userid;// 平台内部用户ID
	private Timestamp modtime;// 修改时间
	private Timestamp intime;// 创建时间
	
	private String authcode;//验证码 用于接收数据与库无关
	private String mobileid;//手机号
	private String login_token;
	
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOauth_user_id() {
		return oauth_user_id;
	}
	public void setOauth_user_id(String oauth_user_id) {
		this.oauth_user_id = oauth_user_id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Timestamp getModtime() {
		return modtime;
	}
	public void setModtime(Timestamp modtime) {
		this.modtime = modtime;
	}
	public Timestamp getIntime() {
		return intime;
	}
	public void setIntime(Timestamp intime) {
		this.intime = intime;
	}
	public String getAuthcode() {
		return authcode;
	}
	public void setAuthcode(String authcode) {
		this.authcode = authcode;
	}
	public String getMobileid() {
		return mobileid;
	}
	public void setMobileid(String mobileid) {
		this.mobileid = mobileid;
	}
	public String getLogin_token() {
		return login_token;
	}
	public void setLogin_token(String loginToken) {
		login_token = loginToken;
	}

}
