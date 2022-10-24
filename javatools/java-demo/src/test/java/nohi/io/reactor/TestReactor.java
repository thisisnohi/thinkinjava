package nohi.io.reactor;

import lombok.extern.slf4j.Slf4j;
import nohi.io.reactor.reactorZC.ReactorZC;
import org.junit.Test;

/**
 * @author NOHI
 * 2021-08-17 22:47
 **/
@Slf4j
public class TestReactor {

    @Test
    public void startServer单Reactor单线程模型() {
        Reactor reactor = new Reactor(9090);
        reactor.run();
    }

    @Test
    public void startClient单Reactor单线程模型() {
        System.out.println("请用main函数启动");
//        try {
//            Socket socket = new Socket();
//            socket.connect(new InetSocketAddress("localhost", 9090));
//            new Thread(() -> {
//                while (true) {
//                    try {
//                        InputStream inputStream = socket.getInputStream();
//                        byte[] bytes = new byte[1024];
//                        inputStream.read(bytes);
//                        System.out.println(new String(bytes, StandardCharsets.UTF_8));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//
//            while (true) {
//                Scanner scanner = new Scanner(System.in);
//                while (scanner.hasNextLine()) {
//                    String s = scanner.nextLine();
//                    socket.getOutputStream().write(s.getBytes());
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void startServer主从Reactor模型() {
        log.debug("startServer主从Reactor模型");
        System.out.println("startServer主从Reactor模型");
        ReactorZC reactor = new ReactorZC(9090);
        reactor.run();
    }
}
