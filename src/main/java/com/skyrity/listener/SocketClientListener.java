package com.skyrity.listener;

import com.skyrity.bean.*;
import com.skyrity.client.EventsCallBack;
import com.skyrity.client.SocketClient;
import com.skyrity.websocket.WebSocketServer;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;

/**
 * @author ： VULCAN
 * @date ：2020/02/18 11:19
 * @description : 启动一个Servlet侦听器，负责自启动SockeClient
 * @path : com.skyrity.listener.SocketClientListener
 * @modifiedBy ：
 */
public class SocketClientListener implements ServletContextListener, EventsCallBack {
    private final Logger logger = LoggerFactory.getLogger(SocketClient.class);
    private SocketClient socketClient;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("远程连接服务启动...");
        socketClient = new SocketClient(this);
        Timer timerRefresh = new Timer();
        timerRefresh.schedule(new MyClientRefreshTask(), 1000, 5000);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        socketClient.close();
        logger.info("远程连接服务停止！");
    }

    @Override
    public void revAccessEvent(AccessPrtcl accessPrtcl) {

        SendMsg sendMsg = new SendMsg();
        sendMsg.setType("access");
        sendMsg.setEvent(accessPrtcl);
        hanlderEvent(sendMsg);


    }

    @Override
    public void revAlarmEvent(AlarmPrtcl alarmPrtcl) {
        SendMsg sendMsg = new SendMsg();
        sendMsg.setType("alarm");
        sendMsg.setEvent(alarmPrtcl);
        hanlderEvent(sendMsg);
    }

    @Override
    public void revTourEvent(TourPrtcl tourPrtcl) {
        SendMsg sendMsg = new SendMsg();
        sendMsg.setType("tour");
        sendMsg.setEvent(tourPrtcl);
        hanlderEvent(sendMsg);
    }

    @Override
    public void revLogEvent(LogPrtcl logPrtcl) {
        SendMsg sendMsg = new SendMsg();
        sendMsg.setType("log");
        sendMsg.setEvent(logPrtcl);
        hanlderEvent(sendMsg);
    }

    private void hanlderEvent(SendMsg message) {

        JSONObject jsonObject = JSONObject.fromObject(message);
        //JsonConfig jsonConfig=new JsonConfig();
        //jsonConfig.setExcludes(new String[]{"eventDateTime", ""});
        for (WebSocketServer item : WebSocketServer.webSocketSet) {
            try {
                item.sendMessage(jsonObject.toString());
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 刷新任务，刷新当前的有效连接
     *
     * @author macai
     */
    class MyClientRefreshTask extends java.util.TimerTask {
        private boolean isFirst = true;

        public void run() {
            try {
                if (!socketClient.isConnected()) {

                    if (!isFirst) {
                        logger.info("重新尝试连接门禁服务器...");
                    }
                    isFirst = false;
                        int ret=socketClient.open("192.168.197.1", 900);
                        if(ret>0) {
                            logger.info("连接门禁服务器成功！");
                            socketClient.register();
                        }else {
                            logger.info("连接门禁服务器失败！");
                        }
                } else if (new Date().getTime() - socketClient.getOnLineTime().getTime() > 30000) {
                    //超过20秒没有收到心跳命令，判断为离线
                    // 关闭Socket连接
                    socketClient.close();
                    logger.info("与门禁服务器已断开连接!");
                    socketClient.setOnLineTime(new Date());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
