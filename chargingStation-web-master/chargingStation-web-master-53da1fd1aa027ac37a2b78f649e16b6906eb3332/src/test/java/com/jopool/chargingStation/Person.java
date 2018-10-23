package com.jopool.chargingStation;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Project : chargingStation-web
 * @Package Name : com.jopool.chargingStation
 * @Author : soupcat
 * @Creation Date : 2017年11月30日 下午9:35
 */
public class Person {
    @JSONField(name="name")
    private String name_;

    private String age;

    private String desc;

    public String getName_() {
        return name_;
    }

    public void setName_(String name_) {
        this.name_ = name_;
    }

    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
