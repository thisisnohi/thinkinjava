package nohi.test.md5;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

/**
 * @author NOHI
 * 2021-11-04 08:59
 **/
public class TestMd5 {

    @Test
    public void test1(){
        String msg = "123456";
        String md5 = DigestUtils.md5Hex(msg);
        System.out.println("msg:" + msg + "\nmd5:" + md5);

        msg = "{\"body\":{\"syncTime\":\"1576395019921\",\"id\":\"111\"},\"head\":{\"authTimeSpan\":\"1576395019921\",\"bankCode\":\"string4\"}}szwzjy2020grsdj";
        md5 = DigestUtils.md5Hex(msg);
        System.out.println("msg:" + msg + "\nmd5:" + md5);
    }

    @Test
    public void test2(){
        String msg = "{\"body\":{\"syncTime\":\"1576395019921\",\"id\":\"111\"},\"head\":{\"authTimeSpan\":\"1576395019921\",\"bankCode\": \"string4\"}}szwzjy2020grsdj";
        String md5 = DigestUtils.md5Hex(msg);
        System.out.println("msg:" + msg + "\nmd5:" + md5);
    }
}
