package nohi.io.reactor;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author NOHI
 * 2021-08-17 22:40
 **/
@Slf4j
public class Acceptor implements Runnable {
    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    public Acceptor(Selector selector, ServerSocketChannel serverSocketChannel) {
        log.debug("Acceptor()");
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
    }

    @Override
    public void run() {
        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            System.out.println("有客户端连接上来了," + socketChannel.getRemoteAddress());
            socketChannel.configureBlocking(false);
            SelectionKey selectionKey = socketChannel.register(selector, SelectionKey.OP_READ);
            // 单Reactor单线程模型
            // selectionKey.attach(new WorkHandler(socketChannel));
            // 单Reactor多线程模型
            selectionKey.attach(new WorkHandlerThread(socketChannel));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
