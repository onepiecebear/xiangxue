package com.ebupt.demo;

/**
 * @Author: yushibo
 * @Date: 2019/6/13 17:02
 * @Description: 方法本身运行是否正确的结果类型
 */
public enum TaskResultType {
    //方法返回了业务人员需要的结果
    SUCCESS,
    //方法返回了业务人员不需要的结果
    FAILURE,
    //方法执行抛出异常
    EXCEPTION
}
