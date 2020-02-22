package com.skyrity.bean;

import java.util.Date;

/**
 * @author ： VULCAN
 * @date ：2020/02/18 15:06
 * @description : Sky协议包含的字段
 * @path : com.skyrity.bean.AccessPrtcl
 */
public class AccessPrtcl {
    private int recNum;
    private int loginId;
    private int pcId;
    private int deviceId;
    private int cardId;
    private int eventRecNum;
    private int color;
    private int typeId;
    private int userId;
    private int cardType;
    private int messageType;
    private String eventDateTime;
    private String departmentName;
    private String personNo;
    private String userName;
    private String deviceName;
    private String eventName;
    private String memo;


    @Override
    public String toString() {
        return "AccessPrtcl{" +
                "recNum=" + recNum +
                ", loginId=" + loginId +
                ", pcId=" + pcId +
                ", deviceId=" + deviceId +
                ", cardId=" + cardId +
                ", eventRecNum=" + eventRecNum +
                ", color=" + color +
                ", typeId=" + typeId +
                ", userId=" + userId +
                ", cardType=" + cardType +
                ", messageType=" + messageType +
                ", eventDateTime=" + eventDateTime +
                ", departmentName='" + departmentName + '\'' +
                ", personNo='" + personNo + '\'' +
                ", userName='" + userName + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", eventName='" + eventName + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }


    public int getRecNum() {
        return recNum;
    }

    public void setRecNum(int recNum) {
        this.recNum = recNum;
    }

    public int getLoginId() {
        return loginId;
    }

    public void setLoginId(int loginId) {
        this.loginId = loginId;
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

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getEventRecNum() {
        return eventRecNum;
    }

    public void setEventRecNum(int eventRecNum) {
        this.eventRecNum = eventRecNum;
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

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public String  getEventDateTime() {
        return this.eventDateTime;
    }

    public void setEventDateTime(String eventDateTime) {
        this.eventDateTime = eventDateTime;
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
