package nohi.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * @author NOHI
 * 2021-12-28 17:23
 **/
@Slf4j
public class Worker implements Runnable{
    private int id;
    public Worker(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {
            log.info("{} 执行任务[{}]", Thread.currentThread().getName(), id);
            Thread.sleep(1000);
            log.info("{} 执行完成[{}]", Thread.currentThread().getName(), id);
        } catch(Exception e) {
            log.error("执行异常", e);
        }
    }
}
