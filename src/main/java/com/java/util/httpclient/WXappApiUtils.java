package com.java.util.httpclient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import com.alibaba.fastjson.JSON;

public class WXappApiUtils {
	
	/**
	 * 根据code 向微信接口请求,获取userOpenId
	 * @param wxSendData
	 * @return
	 */
	public static String getUserOpenIdByCodeFromWXApi(String code) {
		WXSessionData wxSessionData = new WXSessionData();
		WXSessionReturn wxSessionReturn = null ;
		try {
			CloseableHttpClient httpClient = HttpClientUtils.createSSLClientDefault();
			HttpGet getMethod = new HttpGet();

			// 微信https请求接口
			String wxSessionUri = wxSessionData.getWxSessionUri() + "?appid=" + wxSessionData.getAppid() + "&secret="
					+ wxSessionData.getSecret() + "&js_code=" + code + "&grant_type="
					+ wxSessionData.getGrant_type();

			getMethod.setURI(new URI(wxSessionUri));
			CloseableHttpResponse response = httpClient.execute(getMethod);
			HttpEntity entity = response.getEntity();
			
			if (entity != null) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
				String content;
				while ((content = bufferedReader.readLine()) != null) {
					System.out.println(content);
					wxSessionReturn = JSON.parseObject(content, WXSessionReturn.class);
				}
				bufferedReader.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return wxSessionReturn.getOpenid();
	}
	
	/**
	 * 获取商户微信token
	 * @return
	 */
	public static WXSessionReturn getWxAccessToken(){
		HashMap<String, Object> params = new HashMap<>();
		params.put("grant_type", "client_credential");
		params.put("appid", WXConstants.appid);
		params.put("secret", WXConstants.secret);
		String result = HttpClientUtils.doGetSSL("https://api.weixin.qq.com/cgi-bin/token", params);
		WXSessionReturn wxSessionReturn = JSON.parseObject(result, WXSessionReturn.class);
		return wxSessionReturn;
	}

}
class WXConstants{

	public static Object secret;
	public static Object appid;}
class WXSessionData{

	public int getAppid() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getGrant_type() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSecret() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getWxSessionUri() {
		// TODO Auto-generated method stub
		return null;
	}}
class WXSessionReturn{

	public String getOpenid() {
		// TODO Auto-generated method stub
		return null;
	}}
