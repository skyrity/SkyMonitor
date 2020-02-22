package com.skyrity.utils;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 数据转换类
 */
public class Converter {

    /**
     * 转换成十六进制的字符串
     *
     * @param bytes byte数组
     * @return 返回转换后的Hex字符串
     */
    public static String toHexString(byte[] bytes) {
        return toHexString(bytes, "");
    }

    public static String toHexString(byte[] bytes, String split) {
        StringBuilder hexString = new StringBuilder();
        String plainText;
        for (int i = 0; i < bytes.length; i++) {
            plainText = Integer.toHexString(0xFF & bytes[i]);
            if (plainText.length() < 2) {
                plainText = "0" + plainText;
            }
            if (i < bytes.length - 1) {
                hexString.append(plainText + split);
            } else {
                hexString.append(plainText);
            }

        }
        return hexString.toString().toUpperCase();
    }

    /**
     * 把十六进制的字符串转换成byte数组
     *
     * @param hexString 十六进制的字符串
     * @return 返回转换后的字节数组
     */
    public static byte[] toByte(String hexString) {
        int length = hexString.length() / 2;
        byte[] b = new byte[length];
        for (int i = 0; i < length; i++) {
            b[i] = (byte) Integer.parseInt(
                    hexString.substring(2 * i, 2 + 2 * i), 16);
        }
        return b;
    }

    /**
     * 把byte数组转换成String
     *
     * @param bytes byte数组
     * @return 返回转换后的字符串
     */
    public static String toString(byte[] bytes) {

        return toString(bytes, 0);
    }

    /**
     * 把byte数组转换成String
     *
     * @param bytes  byte数组
     * @param offset 偏移量
     * @return 返回转换后的字符串
     */
    public static String toString(byte[] bytes, int offset) {
        StringBuilder strBuff = new StringBuilder();
        for (int i = offset; i < bytes.length; i++) {
            strBuff.append((char) bytes[i]);
        }
        return strBuff.toString();
    }

    /**
     * 从数组中的若干位中返回一个长整型的数字
     *
     * @param recv   从控制器返回的数据
     * @param offset 偏移值
     * @param len    长度
     * @return 返回一个长整型的数字
     */
    public static long getValue(byte recv[], int offset, int len) {
        long result = 0;
        for (int i = 0; i < len; i++) {
            result <<= 8;
            result |= (recv[offset + i] & 0xff); // 防止因Java的默认的数据转换带来的问题
        }
        return result;
    }


    /**
     * 获取指定位的值
     *
     * @param value    字节数据
     * @param bitIndex 指定的位
     * @return 返回指定位的值
     */
    public static int getBitValue(byte value, int bitIndex) {
        value >>= bitIndex;
        value &= 0x1;
        // result &= 0xff;// 防止因Java的默认的数据转换带来的问题
        return value;
    }

