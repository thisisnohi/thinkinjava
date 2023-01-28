package nohi.jdk._17;

/**
 * <h3>jdk17</h3>
 *
 * @author NOHI
 * @description <p>Animal</p>
 * @date 2023/01/28 12:58
 **/
public interface Animal {

    default void eat(){
        System.out.println("eat something");
    }

    default void moving(){
        System.out.println("moving");
    }
}
