package com.ebupt.demo;

import com.ebupt.queue.ItemVo;
import com.ebupt.queue.Order;

import java.util.concurrent.DelayQueue;

/**
 * @Author: yushibo
 * @Date: 2019/6/13 18:09
 * @Description: 任务完成后，
 *                  在一定的时间供查询，
 *                  之后为释放资源节约内存，
 *                  需要定期处理过期的任务
 */
public class CheckJobProcesser {

    //存放任务的队列
    private static DelayQueue<ItemVo<String>> queue = new DelayQueue<>();

    //单例模式
    private CheckJobProcesser(){}

    private static class ProcesserHolder{
        public static CheckJobProcesser pool = new CheckJobProcesser();
    }

    public static CheckJobProcesser getInstance(){
        return ProcesserHolder.pool;
    }
    //单例模式---


    //处理队列中到期任务的线程
    private static class FetchJob implements Runnable{
        @Override
        public void run() {
            while (true){
                try {
                    //拿到已经过期的任务
                    ItemVo<String> item =  queue.take();
                    String jobName = item.getData();
                    PendingJobPool.getMap().remove(jobName);
                    System.out.println("将过期任务 {"+jobName +"} 从列队中移出");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //任务完成后，放入队列，经expireTime时间后，从整个框架中移除
    public void putJob(String jobNmae,long expireTime){
        ItemVo<String> item = new ItemVo<>(expireTime,jobNmae);
        queue.offer(item);
        System.out.println("job【"+jobNmae+"】已经放入了过期检查缓存，过期时长："+expireTime);
    }

    static {
        Thread thread = new Thread(new FetchJob());
        thread.setDaemon(true);
        thread.start();
        System.out.println("开启任务过期检查守护线程...");
    }
}
