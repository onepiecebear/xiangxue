package com.ebupt.utils;

import com.ebupt.tools.SleepTools;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: yushibo
 * @Date: 2019/5/29 13:41
 * @Description: CountDownLatch用法，有5个初始化的线程，6个扣除点
 * 扣除完毕后，主线程和业务线程才能继续自己的工作
 */
public class UseCountDownLatch {


    static CountDownLatch latch = new CountDownLatch(6);

    //初始化线程
    private static class InitThread implements Runnable {
        @Override
        public void run() {
            System.out.println("Thread_" + Thread.currentThread().getId()
                    + " ready init work ...");

            //TODO
            latch.countDown();//初始化线程完成工作了,countDown方法只扣减一次

            for (int i = 0; i < 2; i++) {
                System.out.println("Thread_" + Thread.currentThread().getId()
                        + " count init work ...");
            }
        }
    }

    //业务线程
    private static class BusiThread implements Runnable {
        @Override
        public void run() {

            //TODO
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            for (int i = 0; i < 3; i++) {
                System.out.println("BusiThread" + Thread.currentThread().getId()
                        + " do Business  ...");
            }
        }
    }


    public static void main(String[] args) {
        //单独的初始化线程，初始化分为2步，需要扣减2次
        new Thread(new Runnable() {
            @Override
            public void run() {
//                SleepTools.ms(1);
                System.out.println("Thread_" + Thread.currentThread().getId()
                        + " ready init work step 1st");
                latch.countDown();// 每完成异步初始化工作，扣减一次
                System.out.println("begin step 2nd.....");

//                SleepTools.ms(1);
                System.out.println("Thread_" + Thread.currentThread().getId()
                        + " ready init work step 2nd");
                latch.countDown();// 每完成异步初始化工作，扣减一次

            }
        }).start();

        new Thread(new BusiThread()).start();
        for (int i = 0; i <=3; i++) {
            Thread thread = new Thread(new InitThread());
            thread.start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main work...");
    }


}
