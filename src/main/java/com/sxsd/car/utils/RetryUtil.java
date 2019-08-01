/**
 * Copyright (C), 2015-2019, 联想（北京）有限公司
 * FileName: df
 * Author:   liujx
 * Date:     2019/8/1 11:35
 * Description:
 * History:
 * 描述
 */
package com.sxsd.car.utils;

import java.lang.reflect.Method;

/**
 * 〈〉
 *
 * @author liujx
 * @create 2019/8/1
 */
public class RetryUtil {

    private static ThreadLocal<Integer> retryTimesInThread = new ThreadLocal<>();
    private static ThreadLocal<Long> retryDelayInThread = new ThreadLocal<>();
    private static final Integer RETRY_TIMES = 3;
    private static final long DELAY = 5000;

    public RetryUtil() {
        if (retryTimesInThread.get() == null) {
            retryTimesInThread.set(RETRY_TIMES);
        }
        if (retryDelayInThread.get() == null) {
            retryDelayInThread.set(DELAY);
        }

    }

    /**
     * 设置当前方法重试次数及间隔
     *
     * @param retryTimes
     * @return
     */
    public static RetryUtil setRetryTimes(Integer retryTimes,Long delay) {
        if (retryTimesInThread.get() == null) {
            retryTimesInThread.set(retryTimes);
        }
        if (retryDelayInThread.get() == null) {
            retryDelayInThread.set(delay);
        }

        return new RetryUtil();
    }

    /**
     * 重试当前方法
     * <p>按顺序传入调用者方法的所有参数</p>
     *
     * @param args
     * @return
     */
    public Object retry(Object... args) {
        try {
            Integer retryTimes = retryTimesInThread.get();
            if (retryTimes <= 0) {
                retryTimesInThread.remove();
                retryDelayInThread.remove();
                return null;
            }
            retryTimesInThread.set(--retryTimes);
            Thread.sleep(retryDelayInThread.get());
            String upperClassName = Thread.currentThread().getStackTrace()[2].getClassName();
            String upperMethodName = Thread.currentThread().getStackTrace()[2].getMethodName();

            Class clazz = Class.forName(upperClassName);
            Object targetObject = args[0];
            Object[] targetArgs = new Object[args.length - 1];
            System.arraycopy(args, 1, targetArgs, 0, targetArgs.length);
            Method targetMethod = null;
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.getName().equals(upperMethodName)) {
                    targetMethod = method;
                    break;
                }
            }
            if (targetMethod == null)
                return null;
            targetMethod.setAccessible(true);
            return targetMethod.invoke(targetObject, targetArgs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}