package com.ebupt.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: yushibo
 * @Date: 2019/5/30 13:11
 * @Description:
 */
public class LockDemo {

    private Lock lock = new ReentrantLock();
    private int count;

    public void increament(){
        lock.lock();
        try {
            count++;
        }finally {
            lock.unlock();
        }
    }

    public synchronized void increamentSyn(){
            count++;
        increamentSyn();

    }
}
