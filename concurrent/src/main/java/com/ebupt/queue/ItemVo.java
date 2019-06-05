package com.ebupt.queue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @Author: yushibo
 * @Date: 2019/6/5 15:42
 * @Description: 存放到队列的元素
 * Delayed 使用的定时单位为纳秒
 */
public class ItemVo<T> implements Delayed{

    private long avtiveTime;//到期时间，单位毫秒
    private T data;
//avtiveTime是个过期时长
    public ItemVo(long avtiveTime, T data) {
        //将传入的时长转换为超时的时刻
        this.avtiveTime = TimeUnit.NANOSECONDS.convert
                (avtiveTime,TimeUnit.MILLISECONDS) + System.nanoTime();
        this.data = data;
    }

    //返回元素的剩余时间
    @Override
    public long getDelay(TimeUnit unit) {
        long d = unit.convert
                (avtiveTime-System.nanoTime(),TimeUnit.NANOSECONDS);
        return d;
    }

    //按照剩余时间排序
    @Override
    public int compareTo(Delayed o) {
        long d = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);


        return d==0 ? 0: (d>0?1:-1);
    }


    public long getAvtiveTime() {
        return avtiveTime;
    }

    public void setAvtiveTime(long avtiveTime) {
        this.avtiveTime = avtiveTime;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
