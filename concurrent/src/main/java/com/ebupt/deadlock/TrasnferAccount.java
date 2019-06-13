package com.ebupt.deadlock;

/**
 * @Author: yushibo
 * @Date: 2019/6/12 16:49
 * @Description:
 */
public class TrasnferAccount implements ITransfer {
    private int amount;

    @Override
    public void transfer(UserAccount from, UserAccount to, int amount) throws InterruptedException {
        synchronized (from){ //先锁传出
            System.out.println(Thread.currentThread().getName()+ " get"+from.getName());

            Thread.sleep(100);

            synchronized (to){//在锁转入
                System.out.println(Thread.currentThread().getName()+ " get"+to.getName());
                from.flyMoney(amount);
                to.addMoney(amount);

            }
        }
    }
}
