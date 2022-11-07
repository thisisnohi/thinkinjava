package nohi.jvm._02_classload;

import org.junit.Test;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * <h3>thinkinjava</h3>
 * 判断类定义是否相同
 *   1. 相同类加载器
 *   2. 相同类定义
 * @author NOHI
 * @description <p>自定义类加载器</p>
 * @date 2022/11/07 21:19
 **/
public class MyClassLoader extends ClassLoader {

    public static final String PROJECT_DIR = new File("").getAbsolutePath();

    @Test
    public void showDir(){
        System.out.println("PROJECT_DIR:" + PROJECT_DIR);
    }

    /**
     *
     * @param className 类另
     *         The <a href="#name">binary name</a> of the class
     * @return 类
     */
    @Override
    protected Class<?> findClass(String className) {
        byte[] bytes = null;
        // 将点替换成斜杠
        String fileName = className.replaceAll("\\.", "/");
        // 加载路径
        StringBuilder sb = new StringBuilder(PROJECT_DIR);
        sb.append(File.separator);
        sb.append("ext").append(File.separator);
        sb.append(fileName);
        sb.append(".class");
        fileName = sb.toString();

        try (InputStream is = Files.newInputStream(Paths.get(fileName));
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buf = new byte[1024];
            int r = 0;
            while ((r = is.read(buf)) != -1) {
                bos.write(buf, 0, r);
            }
            bytes = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(className, bytes, 0, bytes.length);
    }

    public static void main(String[] args) throws Exception {
        //自定义类加载器对象1
        MyClassLoader c1=new MyClassLoader();
        // 类的包名
        String className="_02_classloader.BB";
         // loadClass调用的就是findClass()
        Class<?> clazz1=c1.loadClass(className);

        //自定义类加载器对象2
        MyClassLoader c2=new MyClassLoader();
        Class clazz2=c2.loadClass(className);

        System.out.println(clazz1.getClassLoader());
        System.out.println(clazz2.getClassLoader());

        if(clazz1!=clazz2){
            System.out.println("不同的类加载器对象加载相同的class文件，会产生不同的类对象");
        }
        Object obj1=clazz1.getDeclaredConstructor(new Class[]{String.class}).newInstance("自定义加载器加载进内存的");
        Field fa=clazz1.getDeclaredField("a");
        // 将私有变量设置成可以访问的权限
        fa.setAccessible(true);
        System.out.println(fa.get(obj1));
    }
}
