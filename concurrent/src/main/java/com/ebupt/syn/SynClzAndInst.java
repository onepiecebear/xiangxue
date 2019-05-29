package com.ebupt.syn;

import com.ebupt.tools.SleepTools;

/**
 * @Author: yushibo
 * @Date: 2019/5/27 15:27
 * @Description: 对象锁和类锁
 */
public class SynClzAndInst {

    //使用类锁的线程
    private static class SynClass extends Thread {
        @Override
        public void run() {
            System.out.println("TestClass is running ...");
            synClass();
        }
    }

    //使用对象锁的线程
    private static class InstanceSyn implements Runnable {
        private SynClzAndInst synClzAndInst;

        private InstanceSyn(SynClzAndInst synClzAndInst) {
            this.synClzAndInst = synClzAndInst;
        }

        public void run() {
            System.out.println("TestInstance is running ..." + synClzAndInst);
            synClzAndInst.instance();
        }
    }

    //使用对象锁的线程
    private static class Instance2Syn implements Runnable {
        private SynClzAndInst synClzAndInst;

        private Instance2Syn(SynClzAndInst synClzAndInst) {
            this.synClzAndInst = synClzAndInst;
        }

        public void run() {
            System.out.println("TestInstance2 is running ..." + synClzAndInst);
            synClzAndInst.instance2();
        }
    }

    //对象锁
    private synchronized void instance() {
        SleepTools.second(3);
        System.out.println("synInstance is going..." + this.toString());
        SleepTools.second(3);
        System.out.println("synInstance ended " + this.toString());
    }

    //对象锁
    private synchronized void instance2() {
        SleepTools.second(3);
        System.out.println("synInstance2 is going..." + this.toString());
        SleepTools.second(3);
        System.out.println("synInstance2 ended " + this.toString());
    }

    //类锁 static
    //锁的是class对象
    private static synchronized void synClass() {
        SleepTools.second(1);
        System.out.println("synClass is going...");
        SleepTools.second(1);
        System.out.println("synClass ended ");
    }


    public static void main(String[] args) {
       //2个对象锁，看锁住的对象是否相同
//        SynClzAndInst synClzAndInst = new SynClzAndInst();
//        Thread t1 = new Thread(new InstanceSyn(synClzAndInst));
//        SynClzAndInst synClzAndInst2 = new SynClzAndInst();
//        Thread t2 = new Thread(new Instance2Syn(synClzAndInst2));
//        t1.start();
//        t2.start();
//        SleepTools.second(1);

        //对象锁和类锁
        SynClzAndInst synClzAndInst = new SynClzAndInst();
        Thread t1 = new Thread(new InstanceSyn(synClzAndInst));
        t1.start();

        SynClass synClass = new SynClass();
        synClass.start();
        SleepTools.second(1);

    }
}
