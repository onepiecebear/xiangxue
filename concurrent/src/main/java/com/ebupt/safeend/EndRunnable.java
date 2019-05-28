package com.ebupt.safeend;

/**
 * @Author: yushibo
 * @Date: 2019/5/27 13:04
 * @Description: 如何安全的中断线程
 */
public class EndRunnable {

    private static class UseRunnable implements Runnable{

        public void run() {
            String name = Thread.currentThread().getName();

            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(name+" is run");
            }
            System.out.println(name+" 的标志："+Thread.currentThread().isInterrupted());
        }

    }

    public static void main(String[] args) {
        UseRunnable useRunnable = new UseRunnable();
        Thread endThread = new Thread(useRunnable);
        endThread.start();
        try {
            Thread.sleep(20);
            endThread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
