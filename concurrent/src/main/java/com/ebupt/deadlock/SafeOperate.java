package com.ebupt.deadlock;

/**
 * @Author: yushibo
 * @Date: 2019/6/12 17:41
 * @Description:
 */
public class SafeOperate implements ITransfer {

    private static  Object tieLock = new Object();//加时赛锁

    @Override
    public void transfer(UserAccount from, UserAccount to, int amount) throws InterruptedException {
        //UserAccount对象内如果有ID值且唯一且能区分大小可以使用ID值
        int fromHash = System.identityHashCode(from);
        int toHash = System.identityHashCode(to);
        if(fromHash<toHash){
            synchronized (from){ //先锁传出
                System.out.println(Thread.currentThread().getName()+ " get"+from.getName());
                Thread.sleep(100);
                synchronized (to){//在锁转入
                    System.out.println(Thread.currentThread().getName()+ " get"+to.getName());
                    from.flyMoney(amount);
                    to.addMoney(amount);
                }
            }

        }else if(fromHash>toHash){
            synchronized (to){ //先锁传出
                System.out.println(Thread.currentThread().getName()+ " get"+to.getName());
                Thread.sleep(100);
                synchronized (from){//在锁转入
                    System.out.println(Thread.currentThread().getName()+ " get"+from.getName());
                    from.flyMoney(amount);
                    to.addMoney(amount);
                }
            }
        }else{ //解决hash冲突的方法
          synchronized (tieLock){
                synchronized (to){ //先锁传出
                    System.out.println(Thread.currentThread().getName()+ " get"+to.getName());
                    Thread.sleep(100);
                    synchronized (from){//在锁转入
                        System.out.println(Thread.currentThread().getName()+ " get"+from.getName());
                        from.flyMoney(amount);
                        to.addMoney(amount);
                    }
                }
            }
        }

    }
}
