package com.ebupt.safeend;

/**
 * @Author: yushibo
 * @Date: 2019/5/27 13:04
 * @Description: 如何安全的中断线程
 */
public class EndException {

    private static class UseThread extends Thread{

        public UseThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            while (!isInterrupted()) {
                try {
                    Thread.sleep(100);

                } catch (InterruptedException e) {
                    System.out.println(name+" 的标志："+isInterrupted());
                   interrupt();
                    e.printStackTrace();
                }
                System.out.println(name+" is run");
            }
            System.out.println(name+" 的标志："+isInterrupted());
        }

    }

    public static void main(String[] args) {
        Thread endThread = new UseThread("endThread");
        endThread.start();
        try {
            Thread.sleep(550);
            //interrupt方法，将线程从睡眠中唤醒所以报错
            endThread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
