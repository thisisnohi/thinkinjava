package nohi.jdk._16;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <h3>jdk17</h3>
 *
 * @author NOHI
 * @description <p>JDK16</p>
 * @date 2023/01/28 12:36
 **/
public class Jdk16 {

    /**
     * 测试日期
     */
    @Test
    public void testDate() {

        System.out.println(DateTimeFormatter.ofPattern("B").format(LocalDateTime.now()));

    }

}
