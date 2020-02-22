package com.skyrity.socket;

import java.io.IOException;
import java.net.SocketException;

public interface SkySocket {
   /**
    * 打开Socket连接
    * @param host 主机地址
    * @param port 主机端口号
    * @return >0 正确，<=失败
    */
   public int open(String host, int port) throws IOException;
   /**
    * 关闭Socket连接
    */
   public void  close() throws IOException;
   /**
    * 检查Socket是否连接
    * @return true=连接，false=断开
    */
   public boolean isConnected();
   /**
    * 获得等待超时时间(毫秒)
    * @return 超时时间(毫秒)
    */
   public int getWaitTime() throws SocketException;
   /**
    * 设置等待超时时间(毫秒)
    * @param mSeconds 超时时间(毫秒)
    */
   public void setWaitTime(int mSeconds) throws SocketException;
   /**
    * 发送数据
    * @param buffer 发送的数据缓冲区
    */
   public void write(byte[] buffer) throws IOException;
   /**
    * 接收数据
    * @param buffer 数据缓冲区
    * @param revSize 数据长度
    * @return 接收到的实际数据
    */
   public int read(byte[] buffer, int revSize) throws IOException;
   /**
    * 在指定时间内接收数据
    * @param buffer 数据缓冲区
    * @param revSize 接收长度
    * @param waitTime 等待超时时间(毫秒)
    * @return 接收到的实际数据
    */
   public int read(byte[] buffer, int revSize, int waitTime) throws IOException;
   /**
    * 获得通信数据的日志
    * @param buffer 通信数据日志
    * @return 数据长度
    */
   public int getBuffer(byte[] buffer);
   /**
    * 清空日志数据
    * @return >0 正确，<=失败
    */
   public int emptyBuffer();


}
