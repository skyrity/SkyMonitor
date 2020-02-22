import org.junit.Test;

import java.text.DecimalFormat;

/**
 * @author ： VULCAN
 * @date ：2020/02/19 20:00
 * @description : ${description}
 * @path : PACKAGE_NAME.HexStringTest
 * @modifiedBy ：
 */
public class HexStringTest {

    @Test
    public void run() {


        String fileName = "130181";
        System.out.println("================  前补零方法一   =================");
        DecimalFormat g1 = new DecimalFormat("0000000");
        String startZeroStr = g1.format(Integer.valueOf(fileName));
        System.out.println("前补零方法一：" + startZeroStr);

        System.out.println("================  前补零方法二   =================");
        startZeroStr = String.format("%07d", Integer.valueOf(fileName));
        System.out.println("前补零方法二：" + startZeroStr);

        System.out.println("================  后补零方法一   =================");
        DecimalFormat g2 = new DecimalFormat("0.000000");
        String endZeroStr = g2.format(Integer.valueOf(fileName));
        System.out.println("后补零：" + endZeroStr);
        System.out.println("虽然后补零出现这种情况,带有小数点");
        System.out.println("比如你要长度要在7位以内，可以这么做");
        System.out.println("后补零转变后：" + endZeroStr.replace(".", "").
                substring(0, 7));
    }
}
