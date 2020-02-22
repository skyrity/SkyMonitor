package com.skyrity;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * @author ： VULCAN
 * @date ：2020/02/18 14:19
 * @description : ${description}
 * @path : com.skyrity.ByteOrderTest
 * @modifiedBy ：
 */
public class ByteOrderTest {


    @Test
    public void test1() {
        int x = 0x01020304;
        ByteBuffer bb = ByteBuffer.wrap(new byte[4]);
        bb.asIntBuffer().put(x);
        String ss_before = Arrays.toString(bb.array());
        System.out.println("默认字节序 " + bb.order().toString() + "," + " 内存数据 " + ss_before);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.asIntBuffer().put(x);
        String ss_after = Arrays.toString(bb.array());
        System.out.println("修改字节序 " + bb.order().toString() + "," + " 内存数据 " + ss_after);
    }
}
