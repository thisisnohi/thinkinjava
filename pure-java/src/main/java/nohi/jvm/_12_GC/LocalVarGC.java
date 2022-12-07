package nohi.jvm._12_GC;

import org.junit.Test;

/**
 * <h3>thinkinjava</h3>
 *
 * @author NOHI
 * @description <p></p>
 * @date 2022/11/26 16:04
 **/
public class LocalVarGC {

    public void localVarGc1() {
        byte[] bs = new byte[10 * 1024 * 1024];
        System.gc();
    }

    public void localVarGc2() {
        byte[] bs = new byte[10 * 1024 * 1024];
        bs = null;
        System.gc();
    }

    public void localVarGc3() {
        {
            byte[] bs = new byte[10 * 1024 * 1024];
        }
        System.gc();
    }

    public void localVarGc4() {
        {
            byte[] bs = new byte[10 * 1024 * 1024];
        }
        int i = 0;
        System.gc();
    }

    public void localVarGc5() {
        localVarGc1();
        System.gc();
    }

    /**
     * -XX:+PrintGCDetails
     * @param args
     */
    public static void main(String[] args) {
        LocalVarGC l = new LocalVarGC();
        // localVarGc1 不回收
        // [GC (System.gc()) [PSYoungGen: 14172K->10720K(76288K)] 14172K->10812K(251392K), 0.0065290 secs] [Times: user=0.07 sys=0.01, real=0.01 secs]
        // [Full GC (System.gc()) [PSYoungGen: 10720K->0K(76288K)] [ParOldGen: 92K->10656K(175104K)] 10812K->10656K(251392K), [Metaspace: 3111K->3111K(1056768K)], 0.0063655 secs] [Times: user=0.02 sys=0.02, real=0.00 secs]
        // [GC (System.gc()): young gc时未回收
        // Full GC： 到老年代
        // l.localVarGc1();

        // 回收
        // [GC (System.gc()) [PSYoungGen: 14172K->656K(76288K)] 14172K->664K(251392K), 0.0011722 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
        // [Full GC (System.gc()) [PSYoungGen: 656K->0K(76288K)] [ParOldGen: 8K->416K(175104K)] 664K->416K(251392K), [Metaspace: 3111K->3111K(1056768K)], 0.0040826 secs] [Times: user=0.03 sys=0.00, real=0.00 secs]
        // l.localVarGc2();

        // 不回收
        // [GC (System.gc()) [PSYoungGen: 14172K->10720K(76288K)] 14172K->10828K(251392K), 0.0060878 secs] [Times: user=0.06 sys=0.00, real=0.01 secs]
        // [Full GC (System.gc()) [PSYoungGen: 10720K->0K(76288K)] [ParOldGen: 108K->10655K(175104K)] 10828K->10655K(251392K), [Metaspace: 3105K->3105K(1056768K)], 0.0065082 secs] [Times: user=0.02 sys=0.03, real=0.01 secs]
        // l.localVarGc3();

        // 回收
        // [GC (System.gc()) [PSYoungGen: 14172K->704K(76288K)] 14172K->712K(251392K), 0.0012136 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
        // [Full GC (System.gc()) [PSYoungGen: 704K->0K(76288K)] [ParOldGen: 8K->416K(175104K)] 712K->416K(251392K), [Metaspace: 3111K->3111K(1056768K)], 0.0041112 secs] [Times: user=0.02 sys=0.01, real=0.01 secs]
        // l.localVarGc4();

        // 回收
        // [GC (System.gc()) [PSYoungGen: 14172K->10720K(76288K)] 14172K->10808K(251392K), 0.0058146 secs] [Times: user=0.06 sys=0.00, real=0.01 secs]
        // [Full GC (System.gc()) [PSYoungGen: 10720K->0K(76288K)] [ParOldGen: 88K->10656K(175104K)] 10808K->10656K(251392K), [Metaspace: 3112K->3112K(1056768K)], 0.0057435 secs] [Times: user=0.02 sys=0.03, real=0.00 secs]
        // [GC (System.gc()) [PSYoungGen: 0K->0K(76288K)] 10656K->10656K(251392K), 0.0004863 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
        // [Full GC (System.gc()) [PSYoungGen: 0K->0K(76288K)] [ParOldGen: 10656K->416K(175104K)] 10656K->416K(251392K), [Metaspace: 3112K->3112K(1056768K)], 0.0038133 secs] [Times: user=0.03 sys=0.00, real=0.00 secs]
        // 前两个：GC、Full GC为方法一中
        // 后两个：GC、Full Gc 为localVarGc5方法中,由于方法一已经执行完，可以被回收
        l.localVarGc5();
    }

}
