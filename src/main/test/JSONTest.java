import com.skyrity.bean.SendMsg;
import net.sf.json.JSONObject;
import org.junit.Test;

/**
 * @author ： VULCAN
 * @date ：2020/02/19 14:46
 * @description : ${description}
 * @path : PACKAGE_NAME.JSONTest
 * @modifiedBy ：
 */
public class JSONTest {

    @Test
    public void run1(){
        SendMsg sendMsg=new SendMsg();
        sendMsg.setType("log");
        JSONObject jsonObject=JSONObject.fromObject(sendMsg);
        System.out.println(sendMsg.toString());
    }
}
