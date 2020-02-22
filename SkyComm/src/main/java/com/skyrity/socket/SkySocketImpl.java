package com.skyrity.socket;

import com.skyrity.utils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

/**
 * Skyrity公司TCP/IP通讯的通用实现类，是所有网路硬件通信的基础类，所有
 * TCP/IP硬件产品都需要继承该类来实现通信
 * @author macai
 */
public class SkySocketImpl implements SkySocket {

    private final Logger logger=LoggerFactory.getLogger("client");
    private final static int ERROR_OPEN = -1;
    private final static int MAX_BUFFSIEZ = 4096;
    private Socket socket = null;
    private DataOutputStream out = null;
    private DataInputStream in = null;
    private int waitTime = 3000;
    // 记录通讯信息
    private byte[] comBuffer = new byte[MAX_BUFFSIEZ];
    // 记录通讯信息长度
    private int bufferPos = 0;

    /**
     * 打开Socket连接
     *
     * @param host 主机地址
     * @param port 主机端口号
     * @return >0 正确，<=失败
     */
    @Override
    public int open(String host, int port)  {
        int ret=ERROR_OPEN;
        SocketAddress socAddress = new InetSocketAddress(host, port);
        this.socket = new Socket();
        try {
            this.socket.connect(socAddress, waitTime);
            this.socket.setSoTimeout(waitTime);
            out = new DataOutputStream(this.socket.getOutputStream());
            in = new DataInputStream(this.socket.getInputStream());
            ret = 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 关闭Socket连接
     */
    @Override
    public void close()  {
        if (in != null) {
            try {
                in.close();
                if (out != null) {
                    out.close();
                }
                if (socket != null) {
                    socket.close();
                    socket = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * 设置等待超时时间(毫秒)
     *
     * @param mSeconds 超时时间(毫秒)
     */
    @Override
    public void setWaitTime(int mSeconds)  {
        if (!isConnected()) {
            waitTime = mSeconds;
            try {
                socket.setSoTimeout(mSeconds);
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送数据
     *
     * @param buffer 发送的数据缓冲区
     */
    @Override
    public void write(byte[] buffer)  {
        if (buffer == null) {
            return;
        }
        if (isConnected()) {
            writeOut(buffer, buffer.length);
            try {
                out.write(buffer);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    /**
     * 接收数据
     *
     * @param buffer  数据缓冲区
     * @param revSize 数据长度
     * @return 接收到的实际数据
     */
    @Override
    public int read(byte[] buffer, int revSize) {
        int index = 0;
        int nLen;
        if (!isConnected()) return 0;
        try {
            byte[] bContent = new byte[revSize];

            while (index < revSize) {
                nLen = in.read(bContent, index, revSize - index);
                if(nLen==-1){
                    break;
                }else if (nLen >= 0) {
                    index = index + nLen;
                }
            }

            if (index > 0) {
                System.arraycopy(bContent, 0, buffer, 0, index);
                writeIn(buffer, index);
            }
        } catch (SocketTimeoutException e) {
            // 忽略异常
            //e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return index;

    }

    /**
     * 在指定时间内接收数据
     *
     * @param buffer   数据缓冲区
     * @param revSize  接收长度
     * @param waitTime 等待超时时间(毫秒)
     * @return 接收到的实际数据
     */
    @Override
    public int read(byte[] buffer, int revSize, int waitTime)  {
        int size=0 ;
        int oldWaitTime;
        if (!isConnected()) {
            return 0;
        }
        try {
            oldWaitTime = socket.getSoTimeout();
            socket.setSoTimeout(waitTime);
            size = read(buffer, revSize);
            socket.setSoTimeout(oldWaitTime);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return size;
    }

    /**
     * 检查Socket是否连接
     *
     * @return true=连接，false=断开
     */
    @Override
    public boolean isConnected() {
        return socket != null && socket.isConnected();
    }

    /**
     * 获得通信数据的日志
     *
     * @param buffer 通信数据日志
     * @return 数据长度
     */
    @Override
    public int getBuffer(byte[] buffer) {
        System.arraycopy(comBuffer, 0, buffer, 0, bufferPos);
        return bufferPos;
    }

    /**
     * 清空日志数据
     *
     * @return >0 正确，<=失败
     */
    @Override
    public int emptyBuffer() {
        bufferPos = 0;
        return 1;
    }

    private void writeIn(byte[] buffer, int len) {
        if (len > MAX_BUFFSIEZ)
            return;

        int pos = bufferPos;
        if ((pos + len + 3) > MAX_BUFFSIEZ)
            pos = 0;

        int length = len;
        length = length + 1;

        comBuffer[pos++] = (byte) ((length & 0xff00) >> 8);
        comBuffer[pos++] = (byte) (length & 0xff);
        comBuffer[pos++] = (byte) 'i';
        System.arraycopy(buffer, 0, comBuffer, pos, len);
        pos += len;
        bufferPos = pos;
        logger.debug("rev:" + Converter.toHexString(buffer," "));
    }

    private void writeOut(byte[] buffer, int len) {
        if (len > MAX_BUFFSIEZ)
            return;
        int pos = bufferPos;
        if ((pos + len + 3) > MAX_BUFFSIEZ)
            pos = 0;
        int length = len;
        length = length + 1;
        comBuffer[pos++] = (byte) ((length & 0xff00) >> 8);
        comBuffer[pos++] = (byte) (length & 0xff);
        comBuffer[pos++] = (byte) 'o';
        System.arraycopy(buffer, 0, comBuffer, pos, len);
        pos += len;
        bufferPos = pos;
        logger.debug("send:" + Converter.toHexString(buffer," "));
    }

    /**
     * 获得等待超时时间(毫秒)
     *
     * @return 超时时间(毫秒)
     */
    @Override
    public int getWaitTime()  {
        int mSecond=0 ;
        if (!isConnected()) {
            return 0;
        }
        try {
            mSecond = socket.getSoTimeout();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return mSecond;
    }

}
