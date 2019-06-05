package com.ebupt.queue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;

/**
 * @Author: yushibo
 * @Date: 2019/6/5 15:59
 * @Description:  将订单放入队列
 */
public class PutOrder implements Runnable{

    private DelayQueue<ItemVo<Order>> queue;

    public PutOrder(DelayQueue<ItemVo<Order>> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        //5秒到期
        Order orderTb = new Order("TB12345","366");
        ItemVo<Order> itemTb = new ItemVo<>(5000,orderTb);
        queue.offer(itemTb);
        System.out.println("订单5S后到期："+orderTb.getOrderNo());
        //8秒到期
        Order orderZd = new Order("ZD12345","366");
        ItemVo<Order> itemZd = new ItemVo<>(8000,orderZd);
        queue.offer(itemZd);
        System.out.println("订单5S后到期："+orderZd.getOrderNo());
    }
}
