package nohi.thread.future;

import lombok.Data;

import java.security.SecureRandom;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p></p>
 * @date 2023/02/03 15:56
 **/
@Data
public class FutureCallable<T> implements Callable {
    private long sleep;
    @Override
    public T call() throws Exception {
        System.out.println("callable do something then return...");
        FutureVO vo = new FutureVO();
        vo.setMsgId(new SecureRandom().nextLong());
        vo.setTitle("FUTURE");
        vo.setMsg("TEST future");
        if (sleep > 0) {
            TimeUnit.SECONDS.sleep(sleep);
        }
        return (T) vo;
    }
}