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
    //处理成功进度
    private AtomicInteger successCount;
    //处理的总进度
    private AtomicInteger taskProcesserCount;
    //每个任务本身成功失败
    //拿结果从头拿，放结果从尾部放
    private LinkedBlockingDeque<TaskResult<R>> taskDetailDeque;
    //过期时间
    private final long expireTime;

    public JobInfo(String jobName, int jobLength, ITaskProcesser<?, ?> taskProcesser, LinkedBlockingDeque<TaskResult<R>> taskDetailDeque, long expireTime) {
        this.jobName = jobName;
        this.jobLength = jobLength;
        this.taskProcesser = taskProcesser;
        this.successCount = new AtomicInteger(0);
        this.taskProcesserCount = new AtomicInteger(0);
        this.taskDetailDeque = taskDetailDeque;
        this.expireTime = expireTime;
    }

    public int getSuccessCount() {
        return successCount.get();
    }

    public int getTaskProcesserCount() {
        return taskProcesserCount.get();
    }

    public List<TaskResult<R>> getTaskDetail(){
        List<TaskResult<R>> taskList = new LinkedList<>();
        TaskResult<R> taskResult;
        while ((taskResult = taskDetailDeque.pollFirst()) != null) {
            taskList.add(taskResult);
        }
        return taskList;
    }
    //从业务应用角度来讲，保证最终一致性即可，不需要对方法加锁
    public void addTaskResult(TaskResult<R> result){
        if(TaskResultType.SUCCESS.equals(result.getResultType())){
            successCount.incrementAndGet();
        }
        //TODO 为什么会线程不安全
        taskDetailDeque.addLast(result);
        taskProcesserCount.incrementAndGet();
    }
}
