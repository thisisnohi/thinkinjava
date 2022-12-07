package nohi.jvm._13_GC;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p>垃圾回收器， -XX:+PrintCommandLineFlags 显示应用所使用GC</p>
 * @date 2022/11/26 19:33
 **/
public class GCUseTest {


    /**
     * 指定垃圾回收器
     *  1
     * @param args
     */
    public static void main(String[] args) {
        byte[] bytes = new byte[60 * 1024 * 1024];
        System.out.println("===========");
    }

}
