package com.ebupt.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: yushibo
 * @Date: 2019/5/30 0030 0:00
 * @Description: 基本类型的原子操作类
 */

public class UseAtomicInt {

    static AtomicInteger ai = new AtomicInteger(10);

    public static void main(String[] args) {
        System.out.println(ai.getAndIncrement());
        System.out.println(ai.get());
        System.out.println(ai.incrementAndGet());
        System.out.println(ai.get());

    }
}
