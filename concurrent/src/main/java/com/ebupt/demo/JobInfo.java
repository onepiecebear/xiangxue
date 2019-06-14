package com.ebupt.demo;

import com.sun.xml.internal.bind.v2.TODO;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: yushibo
 * @Date: 2019/6/13 17:13
 * @Description: 提交给框架执行的工作实体类
 */
public class JobInfo<R> {
    //区分唯一的工作
    private final String jobName;
    //工作的任务个数
    private final int jobLength;
    //这个工作任务的处理器
    private final ITaskProcesser<?,?> taskProcesser;
    //成功处理的任务数
    private AtomicInteger successCount;
    //已经处理的任务数
    private AtomicInteger taskProcesserCount;

    //结果队列，拿结果从头拿，放结果从尾部放
    private LinkedBlockingDeque<TaskResult<R>> taskDetailDeque;
    //过期时间
    private final long expireTime;

    public JobInfo(String jobName, int jobLength, ITaskProcesser<?, ?> taskProcesser, long expireTime) {
        this.jobName = jobName;
        this.jobLength = jobLength;
        this.taskProcesser = taskProcesser;
        this.successCount = new AtomicInteger(0);
        this.taskProcesserCount = new AtomicInteger(0);
        this.taskDetailDeque = new LinkedBlockingDeque<TaskResult<R>>(jobLength);
        this.expireTime = expireTime;
    }

    public ITaskProcesser<?, ?> getTaskProcesser() {
        return taskProcesser;
    }

    //返回成功处理结果数
    public int getSuccessCount() {
        return successCount.get();
    }
    //返回当前已处理的结果数
    public int getTaskProcesserCount() {
        return taskProcesserCount.get();
    }
    // 提供工作中失败的次数
    public int getFailCount() {
        return taskProcesserCount.get() - successCount.get();
    }


    public String getTotalProcess() {
        return "success{"+successCount.get()+"}/current{"+taskProcesserCount.get()+"}" +
                " Total{"+jobLength+"}";
    }

    //获得工作中每个任务的处理详情
    public List<TaskResult<R>> getTaskDetail(){
        List<TaskResult<R>> taskList = new LinkedList<>();
        TaskResult<R> taskResult;
        //从阻塞队列中拿任务的结果，反复取，一直取到null位置，
        // 说明目前队列中最新的任务结果已经取完，可以不取了
        while ((taskResult = taskDetailDeque.pollFirst()) != null) {
            taskList.add(taskResult);
        }
        return taskList;
    }
    //从业务应用角度来讲，保证最终一致性即可，不需要对方法加锁
    public void addTaskResult(TaskResult<R> result,CheckJobProcesser checkJobProcesser){
        if(TaskResultType.SUCCESS.equals(result.getResultType())){
            successCount.incrementAndGet();
        }
        //TODO 为什么会线程不安全
        taskDetailDeque.addLast(result);
        taskProcesserCount.incrementAndGet();
        if(taskProcesserCount.get()==jobLength){
            checkJobProcesser.putJob(jobName,expireTime);
        }
    }
}
