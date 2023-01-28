package nohi.jdk._9;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p>接口方法私有化</p>
 * @date 2023/01/17 13:10
 **/
public interface InterfaceMethod {

    /**
     * 接口类私有方法
     *
     * @return
     */
    private String privateMethod() {
        return "private Mehtod";
    }

    /**
     * 接口默认方法
     */
    default void defaultMethod() {
        String msg = privateMethod();
        System.out.println("msg:" + msg);
    }
}
