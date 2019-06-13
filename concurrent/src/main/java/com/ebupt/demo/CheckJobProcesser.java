package com.ebupt.demo;

import com.ebupt.queue.ItemVo;

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


    //处理队列中到期任务的线程
    private static class FetchJob implements Runnable{
        @Override
        public void run() {

        }
    }
    //任务完成后，放入队列，经expireTime时间后，从整个框架中移除
    public void putJob(String jobNmae,long expireTime){

    }
}
