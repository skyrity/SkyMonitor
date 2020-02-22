package com.skyrity.client;

import com.skyrity.bean.AccessPrtcl;
import com.skyrity.bean.AlarmPrtcl;
import com.skyrity.bean.LogPrtcl;
import com.skyrity.bean.TourPrtcl;

/**
 * @author ： VULCAN
 * @date ：2020/02/19 11:05
 * @description : ${description}
 * @path : com.skyrity.client.EventsCallBack
 */
public interface EventsCallBack {
    /**
     * 收到访问事件接口
     * @param accessPrtcl 访问事件对象
     */
    void revAccessEvent(AccessPrtcl accessPrtcl);

    /**
     * 收到报警事件接口
     * @param alarmPrtcl 报警事件对象
     */
    void revAlarmEvent(AlarmPrtcl alarmPrtcl);

    /**
     * 收到巡更事件接口
     * @param tourPrtcl 巡更事件对象
     */
    void revTourEvent(TourPrtcl tourPrtcl);

    /**
     * 收到日志事件接口
     * @param logPrtcl 日志事件对象
     */
    void revLogEvent(LogPrtcl logPrtcl);
}
