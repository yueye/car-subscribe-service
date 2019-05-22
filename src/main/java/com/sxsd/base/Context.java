package com.sxsd.base;

public class Context {
	public static Boolean isBatchType() {
		return isBatchType.get()!=null;
	}
	public static void setBatchType() {
		Context.isBatchType.set(true);
	}
	public static Boolean isThreadType() {
		return isThreadType.get()!=null;
	}
	public static void setThreadType() {
		Context.isThreadType.set(true);
	}
	public static Boolean isDetailLog() {
		return isDetailLog.get()!=null;
	}
	public static void setDetailLog() {
		Context.isDetailLog.set(true);
	}
	private static ThreadLocal<Boolean> isBatchType =new ThreadLocal<Boolean>();//是否批量更新
	private static ThreadLocal<Boolean> isThreadType = new ThreadLocal<Boolean>(); //线程模式
	private static ThreadLocal<Boolean> isDetailLog = new ThreadLocal<Boolean>(); //批量模式

}
