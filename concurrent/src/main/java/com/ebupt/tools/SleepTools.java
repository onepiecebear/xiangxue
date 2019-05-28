package com.ebupt.tools;

import java.util.concurrent.TimeUnit;

/**
 * @Author: yushibo
 * @Date: 2019/5/27 15:46
 * @Description:
 */
public class SleepTools {

    /**
     * 休眠秒数
     * @param seconds
     */
    public static final void second(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 休眠秒数
     * @param seconds
     */
    public static final void ms(int seconds){
        try {
            TimeUnit.MILLISECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
