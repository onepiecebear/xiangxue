package com.ebupt.deadlock;

/**
 * @Author: yushibo
 * @Date: 2019/6/12 16:53
 * @Description:
 */
public class PayCompany {

    private static class TranserThread extends Thread{
        private String name; //线程名字
        private UserAccount from;
        private UserAccount to;
        private int amount;
        private ITransfer transfer; //转账动作

        public TranserThread(String name, UserAccount from, UserAccount to, int amount, ITransfer transfer) {
            this.name = name;
            this.from = from;
            this.to = to;
            this.amount = amount;
            this.transfer = transfer;
        }

        @Override
        public void run() {
            Thread.currentThread().setName(name);

            try {
                transfer.transfer(from,to,amount);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public static void main(String[] args) {
            PayCompany payCompany = new PayCompany();
            UserAccount zhangsan = new  UserAccount("张三",20000);
            UserAccount lisi = new UserAccount("李四",20000);
            ITransfer transfer = new TrasnferAccount();
           TranserThread zhangsantolisi = new TranserThread("zhangsantolisi",zhangsan,lisi,2000,transfer);

           TranserThread lisitozhangsan = new TranserThread("lisitozhangsan",zhangsan,lisi,4000,transfer);

            zhangsantolisi.start();
            lisitozhangsan.start();
            }
    }
}
