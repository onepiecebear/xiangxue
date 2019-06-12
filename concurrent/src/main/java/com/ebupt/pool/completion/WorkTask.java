package com.ebupt.pool.completion;

import java.util.Random;
import java.util.concurrent.Callable;

/**
 * @Author: yushibo
 * @Date: 2019/6/11 10:36
 * @Description:
 */
public class WorkTask implements Callable<Integer>{

    private String name;

    public WorkTask(String name) {
        this.name = name;
    }

    @Override
    public Integer call() throws Exception {
        int sleepTime = new Random().nextInt(1000);
        Thread.sleep(sleepTime);
        return sleepTime;
    }
}
