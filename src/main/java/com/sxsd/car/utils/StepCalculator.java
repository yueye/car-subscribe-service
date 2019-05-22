/**
 * Copyright (C), 2015-2019, 联想（北京）有限公司
 * FileName: StepCalculator
 * Author:   liujx
 * Date:     2019/5/13 17:45
 * Description:
 * History:
 * 描述
 */
package com.sxsd.car.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 〈〉
 *
 * @author liujx
 * @create 2019/5/13
 */
public class StepCalculator {
    private static Long[] steps = new Long[]{0l, 240l, 840l, 1440l, 5040l, 12240l, 24840l, 78840l}; //s 4m,10m,10m,1h,2h,6h,15h

    public static Long getTime(Integer index, Long currentTime) {
        if (null == index || null == currentTime) return null;

        if (steps.length <= index) return null;

        return currentTime + steps[index] * 1000l;
    }

    public static Long getNextTime(Integer index, Long thisTime) {
        if (null == index || null == thisTime) return null;

        if (steps.length <= index + 1) return null;

        return thisTime + steps[index + 1] * 1000l;
    }

    public static String getMinute(Long time) {
        if (null == time) return null;

        return DateUtil.formateDate(time, "yyyyMMddHHmm");
    }

    public static String getKey(Long time, Integer index) {
        if (null == time || null == index) return null;

        String minute = getMinute(getTime(index, time));
        if (StringUtils.isEmpty(minute)) return null;

        return minute;
    }

    public static String getNextKey(Long time, Integer index) {
        if (null == time || null == index) return null;

        String minute = getMinute(getNextTime(index, time));
        if (StringUtils.isEmpty(minute)) return null;

        return minute;
    }
}