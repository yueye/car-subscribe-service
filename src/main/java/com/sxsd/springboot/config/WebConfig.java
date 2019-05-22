package com.sxsd.springboot.config;

import com.sxsd.car.listener.StartupListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;
import java.util.EventListener;
import java.util.List;
 
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Bean
	public ServletListenerRegistrationBean<EventListener> getDemoListener1() {
		ServletListenerRegistrationBean<EventListener> registrationBean = new ServletListenerRegistrationBean<EventListener>();
		registrationBean.setListener(new StartupListener());
		registrationBean.setOrder(2);
		return registrationBean;
	}

	@Autowired
    private Environment env;
	public boolean isProd() {
		 String[] arr = env.getActiveProfiles();
         List<String> list=Arrays.asList(arr);
         if (list.contains("dev")||list.contains("test")) {
        	 	return false;
         } else{
        	 	return true;
         }
	}
}