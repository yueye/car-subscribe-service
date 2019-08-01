/**
 * Copyright (C), 2015-2019, 联想（北京）有限公司
 * FileName: RetryMain
 * Author:   liujx
 * Date:     2019/8/1 11:10
 * Description:
 * History:
 * 描述
 */
package com.sxsd.cargateway;

import com.sxsd.car.utils.RetryUtil;

/**
 * 〈〉
 *
 * @author liujx
 * @create 2019/8/1
 */
public class RetryMain {
    private static int c = 1;
    public static void main(String [] args){
        RetryMain rm = new RetryMain();
        System.out.println(rm.test("http://url","111111"));
//        String url = "https://bss-dev.vgs.lenovo.com.cn/synUser";
//        System.out.println(HttpClientUtil.retryDoGet(url,null,3));
    }

    private String test(String url,String params){
        System.out.println("url:"+url+" params:"+params);
        String s = test1();
        if(++c<3){
            Object res = null;
            try {
                res = RetryUtil.setRetryTimes(3,1000L).retry(this.getClass().newInstance(),url,params);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(res == null){
                return "s1111";
            }else{
                return (String) res;
            }
        }
        return s;

    }

    private String test1(){
        return "2222";
    }
}