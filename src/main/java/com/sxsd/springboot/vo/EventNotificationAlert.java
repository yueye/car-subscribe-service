/**
 * Copyright (C), 2015-2019, 联想（北京）有限公司
 * FileName: EventNotificationAlert
 * Author:   liujx
 * Date:     2019/7/4 16:08
 * Description:
 * History:
 * 描述
 */
package com.sxsd.springboot.vo;

/**
 * 〈〉
 *
 * @author liujx
 * @create 2019/7/4
 */
public class EventNotificationAlert {
    private String ipAddress;
    //ANPR
    private String eventType;
    private ANPR ANPR;

    public static class ANPR{
        //蓝京Q37MQ0
        private String licensePlate;
        //1
        private String line;
        //vehicle
        private String vehicleType;
        private EntranceInfo EntranceInfo;

        public String getLicensePlate() {
            return licensePlate;
        }

        public void setLicensePlate(String licensePlate) {
            this.licensePlate = licensePlate;
        }

        public String getLine() {
            return line;
        }

        public void setLine(String line) {
            this.line = line;
        }

        public String getVehicleType() {
            return vehicleType;
        }

        public void setVehicleType(String vehicleType) {
            this.vehicleType = vehicleType;
        }

        public EventNotificationAlert.EntranceInfo getEntranceInfo() {
            return EntranceInfo;
        }

        public void setEntranceInfo(EventNotificationAlert.EntranceInfo entranceInfo) {
            EntranceInfo = entranceInfo;
        }
    }

    public static class EntranceInfo{
        private String direction;

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public EventNotificationAlert.ANPR getANPR() {
        return ANPR;
    }

    public void setANPR(EventNotificationAlert.ANPR ANPR) {
        this.ANPR = ANPR;
    }
}