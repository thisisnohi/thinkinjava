package nohi.io.reactor.zctest;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @author NOHI
 * 2021-08-21 19:48
 **/
public class ReadSelectTask implements Runnable {
    private SocketChannel socketChannel;
    private String name;

    public ReadSelectTask(SocketChannel socketChannel, String name) {
        this.socketChannel = socketChannel;
        this.name = name;
    }

    @Override
    public void run() {
        Selector curSelector = null;
        try {
            curSelector = Selector.open();
            curSelector.wakeup();
            // 注册选择器
            SelectionKey selectionKey = socketChannel.register(curSelector, SelectionKey.OP_READ);
            selectionKey.attach(new WorkProcessHandler(socketChannel, name));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
