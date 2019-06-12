package com.ebupt.pool.completion;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: yushibo
 * @Date: 2019/6/11 10:26
 * @Description:
 */
public class CompletionCase {
    private final int POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private final int TOTAL_TASK = Runtime.getRuntime().availableProcessors() ;

    //自己写集合来实现获取线程池中任务的返回结果
    public void testByQueue() throws Exception {
        long start = System.currentTimeMillis();
        AtomicInteger count = new AtomicInteger(0);
        //创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
        //容器存放提交给线程池的任务，list,map
        BlockingQueue<Future<Integer>> queue = new LinkedBlockingQueue<>();

        for (int i = 0; i < TOTAL_TASK; i++) {
            Future<Integer> future = pool.submit(new WorkTask("ExecTask "+i));
            queue.add(future);
        }

        for (int i = 0; i < TOTAL_TASK; i++) {
            Integer sleptTime = queue.take().get();
            System.out.println("slep "+ sleptTime);
            count.addAndGet(sleptTime);
        }

        //关闭线程池
        pool.shutdown();

    }



    //通过CompletionService来实现获取线程池中任务的返回结果
    public void testByCompletion() throws Exception {
        long start = System.currentTimeMillis();
        AtomicInteger count = new AtomicInteger(0);
        //创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);

        CompletionService<Integer> completionService = new ExecutorCompletionService<>(pool);

        for (int i = 0; i < TOTAL_TASK; i++) {
            completionService.submit(new WorkTask("ExecTask "+i));
        }

        for (int i = 0; i < TOTAL_TASK; i++) {
            Integer sleptTime = completionService.take().get();
            System.out.println("slep "+ sleptTime);
            count.addAndGet(sleptTime);
        }


        //关闭线程池
        pool.shutdown();

    }


public static void main(String[] args) throws Exception {
        CompletionCase completionCase = new CompletionCase();
//        completionCase.testByQueue();
        completionCase.testByCompletion();
    }

}
