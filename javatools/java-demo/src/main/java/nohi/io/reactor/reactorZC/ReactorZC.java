package nohi.io.reactor.reactorZC;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author NOHI
 * 2021-08-17 23:04
 **/
@Slf4j
public class ReactorZC implements Runnable {
    private ServerSocketChannel serverSocketChannel;

    private Selector selector;

    public ReactorZC(int port) {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            selector = Selector.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            selectionKey.attach(new AcceptorZC(serverSocketChannel));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    @Override
    public void run() {
        try {
            while (true) {
                // 此selector只有 ACCEPT
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    log.debug("key:{}", getKeyType(selectionKey));
                    dispatcher(selectionKey);
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dispatcher(SelectionKey selectionKey) {
        Runnable runnable = (Runnable) selectionKey.attachment();
        runnable.run();
    }
}
