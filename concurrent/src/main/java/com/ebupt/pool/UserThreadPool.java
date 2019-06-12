package com.ebupt.pool;

import com.ebupt.tools.SleepTools;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @Author: yushibo
 * @Date: 2019/6/10 11:20
 * @Description: 线程池使用
 */
public class UserThreadPool {
    //工作线程池
    static class Worker implements Runnable{

        private String taskName;
        private Random random =new Random();

        public Worker(String taskName) {
            this.taskName = taskName;
        }

        public String getTaskName() {
            return taskName;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+" process the rask:"+ taskName);
            SleepTools.ms(random.nextInt(100)*5);
        }
    }
    static class CallWorker implements Callable<String>{

        private String taskName;
        private Random random =new Random();

        public CallWorker(String taskName) {
            this.taskName = taskName;
        }

        public String getTaskName() {
            return taskName;
        }

        @Override
        public String call() throws Exception {
            System.out.println(Thread.currentThread().getName()+" process the rask:"+ taskName);
            return Thread.currentThread().getName()+":"+ taskName;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = new ThreadPoolExecutor(2,4,3, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(10),new ThreadPoolExecutor.DiscardOldestPolicy());
//        Executors.newCachedThreadPool(); 其实用的很少

        for (int i = 0; i < 6; i++) {
            Worker worker =new Worker("work_"+i);
            pool.execute(worker);
        }
        for (int i = 0; i < 6; i++) {
            CallWorker callWorker =new CallWorker("callWorker_"+i);
            Future<String> result = pool.submit(callWorker);
            System.out.println(result.get());
        }
        pool.shutdown();
    }
}
