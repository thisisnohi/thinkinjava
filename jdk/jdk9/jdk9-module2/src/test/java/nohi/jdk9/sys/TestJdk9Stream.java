package nohi.jdk9.sys;

import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <h3>jdk</h3>
 *
 * @author NOHI
 * @description <p>jdk9 stream</p>
 * @date 2023/01/19 12:38
 **/
public class TestJdk9Stream {

    @Test
    public void testStream() {
        // 0
        Stream.ofNullable(null).count();

        // [1, 2]
        Stream.of(1, 2, 3, 2, 1).takeWhile(n -> n < 3).collect(Collectors.toList());

        // [3, 2, 1]
        Stream.of(1, 2, 3, 2, 1).dropWhile(n -> n < 3).collect(Collectors.toList());
    }

}
