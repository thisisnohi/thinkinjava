package nohi.jvm._04_statck;

import org.junit.Test;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p>stack</p>
 * @date 2022/11/09 12:30
 **/
public class StatckTest {

    /**
     * StackOverFlow
     *   -Xss 指定大小
     */
    @Test
    public void testStatckOverFlow() {
        loopMethod(1);
    }

    public void loopMethod(int i) {
        System.out.println("i:" + i);
        loopMethod(++i);
    }
}
