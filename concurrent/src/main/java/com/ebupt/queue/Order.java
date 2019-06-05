package com.ebupt.queue;

/**
 * @Author: yushibo
 * @Date: 2019/6/5 15:58
 * @Description: 订单实体类
 */
public class Order {

    private String orderNo;//订单编号
    private String orderMoney;//订单金额

    public Order(String orderNo, String orderMoney) {
        this.orderNo = orderNo;
        this.orderMoney = orderMoney;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(String orderMoney) {
        this.orderMoney = orderMoney;
    }
}
