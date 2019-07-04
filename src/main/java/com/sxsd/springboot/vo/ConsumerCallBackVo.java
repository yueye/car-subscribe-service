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
    private HeadVo header;
    private BodyVo body;


    public static class HeadVo{
        //通道id 0
        private String channelNo;
        //消息id 5d1b4af9e2c9ca0323e2894c
        private String messageId;
        //消息类型 Constants
        private String type;
        //设备号 D28746868
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
        //设备类型 DS-2CDVT-FZCMP-S
        private String devType;
        //注册时间 2019-06-25 12:39:32
        private String regTime;
        //ip地址 60.247.104.85
        private String natIp;
        //消息类型 OFFLINE
        private String msgType;
        //序列号 D28746868
        private String subSerial;
        //触发时间 2019-07-02 20:15:52
        private String occurTime;
        //设备名称
        private String deviceName;
        //dasId das_10.215.131.22_6807
        private String dasId;

        //payload ys.open.isapi专用
        private String payload;


        private String picUrl;
        private String detectTime;
        private String msgSeq;
        private String plateNumber;

        public String getDevType() {
            return devType;
        }

        public void setDevType(String devType) {
            this.devType = devType;
        }

        public String getRegTime() {
            return regTime;
        }

        public void setRegTime(String regTime) {
            this.regTime = regTime;
        }

        public String getNatIp() {
            return natIp;
        }

        public void setNatIp(String natIp) {
            this.natIp = natIp;
        }

        public String getMsgType() {
            return msgType;
        }

        public void setMsgType(String msgType) {
            this.msgType = msgType;
        }

        public String getSubSerial() {
            return subSerial;
        }

        public void setSubSerial(String subSerial) {
            this.subSerial = subSerial;
        }

        public String getOccurTime() {
            return occurTime;
        }

        public void setOccurTime(String occurTime) {
            this.occurTime = occurTime;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public String getDasId() {
            return dasId;
        }

        public void setDasId(String dasId) {
            this.dasId = dasId;
        }

        public String getPayload() {
            return payload;
        }

        public void setPayload(String payload) {
            this.payload = payload;
        }

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

    public HeadVo getHeader() {
        return header;
    }

    public void setHeader(HeadVo header) {
        this.header = header;
    }

    public BodyVo getBody() {
        return body;
    }

    public void setBody(BodyVo body) {
        this.body = body;
    }
}