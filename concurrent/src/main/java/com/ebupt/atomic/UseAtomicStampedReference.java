package com.ebupt.atomic;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Author: yushibo
 * @Date: 2019/5/30 0030 0:26
 * @Description: 带版本戳的原子操作类型
 */

public class UseAtomicStampedReference {
    //(值，版本号)
    static AtomicStampedReference<String> asr =
            new AtomicStampedReference<>("Mark",0);

    public static void main(String[] args) throws InterruptedException {
        final int oldStamp = asr.getStamp();//获得初始的版本号
        final  String oldReference = asr.getReference();

        System.out.println(oldReference+"-----"+oldStamp);

        Thread rightStampThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+
                        "当前变量值"+oldReference+"当前版本戳"+oldStamp+"-"+
                        //(期望值，新值，期望版本号，新的版本号)
                        asr.compareAndSet(oldReference,oldReference+"java",oldStamp,oldStamp+1));
            }
        });
        Thread errorStampThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String ref = asr.getReference();
                System.out.println(Thread.currentThread().getName()+
                        "当前变量值"+oldReference+"当前版本戳"+oldStamp+
                        //(期望值，新值，期望版本号，新的版本号)
                        asr.compareAndSet(oldReference,oldReference+"C",oldStamp,oldStamp+1));
            }
        });

        rightStampThread.start();
        rightStampThread.join();
        errorStampThread.start();
        errorStampThread.join();
        System.out.println(asr.getReference()+"-----"+asr.getStamp());


    }
}
