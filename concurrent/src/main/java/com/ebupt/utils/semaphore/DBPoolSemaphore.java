package com.ebupt.utils.semaphore;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * @Author: yushibo
 * @Date: 2019/5/29 16:14
 * @Description: semaphore实现一个数据连接池
 */
public class DBPoolSemaphore {

    private final static int POOL_SIZE = 10;
    //useful表示可用的数据库连接，useless表示已用的数据里连接
    private final Semaphore useful,useless;

    public DBPoolSemaphore(){
        this.useful = new Semaphore(POOL_SIZE);
        this.useless = new Semaphore(0);
    }

    //存放数据库连接的容器
    private static LinkedList<Connection> pool = new LinkedList<>();
    //初始化池
    static {
        for (int i = 0; i < POOL_SIZE; i++) {
            pool.addLast(SqlConnectImpl.fetchConnection());
        }
    }

    //归还连接
    public  void returnConnect(Connection connection) throws InterruptedException {
        if(connection != null){
            //等待获取许可的线程数
            System.out.println("当前有"+useful.getQueueLength()+"个线程等待数据库连接");
            //有多少个许可数量
            System.out.println("useful可用连接数："+useful.availablePermits());
            System.out.println("useless已用连接数："+useless.availablePermits());
            useless.acquire();
            synchronized (pool){
                pool.addLast(connection);
            }
            useful.release();
        }
    }

    //从池子拿连接
    public Connection tackConnect() throws InterruptedException {
        //拿到许可，当没有许可时，线程阻塞
        useful.acquire();
        Connection connection=null;
        synchronized (pool){
            connection = pool.removeFirst();
        }
        //归还许可
        useless.release();
        return connection ;
    }

}
