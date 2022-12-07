package nohi.jvm._11;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p>StringTable GC</p>
 * @date 2022/11/22 13:04
 **/
public class StringTableGC {

    /**
     * 增加参数设置
     *   -Xmn10m -Xmx10m -XX:+PrintStringTableStatistics -XX:+PrintGC
     * @param args
     */
    public static void main(String[] args) {
        for (int i = 0; i < 1000000; i++) {
            String.valueOf(i).intern();
        }
    }
}
