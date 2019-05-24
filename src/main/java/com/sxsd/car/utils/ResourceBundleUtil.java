package com.sxsd.car.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ResourceBundle;

public class ResourceBundleUtil {
	
	private static final Logger log =  LoggerFactory.getLogger(ResourceBundleUtil.class);
	private static final String default_config = "config";
	
	public static String get(String key){
		return get(key,default_config);
	}
	
	public static String get(String key,String file){
		return get(key,file,null);
	}

	public static String get(String key,String file,String defaultKey){
		try{
			ResourceBundle rb = ResourceBundle.getBundle(file);
			if(rb.containsKey(key)){
				return rb.getString(key);
			}
			if(StringUtils.isNotBlank(defaultKey) && rb.containsKey(defaultKey)){
				return rb.getString(defaultKey);
			}
		}catch (Exception e) {
			log.info("获取配置文件错误");
		}
		return "";
	}

	public static Boolean containsKey(String key){
		ResourceBundle rb = ResourceBundle.getBundle(default_config);
		if(rb.containsKey(key)){
			return true;
		}
		return false;
	}
}
