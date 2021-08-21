package nohi.io.reactor;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author NOHI
 * 2021-08-17 22:32
 **/
public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("localhost", 9090));
            new Thread(() -> {
                while (true) {
                    try {
                        InputStream inputStream = socket.getInputStream();
                        byte[] bytes = new byte[1024];
                        int size = inputStream.read(bytes);
                        String msg = new String(bytes, 0, size, StandardCharsets.UTF_8);
                        System.out.println(msg);
                        if ("exit".equalsIgnoreCase(msg)) {
                            System.out.println("exit.....");
                            System.exit(1);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.exit(1);
                    }
                }
            }).start();

            while (true) {
                Scanner scanner = new Scanner(System.in);
                while (scanner.hasNextLine()) {
                    String s = scanner.nextLine();
                    socket.getOutputStream().write(s.getBytes());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
