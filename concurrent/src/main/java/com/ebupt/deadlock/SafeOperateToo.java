package com.ebupt.deadlock;

import com.ebupt.tools.SleepTools;

import java.util.Random;

/**
 * @Author: yushibo
 * @Date: 2019/6/12 17:52
 * @Description:
 */
public class SafeOperateToo implements ITransfer{
    @Override
    public void transfer(UserAccount from, UserAccount to, int amount) throws InterruptedException {
        Random random = new Random();

        while (true){
            if(from.getLock().tryLock()){
                try {
                    if(to.getLock().tryLock()){
                        try {
                            //两把锁都拿到了
                            from.flyMoney(amount);
                            to.addMoney(amount);
                            break;
                        }finally {
                        to.getLock().unlock();
                    }
                    }
                }finally {
                    from.getLock().unlock();
                }
            }
            SleepTools.ms(random.nextInt(10));
        }
    }
}
