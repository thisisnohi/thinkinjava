package nohi.jvm._02_classload;

import org.junit.Test;

import java.net.URL;
import java.security.Provider;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p>类加载器</p>
 * @date 2022/11/07 13:13
 **/
public class ClassLoaderTest {

    public static void main(String[] args) {
        // 系统类加载器
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        // sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println(systemClassLoader);

        // 获取上层，扩展加载器
        ClassLoader extClassLoader = systemClassLoader.getParent();
        // sun.misc.Launcher$ExtClassLoader@60e53b93
        System.out.println(extClassLoader);

        // 获取扩展类上层
        ClassLoader bootStrap = extClassLoader.getParent();
        // null
        System.out.println(bootStrap);

        // 用户自定义类: 默认使用系统类加载器
        ClassLoader userClass = ClassLoaderTest.class.getClassLoader();
        System.out.println(userClass);

        // String（java核心类库）使用引导类加载器
        ClassLoader stringClass = String.class.getClassLoader();
        System.out.println(stringClass);
    }

    /**
     * 显示加载路径
     */
    @Test
    public void showClassLoader(){
        System.out.println("===========BootstrapClassLoader============");
        // 获取BootstrapClassLoader加载路径
        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for (URL url : urls) {
            System.out.println(url);
        }

        ClassLoader classLoader = Provider.class.getClassLoader();
        System.out.println("BootstrapClassLoader:" + classLoader);

        System.out.println("===========java.ext.dirs============");
        // 显示扩展类加载器
        String extDir = System.getProperty("java.ext.dirs");
        // 分隔符不同  windows ;  Linux :
        for (String s : extDir.split(":")) {
            System.out.println(s);
        }
    }
}
