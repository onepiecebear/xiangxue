package com.ebupt.readwrite;

import com.ebupt.tools.SleepTools;

/**
 * @Author: yushibo
 * @Date: 2019/5/30 14:59
 * @Description: 用内置锁来实现商品服务接口
 */
public class UseSyn implements GoodsService{

    private GoodsInfo goodsInfo;

    public UseSyn(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    @Override
    public synchronized GoodsInfo getNum() {
        SleepTools.ms(5);
        return this.goodsInfo;
    }

    @Override
    public synchronized void setNum(int number) {
        SleepTools.ms(5);
        goodsInfo.changeMunber(number);
    }
}