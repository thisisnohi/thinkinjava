package nohi.jvm._08_heap;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p>显示堆信息</p>
 * @date 2022/11/12 13:56
 **/
public class ShowHeap {

    /**
     * 显示默认大小
     */
    @Test
    public void showHeap() {
        // 初始大小
        long initialMemory = Runtime.getRuntime().totalMemory() / 1024/1024;
        // 最大
        long maxMemory = Runtime.getRuntime().maxMemory() / 1024/1024;
        System.out.println("-Xms:" + initialMemory + "M");
        System.out.println("-Xmx:" + maxMemory + "M");

        // initialMemory: S0 S1 其中一个 + 老年代
    }

    /**
     * 显示默认大小
     *  指定运行时大小: -Xms100m -Xmx110m -XX:+PrintGCDetails -XX:SurvivorRation=8
     */
    @Test
    public void showHeapWithParam() throws InterruptedException {
        // 初始大小
        long initialMemory = Runtime.getRuntime().totalMemory() / 1024/1024;
        // 最大
        long maxMemory = Runtime.getRuntime().maxMemory() / 1024/1024;
        System.out.println("-Xms:" + initialMemory + "M");
        System.out.println("-Xmx:" + maxMemory + "M");

        // initialMemory: S0 S1 其中一个 + 老年代
        TimeUnit.MINUTES.sleep(5);
    }

}
