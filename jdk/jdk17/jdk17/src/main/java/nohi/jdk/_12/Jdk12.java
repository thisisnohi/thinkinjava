package nohi.jdk._12;

import org.junit.jupiter.api.Test;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * <h3>jdk17</h3>
 *
 * @author NOHI
 * @description <p>jdk12</p>
 * @date 2023/01/19 17:00
 **/
public class Jdk12 {
    static final int MONDAY = 1;
    static final int TUESDAY = 2;
    static final int WEDNESDAY = 3;
    static final int THURSDAY = 4;
    static final int FRIDAY = 5;
    static final int SATURDAY = 6;
    static final int SUNDAY = 7;

    public void doSwitch(int day) {
        switch (day) {
            case MONDAY, FRIDAY, SUNDAY -> System.out.println(6);
            case TUESDAY -> System.out.println(7);
            case THURSDAY, SATURDAY -> System.out.println(8);
            case WEDNESDAY -> System.out.println(9);
            default -> System.out.println("default");
        }
    }

    @Test
    public void testSwitch() {
        doSwitch(1);
    }

    @Test
    public void testNumFormat() {
        NumberFormat fmt = NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.SHORT);
        String result = fmt.format(1000);
        System.out.println("result1:" + result);
        result = fmt.format(1000000);
        System.out.println("result2:" + result);
    }
}
