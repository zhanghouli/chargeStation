package com.jopool.chargingStation;

import com.alibaba.fastjson.JSONObject;
import org.junit.Before;
import org.junit.Test;

/**
 * @Project : chargingStation-web
 * @Package Name : com.jopool.chargingStation
 * @Author : soupcat
 * @Creation Date : 2017年11月30日 下午9:36
 */
public class PersonTest {
    private Person person;

    /**
     * 初始化对象
     */
    @Before
    public void setUp() {
        person = new Person();
        person.setName_("soupcat");
        person.setAge("20");
        person.setDesc("只是一个测试");
    }

    @Test
    public void test() {
        String jsonStr = JSONObject.toJSONString(person);
        System.out.println("bean to json:" + jsonStr);
    }
}
