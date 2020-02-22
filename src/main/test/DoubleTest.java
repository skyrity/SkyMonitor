import com.skyrity.utils.Converter;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ： VULCAN
 * @date ：2020/02/19 18:19
 * @description : ${description}
 * @path : PACKAGE_NAME.DoubleTest
 * @modifiedBy ：
 */
public class DoubleTest {


    public static byte[] double2Bytes(double d) {
        long value = Double.doubleToRawLongBits(d);
        byte[] byteRet = new byte[8];
        for (int i = 0; i < 8; i++) {
            byteRet[i] = (byte) ((value >> 8 * i) & 0xff);
        }
        return byteRet;
    }

    @Test
    public  void run1() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mmL:ss");
        double b=43882.608198;

        System.out.println(Converter.toHexString(double2Bytes(b)));
        System.out.println(bytes2Double(double2Bytes(b)));

       // Date date=Converter.bytes2DateTime(double2Bytes(b));
        Date date=Converter.delphiTimeToJavaTime(b);
        System.out.println(sdf.format(date));


    }
    @Test
    public  void run2() {
      // Long mSecond1= new Date("1899-12-30 12:00:00").getTime();
      // Long mSecond2= new Date("1970-01-01 00:00:00").getTime();
      // System.out.println(mSecond1.toString());
      // System.out.println(mSecond2.toString());
    }
    public static double bytes2Double(byte[] arr) {
        long value = 0;
        for (int i = 0; i < 8; i++) {
            value |= ((long) (arr[i] & 0xff)) << (8 * i);
        }
        return Double.longBitsToDouble(value);
    }
}
