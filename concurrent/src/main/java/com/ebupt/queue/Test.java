package com.ebupt.queue;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.concurrent.DelayQueue;

/**
 * @Author: yushibo
 * @Date: 2019/6/5 16:11
 * @Description:
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        DelayQueue<ItemVo<Order>> queue = new  DelayQueue<ItemVo<Order>> ();
        new  Thread(new PutOrder(queue)).start();
        new  Thread(new FetchOrder(queue)).start();

        for (int i = 0; i < 15; i++) {
            Thread.sleep(500);
            System.out.println(i*500);
        }
        }
}
