package nohi.io.reactor.zctest;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * Nio 主Reactor
 *
 * @author NOHI
 * 2021-08-21 16:18
 **/
@Slf4j
public class MainReactor extends DispatchSelectKey implements Runnable {
    private Selector selector;
    private String name;

    public MainReactor(String name, int port) throws IOException {
        this.name = name;
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            selector = Selector.open();

            // 注册ACCEPT SelectKey
            SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            selectionKey.attach(new AcceptorPool(serverSocketChannel));
        } catch (IOException e) {
            log.error("开起端口异常:{}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                selector.select();
                Iterator<SelectionKey> iterable = selector.selectedKeys().iterator();

//                for (SelectionKey selectionKey : selector.selectedKeys()) {
//                    log.debug("MainRector.selectKey {}", this.getKeyType(selectionKey));
//                    this.dispatcher(selectionKey);
//                    // 如果不删除这里的selector 会不会每次都有事件
//
//                }

                while (iterable.hasNext()) {
                    SelectionKey key = iterable.next();
                    log.debug("MainRector.selectKey {}", this.getKeyType(key));
                    this.dispatcher(key);
                    iterable.remove();
                }

            } catch (IOException e) {
                log.error("选择器异常:{}", e.getMessage());
            }
        }
    }


    public static void main(String[] args) throws IOException {
        new MainReactor("MAIN", 9090).run();
    }
}
