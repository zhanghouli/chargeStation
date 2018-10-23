package com.jopool.chargingStation.www.base.pay.wxpay.protocol;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gexin on 16/1/12.
 */
public class BaseReqData {
    /**
     *
     * @return
     */
    protected Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<String, Object>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object obj;
            try {
                obj = field.get(this);
                if(obj!=null){
                    map.put(field.getName(), obj);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
