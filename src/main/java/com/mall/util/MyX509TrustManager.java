/**
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>2014年10月22日上午8:47:31</p>
 *
 * @author wangyunpeng
 * @version 1.0
 */
package com.mall.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * desc:证书信任管理器（用于https请求） 
 * <p>创建人：wangyunpeng 创建日期：2014年10月24日</p>
 * @version V1.0
 */
public class MyX509TrustManager implements X509TrustManager {

    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}  