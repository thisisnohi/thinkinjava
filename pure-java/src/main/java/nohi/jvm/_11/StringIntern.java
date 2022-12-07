package nohi.jvm._11;

import org.junit.Test;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p>String.intern()方法测试</p>
 * @date 2022/11/22 08:57
 **/
public class StringIntern {

    @Test
    public void testInter(){
        String a = new String("a") + new String("b");
        // 保证堆中只有一份相同值： if only if
        // intern()方法：查找常量池中是否存在"ab",如果不存在，则常量池中分配索引指向堆中已有对象“ab”地址
        // 如果存在，则返回常量池中地址
        a.intern();
        String b = "ab";
        System.out.println("a==b ? " + (a == b));
    }
    @Test
    public void testInter2(){
        String a = new String("a") + new String("b");
        String b = "ab";
        String c = a.intern();
        System.out.println("a==b ? " + (a == b));
        System.out.println("c==b ? " + (c == b));
    }
}
