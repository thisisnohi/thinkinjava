package nohi.jvm._08_heap;

import nohi.jvm._08_heap.vo.Student;
import nohi.jvm._08_heap.vo.WebPage;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p>演示OOM</p>
 * @date 2022/11/12 14:31
 **/
public class StudentTrace {
    static List<WebPage> webPageList = new ArrayList<>();

    public void createWebPage() {
        for (int i = 0; i < 100; i++) {
            webPageList.add(new WebPage("http://www." + i + ".com", "WP_" + i, ("CONTENT_" + i).getBytes()));
        }
    }

    /**
     * 不停创建byte数组分配堆内存
     * -Xms10m -Xmx10m -XX:+PrintGCDetails
     * -Xms60m -Xmx60m -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGC
     * -XX:+HeapDumpBeforeFullGC -XX:HeapDumpPath=/Users/nohi/tmp/dump/hprof/StudentTrace.hprof
     *
     * @throws InterruptedException
     */
    @Test
    public void show() throws InterruptedException {
        createWebPage();

        Student s1 = new Student(3, "TOM");
        Student s2 = new Student(5, "Jerry");
        Student s3 = new Student(7, "Supper");


        for (int i = 0; i < webPageList.size(); i++) {
            if (i % s1.getId() == 0) {
                s1.getWebPageList().add(webPageList.get(i));
            } else if (i % s2.getId() == 0) {
                s2.getWebPageList().add(webPageList.get(i));
            } else if (i % s3.getId() == 0) {
                s3.getWebPageList().add(webPageList.get(i));
            }
        }

        webPageList.clear();

        System.gc();

    }

}
