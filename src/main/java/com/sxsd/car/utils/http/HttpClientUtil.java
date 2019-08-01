package com.sxsd.car.utils.http;

import com.sxsd.car.utils.DateUtil;
import com.sxsd.car.utils.RetryUtil;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.*;

//@Component
public class HttpClientUtil {

	private static final int DEFAULT_POOL_MAX_TOTAL = 1000;
    private static final int DEFAULT_POOL_MAX_PER_ROUTE = 500;
 
    private static final int DEFAULT_CONNECT_TIMEOUT = 10000;
    private static final int DEFAULT_CONNECT_REQUEST_TIMEOUT = 10000;
    private static final int DEFAULT_SOCKET_TIMEOUT = 10000;

	
    private static CloseableHttpClient httpClient = null;
    private final static Object syncLock = new Object();

    public static CloseableHttpClient getHttpClient(){
    	if(httpClient == null){
    		synchronized (syncLock){
    			if(httpClient == null){
    				httpClient = init();
    			}
    		}
    	}
    	return httpClient;
    }
	
	private static CloseableHttpClient init() {
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        PoolingHttpClientConnectionManager gcm = initPool();
        CloseableHttpClient httpClient = httpClientBuilder
                .setConnectionManager(gcm)
                .setDefaultRequestConfig(timeOutConfig())
                .build();
       new IdleConnectionMonitorThread(gcm).start();
       return httpClient;
	}
	  
    public static Map<String,String> doGet(String url, Map<String, String> param) {  
  
        Map<String,String> map = new HashMap<String,String>();  
        CloseableHttpResponse response = null;
        long start = System.currentTimeMillis();
        String exeStartTime = DateUtil.fomatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        try {  
            // 创建uri  
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {  
                for (String key : param.keySet()) {  
                    builder.addParameter(key, param.get(key));  
                }  
            }  
            URI uri = builder.build();  
  
            // 创建http GET请求  
            HttpGet httpGet = new HttpGet(uri);
            // 执行请求
            response = getHttpClient().execute(httpGet);
            // 判断返回状态是否为200  
            map.put("statusCode", response.getStatusLine().getStatusCode()+"");
            map.put("result", EntityUtils.toString(response.getEntity(), "utf-8"));
        } catch(HttpHostConnectException e){
        	e.printStackTrace();
        	map.put("statusCode", HttpStatus.GATEWAY_TIMEOUT);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
        	release(response);
        }  
        return map;
    }  
  
    public static Map<String,String> doGet(String url) {  
        return doGet(url, null);  
    }
    
    public static String doGet2Str(String url) {
		return doGet2Str(url, null);
	}
    
    public static String doGet2Str(String url, Map<String, String> param) {

		String resultString = "";
		CloseableHttpResponse response = null;
		long start = System.currentTimeMillis();
        String exeStartTime = DateUtil.fomatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
		try {
			// 创建uri
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key));
				}
			}
			URI uri = builder.build();

			// 创建http GET请求
			HttpGet httpGet = new HttpGet(uri);
//			httpGet.setConfig(timeOutConfig());
			// 执行请求
			response = getHttpClient().execute(httpGet);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			release(response);
		}
		return resultString;
	}
  
    public static Map<String,String> doPost(String url, Map<String, String> param) {  
        CloseableHttpResponse response = null;  
        Map<String,String> map = new HashMap<String,String>();
        long start = System.currentTimeMillis();
        String exeStartTime = DateUtil.fomatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        try {  
            // 创建Http Post请求  
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表  
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (String key : param.keySet()) {  
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }  
                // 模拟表单  
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,"utf-8");
                httpPost.setEntity(entity);  
            }
//            httpPost.setConfig(timeOutConfig());
            // 执行http请求
            response = getHttpClient().execute(httpPost);  
            map.put("statusCode", response.getStatusLine().getStatusCode()+"");
            map.put("result", EntityUtils.toString(response.getEntity(), "utf-8"));  
        } catch(HttpHostConnectException e){
        	e.printStackTrace();
        	map.put("statusCode", HttpStatus.GATEWAY_TIMEOUT);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
        	release(response);
        }  
        return map;  
    }  
  
    public static Map<String,String> doPost(String url) {  
        return doPost(url, null);  
    }  
      
    public static Map<String,String> doPostJson(String url, String json,Map<String,String> headers) {  
        CloseableHttpResponse response = null;
        Map<String,String> map = new HashMap<String,String>();
        long start = System.currentTimeMillis();
        String exeStartTime = DateUtil.fomatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        try {  
            // 创建Http Post请求  
            HttpPost httpPost = new HttpPost(url);
            for(String key:headers.keySet()){
            	 httpPost.addHeader(key,headers.get(key));
            }
            // 创建请求内容  
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
//            httpPost.setConfig(timeOutConfig());
            // 执行http请求
            response = getHttpClient().execute(httpPost);
            map.put("statusCode", response.getStatusLine().getStatusCode()+"");
            map.put("result", EntityUtils.toString(response.getEntity(), "utf-8"));
        } catch(HttpHostConnectException e){
        	e.printStackTrace();
        	map.put("statusCode", HttpStatus.GATEWAY_TIMEOUT);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
        	release(response);
        }  
        return map;  
    }

    public static Map<String,String> retryDoGet(String url, Map<String, String> param,Integer retryTimes) {
        return retryDoGet(url,param, retryTimes,1000L);
    }
    public static Map<String,String> retryDoGet(String url, Map<String, String> param,Integer retryTimes,Long delay) {
        Map<String,String> res = doGet(url,param);
        if(!HttpStatus.OK.equals(res.get("statusCode"))){
            Object o = RetryUtil.setRetryTimes(retryTimes,delay).retry(new HttpClientUtil(),url,param,retryTimes);
            if(o == null){
                return res;
            }else{
                return (Map<String,String>)o;
            }
        }
        return res;
    }

    public static Map<String,String> retryDoPost(String url, Map<String, String> param,Integer retryTimes) {
        return retryDoPost(url,param, retryTimes,1000L);
    }

    public static Map<String,String> retryDoPost(String url, Map<String, String> param,Integer retryTimes,Long delay) {
        Map<String,String> res = doPost(url,param);
        if(!HttpStatus.OK.equals(res.get("statusCode"))){
            Object o = RetryUtil.setRetryTimes(retryTimes,delay).retry(new HttpClientUtil(),url,param,retryTimes);
            if(o == null){
                return res;
            }else{
                return (Map<String,String>)o;
            }
        }
        return res;
    }

    /**
     * Description: 释放资源
     * @param httpResponse
     */
    private static void release(CloseableHttpResponse httpResponse) {
//    	httpClientUtil.superLog.getLogger().info("httpClient-end:"+System.currentTimeMillis());
    	try {
        	if (httpResponse != null) {
        		httpResponse.close();
        	}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    private static RequestConfig timeOutConfig() {
		RequestConfig requestConfig = RequestConfig.custom()  
		        .setConnectTimeout(DEFAULT_CONNECT_TIMEOUT).setConnectionRequestTimeout(DEFAULT_CONNECT_REQUEST_TIMEOUT)  
		        .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT).build();
		return requestConfig;
	}
    
    private static PoolingHttpClientConnectionManager initPool() {
		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
    	PoolingHttpClientConnectionManager gcm = new PoolingHttpClientConnectionManager(registry);
        gcm.setMaxTotal(DEFAULT_POOL_MAX_TOTAL);
        gcm.setDefaultMaxPerRoute(DEFAULT_POOL_MAX_PER_ROUTE);
		return gcm;
	}

}  
