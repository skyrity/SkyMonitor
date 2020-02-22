package com.skyrity.bean;

import java.util.Date;

/**
 * @author ： VULCAN
 * @date ：2020/02/18 15:44
 * @description : ${description}
 * @path : com.skyrity.bean.AlarmPrtcl
 * @modifiedBy ：
 */
public class AlarmPrtcl {

    private int deviceType;
    private int userId;
    private int deviceId;
    private int deviceStatus;
    private int eventsRecNum;
    private String eventDateTime;
    private int recNum;
    private int color;
    private int typeId;

    private String deviceName;
    private String eventName;
    private String memo;

    @Override
    public String toString() {
        return "AlarmPrtcl{" +
                "deviceType=" + deviceType +
                ", userId=" + userId +
                ", deviceId=" + deviceId +
                ", deviceStatus=" + deviceStatus +
                ", eventsRecNum=" + eventsRecNum +
                ", eventDateTime=" + eventDateTime +
                ", recNum=" + recNum +
                ", color=" + color +
                ", typeId=" + typeId +
                ", deviceName='" + deviceName + '\'' +
                ", eventName='" + eventName + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(int deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public int getEventsRecNum() {
        return eventsRecNum;
    }

    public void setEventsRecNum(int eventsRecNum) {
        this.eventsRecNum = eventsRecNum;
    }

    public String getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(String eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    public int getRecNum() {
        return recNum;
    }

    public void setRecNum(int recNum) {
        this.recNum = recNum;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
