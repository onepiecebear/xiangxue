package com.ebupt.forkjoin;

import com.ebupt.tools.SleepTools;

import javax.naming.ldap.SortResponseControl;

/**
 * @Author: yushibo
 * @Date: 2019/5/28 17:16
 * @Description:
 */
public class SumNormal {

    public static void main(String[] args) {
            int count = 0;
        int[] src =  MakeArray.makeArray();
        long start = System.currentTimeMillis();
        for (int i = 0; i < src.length; i++) {
            SleepTools.ms(1);
            count = count+ src[i];
        }
        System.out.println("The count is "+count+" spend time "+ (System.currentTimeMillis()-start)+" ms");
        }
}
