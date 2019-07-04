/**
 * Copyright (C), 2015-2019, 联想（北京）有限公司
 * FileName: ConsumerCallBackVo
 * Author:   liujx
 * Date:     2019/7/1 17:44
 * Description:
 * History:
 * 描述
 */
package com.sxsd.springboot.vo;

/**
 * 〈〉
 *
 * @author liujx
 * @create 2019/7/1
 */
public class ConsumerCallBackVo {
    private HeadVo head;
    private BodyVo body;


    public static class HeadVo{
        private String channelNo;
        private String messageId;
        private String type;//ys.open.vehicle
        private String deviceId;

        public String getChannelNo() {
            return channelNo;
        }

        public void setChannelNo(String channelNo) {
            this.channelNo = channelNo;
        }

        public String getMessageId() {
            return messageId;
        }

        public void setMessageId(String messageId) {
            this.messageId = messageId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }
    }

    public static class BodyVo{
        private String picUrl;
        private String detectTime;
        private String msgSeq;
        private String plateNumber;

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getDetectTime() {
            return detectTime;
        }

        public void setDetectTime(String detectTime) {
            this.detectTime = detectTime;
        }

        public String getMsgSeq() {
            return msgSeq;
        }

        public void setMsgSeq(String msgSeq) {
            this.msgSeq = msgSeq;
        }

        public String getPlateNumber() {
            return plateNumber;
        }

        public void setPlateNumber(String plateNumber) {
            this.plateNumber = plateNumber;
        }
    }

    public HeadVo getHead() {
        return head;
    }

    public void setHead(HeadVo head) {
        this.head = head;
    }

    public BodyVo getBody() {
        return body;
    }

    public void setBody(BodyVo body) {
        this.body = body;
    }
}