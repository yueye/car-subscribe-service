/**
 * Copyright (C), 2015-2019, 联想（北京）有限公司
 * FileName: df
 * Author:   liujx
 * Date:     2019/7/11 17:27
 * Description:
 * History:
 * 描述
 */
package com.sxsd.springboot.vo;

import java.util.List;

/**
 * 〈〉
 *
 * @author liujx
 * @create 2019/5/23
 */
public class VehiclePropsAndTraffic {
    private String requestId;
    private List<Props> data;
    private String code;
    private String msg;
    public static class Props{
        //props
        private String plateNumber;
        private VehicleColor vehicleColor;
        private VehicleType vehicleType;
        private String vehicleLogo;
        private String vehicleSublogo;
        private String vehicleModel;
        //traffic
        private MajorSafeBelt majorSafeBelt;
        private ViceSafeBelt viceSafeBelt;
        private MajorSunVisor majorSunVisor;
        private ViceSunVisor viceSunVisor;
        private DangeMark dangeMark;
        private YellowLabel yellowLabel;
        private Phoning phoning;
        private Pendant pendant;
        //common
        private Rect rect;

        public String getPlateNumber() {
            return plateNumber;
        }

        public void setPlateNumber(String plateNumber) {
            this.plateNumber = plateNumber;
        }

        public VehicleColor getVehicleColor() {
            return vehicleColor;
        }

        public void setVehicleColor(VehicleColor vehicleColor) {
            this.vehicleColor = vehicleColor;
        }

        public VehicleType getVehicleType() {
            return vehicleType;
        }

        public void setVehicleType(VehicleType vehicleType) {
            this.vehicleType = vehicleType;
        }

        public String getVehicleLogo() {
            return vehicleLogo;
        }

        public void setVehicleLogo(String vehicleLogo) {
            this.vehicleLogo = vehicleLogo;
        }

        public String getVehicleSublogo() {
            return vehicleSublogo;
        }

        public void setVehicleSublogo(String vehicleSublogo) {
            this.vehicleSublogo = vehicleSublogo;
        }

        public String getVehicleModel() {
            return vehicleModel;
        }

        public void setVehicleModel(String vehicleModel) {
            this.vehicleModel = vehicleModel;
        }

        public MajorSafeBelt getMajorSafeBelt() {
            return majorSafeBelt;
        }

        public void setMajorSafeBelt(MajorSafeBelt majorSafeBelt) {
            this.majorSafeBelt = majorSafeBelt;
        }

        public ViceSafeBelt getViceSafeBelt() {
            return viceSafeBelt;
        }

        public void setViceSafeBelt(ViceSafeBelt viceSafeBelt) {
            this.viceSafeBelt = viceSafeBelt;
        }

        public MajorSunVisor getMajorSunVisor() {
            return majorSunVisor;
        }

        public void setMajorSunVisor(MajorSunVisor majorSunVisor) {
            this.majorSunVisor = majorSunVisor;
        }

        public ViceSunVisor getViceSunVisor() {
            return viceSunVisor;
        }

        public void setViceSunVisor(ViceSunVisor viceSunVisor) {
            this.viceSunVisor = viceSunVisor;
        }

        public DangeMark getDangeMark() {
            return dangeMark;
        }

        public void setDangeMark(DangeMark dangeMark) {
            this.dangeMark = dangeMark;
        }

        public YellowLabel getYellowLabel() {
            return yellowLabel;
        }

        public void setYellowLabel(YellowLabel yellowLabel) {
            this.yellowLabel = yellowLabel;
        }

        public Phoning getPhoning() {
            return phoning;
        }

        public void setPhoning(Phoning phoning) {
            this.phoning = phoning;
        }

        public Pendant getPendant() {
            return pendant;
        }

        public void setPendant(Pendant pendant) {
            this.pendant = pendant;
        }

        public Rect getRect() {
            return rect;
        }

        public void setRect(Rect rect) {
            this.rect = rect;
        }
    }

    public static class VehicleColor{
        private String val;
        private String des;

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }
    }

    public static class VehicleType{
        private String val;
        private String des;

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }
    }

    public static class MajorSafeBelt{
        private String val;
        private String des;

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }
    }
    public static class ViceSafeBelt{
        private String val;
        private String des;

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }
    }
    public static class MajorSunVisor{
        private String val;
        private String des;

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }
    }
    public static class ViceSunVisor{
        private String val;
        private String des;

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }
    }
    public static class DangeMark{
        private String val;
        private String des;

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }
    }
    public static class YellowLabel{
        private String val;
        private String des;

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }
    }
    public static class Phoning{
        private String val;
        private String des;

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }
    }
    public static class Pendant{
        private String val;
        private String des;

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }
    }

    public static class Rect{
        private String x;
        private String y;
        private String width;
        private String height;

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public List<Props> getData() {
        return data;
    }

    public void setData(List<Props> data) {
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