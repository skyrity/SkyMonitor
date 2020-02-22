package com.skyrity.client;

import com.skyrity.bean.*;
import com.skyrity.socket.SkySocketImpl;
import com.skyrity.utils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ： macai
 * @date ：2020/02/18 11:50
 * @description : 与Sky2010中间层连接的协议库，具体作用是接收所有事件信息。
 */
public class SocketClient extends SkySocketImpl {

    private final Logger logger = LoggerFactory.getLogger(SocketClient.class);

    //private final static int CMD_ONLINE = 9999;//在线命令
    private final static int CMD_ACCESSEVENTS = 1000;//访问事件命令
    private final static int CMD_ALARMEVENTS = 1001;//报警事件命令
    private final static int CMD_LOGEVENTS = 1002;//日志事件命令
    private final static int CMD_TOUREVENTS = 1004;//巡更事件命令
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    private Lock lock = new ReentrantLock();
    private EventsThread eventsThread;

    public void setOnLineTime(Date onLineTime) {
        this.onLineTime = onLineTime;
    }

    public Date getOnLineTime() {
        return onLineTime;
    }

    private Date onLineTime = new Date();
    EventsCallBack eventsCallBack = null;

    public SocketClient(EventsCallBack eventsCallBack) {
        this.eventsCallBack = eventsCallBack;
    }

    private void connect() {

        eventsThread = new EventsThread();
        eventsThread.start();
    }

