package nohi.jdk._17;

import org.junit.jupiter.api.Test;

/**
 * <h3>jdk17</h3>
 *
 * @author NOHI
 * @description <p>jdk17</p>
 * @date 2023/01/28 13:01
 **/
public class Jdk17Test {

    @Test
    public void testSwitch(){
        animalMoving(new Rabbit());
        animalMoving(new Bird());
        animalMoving(null);
        animalMoving(new Dog());
    }

    public void animalMoving(Animal animal) {
        switch (animal){
            case Rabbit r -> r.run();
            case Bird b -> b.fly();
            case null -> System.out.println("null....");
            default -> animal.moving();
        }
    }
}
