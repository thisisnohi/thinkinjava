package nohi.jdk._11;


import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <h3>jdk</h3>
 *
 * @author NOHI
 * @description <p>jdk11</p>
 * @date 2023/01/19 12:24
 **/
public class Jdk11 {

    /**
     * jdk11 String 新增方法
     */
    @Test
    public void testString() {
        // unicode 空白符
        char c = '\u2000';
        String str = c + "abc" + c;
        System.out.println(String.format("[%s]", str));
        System.out.println(String.format("trim[%s]", str.trim()));
        System.out.println(String.format("strip[%s]", str.strip()));
        str = " abc ";
        System.out.println(String.format("[%s]", str));
        System.out.println(String.format("trim[%s]", str.trim()));
        System.out.println(String.format("strip[%s]", str.strip()));
    }

    @Test
    public void testStream() {
        // 0
        Stream.ofNullable(null).count();

        // [1, 2]
        Stream.of(1, 2, 3, 2, 1).takeWhile(n -> n < 3).collect(Collectors.toList());

        // [3, 2, 1]
        Stream.of(1, 2, 3, 2, 1).dropWhile(n -> n < 3).collect(Collectors.toList());
    }

    @Test
    public void testOption() {
        // javastack
        var msg = Optional.of("javastack").orElseThrow();
        System.out.println("msg:" + msg);
        System.out.println("msg.orElse:" + Optional.ofNullable(null).orElse("11111"));
        // 1
        long count = Optional.of("javastack").stream().count();
        System.out.println("count:" + count);
        // javastack
        var obj = Optional.ofNullable(null).or(() -> Optional.of("javastack")).get();
        System.out.println("obj:" + obj);

        // 没有值，抛出异常
        System.out.println("msg.orElse:" + Optional.ofNullable(null).orElseThrow());
    }

    @Test
    public void testInputStream() throws IOException {
        var classLoader = ClassLoader.getSystemClassLoader();
        var inputStream = classLoader.getResourceAsStream("application.yml");
        var javastack = File.createTempFile("application-target", "txt");
        try (var outputStream = new FileOutputStream(javastack)) {
            inputStream.transferTo(outputStream);
        }
        System.out.println("java.io.tmpdir:" + System.getProperty("java.io.tmpdir"));
    }


    @Test
    public void testJdkHttpClient() throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder().uri(URI.create("https://www.baidu.com")).GET().build();
        var client = HttpClient.newHttpClient();
        // 同步
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        // 异步
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).thenAccept(System.out::println);
    }

    @Test
    public void testFile() throws IOException {
        System.out.println(Path.of("./").toAbsolutePath());;
        Files.writeString(
                // 路径
                Path.of("./", "tmp.txt"),
                // 内容
                "hello, jdk11 files api",
                // 编码
                StandardCharsets.UTF_8);
        String s = Files.readString(
                // 路径
                Paths.get("./tmp.txt"),
                // 编码
                StandardCharsets.UTF_8);
        System.out.println(s);
    }
}
