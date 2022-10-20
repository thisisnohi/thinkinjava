package nohi.http;

import ch.qos.logback.classic.LoggerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.hc.client5.http.async.methods.*;
import org.apache.hc.client5.http.classic.ExecChain;
import org.apache.hc.client5.http.classic.ExecChainHandler;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.fluent.Response;
import org.apache.hc.client5.http.impl.ChainElement;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicClassicHttpResponse;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.http.nio.AsyncRequestProducer;
import org.apache.hc.core5.http.nio.support.AsyncRequestBuilder;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.net.URIBuilder;
import org.apache.hc.core5.util.Timeout;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <h3>nohi-web</h3>
 *
 * @author NOHI
 * @description <p>测试apache http client</p>
 * @date 2022/09/14 17:13
 **/
@Slf4j
public class TestApacheHttpClient {
    /*
     * 初始化:
     *  打印默认级别
     */
    static {
        ((LoggerContext) LoggerFactory.getILoggerFactory()).getLoggerList().forEach(logger -> {
            if (null != logger && null != logger.getLevel()) {
                System.out.println(logger.getName() + ":" + logger.getLevel().levelStr);
            }
        });
    }

    @Test
    public void testLog() {
        log.debug("====debug=====");
        log.info("====INFO=====");
        log.warn("====warn=====");
        System.out.println("======");
    }

    // 设置超时时间
    RequestConfig config = RequestConfig.custom().setConnectTimeout(Timeout.ofMilliseconds(5000L)).setConnectionRequestTimeout(Timeout.ofMilliseconds(5000L)).setResponseTimeout(Timeout.ofMilliseconds(5000L)).build();

