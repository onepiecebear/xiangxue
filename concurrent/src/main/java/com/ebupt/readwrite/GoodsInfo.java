package com.ebupt.readwrite;


/**
 * @Author: yushibo
 * @Date: 2019/5/30 14:54
 * @Description:
 */
public class GoodsInfo {

    private String name;
    private double totalMoney;
    private int storeNumber;

    public GoodsInfo(String name, double totalMoney, int storeNumber) {
        this.name = name;
        this.totalMoney = totalMoney;
        this.storeNumber = storeNumber;
    }

    public void changeMunber(int sellNumber){
        this.totalMoney+=sellNumber*25;
        this.storeNumber-=sellNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