    /**
     * 把十进制数转换成相应的BCD码
     *
     * @param value 输入的十进制整数
     * @return 对应的BCD码
     */
    public static byte decimalToBcdAscii(int value) {
        try {
            return (byte) Integer.valueOf(Integer.toString(value), 16)
                    .intValue();
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * 把十进制数转换成相应的BCD码
     *
     * @param value 输入的十进制整数
     * @return 对应的BCD码
     */
    public static long decimalToBcdAscii(long value) {
        try {
            return Long.valueOf(Long.toString(value), 16);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * 把BCD码转换成相应的十进制数
     *
     * @param value 输入的BCD码
     * @return 对应的十进制整数
     */
    private static int bcdAsciiTodecimal(byte value[], int offset)
            throws Exception {
        int len = 1;
        try {
            StringBuilder tmp = new StringBuilder();
            for (int i = 0; i < len; i++) {
                String result = "0"
                        + Integer.toHexString(value[offset + i] & 0xff);
                result = result.substring(result.length() - 2, result.length());
                tmp.append(result);
            }
            return Integer.valueOf(tmp.toString(), 10);
        } catch (NumberFormatException e) {
            throw new Exception("BCD_ACCII_TO_DECIMAL");
        }
    }

    /**
     * 获取一个字节的高四位BCD编码对应的十进制数字
     *
     * @param value 输入的值
     * @return 对应的十进制数字
     * @throws Exception 异常
     */
    public static int getBcdHiWord(byte value) throws Exception {
        try {
            String result = Integer.toHexString(value >>> 4);
            return Integer.valueOf(result, 10);

        } catch (NumberFormatException e) {
            throw new Exception("BCD_ACCII_TO_DECIMAL");
        }
    }

    /**
     * 获取一个字节的低四位BCD编码对应的十进制数字
     *
     * @param value
     * @return 对应的十进制数字
     * @throws Exception 异常
     */
    public static int getBcdLowWord(byte value) throws Exception {
        try {
            String result = Integer.toHexString(value & 0xf);
            return Integer.valueOf(result.toString(), 10);
        } catch (NumberFormatException e) {
            throw new Exception("BCD_ACCII_TO_DECIMAL");
        }
    }

    /**
     * 判断字符串是否为十六进制数
     *
     * @param string 十六进制字符串
     * @return 如果输入的全部都为十六进制字符串, 返回true, 否则返回false
     */
    public static boolean isHexString(String string) {
        try {
            Long.parseLong(string, 16);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static int getHour(String timeStr) {
        if (!(null == timeStr || "".equals(timeStr))) {
            try {
                String[] time = timeStr.split(":");
                return decimalToBcdAscii(Integer.valueOf(time[0]));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0xAA;
    }

    public static int getMinute(String timeStr) {
        if (!(null == timeStr || "".equals(timeStr))) {
            try {
                String[] time = timeStr.split(":");
                return decimalToBcdAscii(Integer.valueOf(time[1]));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0xAA;
    }

    public static int Ineger2Int(Integer value, int defaultValue) {
        if (value != null) {
            return value;
        }
        return defaultValue;
    }


    /**
     * 解释查询回来的BCD编码的日期
     *
     * @param recv 从设备接收的数据
     * @return 解释成功返回一个Date对象, 否则返回null
     */
    public static Date parseBcdDate(byte[] recv, int offset) throws Exception {
        Date date = null;
        if (recv != null) {
            int year = 2000 + bcdAsciiTodecimal(recv, offset);// BcdAsciiTodecimal(recv[offset]);
            int month = bcdAsciiTodecimal(recv, offset + 1) - 1;
            int day = bcdAsciiTodecimal(recv, offset + 2);

            int hour = bcdAsciiTodecimal(recv, offset + 4);
            int minute = bcdAsciiTodecimal(recv, offset + 5);
            int second = bcdAsciiTodecimal(recv, offset + 6);
            // Debuger.debug("year:"+year+",month:"+month+",day:"+day+",hour:"+hour+",minute:"+minute+",second:"+second);
            if (month >= 0 && day < 32 && hour < 24 && minute < 60
                    && second < 60) {
                Calendar cal = Calendar.getInstance(
                        TimeZone.getTimeZone("GMT+8:00"), Locale.CHINESE);
                cal.set(year, month, day, hour, minute, second);
                date = cal.getTime();
            }
        }

        return date;
    }

    /**
     * 解释查询回来的压缩格式的日期
     *
     * @param recv   从设备接收的数据
     * @param offset 偏移量
     * @return 解释成功返回一个Date对象, 否则返回null
     */
    public static Date parseZipDate(byte[] recv, int offset) {
        /*
         * 时间格式 B1 B2 B3 B4 7 |6|5|4|3|2|1|0 |7|6|5|4|3|2|1|0 |7|6|5|4|3|2|1|0
		 * |7|6|5|4|3|2|1|0 年 | 日 | 月 | 3600x时+60x分+秒
		 */
        int year = ((recv[3 + offset] & 0xff) >>> 2) & 0x3f; // 第四个字节右移两位得到年份
        int month = ((recv[2 + offset] & 0xff) >>> 1) & 0xf; // 第三个字节右移一位,取低四位
        int day = (((recv[3 + offset] & 0xff) << 3) & 0x18)
                | (((recv[2 + offset] & 0xff) >>> 5) & 0x7); // 取第四个字节的低两位和第三个字节高三位
        long total_second = (long) recv[2 + offset] & 0x1; // 第二个字节的最低位和第三第四个字节
        total_second <<= 16;
        total_second |= (long) ((recv[1 + offset]) & 0xff) << 8;
        total_second |= (long) ((recv[offset]) & 0xff);
        int hour = (int) total_second / 3600;
        total_second -= hour * 3600;
        int minute = (int) total_second / 60;
        total_second -= minute * 60;
        int second = (int) total_second;
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"),
                Locale.CHINESE);
        cal.set(year + 2000, month - 1, day, hour, minute, second);
        return cal.getTime();
    }

//	public static AgSendCmdBean fromAgSendCmdTemp(AgSendCmdTempBean cmd) {
//		AgSendCmdBean cmdSend = new AgSendCmdBean();
//		cmdSend.setCommandId(cmd.getCommandId());
//		cmdSend.setCommandName(cmd.getCommandName());
//		cmdSend.setCtrId(cmd.getCtrId());
//		cmdSend.setLinkId(cmd.getLinkId());
//		cmdSend.setSendHexData(cmd.getSendHexData());
//		cmdSend.setSendLength(cmd.getSendLength());
//		cmdSend.setSkipResponse(cmd.getSkipResponse());
//		cmdSend.setSendUserId(cmd.getSendUserId());
//		return cmdSend;
//	}

    public static long getTime(Date time, long defaultValue) {
        if (time != null) {
            return time.getTime();
        }
        return defaultValue;
    }

    public static byte[] int2bytes(int i) {
        byte[] b = new byte[4];

        b[3] = (byte) (0xff & i);
        b[2] = (byte) ((0xff00 & i) >> 8);
        b[1] = (byte) ((0xff0000 & i) >> 16);
        b[0] = (byte) ((0xff000000 & i) >> 24);
        return b;
    }

    /**
     * 字节数组转整型
     *
     * @param bytes 字节数组
     * @return 返回整型
     */
    public static int bytes2int(byte[] bytes) {
        return bytes2int(bytes, 0);
    }

    /**
     * 字节数组转整型
     *
     * @param bytes  字节数组
     * @param offset 偏移量
     * @return 返回整型
     */
    public static int bytes2int(byte[] bytes, int offset) {
        int num = bytes[offset + 3] & 0xFF;
        num |= ((bytes[offset + 2] << 8) & 0xFF00);
        num |= ((bytes[offset + 1] << 16) & 0xFF0000);
        num |= ((bytes[offset] << 24) & 0xFF000000);
        return num;
    }

    public static byte[] long2bytes(long num) {
        byte[] b = new byte[8];
        for (int i = 0; i < 8; i++) {
            b[i] = (byte) (num >>> (56 - (i * 8)));
        }
        return b;
    }

    public static long bytes2long(byte[] b) {

        return bytes2long(b, 0);
    }

    public static long bytes2long(byte[] b, int offset) {
        long temp;
        long res = 0;
        for (int i = offset; i < 8 + offset; i++) {
            res <<= 8;
            temp = b[i] & 0xff;
            res |= temp;
        }
        return res;
    }

    public static double bytes2Double(byte[] b) {
        return bytes2Double(b, 0);
    }

    public static double bytes2Double(byte[] b, int offset) {
        long value = 0;
        for (int i = offset; i < 8 + offset; i++) {
            value |= ((long) (b[i] & 0xff)) << (8 * i);
        }
        return Double.longBitsToDouble(value);
    }

    /**
     * Byte3 Byte2 Byte1 Byte0 __IO uint8_t StDate[4]; //时间[yyyyyydd] [dddmmmms]
     * [ssssssss][ssssssss]
     *
     * @param time 时间
     * @return 返回字节
     */
    public static byte[] dateTime2Bytes(Date time) {
        byte[] data = new byte[4];
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time.getTime());

        int y = cal.get(Calendar.YEAR) - 2000;
        int d = cal.get(Calendar.DATE);
        int m = cal.get(Calendar.MONTH) + 1;
        int s = cal.get(Calendar.HOUR_OF_DAY) * 3600 + cal.get(Calendar.MINUTE)
                * 60 + cal.get(Calendar.SECOND);

        int i = 0;
        i = (y << 26); // 32-6=26 左移26位表示 日的长度+月的长度+秒的长度
        i |= (d << 21);
        i |= (m << 17);
        i |= s;

        data[3] = (byte) (i & 0xff);
        data[2] = (byte) ((i >> 8) & 0xff);
        data[1] = (byte) ((i >> 16) & 0xff);
        data[0] = (byte) ((i >> 24) & 0xff);

        return data;
    }

    /**
     * 数据转换为日期
     *
     * @param data 数据
     * @return 日期时间
     */
    public static Date bytes2DateTime(byte[] data) {
        long temp = 0;

        temp = (data[0] < 0 ? data[0] + 256 : data[0]) << 24;
        temp |= (data[1] < 0 ? data[1] + 256 : data[1]) << 16;
        temp |= (data[2] < 0 ? data[2] + 256 : data[2]) << 8;
        temp |= data[3] + 256;

        int year = (int) ((temp >> 26) & 0x3f) + 2000;
        int month = (int) ((temp >> 17) & 0x0f);
        int day = (int) ((temp >> 21) & 0x1f);
        int second = (int) (temp & 0x1ffff);
        int hour = second / 3600;
        second = second % 3600;
        int minute = second / 60;
        second = second % 60;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);

        return c.getTime();
    }

    /**
     * 数据转换为日期
     *
     * @param data 数据
     * @return 日期
     */
    public static Date bytes2Date(byte[] data) {
        long temp = 0;

        temp = (data[0] < 0 ? data[0] + 256 : data[0]) << 8;
        temp |= (data[1] < 0 ? data[1] + 256 : data[1]);

        int year = (int) ((temp >> 10) & 0x3f) + 2000;
        int month = (int) ((temp >> 1) & 0x0f) - 1;
        int day = (int) ((temp >> 5) & 0x1f);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        return c.getTime();
    }




    /**
     * 日期格式字符串转换为Byte。
     *
     * @param floor 格式为yy-MM-dd-HH-mm-ss
     * @return 返回字节数组
     */
    public static byte[] dateToByte(String floor) {

        byte[] data = new byte[10];

        String[] str = floor.split("-");

        for (int i = 0; i < str.length; ++i) {
            data[i] = (byte) Integer.parseInt(str[i]);
        }

        return data;
    }

    /**
     * 获取IP地址的最后一个字段
     *
     * @param ip 地址
     * @return 返回字符串
     */
    public static String getIpAddress(String ip) {
        String s = "000";
        String ipFormat = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
        if (ip.matches(ipFormat)) {
            s += ip.substring(ip.lastIndexOf(".") + 1);
            s = s.substring(s.length() - 3);
        }
        return s;
    }

    /**
     * 蓝牙名称转换为Byte。
     *
     * @param bleName 蓝牙名称
     * @return 返回蓝牙名称字符串
     */
    public static byte[] bleNameToByte(String bleName) {

        byte[] data = new byte[9];

        if (bleName != null && !"".equals(bleName)) {
            try {
                byte[] b = bleName.getBytes("ascii");
                if (b.length > 9) {
                    System.arraycopy(b, 0, data, 0, 9);
                } else {
                    System.arraycopy(b, 0, data, 0, b.length);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return data;
    }

    /**
     * 函数：reverseArray1和reverseArray2
     * 功能：实现 数组翻转
     * 例如：{'a','b','c','d'}变成{'d','c','bb','a'}
     */
    public static byte[] reverseArray(byte[] b) {
        byte[] temp=new byte[b.length];
        for (int i = 0; i < b.length; i++) {
            temp[b.length - i - 1]=b[i];
        }
        return temp;
    }

    public static Date delphiTimeToJavaTime(final double time) {
        final long DAYS_BETWEEN_18991230_AND_19700101 = 25569;
        final long MILLISECONDS_PER_DAY = 86400000;
        long millis = (long) ((time - DAYS_BETWEEN_18991230_AND_19700101) * MILLISECONDS_PER_DAY);
        // 再转一次，消除误差
        double delphiTime = (millis + DAYS_BETWEEN_18991230_AND_19700101 * MILLISECONDS_PER_DAY) / (double) MILLISECONDS_PER_DAY;
        millis = (long) ((delphiTime - DAYS_BETWEEN_18991230_AND_19700101) * MILLISECONDS_PER_DAY);
        return new Date(millis);

    }
}
