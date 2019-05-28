package com.ebupt.main;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Author: yushibo
 * @Date: 2019/5/27 11:28
 * @Description:
 */
public class NewThread {

    //实现Runnable接口
    private static class UseRun implements Runnable{

        public void run() {
            System.out.println("Runnable");
        }
    }

    //实现Callable接口,允许有返回值
    private static class UseCall implements Callable<String>{
        public String call() throws Exception {
            System.out.println("Callable");
            return "CallResult";
        }
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        UseRun useRun = new UseRun();
        new Thread(useRun).start();

        UseCall useCall =  new UseCall();
        FutureTask<String> futureTask = new FutureTask<String>(useCall);
        new Thread(futureTask).start();
        //阻塞等待线程都执行完返回
        System.out.println(futureTask.get());

        Thread thread = new Thread(useRun);
        //中断线程安全
        thread.interrupt();
        thread.isInterrupted();
        thread.interrupt();
    }

}
