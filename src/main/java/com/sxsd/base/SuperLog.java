package com.sxsd.base;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("superLog")
public class SuperLog {
	//使用ThreadLocal保存Logger变量  
	public final static int THREAD_COUNT=5;
	static int i=0;
	private static ThreadLocal<Logger> currentLog = new ThreadLocal<Logger>();  
	private static ThreadLocal<List<Logger>> currentLogs = new ThreadLocal<List<Logger>>();  
	private static ThreadLocal<Logger> currentMainLog = new ThreadLocal<Logger>();  

    private static Logger Default=LoggerFactory.getLogger(Default.class);
	static {
		//resetLogs(CommonLogs.logs,CommonLogs.threadMain);
	}

	public static void resetLogs(List<Logger> _logs,Logger _threadMain) {
		currentMainLog.set(_threadMain);
		currentLogs.set(_logs);
	}
	public void setCurrentLog(Logger value) {
		currentLog.set(value);
	}
	public Logger getMaiLog() {//在子线程中使用，方便在主线程中打印信息
		if(Context.isThreadType()) {
			return currentMainLog.get();
		}else {
			return currentLog.get();
		}
	}
	public Logger getLogger() {

		if (currentLog.get() == null) {
			if(Context.isBatchType()) {
				synchronized (currentLogs) {
					if (currentLog.get() == null) {
						if(Context.isThreadType()) {
							Logger value = currentLogs.get().get(i%THREAD_COUNT);
							currentLog.set(value);
							i++;
						}else {
							currentLog.set(currentMainLog.get());
						}
					}
				}
			}else {
				currentLog.set(Default);
			}
		}
		return currentLog.get();
	}

	public static class Default{}
}
