package com.sxsd.car.listener;

import com.sxsd.car.utils.OSUtil;
import com.sxsd.springboot.config.WebConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ResourceBundle;

public class StartupListener implements ServletContextListener {
	public static boolean isProd;
	public static String appName;//应用名称
	public static String version;//版本
	public static String ip;//ip
    private static final Logger log =  LoggerFactory.getLogger(StartupListener.class);
    public void contextInitialized(ServletContextEvent event) {
        ApplicationContext ctx =WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());
        ini(ctx);
        /*自定义代码开始*/
        
        /*自定义代码结束*/
    }
	private void ini(ApplicationContext ctx) {
		WebConfig webConfig=(WebConfig) ctx.getBean("webConfig");
		version=ResourceBundle.getBundle("mail").getString("version");
		appName=ResourceBundle.getBundle("mail").getString("appName");
		isProd=webConfig.isProd();
		ip= OSUtil.getLocalIP();
		String[] ips=ip.split("\\.");
		String vip=ips[3]+","+version;
		log.info("vip:"+vip+",isProd:"+isProd);
	}
    public void contextDestroyed(ServletContextEvent event) {}
}