    /**
     * 同步Get请求
     */
    @Test
    public void testGet() {
        String url = "http://127.0.0.1:8888/mock/http?sleep=8000";

        // 创建 HttpGet 请求
        HttpGet httpGet = new HttpGet(url);
        // 设置长连接
        httpGet.setHeader("Connection", "keep-alive");
        // 设置 Cookie
        httpGet.setHeader("Cookie", "Cooooooooooookie...");

        // 请求级别的超时
        httpGet.setConfig(config);

        RequestConfig config = RequestConfig.custom().setConnectTimeout(Timeout.ofMilliseconds(5000L)).setConnectionRequestTimeout(Timeout.ofMilliseconds(5000L)).setResponseTimeout(Timeout.ofMilliseconds(10000L)).build();
        // 客户端级别的超时
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
        // 创建 HttpClient 客户端
        // CloseableHttpClient httpClient = HttpClients.createDefault();

        // 表单参数
        List<NameValuePair> nvps = new ArrayList<>();
        // GET 请求参数
        nvps.add(new BasicNameValuePair("username", "wdbyte.com"));
        nvps.add(new BasicNameValuePair("password", "secret"));
        // 增加到请求 URL 中
        try {
            URI uri = new URIBuilder(new URI(url)).addParameters(nvps).build();
            httpGet.setUri(uri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }


        CloseableHttpResponse httpResponse = null;
        try {
            // 请求并获得响应结果
            httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            // 输出请求结果
            log.info("输出请求结果:{}", EntityUtils.toString(httpEntity));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 无论如何必须关闭连接
        finally {
            IOUtils.closeQuietly(httpResponse);
            IOUtils.closeQuietly(httpClient);
        }
    }

    /**
     * fluent GET
     */
    @Test
    public void getFluent() {
        String result = null;
        String url = "http://127.0.0.1:8888/mock/http?sleep=8000";
        try {
            Response response = Request.get(url).execute();
            result = response.returnContent().asString();

            // 输出请求结果
            log.info("输出请求结果:{}", result);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 同步POST请求
     */
    @Test
    public void post() {
        // 创建 HttpClient 客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建 HttpPost 请求
        HttpPost httpPost = new HttpPost("http://127.0.0.1:8888/mock/http?sleep=800");
        // 设置长连接
        httpPost.setHeader("Connection", "keep-alive");
        // 设置代理（模拟浏览器版本）
//        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
        // 设置 Cookie
//        httpPost.setHeader("Cookie", "UM_distinctid=16442706a09352-0376059833914f-3c604504-1fa400-16442706a0b345; CNZZDATA1262458286=1603637673-1530123020-%7C1530123020; JSESSIONID=805587506F1594AE02DC45845A7216A4");

        // 创建 HttpPost 参数
        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("draw", "1"));
        params.add(new BasicNameValuePair("start", "0"));
        params.add(new BasicNameValuePair("length", "10"));

        CloseableHttpResponse httpResponse = null;
        try {
            // 设置 HttpPost 参数
            httpPost.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));
            httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            // 输出请求结果
            System.out.println(EntityUtils.toString(httpEntity));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 无论如何必须关闭连接
        finally {
            IOUtils.closeQuietly(httpResponse);
            IOUtils.closeQuietly(httpClient);
        }
    }

    /**
     * Fluent Post
     */
    @Test
    public void postFluent() {
        String url = "http://127.0.0.1:8888/mock/http?sleep=100";
        String result = null;
        Request request = Request.post(url);
        // POST 请求参数
        request.bodyForm(new BasicNameValuePair("username", "wdbyte.com"), new BasicNameValuePair("password", "secret"));
        try {
            result = request.execute().returnContent().asString();
            // 输出请求结果
            log.info("result:{}", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void postJSON() {
        // 创建 HttpClient 客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建 HttpPost 请求
        HttpPost httpPost = new HttpPost("http://127.0.0.1:8888/mock/http?sleep=800");
        // 设置长连接
        httpPost.setHeader("Connection", "keep-alive");

        // 创建 HttpPost 参数
        String reqMsg = "{" + "\"retCode\": \"\"," + "\"retMsg\": \"\"," + "\"data\": {" + "\"a\": \"这是请求\"," + "\"b\": \"2\"," + "\"c\": \"3\"" + "}," + "\"traceId\": \"202209162226011001\"," + "\"traceTime\": \"20220916222601\"," + "\"txCode\": \"POST\"" + "}";
        // 设置 HttpPost 参数
        httpPost.setEntity(new StringEntity(reqMsg, ContentType.APPLICATION_JSON));

        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            // 输出请求结果
            System.out.println(EntityUtils.toString(httpEntity));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 无论如何必须关闭连接
        finally {
            IOUtils.closeQuietly(httpResponse);
            IOUtils.closeQuietly(httpClient);
        }
    }

    /**
     * 异步HTTP Client
     * httpclient5 提供
     */
    @Test
    public void testGetSync() {
        String url = "http://127.0.0.1:8888/mock/http?sleep=8000";
        try (CloseableHttpAsyncClient client = HttpAsyncClients.createDefault()) {
            client.start();
            final SimpleHttpRequest request = SimpleRequestBuilder.get().setUri(url).build();

            Future<SimpleHttpResponse> future = client.execute(request, new FutureCallback<>() {
                @Override
                public void completed(SimpleHttpResponse simpleHttpResponse) {
                    log.info("...SimpleHttpRequest.completed");
                    String msg = simpleHttpResponse.getBodyText();
                    log.debug("msg:{}", msg);
                }

                @Override
                public void failed(Exception e) {
                    log.error("...SimpleHttpRequest.failed:{}", e.getMessage());
                }

                @Override
                public void cancelled() {
                    log.info("...SimpleHttpRequest.cancelled");
                }
            });

            SimpleHttpResponse response = future.get();

            log.info("response.status[{}]", response.getCode());
            log.info("response.reasonPhrase[{}]", response.getReasonPhrase());

        } catch (Exception e) {
            log.error("{} 异常:{}", url, e.getMessage());
        }
    }

    /**
     *
     */
    @Test
    public void testGetSync2() {
        String url = "http://127.0.0.1:8888/mock/http?sleep=8000";
        try (CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault()) {
            // 开始 http clinet
            httpclient.start();
            CountDownLatch latch = new CountDownLatch(1);
            SimpleHttpRequest request = SimpleRequestBuilder.get().setUri(url).build();
            AsyncRequestProducer producer = AsyncRequestBuilder.get(url).setCharset(StandardCharsets.UTF_8).build();
            AbstractCharResponseConsumer<HttpResponse> consumer = new AbstractCharResponseConsumer<HttpResponse>() {
                HttpResponse response;

                @Override
                protected void start(HttpResponse response, ContentType contentType) throws HttpException, IOException {
                    log.info("testGetSync2: 开始响应....");
                    this.response = response;
                }

                @Override
                protected int capacityIncrement() {
                    return Integer.MAX_VALUE;
                }

                @Override
                protected void data(CharBuffer data, boolean endOfStream) throws IOException {
                    log.info("testGetSync2: 收到数据 endOfStream[{}]....{}", endOfStream, data.toString());
                }

                @Override
                protected HttpResponse buildResult() throws IOException {
                    log.info("testGetSync2: 接收完毕....");
                    return response;
                }

                @Override
                public void releaseResources() {
                }

            };
            httpclient.execute(producer, consumer, new FutureCallback<HttpResponse>() {
                @Override
                public void completed(HttpResponse response) {
                    latch.countDown();
                    log.info("testGetSync2: completed....{}", response.getCode());
                }

                @Override
                public void failed(Exception ex) {
                    latch.countDown();
                    log.error("testGetSync2: failed....{}", ex.getMessage(), ex);
                }

                @Override
                public void cancelled() {
                    latch.countDown();
                    log.info("testGetSync2: cancelled....");
                }

            });
            latch.await();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 异步HTTP Client
     * httpclient5 提供
     */
    @Test
    public void testPostSync() {
        String url = "http://127.0.0.1:8888/mock/http?sleep=1000";
        String reqMsg = "{" + "\"retCode\": \"\"," + "\"retMsg\": \"\"," + "\"data\": {" + "\"a\": \"这是请求\"," + "\"b\": \"2\"," + "\"c\": \"3\"" + "}," + "\"traceId\": \"202209162226011001\"," + "\"traceTime\": \"20220916222601\"," + "\"txCode\": \"POST\"" + "}";
        try (CloseableHttpAsyncClient client = HttpAsyncClients.createDefault()) {
            client.start();
            final SimpleHttpRequest request = SimpleRequestBuilder.post(url).addHeader("Content-Type", "application/json").build();
            request.setBody(reqMsg, ContentType.APPLICATION_JSON);
            Future<SimpleHttpResponse> future = client.execute(request, new FutureCallback<>() {
                @Override
                public void completed(SimpleHttpResponse simpleHttpResponse) {
                    log.info("...SimpleHttpRequest.completed");
                    String msg = simpleHttpResponse.getBodyText();
                    log.debug("msg:{}", msg);
                }

                @Override
                public void failed(Exception e) {
                    log.error("...SimpleHttpRequest.failed:{}", e.getMessage());
                }

                @Override
                public void cancelled() {
                    log.info("...SimpleHttpRequest.cancelled");
                }
            });

            SimpleHttpResponse response = future.get();
            log.info("response.status[{}]", response.getCode());
            log.info("response.reasonPhrase[{}]", response.getReasonPhrase());
        } catch (Exception e) {
            log.error("{} 异常:{}", url, e.getMessage());
        }
    }

    @Test
    public void testIntercepter() throws IOException, ParseException, URISyntaxException {

        String url = "http://127.0.0.1:8888/mock/http?sleep=1000";

        try (final CloseableHttpClient httpclient = HttpClients.custom()
                // 添加一个请求 id 到请求 header
                .addRequestInterceptorFirst(new HttpRequestInterceptor() {
                    private final AtomicLong count = new AtomicLong(0);
                    @Override
                    public void process(
                            final HttpRequest request,
                            final EntityDetails entity,
                            final HttpContext context) throws HttpException, IOException {
                        request.setHeader("request-id", Long.toString(count.incrementAndGet()));
                    }
                })
                .addExecInterceptorAfter(ChainElement.PROTOCOL.name(), "custom", new ExecChainHandler() {
                    // 请求 id 为 2 的，模拟 404 响应，并自定义响应的内容。
                    @Override
                    public ClassicHttpResponse execute(
                            final ClassicHttpRequest request,
                            final ExecChain.Scope scope,
                            final ExecChain chain) throws IOException, HttpException {

                        final Header idHeader = request.getFirstHeader("request-id");
                        if (idHeader != null && "2".equalsIgnoreCase(idHeader.getValue())) {
                            final ClassicHttpResponse response = new BasicClassicHttpResponse(HttpStatus.SC_NOT_FOUND,
                                    "Oppsie");
                            response.setEntity(new StringEntity("bad luck", ContentType.TEXT_PLAIN));
                            return response;
                        } else {
                            return chain.proceed(request, scope);
                        }
                    }
                })
                .build()) {

            for (int i = 0; i < 3; i++) {
                final HttpGet httpget = new HttpGet(url);

                try (final CloseableHttpResponse response = httpclient.execute(httpget)) {
                    System.out.println("----------------------------------------");
                    System.out.println("执行请求 " + httpget.getMethod() + " " + httpget.getUri());
                    System.out.println(response.getCode() + " " + response.getReasonPhrase());
                    System.out.println(EntityUtils.toString(response.getEntity()));
                }
            }
        }
    }
}


