package nohi.test.thread;

import nohi.thread.Worker;
import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author NOHI
 * 2021-12-28 17:30
 **/
public class TestThreadPools {

    @Test
    public void testThreadBlock() {
        ExecutorService executor = new ThreadPoolExecutor(3, 3, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(15));

        int size = 10;
        size = 19;
        Worker tasks[] = new Worker[size];
        for (int i = 0; i < size; i++) {
            tasks[i] = new Worker(i);
            System.out.println("提交任务: " + tasks[i] + ", " + i);
            executor.execute(tasks[i]);
        }
        System.out.println("主线程结束");
        try {
            TimeUnit.SECONDS.sleep(25);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();    // 关闭线程池
    }

    @Test
    public void testThreadLink() {
        ExecutorService executor = new ThreadPoolExecutor(3, 3, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(15));

        int size = 10;
        size = 19;
        Worker tasks[] = new Worker[size];
        for (int i = 0; i < size; i++) {
            tasks[i] = new Worker(i);
            System.out.println("提交任务: " + tasks[i] + ", " + i);
            executor.execute(tasks[i]);
        }
        System.out.println("主线程结束");
        try {
            TimeUnit.SECONDS.sleep(25);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();    // 关闭线程池
    }
}
