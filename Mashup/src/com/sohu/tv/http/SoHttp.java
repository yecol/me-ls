package com.sohu.tv.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.Request;

/**
 * 读URL
 * 
 * @author chengxinsun
 * @date 2011-6-14
 */
public class SoHttp {
	private static final Log log = LogFactory.getLog(SoHttp.class);

	/**
	 * Get
	 * 
	 * @param url
	 * @param postParameter
	 * @return
	 */
	public static String getResponseByGet(String url,String api_key,String secret_Key) {
		
		log.info("url :[" + url + "]");
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		// 使用系统提供的默认的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				log.error("HTTP 请求失败: " + getMethod.getStatusLine());
			}
			String res = getMethod.getResponseBodyAsString();
			log.debug(res);
			return res;

		} catch (IOException e) {
			log.error("Net work exception :" + e.getMessage());
		} finally {
			getMethod.releaseConnection();
		}
		return null;
		
	}
	
	

public static String getResponseByOauth(String url,String api_key,String secret_Key,String oauthToken,String tokenSecret) {
		
		OAuthConsumer consumer = new DefaultOAuthConsumer(api_key,secret_Key);

		//访问受保护资源时，需要传递oauth_token和token_secret
		//consumer.setTokenWithSecret("a3283e31cf028fb6ba9d6e32d442b71c","d1f791d58d82eaac8a9e66e0b4f9ddb8");

		//访问公共资源时，无须传递oauth_token和token_secret
		if(StringUtils.isNotBlank(oauthToken) && StringUtils.isNotBlank(tokenSecret))
			consumer.setTokenWithSecret(oauthToken,tokenSecret);
		
		HttpURLConnection request=null;
		try{
			URL myUrl = new URL(url);
			request = (HttpURLConnection) myUrl.openConnection();
			request.setDoOutput(true);
			request.setRequestMethod("GET");
	
			//对请求进行签名
			consumer.sign(request);
			request.connect();
			log.info("Response: " + request.getResponseCode() + " "+ request.getResponseMessage());
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(),"utf-8"));
			StringBuffer result = new StringBuffer();
			String b = null;
			while ((b = reader.readLine()) != null) {
				result.append(b);
			}
			return result.toString();
		}catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(request!=null){
				request.disconnect();
			}
		}
		return null;
	}
	/**
	 * Post
	 * 
	 * @param url
	 * @param postParameter
	 * @return
	 */
	public static String getResponseByPost(String url) {

		log.info("url :[" + url + "]");
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		// 使用系统提供的默认的恢复策略
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		try {
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				log.error("HTTP 请求失败: " + postMethod.getStatusLine());
			}
			String res = postMethod.getResponseBodyAsString();
			log.debug(res);
			return res;

		} catch (IOException e) {
			log.error("Net work exception :" + e.getMessage());
		} finally {
			postMethod.releaseConnection();
		}
		return null;
	}
	
}
