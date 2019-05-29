package com.ebupt.forkjoin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * @Author: yushibo1·
 * @Date: 2019/5/29 11:38
 * @Description: 异步遍历指定目录（含子目录）找寻指定类型文件
 */
public class FindDirsFiles extends RecursiveAction{


    private File path; //当前任务需要搜寻的目录

    public FindDirsFiles(File path) {
        this.path = path;
    }

    @Override
    protected void compute() {

        List<FindDirsFiles> subTasks = new ArrayList<>();

        File[] files = path.listFiles();
        if(files != null){
            for (File file : files) {
                if(file.isDirectory()){
                    subTasks.add(new FindDirsFiles(file));
                }else{
                    //遇到文件
                    if(file.getAbsolutePath().endsWith("txt")){
                        System.out.println("文件："+file.getAbsolutePath());
                    }
                }
            }
            if (!subTasks.isEmpty()) {
                for (FindDirsFiles subTask: invokeAll(subTasks)){
                    subTask.join();//等待子任务执行完成
                }
            }
        }
    }

    public static void main(String[] args) {

        ForkJoinPool pool = new ForkJoinPool() ;
        FindDirsFiles task = new FindDirsFiles(new File("F:/"));

        pool.execute(task);//异步调用

        System.out.println("Task is running ");
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int otherWork = 0;
        for (int i = 0; i < 100; i++) {
            otherWork = otherWork+i ;

        }
        System.out.println("main thread done sth,otherWork: "+otherWork);
        task.join();//阻塞的方法 不然主线程结束，forkjoin自动终止
        System.out.println("task end");

    }
}
