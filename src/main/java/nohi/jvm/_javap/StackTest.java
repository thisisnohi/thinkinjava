package nohi.jvm._javap;

/**
 * @author NOHI
 * 2022-08-07 10:38
 **/
public class StackTest {

    public static void main(String[] args) {
        int i = 0;
        int j = 3;
        int k = j - i;
    }

    /**
     * javap -v StackTest 查看对应class的机器码
     */
}
