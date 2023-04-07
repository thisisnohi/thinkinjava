package nohi._java._thread;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import nohi.thread.future.FutureCallable;
import nohi.thread.future.FutureVO;
import org.junit.Test;

import java.security.SecureRandom;
import java.util.concurrent.*;


/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p>Test Future</p>
 * @date 2023/02/03 15:48
 **/
public class TestFuture {
    int poolMax = 2;
    // 固定大小
    ExecutorService executor = Executors.newFixedThreadPool(poolMax);

    @Test
    public void testFuture() throws InterruptedException {
        FutureCallable<FutureVO> futureCallable = new FutureCallable<FutureVO>();
        futureCallable.setSleep(10l);
        FutureTask<FutureVO> ft = new FutureTask(futureCallable);
        executor.submit(ft);

        try {
            System.out.println("isDone:" + ft.isDone());
            System.out.println("isDone:" + ft.isDone());
            System.out.println("isDone:" + ft.isDone());

            FutureVO futureVO = ft.get();
            System.out.println(JSON.toJSONString(futureVO));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFutureCallable() throws InterruptedException {
        FutureTask<FutureVO> ft = new FutureTask(new Callable<FutureVO>() {
            long sleep = 10l;

            @Override
            public FutureVO call() throws Exception {
                System.out.println("callable do something then return...");
                FutureVO vo = new FutureVO();
                vo.setMsgId(new SecureRandom().nextLong());
                vo.setTitle("FUTURE");
                vo.setMsg("TEST future");
                if (sleep > 0) {
                    TimeUnit.SECONDS.sleep(sleep);
                }
                return vo;
            }
        });
        executor.submit(ft);

        try {
            System.out.println("isDone:" + ft.isDone());
            System.out.println("isDone:" + ft.isDone());
            System.out.println("isDone:" + ft.isDone());

            FutureVO futureVO = ft.get();
            System.out.println(JSON.toJSONString(futureVO));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCompletableFuture() {
        long sleep = 10l;

        CompletableFuture<FutureVO> cf = CompletableFuture.supplyAsync(() -> {
            System.out.println("CompletableFuture.supplyAsync do something then return...");
            FutureVO vo = new FutureVO();
            vo.setMsgId(new SecureRandom().nextLong());
            vo.setTitle("FUTURE");
            vo.setMsg("TEST future");
            if (sleep > 0) {
                try {
                    TimeUnit.SECONDS.sleep(sleep);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return vo;
        }, executor);

        try {
            FutureVO vo = cf.get(1, TimeUnit.SECONDS);
            System.out.println("1:" + JSONObject.toJSONString(vo));
            return;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("get with 1s error " + e.getMessage());
        }

        try {
            FutureVO vo = cf.get(2, TimeUnit.SECONDS);
            System.out.println("2:" + JSONObject.toJSONString(vo));
            return;
        } catch (Exception e) {
            System.out.println("get with 2s error " + e.getMessage());
        }

        try {
            FutureVO vo = cf.get();
            System.out.println("3:" + JSONObject.toJSONString(vo));
        } catch (Exception e) {
            System.out.println("get error " + e.getMessage());
        }
    }


    @Test
    public void testThenRun() throws ExecutionException, InterruptedException {
        CompletableFuture<String> orgFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("先执行第一个CompletableFuture方法任务");
            return "捡田螺的小男孩";
        });

        CompletableFuture thenRunFuture = orgFuture.thenRun(() -> {
            System.out.println("接着执行第二个任务");
        });

        System.out.println(thenRunFuture.get());
    }

    @Test
    public void testThenAccept() throws ExecutionException, InterruptedException {
        CompletableFuture<String> orgFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("原始CompletableFuture方法任务");
            return "捡田螺的小男孩";
        });
        System.out.println("orgFuture:" + orgFuture.get());
        CompletableFuture thenAcceptFuture = orgFuture.thenAccept((a) -> {
            if ("捡田螺的小男孩".equals(a)) {
                System.out.println("关注了");
            }
            System.out.println("先考虑考虑");
        });
        System.out.println("thenAcceptFuture:" + thenAcceptFuture.get());
    }

}
