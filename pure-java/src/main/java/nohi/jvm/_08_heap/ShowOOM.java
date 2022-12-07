package nohi.jvm._08_heap;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p>演示OOM</p>
 * @date 2022/11/12 14:31
 **/
public class ShowOOM {

    /**
     * 不停创建byte数组分配堆内存
     * -Xms10m -Xmx10m -XX:+PrintGCDetails
     * -Xms60m -Xmx60m -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGC
     * @throws InterruptedException
     */
    @Test
    public void showOOM() throws InterruptedException {
        List<Byte[]> list = new ArrayList();
        while (true) {
            list.add(new Byte[10240]);
            TimeUnit.MILLISECONDS.sleep(10);
        }
    }

}
