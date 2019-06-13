package com.ebupt.demo;

/**
 * @Author: yushibo
 * @Date: 2019/6/13 18:09
 * @Description:
 */
public class PendingJobPool {
    private static class PendingTask<T,R> implements Runnable{

        private JobInfo<R> jobInfo;
        private T processData;

        public PendingTask(JobInfo<R> jobInfo, T processData) {
            this.jobInfo = jobInfo;
            this.processData = processData;
        }

        @Override
        public void run() {

        }
    }

    //调用者提交工作中的任务
    public <T,R> void  putTask(String jobName,T t){

    }

    //调用者注册工作，如工作名，任务的处理器等等
    public <R> void  registerJob(String jobName,int jobLength,ITaskProcesser<?,?> taskProcesser,long expireTime){

    }




}
