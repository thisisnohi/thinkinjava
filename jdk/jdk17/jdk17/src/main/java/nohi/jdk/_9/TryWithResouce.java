package nohi.jdk._9;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p>改进的try with resource</p>
 * @date 2023/01/17 13:19
 **/
public class TryWithResouce {

    public void testTryWithResource(String src, String target) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(src);
        FileOutputStream fos = new FileOutputStream(target);
        try (fis; fos) {
            byte[] is = new byte[1024];
            int length;
            while ((length = fis.read(is)) > -1) {
                fos.write(is, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
