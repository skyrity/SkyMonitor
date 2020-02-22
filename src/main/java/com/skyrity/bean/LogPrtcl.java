package com.skyrity.bean;

import java.util.Date;

/**
 * @author ： VULCAN
 * @date ：2020/02/18 15:53
 * @description : ${description}
 * @path : com.skyrity.bean.LogPrtcl
 * @modifiedBy ：
 */
public class LogPrtcl {

    private int userId;
    private int pcId;
    private int deviceId;
    private int eventRecNum;
    private String eventDateTime;
    private int color;
    private int typeId;
    private String eventName;
    private String deviceName;
    private String hostName;
    private String userName;
    private String memo;

    @Override
    public String toString() {
        return "LogPrtcl{" +
                "userId=" + userId +
                ", pcId=" + pcId +
                ", deviceId=" + deviceId +
                ", eventRecNum=" + eventRecNum +
                ", eventDateTime=" + eventDateTime +
                ", color=" + color +
                ", typeId=" + typeId +
                ", eventName='" + eventName + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", hostName='" + hostName + '\'' +
                ", userName='" + userName + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPcId() {
        return pcId;
    }

    public void setPcId(int pcId) {
        this.pcId = pcId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getEventRecNum() {
        return eventRecNum;
    }

    public void setEventRecNum(int eventRecNum) {
        this.eventRecNum = eventRecNum;
    }

    public String getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(String eventDateTime) {
        this.eventDateTime = eventDateTime;
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

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
