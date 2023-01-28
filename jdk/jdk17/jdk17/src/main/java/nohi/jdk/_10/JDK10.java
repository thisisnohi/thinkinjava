package nohi.jdk._10;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * <h3>jdk</h3>
 *
 * @author NOHI
 * @description <p>JDK10</p>
 * @date 2023/01/19 09:15
 **/
public class JDK10 {

    /**
     * 局部变量类型推断
     */
    @Test
    public void localVar() {
        //根据推断为 字符串类型
        var str = "ABC";
        var l = 10L;
        var flag = true;
        var flag1 = 1;
        var list = new ArrayList<String>();
        var stream = list.stream();

        System.out.println("str:" + str.getClass());
        System.out.println("l:" + l);
        System.out.println("flag:" + flag);
        System.out.println("flag1:" + flag1);
        System.out.println("flag1:" + list.getClass());
        System.out.println("flag1:" + stream.getClass());
    }

    @Test
    public void localVar2() {
        Arrays.asList("Java", "Python", "Ruby").forEach((var s) -> {
            System.out.println("Hello, " + s);
        });
    }

}
