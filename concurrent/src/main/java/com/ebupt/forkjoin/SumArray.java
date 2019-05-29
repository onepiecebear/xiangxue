package com.ebupt.forkjoin;

import com.ebupt.tools.SleepTools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @Author: yushibo
 * @Date: 2019/5/28 17:16
 * @Description: 同步多线程计算数组和
 */
public class SumArray {

    private static class SumTask extends RecursiveTask<Integer>{

        private final static int THRESHOLD = MakeArray.ARRAY_LENGTH/10;
        private int[] src;//表示我们要实际统计的数组
        private int fromIndex;//开始统计的下标
        private int toIndex;//统计到哪里结束的下标

        public SumTask(int[] src, int fromIndex, int toIndex) {
            this.src = src;
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
        }

        @Override
        protected Integer compute() {
            if(toIndex-fromIndex<THRESHOLD){
                int count = 0;
                for (int i = fromIndex; i <toIndex ; i++) {
                    SleepTools.ms(1);
                    count = count +src[i];
                }
                return count;
            }else{
                int mid = (fromIndex+toIndex)/2;
                SumTask left = new SumTask(src,fromIndex,mid);
                SumTask right = new SumTask(src,mid+1,toIndex);
                //递归处理所有拆分后的任务
                invokeAll(left,right);
                //获取拆分后任务的返回值
                return left.join()+right.join();


            }
        }
    }

    public static void main(String[] args) {
        //TODO 1.8版本有优化
        ForkJoinPool pool = new ForkJoinPool();
        int[] src = MakeArray.makeArray();
        SumTask innerFind = new SumTask(src,0,src.length);
        long start = System.currentTimeMillis();

        pool.invoke(innerFind);
        //主线程阻塞 等待invoke完事之后主线程才会继续放下走（同步用法）
        System.out.println("Task is running...");

        System.out.println("The is running "+ innerFind.join()
        +"spend time :" + (System.currentTimeMillis()-start)+ "ms");
        }
}
