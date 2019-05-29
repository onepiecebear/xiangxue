package com.ebupt.safeend;

/**
 * @Author: yushibo
 * @Date: 2019/5/27 13:04
 * @Description: 守护线程
 */
public class EndThread {

    private static class UseThread extends Thread{

        public UseThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            String name = Thread.currentThread().getName();

            while (!isInterrupted()) {
                System.out.println(name+" is run");
            }
            System.out.println(name+" 的标志："+isInterrupted());
        }

    }

    public static void main(String[] args) {
        Thread endThread = new UseThread("endThread");
        //设置线程的优先级，不同操作系统优先级可能不同
        endThread.setPriority(6);
        endThread.setDaemon(true);
        endThread.start();
        try {
            Thread.sleep(20);
            endThread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
