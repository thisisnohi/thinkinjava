package nohi.jdk9.sys;

import nohi.jdk9.module1.user.UserService;
import nohi.jdk9.module1.user.addres.Address;
import org.junit.jupiter.api.Test;

/**
 * <h3>jdk9</h3>
 *
 * @author NOHI
 * @description <p></p>
 * @date 2023/01/18 12:55
 **/
public class TestJdk9Module {

    @Test
    public void testModule(){
        //
        UserService userService = new UserService();
        userService.userInfo();

        Address address = new Address();

        System.out.println(userService.getClass().getModule().getName());
        System.out.println(address.getClass().getModule().getName());

    }

    public static void main(String[] args) {
        UserService userService = new UserService();
        userService.userInfo();

        Address address = new Address();

        System.out.println(userService.getClass().getModule().getName());
        System.out.println(address.getClass().getModule().getName());
    }
}
