package com.ebupt.forkjoin;

import java.util.Random;

/**
 * @Author: yushibo
 * @Date: 2019/5/28 17:16
 * @Description: 产生整型数组
 */
public class MakeArray {

    public static final int ARRAY_LENGTH = 4000;

    public static int[]  makeArray(){

        //New一个随机数发生器
        Random random = new Random();
        int[] result = new int[ARRAY_LENGTH];
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            result[i] = random.nextInt(ARRAY_LENGTH * 3);
        }
        return result;

    }
}
