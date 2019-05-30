package com.ebupt.readwrite;

import com.ebupt.tools.SleepTools;

import java.util.Random;

/**
 * @Author: yushibo
 * @Date: 2019/5/30 15:02
 * @Description:
 */
public class BusiApp {

    static final int readWriteRatio = 10; //读写线程的比例
    static final int minthreadCount = 3; //最少线程数

    //读操作
    private static class GetTHread implements Runnable {

        private GoodsService goodsService;

        public GetTHread(GoodsService goodsService) {
            this.goodsService = goodsService;
        }

        @Override
        public void run() {

            long start = System.currentTimeMillis();
            for (int i = 0; i < 100; i++) {//操作100次
                goodsService.getNum();
            }
            System.out.println(Thread.currentThread().getName() + "读取商品数据耗时：" +
                    (System.currentTimeMillis() - start) + "ms");

        }
    }

    //写操作
    private static class SetTHread implements Runnable {

        private GoodsService goodsService;

        public SetTHread(GoodsService goodsService) {
            this.goodsService = goodsService;
        }

        @Override
        public void run() {

            long start = System.currentTimeMillis();
            Random random = new Random();
            for (int i = 0; i < 10; i++) {//操作10次
                SleepTools.ms(50);
                goodsService.setNum(random.nextInt(10));
            }
            System.out.println(Thread.currentThread().getName() + "写商品数据耗时：" +
                    (System.currentTimeMillis() - start) + "ms");

        }
    }

    public static void main(String[] args) {
            GoodsInfo goodsInfo = new GoodsInfo("cpu",100000,10000);
//            GoodsService goodsService = new UseSyn(goodsInfo);
            GoodsService goodsService = new UserRWLock(goodsInfo);
        for (int i = 0; i < minthreadCount; i++) {
            Thread setT = new Thread(new SetTHread(goodsService));
            for (int j = 0; j < readWriteRatio; j++) {
                Thread getT = new Thread(new GetTHread(goodsService));
                getT.start();
            }
            SleepTools.ms(100);
            setT.start();
        }
    }
}
