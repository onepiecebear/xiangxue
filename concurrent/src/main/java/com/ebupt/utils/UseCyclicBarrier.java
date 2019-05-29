package com.ebupt.utils;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author: yushibo
 * @Date: 2019/5/29 13:41
 * @Description: CyclicBarrier 使用
 */
public class UseCyclicBarrier {

    private static CyclicBarrier barrier = new CyclicBarrier(5,new CollectThread());

    private static ConcurrentHashMap<String, Long> resultMap = new ConcurrentHashMap<>();//存放子线程工作结果的容器

    public static void main(String[] args) {
        for (int i = 0; i <= 4; i++) {
            Thread thread = new Thread(new SubThread());

            thread.start();
        }
    }
    //负责屏障开放以后的工作
    private static class CollectThread implements Runnable {
        @Override
        public void run() {
            StringBuffer result = new StringBuffer();
            for (Map.Entry<String, Long> workResult : resultMap.entrySet()) {
                result.append("[" + workResult.getValue() + "]");
            }
            System.out.println("the result = " + result);
            System.out.println("do other business.....");
        }
    }

    //工作线程
    private static class SubThread implements Runnable {
        @Override
        public void run() {
            long id = Thread.currentThread().getId();//线程本身的处理结果
            resultMap.put(Thread.currentThread().getId() + "", id);
            Random random = new Random();//随机决定工作线程是否睡眠
            try {
                if (random.nextBoolean()) {
                    Thread.sleep(1000 + id);
                    System.out.println("Thread_" + id + "....do something");
                }
                System.out.println(id+ "... is await");
                //TODO
                barrier.await();
                Thread.sleep(1000 + id);
                System.out.println("Thread_" + id + "....do business");

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

        }
    }

}
