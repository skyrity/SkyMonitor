package com.skyrity.bean;

import java.util.Date;

/**
 * @author ： VULCAN
 * @date ：2020/02/18 16:01
 * @description : ${description}
 * @path : com.skyrity.bean.TourPrtcl
 * @modifiedBy ：
 */
public class TourPrtcl {

    private int cardId;
    private int tourLineId;
    private int pcId;
    private int loginId;
    private int deviceId;
    private int eventRecNum;
    private String EventDateTime;
    private int color;
    private int typeId;
    private int userId;
    private String deviceName;
    private String eventName;
    private String departmentName;
    private String personNo;
    private String userName;
    private String tourLineName;
    private String memo;

    @Override
    public String toString() {
        return "TourPrtcl{" +
                "cardId=" + cardId +
                ", tourLineId=" + tourLineId +
                ", pcId=" + pcId +
                ", loginId=" + loginId +
                ", deviceId=" + deviceId +
                ", eventRecNum=" + eventRecNum +
                ", EventDateTime=" + EventDateTime +
                ", color=" + color +
                ", typeId=" + typeId +
                ", userId=" + userId +
                ", deviceName='" + deviceName + '\'' +
                ", eventName='" + eventName + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", personNo='" + personNo + '\'' +
                ", userName='" + userName + '\'' +
                ", tourLineName='" + tourLineName + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getTourLineId() {
        return tourLineId;
    }

    public void setTourLineId(int tourLineId) {
        this.tourLineId = tourLineId;
    }

    public int getPcId() {
        return pcId;
    }

    public void setPcId(int pcId) {
        this.pcId = pcId;
    }

    public int getLoginId() {
        return loginId;
    }

    public void setLoginId(int loginId) {
        this.loginId = loginId;
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
        return EventDateTime;
    }

    public void setEventDateTime(String eventDateTime) {
        EventDateTime = eventDateTime;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPersonNo() {
        return personNo;
    }

    public void setPersonNo(String personNo) {
        this.personNo = personNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTourLineName() {
        return tourLineName;
    }

    public void setTourLineName(String tourLineName) {
        this.tourLineName = tourLineName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
