package com.sxsd.car.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;


public final class HttpTookit {
	private static final Logger log =  LoggerFactory.getLogger(HttpTookit.class);

    /** 
     * 执行一个HTTP GET请求，返回请求响应的HTML 
     * 
     * @param url                 请求的URL地址 
     * @return 返回请求响应的HTML 
     */ 
	public static String doGet(Logger logger, String url) {
		if (logger == null)
			logger = log;
		String response = null;
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(url);
		try {
			HttpConnectionManagerParams params = client.getHttpConnectionManager().getParams();
			params.setConnectionTimeout(1000 * 10);// 连接超时
			params.setSoTimeout(1000 * 10);// 读数据超时
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				response =IOUtils.toString(method.getResponseBodyAsStream());
			} else {
				logger.error("执行HTTP Get请求" + url + "时，返回值不是ok！" + method.getResponseBodyAsString());
				throw new RuntimeException("StatusCode!=200");
			}
		} catch (IOException e) {
			logger.error("执行HTTP Get请求" + url + "时，发生io异常！"+ e.getMessage());
			throw new RuntimeException("网络请求遇到异常"+e.getMessage());
		} finally {
			method.releaseConnection();
		}
		return response;
	} 
	public static JSONObject getJsonFromUrl(String url) {
		InputStream is = null;
		try {
			is = new URL(url).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = JSON.parseObject(jsonText);
			return json;
		}catch(Exception e){
			//log.warn("begin:"+DateUtil.yyyyMMddhhmmssSSS(begin)+",end:"+DateUtil.yyyyMMddhhmmssSSS(new Date())+",url:"+url);
			//exceptionHandler.handle(e);
			return null;
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}
	
	
	public static void main(String[] arg){
		String url="http://ybd.buu.edu.cn/yx.yx_ybdxxb.do?m=callbackDcwj&ksh=2015100434014";
		String res = doGet(null, url);
		System.out.println("res:"+res);
	}

}
