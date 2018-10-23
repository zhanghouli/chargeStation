package com.jopool.chargingStation;

import com.jopool.jweb.enums.Code;
import com.jopool.jweb.exceptions.JWebException;

/**
 * Created by gexin on 2018/4/10.
 */
public class Demo {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            if (5 == i) {
                throw new JWebException(Code.ERROR);
            }
            System.out.println(i);
        }
    }
}
