package nohi.jdk._13;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * <h3>jdk17</h3>
 *
 * @author NOHI
 * @description <p>jdk13</p>
 * @date 2023/01/19 18:01
 **/
public class Jdk13 {
    static final int MONDAY = 1;
    static final int TUESDAY = 2;
    static final int WEDNESDAY = 3;
    static final int THURSDAY = 4;
    static final int FRIDAY = 5;
    static final int SATURDAY = 6;
    static final int SUNDAY = 7;

    public int doSwitch(int day) {
        int numLetters = switch (day) {
            case MONDAY, FRIDAY, SUNDAY -> 6;
            case TUESDAY -> 7;
            case THURSDAY, SATURDAY -> 8;
            case WEDNESDAY -> 9;
            default -> 0;
        };
        return numLetters;
    }
    @Test
    public void testSwitch(){
        Assertions.assertEquals(0, doSwitch(8));
        Assertions.assertEquals(6, doSwitch(1));
        Assertions.assertEquals(9, doSwitch(3));
    }

    @Test
    public void testText(){
        String text = """
                第一行
                第二行
                第三行
                """;
        System.out.println(text);
    }
}
