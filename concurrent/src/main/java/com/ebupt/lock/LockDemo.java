package com.ebupt.lock;

import com.ebupt.aqs.SelfLock;
import com.ebupt.tools.SleepTools;
import sun.applet.Main;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: yushibo
 * @Date: 2019/5/30 13:11
 * @Description:
 */
public class LockDemo {

    private static Lock lock = new ReentrantLock();
//    private static Lock lock = new SelfLock();
    private static int count;

    public static void increament(){
//        lock.lock();
        try {

            count++;
            SleepTools.second(1);
            System.out.println(Thread.currentThread().getName()+"--当前计数"+count);

        }finally {
//            lock.unlock();
        }
    }

    public synchronized void increamentSyn(){
            count++;
        increamentSyn();

    }

    public static void main(String[] args) {


        for (int i = 0; i < 10; i++) {
           new Thread(new Runnable() {
                @Override
                public void run() {
                    increament();
                }
            }).start();
        }
    }
}
