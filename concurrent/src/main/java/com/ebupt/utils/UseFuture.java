package com.ebupt.utils;

import com.ebupt.tools.SleepTools;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Author: yushibo
 * @Date: 2019/5/29 0029 21:52
 * @Description:
 */

public class UseFuture {

    private static class UseCallable implements Callable<Integer>{

        private int sum;

        @Override
        public Integer call() throws Exception {
            System.out.println("Callable子线程开始计算");
            SleepTools.second(2);
            for (int i = 0; i < 5000; i++) {
                sum = sum + i;
            }
            System.out.println("Callable子线程开始计算结果："+sum);
            return sum;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        UseCallable useCallable =  new UseCallable();
        FutureTask<Integer> futureTask = new FutureTask<>(useCallable);
        new Thread(futureTask).start();

        System.out.println("返回结果"+futureTask.get());
        Random random = new Random();



    }
}
