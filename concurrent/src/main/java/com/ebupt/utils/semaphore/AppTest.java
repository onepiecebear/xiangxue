package com.ebupt.utils.semaphore;

import com.ebupt.tools.SleepTools;

import java.sql.Connection;
import java.util.Random;

/**
 * @Author: yushibo
 * @Date: 2019/5/29 17:03
 * @Description:
 */
public class AppTest {

    private static DBPoolSemaphore dbPool = new DBPoolSemaphore();

    //业务线程
    private static class BusiThread extends Thread{
        @Override
        public void run() {
            Random r = new Random();//每个线程持有连接的时间不一样
            long start = System.currentTimeMillis();
            try {
                Connection connection = dbPool.tackConnect();
                System.out.println("Thread_"+Thread.currentThread().getId()
                +"_获取数据库连接共耗时【"+(System.currentTimeMillis()-start)+"】ms");
                SleepTools.ms(100+r.nextInt(100));//模拟业务操作，线程持有连接查询数据
                System.out.println("查询数据完成，归还连接");
                dbPool.returnConnect(connection);


            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            Thread thread = new BusiThread();
            thread.start();
        }
        }
}
