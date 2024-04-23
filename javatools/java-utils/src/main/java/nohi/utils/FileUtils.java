package nohi.utils;


import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件工具类
 */
public class FileUtils {

    /**
     * 默认字符集
     */
    public static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * 获取项目/应用目录
     *
     * @return 项目绝对路径
     */
    public static String getProjectPath() throws IOException {
        File directory = new File("");
        return directory.getCanonicalPath();
    }

    /**
     * 判断路径是否存在
     *
     * @param path 路径
     */
    public static boolean exists(String path) {
        if (StringUtils.isBlank(path)) {
            return false;
        }
        File file = new File(path);
        return file.exists();
    }


    /**
     * 保存信息至文件，指定文件名
     * 默认UTF-8
     *
     * @param message  内容
     * @param docPath  文件路径
     * @param fileName 文件名
     * @return 文件路径
     */
    public static String storeMessage(String message, String docPath, String fileName) throws Exception {
        return storeMessage(message, DEFAULT_ENCODING, docPath, fileName, false, false);
    }

    /**
     * 保存信息至文件，指定文件名
     * 默认UTF-8
     *
     * @param message     内容
     * @param docPath     文件路径
     * @param fileName    文件名
     * @param checkExists 是否判断文件已存在
     * @return 文件路径
     */
    public static String storeMessage(String message, String docPath, String fileName, boolean checkExists) throws Exception {
        return storeMessage(message, DEFAULT_ENCODING, docPath, fileName, checkExists, false);
    }

    /**
     * 保存输入流中的数据至文件，指定文件名
     * 默认UTF-8
     *
     * @param docPath  目录
     * @param fileName 文件名
     * @return 返回文件路径
     * @throws IOException 异常
     */
    public static String storeMessage(InputStream is, String docPath, String fileName) throws IOException {
        if (null == is || StringUtils.isBlank(fileName)) {
            throw new RuntimeException("输入流不能为空/文件名不能为空!");
        }
        //创建目录
        Path path = Paths.get(docPath);
        Files.createDirectories(path);

        path = Paths.get(docPath, fileName);

        File file = path.toFile();
        if (file.exists()) {
            throw new RuntimeException("文件已经存在");
        }
        try (FileOutputStream fos = new FileOutputStream(file); //直接写文件全路径
        ) {
            byte[] bytes = new byte[2048];
            int length = -1;
            while ((length = is.read(bytes)) > -1) {
                fos.write(bytes, 0, length);
            }
            fos.flush();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        // 返回文件路径和文件名
        return path.toString();
    }

    /**
     * 保存信息至文件，指定文件名、字符集
     *
     * @param message     内容
     * @param encode      字符集
     * @param docPath     文件路径
     * @param fileName    文件名
     * @param checkExists 是否判断文件已存在
     */
    public static String storeMessage(String message, String encode, String docPath, String fileName, boolean checkExists, boolean append) throws Exception {
        if (StringUtils.isBlank(fileName)) {
            throw new RuntimeException("文件名不能为空!");
        }
        //创建目录
        Path path = Paths.get(docPath);
        Files.createDirectories(path);

        path = Paths.get(docPath, fileName);

        File file = new File(docPath + File.separator + fileName);
        if (file.exists() && (file.isDirectory() || checkExists)) { //如果文件存在: 文件是目录/需要检查文件是否存在,则报错
            throw new Exception("文件已经存在");
        }

        if (null == message) {
            message = "";
        }
        try (FileOutputStream fos = new FileOutputStream(file, append); //直接写文件全路径
             OutputStreamWriter writer = new OutputStreamWriter(fos, encode);) {
            writer.write(message);
            writer.flush();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        return path.toString();
    }

    /**
     * 从输入流中获取数据
     *
     * @param inStream 输入流
     * @return 返回内容字节数组
     */
    public static byte[] readStream(InputStream inStream) throws IOException {
        try (ByteArrayOutputStream outStream = new ByteArrayOutputStream();) {
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            return outStream.toByteArray();
        } finally {
            IOUtils.closeQuietly(inStream);
        }
    }

    /**
     * 从输入流中获取数据
     *
     * @param inStream 输入流
     * @return 返回内容字符串
     */
    public static String readStringFromStream(InputStream inStream) throws IOException {
        byte[] buffer = readStream(inStream);
        return new String(buffer, DEFAULT_ENCODING);
    }

    /**
     * 从输入流中获取数据
     */
    public static String readStringFromPath(String path) throws Exception {
        return readStringFromStream(Files.newInputStream(Paths.get(path)));
    }

    /**
     * 从输入流中获取数据
     */
    public static String readStringFromPath(Path path) throws Exception {
        return readStringFromStream(Files.newInputStream(path));
    }

    /**
     * 获取文件扩展名
     *
     * @param filename 文件名
     * @return 文件扩展名
     */
    public static String getExtensionName(String filename) {
        if (StringUtils.isNotBlank(filename)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

}
