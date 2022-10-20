package nohi.io.reactor.zctest;

import lombok.extern.slf4j.Slf4j;

import java.nio.channels.SelectionKey;

/**
 * 选择器事件分发
 *
 * @author NOHI
 * 2021-08-21 18:43
 **/
@Slf4j
public class DispatchSelectKey {

    /**
     * 获取事件类型
     *
     * @param selectionKey
     * @return
     */
    public String getKeyType(SelectionKey selectionKey) {
        if (selectionKey == null) {
            return null;
        } else if (selectionKey.isAcceptable()) {
            return "ACCEPT";
        } else if (selectionKey.isConnectable()) {
            return "CONNECT";
        } else if (selectionKey.isReadable()) {
            return "READ";
        } else if (selectionKey.isWritable()) {
            return "WRITE";
        }
        return null;
    }

    protected void dispatcher(SelectionKey selectionKey) {
        Runnable runnable = (Runnable) selectionKey.attachment();
        runnable.run();
    }
}
