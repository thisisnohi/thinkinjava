package nohi.io.reactor.zctest;

import lombok.extern.slf4j.Slf4j;
import nohi.io.reactor.reactorZC.SubReactor;
import nohi.io.reactor.reactorZC.WorkHandlerZC;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 接收器，创建子响应器线程组
 *
 * @author NOHI
 * 2021-08-21 18:38
 **/
@Slf4j
public class AcceptorPool implements Runnable {
    private ServerSocketChannel serverSocketChannel;

    private static int corePoolSize = 5;
    private static int maxPoolSize = 5;
    private static int keepAliveTime = 200;

    ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5));


    private int index;
    private final int CORE = 8;
    private SubReactor[] subReactors = new SubReactor[CORE];
    private Thread[] threads = new Thread[CORE];
    private final Selector[] selectors = new Selector[CORE];

    public AcceptorPool(ServerSocketChannel serverSocketChannel) {
        this.serverSocketChannel = serverSocketChannel;

        // 创建子响应器线程组
        // 使用线程池处理
        for (int i = 0; i < CORE; i++) {
            try {
                selectors[i] = Selector.open();
            } catch (IOException e) {
                e.printStackTrace();
            }
            subReactors[i] = new SubReactor(selectors[i], i);
            threads[i] = new Thread(subReactors[i]);
            threads[i].start();
        }
    }

    /**
     * 接收到客户连接后，处理交给读工作处理器处理
     */
    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        try {
            index++;
            log.debug("AcceptorPool thread:" + Thread.currentThread().getName());
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            log.debug("connect from {}", socketChannel.getRemoteAddress());
            name = name + "=" + socketChannel.getRemoteAddress() + "-" + index;
            String finalName = name;

            // 方式一数组方式
//            selectors[index].wakeup();
//            SelectionKey selectionKey = socketChannel.register(selectors[index], SelectionKey.OP_READ);
//            selectionKey.attach(new WorkProcessHandler(socketChannel, finalName));
//            if (++index == threads.length) {
//                index = 0;
//            }

//                executor.execute(new ReadSelectTask(socketChannel, name));

            // 方式二，线程池
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    Selector curSelector = null;
                    try {
                        curSelector = Selector.open();
                        // 启用子线程
                        SubReactor subReactor = new SubReactor(curSelector, index);
                        new Thread(subReactor).start();

                        // 注册选择器
                        SelectionKey selectionKey = socketChannel.register(curSelector, SelectionKey.OP_READ);
                        selectionKey.attach(new WorkProcessHandler(socketChannel, finalName));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });


            StringBuilder sb = new StringBuilder();
            sb.append("正在执行的线程:").append(executor.getActiveCount())
                    .append("poolSize:").append(executor.getPoolSize())
                    .append("getTaskCount:").append(executor.getTaskCount())
                    ;
            log.info("ThreadPool:{}", sb.toString());

        } catch (Exception e) {
            log.debug("{} AcceptorPool.run error:{}", name, e.getMessage(), e);
        }
    }
}
