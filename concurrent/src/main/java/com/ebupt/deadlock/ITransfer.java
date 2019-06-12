package com.ebupt.deadlock;

/**
 * @Author: yushibo
 * @Date: 2019/6/12 16:46
 * @Description:
 */
public interface ITransfer {

    /**
     *
     * @param from 转出账户
     * @param to 转入账户
     * @param amount 金额
     */
    void transfer(UserAccount from,UserAccount to ,int amount) throws InterruptedException;
}
