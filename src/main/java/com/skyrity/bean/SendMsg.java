package com.skyrity.bean;

/**
 * @author ： VULCAN
 * @date ：2020/02/19 14:22
 * @description : ${description}
 * @path : com.skyrity.bean.SendMsg
 * @modifiedBy ：
 */
public class SendMsg {
    private String type;
    private Object event;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getEvent() {
        return event;
    }

    public void setEvent(Object event) {
        this.event = event;
    }
}
