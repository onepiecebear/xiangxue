package com.ebupt.demo;

import com.sun.corba.se.spi.orbutil.threadpool.WorkQueue;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @Author: yushibo
 * @Date: 2019/6/13 18:09
 * @Description:
 */
public class PendingJobPool {

    //保守估计
    private static final int THREAD_COUNTS = Runtime.getRuntime().availableProcessors();

    //任务队列
    private static BlockingQueue<Runnable> taskQueue = new ArrayBlockingQueue<>(5000);

    //线程池，固定大小，有界队列
    private static ExecutorService taskExecutor = new ThreadPoolExecutor(THREAD_COUNTS,THREAD_COUNTS,60,
                                                   TimeUnit.SECONDS, taskQueue);

    private static CheckJobProcesser checkJobProcesser = CheckJobProcesser.getInstance();

    //工作的存放容器
    private static ConcurrentHashMap<String,JobInfo<?>> jobInfoMap = new ConcurrentHashMap<>();

    public static Map<String,JobInfo<?>> getMap(){
        return jobInfoMap;
    }

    //单例模式
    private PendingJobPool(){}

    private static class JobPoolHolder{
        public static PendingJobPool pool = new PendingJobPool();
    }

    public static PendingJobPool getInstance(){
        return JobPoolHolder.pool;
    }
    //单例模式---

    //对工作中的任务进行包装，提交给线程池使用，并处理任务的结果，写入缓存已供查询
    private static class PendingTask<T,R> implements Runnable{

        private JobInfo<R> jobInfo;
        private T processData;

        public PendingTask(JobInfo<R> jobInfo, T processData) {
            this.jobInfo = jobInfo;
            this.processData = processData;
        }

        @Override
        public void run() {
            R r = null;
            ITaskProcesser<T, R> taskProcesser = (ITaskProcesser<T, R>)jobInfo.getTaskProcesser();
            //调用业务人员实现的具体方法
            TaskResult<R> taskResult = null;

            try {
                //调用业务人员实现的具体方法
                taskResult = taskProcesser.taskExecute(processData);
                //要做检查，防止开发人员处理不当
                if(taskResult == null){
                    taskResult = new TaskResult<R>(TaskResultType.EXCEPTION ,r, "result is null");
                }
                if (taskResult.getResultType()==null) {
                    if (taskResult.getReason()==null) {
                        taskResult = new TaskResult<R>(TaskResultType.EXCEPTION ,r, "Reason is null");

                    }else{
                        taskResult = new TaskResult<R>(TaskResultType.EXCEPTION ,r, "ResultType is null");
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                taskResult = new TaskResult<R>(TaskResultType.EXCEPTION ,r, e.getMessage());
            }finally {
                jobInfo.addTaskResult(taskResult,checkJobProcesser);
            }


        }
    }

    //根据工作名检索工作
    private <R> JobInfo<R> getJob(String jobName){
        JobInfo<R> jobInfo = (JobInfo<R>) jobInfoMap.get(jobName);
        if (jobInfo == null){
            throw new RuntimeException(jobName+"是个非法任务");
        }
        return jobInfo;
    }

    //调用者提交工作中的任务
    public <T,R> void  putTask(String jobName,T t){
        JobInfo<R> jobInfo = getJob(jobName);
        PendingTask<T,R> task = new PendingTask<>(jobInfo,t);
        taskExecutor.execute(task);


    }

    //调用者注册工作，如工作名，任务的处理器等等
    public <R> void  registerJob(String jobName,int jobLength,ITaskProcesser<?,?> taskProcesser,long expireTime){
        JobInfo<R> jobInfo = new JobInfo<>(jobName,jobLength,taskProcesser,expireTime);
        if (jobInfoMap.putIfAbsent(jobName,jobInfo)!=null) {
            throw new RuntimeException(jobName + "已经注册了");
        }

    }


    //获得每个任务的处理详情
    public <R> List<TaskResult<R>> getTaskDetail(String jobName){
        JobInfo<R> jobInfo = getJob(jobName);
        return jobInfo.getTaskDetail();
    }

    //获得工作的整体处理进度
    public <R> String getTaskProgess(String jobName){
        JobInfo<R> jobInfo = getJob(jobName);
        return jobInfo.getTotalProcess();
    }


}
