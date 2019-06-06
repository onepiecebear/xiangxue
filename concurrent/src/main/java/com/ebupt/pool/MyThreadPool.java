package com.ebupt.pool;

import java.util.concurrent.*;

/**
 * @Author: yushibo
 * @Date: 2019/6/6 12:03
 * @Description:
 */
public class MyThreadPool {

    //线程池中默认线程的个数为5
    private static int WORK_NUM = 5;

    //队列默认任务个数为100
    private static int TASK_COUNT = 100;

    //工作线程
    private WorkeThread[] workeThreads;

    private final BlockingQueue<Runnable> tackeQueue;

    private final int worker_num;//用户在构造这个池，希望启动的线程数

    public MyThreadPool() {
        this(WORK_NUM, TASK_COUNT);
    }

    public MyThreadPool(int worker_num, int tackCount) {
        if (worker_num < 0) worker_num = WORK_NUM;
        if (tackCount < 0) tackCount = TASK_COUNT;

        this.worker_num = worker_num;
        tackeQueue = new ArrayBlockingQueue<>(tackCount);
        workeThreads = new WorkeThread[worker_num];
        for (int i = 0; i < worker_num; i++) {
            workeThreads[i] = new WorkeThread();
            workeThreads[i].start();
        }
    }

    //执行任务，其实只是把任务加入任务队列，什么时候执行有线程池管理器决定
    public void execute(Runnable task) {

        try {
            tackeQueue.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        System.out.println("close pool....");
        for (int i = 0; i < worker_num; i++) {
            workeThreads[i].stopWorker();
            workeThreads[i] = null;
        }
        tackeQueue.clear();//清空任务队列
    }


    private class WorkeThread extends Thread {
        @Override
        public void run() {
            Runnable r = null;
            try {
                while (!isInterrupted()) {
                    r = tackeQueue.take();
                    if(r!=null){
                        System.out.println(getName()+"exec:"+r);
                        r.run();
                    }
                    r = null;//help gc
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public void stopWorker(){
            interrupt();
        }
    }
}
