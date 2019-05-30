package com.ebupt.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @Author: yushibo
 * @Date: 2019/5/30 11:32
 * @Description:
 */
public class UseAtomicArray {
    static int[] value = new int[]{1, 2};
    static AtomicIntegerArray ai = new AtomicIntegerArray(value);

    public static void main(String[] args) {
        //(当前数组下标，更改后的数值)
        ai.getAndSet(0,3);
        System.out.println(ai.get(0));
        System.out.println(value[0]);
    }
}
