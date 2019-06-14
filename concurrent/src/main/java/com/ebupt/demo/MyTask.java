package com.ebupt.demo;

import com.ebupt.tools.SleepTools;

import java.util.Random;

/**
 * @Author: yushibo
 * @Date: 2019/6/14 16:42
 * @Description:
 */
public class MyTask implements ITaskProcesser<Integer,Integer>{
    @Override
    public TaskResult<Integer> taskExecute(Integer data) {
        Random random = new Random();
        int flag = random.nextInt(500);
        SleepTools.ms(flag);
        if(flag<=300){//正常情况
            Integer value = data.intValue() + flag;
            return new TaskResult<>(TaskResultType.SUCCESS,value);
        }else if(flag>301 && flag<=400){//处理失败情况
            return new TaskResult<>(TaskResultType.FAILURE,-1,"Failure");
        }else{//发生异常
            try {
                throw new RuntimeException("异常发生了");
            }catch (Exception e){
                return new TaskResult<>(TaskResultType.EXCEPTION,-1,e.getMessage());
            }

        }
    }
}
