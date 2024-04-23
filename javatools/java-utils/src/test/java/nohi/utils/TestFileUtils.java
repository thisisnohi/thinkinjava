package nohi.utils;

import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p>file</p>
 * @date 2024/04/01 16:19
 **/
public class TestFileUtils {

    @Test
    public void filePath() throws IOException {
        String path = FileUtils.getProjectPath();
        System.out.println("path: " + path);
    }
}
