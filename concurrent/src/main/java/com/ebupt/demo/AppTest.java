package com.ebupt.demo;

import com.ebupt.tools.SleepTools;

import java.util.List;
import java.util.Random;

/**
 * @Author: yushibo
 * @Date: 2019/6/14 16:49
 * @Description:
 */
public class AppTest {

    private final static String JOB_NAME = "计算数值";
    private final static int JOB_LENGTH = 100;


    //查询任务进度的线程
    private static class QueryResult implements Runnable{

        private PendingJobPool pool;

        public QueryResult(PendingJobPool pool) {
            this.pool = pool;
        }

        @Override
        public void run() {
            int i = 0;//查询次数
            while (i<10){
                List<TaskResult<String>> taskDetail = pool.getTaskDetail(JOB_NAME);
                if(!taskDetail.isEmpty()){
                    System.out.println(pool.getTaskProgess(JOB_NAME));
                    System.out.println(taskDetail);
                }
                SleepTools.ms(100);
                i++;
            }

        }
    }

    public static void main(String[] args) {
        MyTask myTask = new MyTask();
        //拿到框架的实例
        PendingJobPool pool = PendingJobPool.getInstance();
        //注册job
       pool.registerJob(JOB_NAME,JOB_LENGTH,myTask,1000*5);
        Random random = new Random();
        for (int i = 0; i < JOB_LENGTH; i++) {
            //依次推入task
            pool.putTask(JOB_NAME,random.nextInt(1000));
        }
        Thread thread = new Thread(new QueryResult(pool));
        thread.start();


        }

}
