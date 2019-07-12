/**
 * Copyright (C), 2015-2019, 联想（北京）有限公司
 * FileName: VehicleProps
 * Author:   liujx
 * Date:     2019/5/23 16:59
 * Description:
 * History:
 * 描述
 */
package com.sxsd.springboot.vo;

/**
 * 〈〉
 *
 * @author liujx
 * @create 2019/5/23
 */
public class EzvizopenToken {
    private DataProps data;
    private String code;
    private String msg;
    public static class DataProps{
        private String accessToken;
        private Long expireTime;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public Long getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(Long expireTime) {
            this.expireTime = expireTime;
        }
    }

    public DataProps getData() {
        return data;
    }

    public void setData(DataProps data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}