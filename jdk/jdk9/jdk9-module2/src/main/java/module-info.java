/**
 * <h3>jdk9</h3>
 *
 * @description <p>jdk9模块化</p>
 * @author NOHI
 * @date 2023/01/18 12:55
 **/
module jdk9.module2 {
    requires org.junit.jupiter.api;
    requires jdk9.module1;
    requires org.junit.platform.commons;
}