package _02_classloader;

public class BB {
    private String aa;
    private static final String a = "a1";
    private static  String b = "b1";

    static {
        System.out.println("BB.static");
//        a = "a2";
        b = "b2";
    }

    public BB() {

    }

    public BB(String a) {
        this.aa = a;
    }

    public static void main(String[] args) {
        System.out.println("aaaaaaaaaa");
    }
}