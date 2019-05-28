package com.ebupt.main;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @Author: yushibo
 * @Date: 2019/5/27 11:15
 * @Description:
 */
public class OnlyMain {
    public static void main(String[] args) {
        //虚拟机线程管理接口
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
    }
}
