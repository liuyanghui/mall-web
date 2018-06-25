package com.mall.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *@data 2017年4月7日上午10:10:30
 *@auther liuyanghui
 *@desc:
 */
public class HttpUtils {
	

	private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

	  /**
     * desc:发起https请求并获取结果
     * <p>创建人：wangyunpeng , 2014年10月24日下午2:38:05</p>
     * @param requestUrl    请求地址
     * @param requestMethod    请求方式（GET、POST）
     * @param outputStr    提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        log.info("httpRequest->requestUrl:{}", requestUrl);
        log.info("httpRequest->outputStr:{}", outputStr);
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        }
        return jsonObject;
    }
    

	

	
	/**
	 * ********************************************
	 * method name   : doPost 
	 * description   : Http协议Post方式请求
	 * @return       : String
	 * @param        : @param url
	 * @param        : @param body
	 * @param        : @param headers
	 * @param        : @param connectionTimeout
	 * @param        : @param readTimeout
	 * @param        : @param charset
	 * @param        : @return
	 * modified      : hejinyun@umpay.com ,  2016-11-7  下午3:28:02
	 * @see          : 
	 * *******************************************
	 */
	public static String doPost(String url, String body, String[][] headers,
			int connectionTimeout, int readTimeout, String charset){
		URL uri = null;
		HttpURLConnection urlConnection = null;
		try {
			uri = new URL(url);
			urlConnection = (HttpURLConnection)uri.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setConnectTimeout(1000 * connectionTimeout); // 连接超时时间
			urlConnection.setReadTimeout(1000 * readTimeout); // 响应超时时间
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);
			urlConnection.setUseCaches(false);
			
			if (null == headers){
				urlConnection.addRequestProperty("Content-Type", "application/json");
			} else {
				// 设置Http报文请求头
				for (String[] header : headers){
					urlConnection.addRequestProperty(header[0], header[1]);
				}
			}
			
			// 提交Http请求参数
			urlConnection.connect();
			if (null != body){
				urlConnection.getOutputStream().write(body.getBytes(charset));
				urlConnection.getOutputStream().flush();
				urlConnection.getOutputStream().close();
			}
			
			// 读取响应参数
			int responseCode = urlConnection.getResponseCode();
			if (HttpURLConnection.HTTP_OK == responseCode){
				InputStream is = urlConnection.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is, charset));
				StringBuffer response = new StringBuffer();
				String line = br.readLine();
				while (null != line){
					response.append(line);
					line = br.readLine();
				}
				br.close();
				is.close();
				return response.toString();
			} else {
				log.info("#doPost() 请求失败！"+responseCode);
			}
		} catch(Exception e){
			log.error("#doPost() 请求异常:"+e.toString(), e);
		} finally{
			if (null != urlConnection){
				urlConnection.disconnect();
			}
		}
		return null;
	}
	
	/**
	 * ********************************************
	 * method name   : doGet 
	 * description   : Http协议Get方式请求
	 * @return       : String
	 * @param        : @param url
	 * @param        : @param headers
	 * @param        : @param connectionTimeout
	 * @param        : @param readTimeout
	 * @param        : @param charset
	 * @param        : @return
	 * modified      : hejinyun@umpay.com ,  2016-11-7  下午3:28:18
	 * @see          : 
	 * *******************************************
	 */
	public static String doGet(String url, String[][] headers,
			int connectionTimeout, int readTimeout, String charset){
		URL uri = null;
		HttpURLConnection urlConnection = null;
		try {
			uri = new URL(url);
			urlConnection = (HttpURLConnection) uri.openConnection();
			urlConnection.setConnectTimeout(1000 * connectionTimeout);
			urlConnection.setReadTimeout(1000 * readTimeout);
			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(true);
			urlConnection.setUseCaches(false);
//			for (String[] header : headers){
//				urlConnection.addRequestProperty(header[0], header[1]);
//			}
			urlConnection.connect();
			// 读取响应参数
			int responseCode = urlConnection.getResponseCode();
			if (HttpURLConnection.HTTP_OK == responseCode){
				InputStream is = urlConnection.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is, charset));
				StringBuffer response = new StringBuffer();
				String line = br.readLine();
				while (null != line){
					response.append(line);
					line = br.readLine();
				}
				br.close();
				is.close();
				return response.toString();
			} else {
				log.info("#doGet() 请求失败！"+responseCode);
			}
		} catch (Exception e){
			log.error("#doGet() 请求异常", e);
		} finally {
			if (null != urlConnection){
				urlConnection.disconnect();
			}
		}
		return null;
	}
}
