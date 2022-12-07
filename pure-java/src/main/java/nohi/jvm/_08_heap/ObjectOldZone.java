package nohi.jvm._08_heap;

import org.junit.Test;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p>对象直接分配老年区</p>
 * @date 2022/11/12 21:56
 **/
public class ObjectOldZone {

    /**
     * 对象直接分配至老年代
     * -Xms60m -Xmx60m -XX:NewRatio=2 -XX:+PrintGCDetails -XX:SurvivorRatio=8
     *   Eden S0 S1 OLD
     *    16   2  2  40
     */
    public static void main(String[] args) {
        // 20M
        byte[] bytes = new byte[20 * 1024 * 1024];
//        Byte[] bytes = new Byte[20 * 1024 * 1024];
    }
}
