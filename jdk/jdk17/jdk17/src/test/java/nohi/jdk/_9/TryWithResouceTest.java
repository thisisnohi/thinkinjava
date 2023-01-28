package nohi.jdk._9;

import java.io.FileNotFoundException;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p></p>
 * @date 2023/01/17 13:24
 **/
class TryWithResouceTest {

    @org.junit.jupiter.api.Test
    void testTryWithResource() throws FileNotFoundException {
        TryWithResouce tr = new TryWithResouce();
        String src = "./pom.xml";
        String target =  "./pom_target.xml";
        tr.testTryWithResource(src, target);
    }

}