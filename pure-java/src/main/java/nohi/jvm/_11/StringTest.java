package nohi.jvm._11;

import org.junit.Test;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p>字符串测试</p>
 * @date 2022/11/18 13:59
 **/
public class StringTest {
    @Test
    public void test() {
        String[] str = new String[]{"1", "1", "ABC", "ABC"};
        System.out.println(str[0] == str[1]);
        System.out.println(str[2] == str[3]);
    }

    @Test
    public void testString() {
        String s01 = "Hello";
        String s02 = "World";

        String s1 = "HelloWorld";
        String s2 = new String("HelloWorld");
        System.out.println("1:s1.equals(2):" + s1.equals(s2));
        System.out.println("2:s1 == s2:" + (s1 == s2));
        s2 = new String("HelloWorld").intern();
        System.out.println("3:s1 == s2(String.intern()):" + (s1 == s2));

        s2 = "Hello" + "World";
        System.out.println("4:s1 == s2(\"Hello\" + \"World\"):" + (s1 == s2));

        s2 = s01 + s02;
        System.out.println("5:s1 == s2(s2 = s01 + s02):" + (s1 == s2));

        final String s21 = s01;
        final String s22 = s02;
        String s23 = s21 + s22;
        System.out.println("6:s1 == s2(s2 = final s01 + final s02):" + (s1 == s23));
        final String s31 = "Hello";
        final String s32 = "World";
        s23 = s31 + s32;
        System.out.println("7:s1 == s2(s2 = final s01 + final s02):" + (s1 == s23));
    }

    /**
     * 测试字符串拼接，字节码
     */
    public void testStringAdd() {
        String s1 = "a";
        String s2 = "b";
        String s3 = "a" + "b";
        String s4 = s1 + s2;
        String s5 = s4.intern();
    }
}
