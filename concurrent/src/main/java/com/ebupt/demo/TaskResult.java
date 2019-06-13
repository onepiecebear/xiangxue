package com.ebupt.demo;

/**
 * @Author: yushibo
 * @Date: 2019/6/13 17:07
 * @Description: 任务处理返回结果实体类
 */
public class TaskResult<R> {

    private final TaskResultType resultType;
    private final R returnValue;//方法的业务结果数据
    private final String reason;//方法失败的全因

    public TaskResult(TaskResultType resultType, R returnValue, String reason) {
        this.resultType = resultType;
        this.returnValue = returnValue;
        this.reason = reason;
    }

    public TaskResult(TaskResultType resultType, R returnValue) {
        this.resultType = resultType;
        this.returnValue = returnValue;
        this.reason = "success";
    }

    public TaskResultType getResultType() {
        return resultType;
    }

    public R getReturnValue() {
        return returnValue;
    }

    public String getReason() {
        return reason;
    }
}
