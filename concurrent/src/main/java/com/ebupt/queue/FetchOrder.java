package com.ebupt.queue;

import java.util.concurrent.DelayQueue;

/**
 * @Author: yushibo
 * @Date: 2019/6/5 16:06
 * @Description: 取出到期订单的功能
 */
public class FetchOrder implements Runnable{

    private DelayQueue<ItemVo<Order>> queue;

    public FetchOrder(DelayQueue<ItemVo<Order>> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true){
            try {
                ItemVo<Order> item =  queue.take();
                Order order = item.getData();
                System.out.println("get from queue:"+order.getOrderNo());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
