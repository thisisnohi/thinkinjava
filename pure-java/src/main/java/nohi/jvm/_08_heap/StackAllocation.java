package nohi.jvm._08_heap;

import java.util.concurrent.TimeUnit;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p>演示逃逸分析、栈上分配</p>
 * @date 2022/11/12 23:05
 **/
public class StackAllocation {


    /**
     * 演示逃逸分析、栈上分配
     * 1. 分配足够大内存，不开启逃逸分析： 不产生GC，查看内存使用
     *    -Xms1G -Xmx1G -XX:-DoEscapeAnalysis -XX:NewRatio=2 -XX:+PrintGCDetails -XX:SurvivorRatio=8
     * 2. 分配足够大内存，开启逃逸分析： 不产生GC，查看内存使用
     *   -Xms1-Xms100m -Xmx100m -XX:+DoEscapeAnalysis -XX:NewRatio=2 -XX:+PrintGCDetails -XX:SurvivorRatio=8G -Xmx1G -XX:+DoEscapeAnalysis -XX:NewRatio=2 -XX:+PrintGCDetails -XX:SurvivorRatio=8
     * 3. 分配100m内存，不使用逃逸分析，产品GC
     *    -Xms100m -Xmx100m -XX:-DoEscapeAnalysis -XX:NewRatio=2 -XX:+PrintGCDetails -XX:SurvivorRatio=8
     * 4. 分配100内存，开启逃逸分析，不产生GC
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        StackAllocation s = new StackAllocation();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            s.createUser();
        }
        System.out.println("耗时:" + (System.currentTimeMillis() - start));

        TimeUnit.MINUTES.sleep(5);
    }

    public void createUser(){
        Test t = new Test();
    }
    class Test{

    }
}
