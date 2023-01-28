package nohi.jdk._14;

import org.junit.jupiter.api.Test;

/**
 * <h3>jdk17</h3>
 *
 * @author NOHI
 * @description <p>jdk14</p>
 * @date 2023/01/19 18:15
 **/
public class Jdk14 {

    @Test
    public void testInstanceOf() {
        Object obj = 1;
        // 自动传值至变量abc
        if (obj instanceof Integer abc) {
            System.out.println(abc);
        }
    }

    record Student(String name, int age) {

        public void daydayUp() {
            System.out.println(String.format("[%s][%s] daydayup", name, age));
        }
    }

    @Test
    public void testRecord() {
        Student student = new Student("NOHI", 18);
        System.out.println(student);
        student.daydayUp();
    }
}