    private void disConnect() {
        try {
            if (eventsThread != null) {
                // 终止线程
                eventsThread.interrupt();
                // 等待线程结束
                eventsThread.join();
                eventsThread = null;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int open(String host, int port) {

        int ret = super.open(host, port);

        if (ret > 0) {
            connect();
        }
        return ret;
    }

    public void close()   {
        disConnect();
        super.close();

    }

    public int register() throws IOException {
        String msg = "1,11979,0";
        byte[] sendBuffer = msg.getBytes();

        write(sendBuffer);
        return 1;

    }

    /**
     * 获得接收协议内容
     *
     * @return 返回获得的内容数据数组
     */
    private byte[] getContent() throws IOException {
        byte[] ret = null;
        int HEADSIZE = 7;
        byte[] headerBuffer = new byte[HEADSIZE];
        headerBuffer[0] = (byte) 0xF1;
        byte[] bHead = new byte[HEADSIZE - 1];
        lock.lock();
        try {
            // 读协议头字节 0xF1
            if (getHeaderByte(100)) {
                int headSize = read(bHead, HEADSIZE - 1);
                if (headSize == HEADSIZE - 1) {
                    System.arraycopy(bHead, 0, headerBuffer, 1, bHead.length);
                    int packageSize = headerBuffer[5] & 0xFF
                            | (headerBuffer[4] & 0xFF) << 8
                            | (headerBuffer[3] & 0xFF) << 16
                            | (headerBuffer[2] & 0xFF) << 24;

                    byte[] bContent = new byte[packageSize - HEADSIZE];
                    int contentSize = read(bContent, packageSize - HEADSIZE);
                    if (contentSize == packageSize - HEADSIZE) {
                        byte[] revBuffer = new byte[packageSize];
                        System.arraycopy(headerBuffer, 0, revBuffer, 0,
                                headerBuffer.length);
                        System.arraycopy(bContent, 0, revBuffer,
                                headerBuffer.length, contentSize);
                       //logger.info("收到数据：" + Converter.toHexString(revBuffer, " "));
                        ret = revBuffer;

                    }
                }
            }
        } finally {
            lock.unlock();
        }
        return ret;
    }

    /**
     * 获得头字节
     *
     * @return False=没有，True=有
     */
    private boolean getHeaderByte() throws IOException {
        boolean ret = false;
        int revSize;
        byte[] buffer = new byte[1];
        revSize = read(buffer, 1);
        if (revSize == 1) {
            while (buffer[0] != (byte) 0xF1) {
                revSize = read(buffer, 1);
                if (revSize <= 0) {
                    break;
                }
            }
            if (buffer[0] == (byte) 0xF1) {
                ret = true;
            }
        }
        return ret;
    }

    /**
     * 获得头字节
     *
     * @return False=没有，True=有
     */
    private boolean getHeaderByte(int waitTime) throws IOException {
        boolean ret = false;
        int revSize;
        byte[] buffer = new byte[1];
        revSize = read(buffer, 1, 100);
        if (revSize == 1) {
            while (buffer[0] != (byte) 0xF1) {
                revSize = read(buffer, 1, 100);
                if (revSize <= 0) {
                    break;
                }
            }
            if (buffer[0] == (byte) 0xF1) {
                ret = true;
            }
        }
        return ret;
    }

    /**
     * 协议处理函数
     * 协议结构：     Head(17Byte) +Data(可变) + CRC(2Byte)
     * Head:        HeadByte(1Byte) +Status(1Byt4e) + PackageSize(4Byte) + Type(1Byte)
     * +0 HeadByte:    0xF1
     * +1 Status:      协议状态
     * 7bit:      0=发送，1=回应
     * 6..0bit:   Address
     * +2 PackageSize: 协议包长度
     * +6 Type:        协议包类型
     * Data: Command(4Byte)  + DataLength(4Byte) + Data(DataLength)
     * +7 Command:     命令号
     * +11 DataLength:  数据长度
     * +15 Data:        数据
     * CRC:         0000 (2Byte) 保留
     *
     * @return 返回获得的内容数据数组
     */
    private int handler_Protocol(byte[] revBuffer) {
        final int FRAME_DATA_OFFSET = 19;
        int ret = 0;
        int prtclNo = Converter.bytes2int(revBuffer, 7);
        int dataLength = Converter.bytes2int(revBuffer, 11);
        byte[] frameData = new byte[dataLength];
        System.arraycopy(revBuffer, FRAME_DATA_OFFSET, frameData, 0, dataLength);
        switch (prtclNo) {
            case CMD_ACCESSEVENTS:
                ret = handlerAccessEvent(frameData);
                break;
            case CMD_ALARMEVENTS:
                ret = handlerAlarmEvent(frameData);
                break;
            case CMD_LOGEVENTS:
                ret = handlerLogEvent(frameData);
                break;
            case CMD_TOUREVENTS:
                ret = handlerTourEvent(frameData);
                break;
        }
        return ret;
    }


    private int handlerAccessEvent(byte[] frameData) {
        int ret = 0;
        try {
            AccessPrtcl accessPrtcl = new AccessPrtcl();
            accessPrtcl.setLoginId(Converter.bytes2int(frameData));
            accessPrtcl.setPcId(Converter.bytes2int(frameData, 4));
            accessPrtcl.setDeviceId(Converter.bytes2int(frameData, +8));
            accessPrtcl.setCardId(Converter.bytes2int(frameData, 12));
            accessPrtcl.setEventRecNum(Converter.bytes2int(frameData, 16));
            byte[] bDate = new byte[8];
            System.arraycopy(frameData, 20, bDate, 0, 8);
            bDate=Converter.reverseArray(bDate);

            Date eventDateTime = Converter.delphiTimeToJavaTime(Converter.bytes2Double(bDate));
            accessPrtcl.setEventDateTime(sdf.format(eventDateTime));
            accessPrtcl.setRecNum(Converter.bytes2int(frameData, 28));
            byte[] bMsg = new byte[frameData.length - 32];
            System.arraycopy(frameData, 32, bMsg, 0, frameData.length - 32);
            String msg = new String(bMsg, "GBK");
            String[] fields = msg.split(";");
            if (fields.length == 1) {
                accessPrtcl.setMemo(fields[0]);
            } else {

                accessPrtcl.setMemo(fields[0]);
                accessPrtcl.setColor(Integer.parseInt(fields[1]));
                accessPrtcl.setTypeId(Integer.parseInt(fields[2]));
                accessPrtcl.setUserId(Integer.parseInt(fields[3]));
                accessPrtcl.setDepartmentName(fields[4]);
                accessPrtcl.setPersonNo(fields[5]);
                accessPrtcl.setUserName(fields[6]);
                accessPrtcl.setDeviceName(fields[7]);
                accessPrtcl.setEventName(fields[8]);
                accessPrtcl.setMessageType(Integer.parseInt(fields[9]));
                accessPrtcl.setCardType(Integer.parseInt(fields[10]));

            }
            logger.info("收到访问事件：" + accessPrtcl.toString());
            if (eventsCallBack != null) {
                eventsCallBack.revAccessEvent(accessPrtcl);
            }
            ret = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    private int handlerAlarmEvent(byte[] frameData) {
        int ret = 0;
        try {
            AlarmPrtcl alarmPrtcl = new AlarmPrtcl();
            alarmPrtcl.setDeviceType(Converter.bytes2int(frameData));
            alarmPrtcl.setUserId(Converter.bytes2int(frameData, 4));
            alarmPrtcl.setDeviceId(Converter.bytes2int(frameData, 8));
            alarmPrtcl.setDeviceStatus(Converter.bytes2int(frameData, +12));
            alarmPrtcl.setEventsRecNum(Converter.bytes2int(frameData, 16));

            byte[] bDate = new byte[8];
            System.arraycopy(frameData, 20, bDate, 0, 8);
            bDate=Converter.reverseArray(bDate);
            Date eventDateTime = Converter.delphiTimeToJavaTime(Converter.bytes2Double(bDate));
            alarmPrtcl.setEventDateTime(sdf.format(eventDateTime));

            alarmPrtcl.setRecNum(Converter.bytes2int(frameData, 28));
            byte[] bMsg = new byte[frameData.length - 32];
            System.arraycopy(frameData, 32, bMsg, 0, frameData.length - 32);
            String msg = new String(bMsg, "GBK");

            String[] fields = msg.split(";");
            if (fields.length == 1) {
                alarmPrtcl.setMemo(fields[0]);
            } else {
                alarmPrtcl.setMemo(fields[0]);
                //alarmPrtcl.setColor("#" + startZeroStr);
                alarmPrtcl.setColor(Integer.parseInt(fields[1]));
                alarmPrtcl.setTypeId(Integer.parseInt(fields[2]));
                alarmPrtcl.setDeviceName(fields[3]);
                alarmPrtcl.setEventName(fields[4]);
            }
            logger.info("收到报警事件：" + alarmPrtcl.toString());
            if (eventsCallBack != null) {
                eventsCallBack.revAlarmEvent(alarmPrtcl);
            }
            ret = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    private int handlerLogEvent(byte[] frameData) {

        int ret = 0;
        try {
            LogPrtcl logPrtcl = new LogPrtcl();
            logPrtcl.setUserId(Converter.bytes2int(frameData));
            logPrtcl.setPcId(Converter.bytes2int(frameData, 4));
            logPrtcl.setDeviceId(Converter.bytes2int(frameData, 8));
            logPrtcl.setEventRecNum(Converter.bytes2int(frameData, 12));

            byte[] bDate = new byte[8];
            System.arraycopy(frameData, 16, bDate, 0, 8);
            bDate=Converter.reverseArray(bDate);
            Date eventDateTime = Converter.delphiTimeToJavaTime(Converter.bytes2Double(bDate));
            logPrtcl.setEventDateTime(sdf.format(eventDateTime));

            byte[] bMsg = new byte[frameData.length - 24];
            System.arraycopy(frameData, 24, bMsg, 0, frameData.length - 24);
            String msg = new String(bMsg, "GBK");

            String[] fields = msg.split(";");
            if (fields.length == 1) {
                logPrtcl.setMemo(fields[0]);
            } else {
                logPrtcl.setMemo(fields[0]);
                logPrtcl.setColor(Integer.parseInt(fields[1]));
                logPrtcl.setTypeId(Integer.parseInt(fields[2]));
                logPrtcl.setEventName(fields[3]);
                logPrtcl.setDeviceName(fields[4]);
                logPrtcl.setHostName(fields[5]);
                logPrtcl.setUserName(fields[6]);

            }
            logger.info("收到日志事件：" + logPrtcl.toString());
            if (eventsCallBack != null) {
                eventsCallBack.revLogEvent(logPrtcl);
            }
            ret = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    private int handlerTourEvent(byte[] frameData) {

        int ret = 0;
        try {
            TourPrtcl tourPrtcl = new TourPrtcl();
            tourPrtcl.setCardId(Converter.bytes2int(frameData));
            tourPrtcl.setTourLineId(Converter.bytes2int(frameData, 4));
            tourPrtcl.setPcId(Converter.bytes2int(frameData, 8));
            tourPrtcl.setLoginId(Converter.bytes2int(frameData, 12));
            tourPrtcl.setDeviceId(Converter.bytes2int(frameData, 16));
            tourPrtcl.setEventRecNum(Converter.bytes2int(frameData, 20));

            byte[] bDate = new byte[8];
            System.arraycopy(frameData, 24, bDate, 0, 8);
            bDate=Converter.reverseArray(bDate);
            Date eventDateTime = Converter.delphiTimeToJavaTime(Converter.bytes2Double(bDate));
            tourPrtcl.setEventDateTime(sdf.format(eventDateTime));


            byte[] bMsg = new byte[frameData.length - 32];
            System.arraycopy(frameData, 32, bMsg, 0, frameData.length - 32);
            String msg = new String(bMsg, "GBK");
            String[] fields = msg.split(";");
            if (fields.length == 1) {
                tourPrtcl.setMemo(fields[0]);
            } else {
                tourPrtcl.setMemo(fields[0]);
                tourPrtcl.setColor(Integer.parseInt(fields[1]));
                tourPrtcl.setTypeId(Integer.parseInt(fields[2]));
                tourPrtcl.setUserId(Integer.parseInt(fields[3]));
                tourPrtcl.setDeviceName(fields[4]);
                tourPrtcl.setEventName(fields[5]);
                tourPrtcl.setDepartmentName(fields[6]);
                tourPrtcl.setPersonNo(fields[7]);
                tourPrtcl.setUserName(fields[8]);
                tourPrtcl.setTourLineName(fields[9]);
            }
            logger.info("收到巡更事件：" + tourPrtcl.toString());
            if (eventsCallBack != null) {
                eventsCallBack.revTourEvent(tourPrtcl);
            }
            ret = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 内部线程类负责接收事件
     */
    class EventsThread extends Thread {
        @Override
        public void run() {
            //super.run();
            while (!isInterrupted()) {
                try {
                    byte[] revBuffer = getContent();
                    if (revBuffer != null) {
                        handler_Protocol(revBuffer);
                        onLineTime = new Date();
                    } else {
                        Thread.sleep(100);
                    }
                } catch (InterruptedException e) {
                    interrupt();
                    e.printStackTrace();
                } catch (Exception e) {
                    interrupt();
                    e.printStackTrace();
                }
            }
        }
    }

}
