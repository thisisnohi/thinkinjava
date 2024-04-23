package nohi.utils;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p>StringFormat</p>
 * @date 2024/04/16 14:44
 **/
public class StringFormat {

    /**
     * 获取汉字长度
     *
     * @param str 字符串
     * @return
     */
    public static int getHanZiLength(String str) {
        if (null == str) {
            return 0;
        }
        int hanZi = 0;
        for (char c : str.toCharArray()) {
            if (Character.isIdeographic(c)) {
                hanZi++;
            }
        }
        return hanZi;
    }

    public static int maxLength(String... args) {
        if (args == null || args.length == 0) {
            return 0;
        }
        int max = 0;
        for (String item : args) {
            max = Math.max(max, StringFormat.maxLength(item));
        }
        return max;
    }

    public static int maxLength(Iterable<String> iterable) {
        if (iterable == null) {
            return 0;
        }

        int max = 0;
        for (String item : iterable) {
            max = Math.max(max, StringFormat.maxLength(item));
        }
        return max;
    }

    /**
     * 获取字符串，屏幕占用最大长度
     *
     * @param str 字符串
     * @return 长度
     */
    public static int maxLength(String str) {
        if (null == str) {
            return 0;
        }
        int hanZi = getHanZiLength(str);
        return hanZi * 5 / 3 + str.length();
    }

    /**
     * 获取字符串，格式化长度
     *
     * @param str 字符串
     * @return 长度
     */
    public static int formatLength(String str, int maxLength) {
        int hanZi = getHanZiLength(str);
        int charLength = null == str ? 0 : str.length();
        if (hanZi > maxLength) {
            return hanZi;
        }
        int length = maxLength - hanZi * 5 / 3 + hanZi;
        if (length + hanZi * 5 / 3 < maxLength) {
            length++;
        }
        return Math.max(length, charLength);
    }

}
