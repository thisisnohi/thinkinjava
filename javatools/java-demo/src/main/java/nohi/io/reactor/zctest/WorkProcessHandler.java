package nohi.io.reactor.zctest;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;

/**
 * @author NOHI
 * 2021-08-21 19:29
 **/
@Slf4j
public class WorkProcessHandler implements Runnable {
    private SocketChannel socketChannel;
    private String name;

    public WorkProcessHandler(SocketChannel socketChannel, String name) {
        this.socketChannel = socketChannel;
        this.name = name;
    }

    @Override
    public void run() {
        log.debug("{} start ", name);
        boolean close = false;
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            socketChannel.read(byteBuffer);


            String message = new String(byteBuffer.array(), 0, byteBuffer.position(), StandardCharsets.UTF_8);
//            String message = byteBuffer.toString();
            log.debug(socketChannel.getRemoteAddress() + "发来的消息是:" + message);
            if ("exit".equalsIgnoreCase(message)) {
                log.debug("exit for close");
                close = true;
            }
            socketChannel.write(ByteBuffer.wrap("你的消息我收到了".getBytes(StandardCharsets.UTF_8)));

            // 关闭
            if (close) {
                Thread.sleep(10 * 1000);
                log.debug(socketChannel.getRemoteAddress() + " close:");

                socketChannel.write(ByteBuffer.wrap("exit".getBytes(StandardCharsets.UTF_8)));

                socketChannel.finishConnect();
                socketChannel.close();
            }
        } catch (Exception e) {
            log.error("{} run error:{}", name, e.getMessage(), e);
            try {
                log.debug(socketChannel.getRemoteAddress() + " close:");
                socketChannel.finishConnect();
                socketChannel.close();
            } catch(Exception e1) {
                log.error("{} socketChannel.close error:{}", name, e1.getMessage(), e1);
            }
        }
        log.debug("{} over ", name);
    }
}
