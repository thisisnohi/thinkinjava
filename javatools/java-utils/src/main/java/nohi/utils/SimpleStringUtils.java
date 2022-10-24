package nohi.utils;


/**
 * 简单String工具类
 *
 * @author NOHI
 * @date 2022/10/20 15:20
 */
public class SimpleStringUtils {

    /**
     * 截取指定节点内的字符串
     *
     * @param msg  字符串
     * @param startNode  开始结点
     * @param endNode   结束节点
     * @return 结果
     */
    public static String cutString(String msg, String startNode, String endNode) {
        int index = msg.indexOf(startNode);
        int length = startNode.length();
        return msg.substring(index + length, msg.indexOf(endNode));
    }

    public static String getStringWithLength(String str, int length) {
        return getStringWithLength(str, length, "0");
    }

    public static String getStringWithLength(String str, int length, String appendStr) {
        if (null == str) {
            str = "";
        }
        StringBuilder sb = new StringBuilder(str);
        if (str.length() > length) {
            return str.substring(str.length() - length);
        } else if (str.length() == length) {
            return str;
        }

        for (int i = str.length(); i < length; i++) {
            sb.append(appendStr);
        }
        sb.append(str);
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(getStringWithLength("123", 8));
    }
}
