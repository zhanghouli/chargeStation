package com.jopool.chargingStation.www.base.pay.wxpay.message;

import com.jopool.jweb.utils.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by gexin on 2017/6/28.
 */
public class TemplateData extends HashMap<String, TemplateData.Item> {
    /**
     * 生成数据数据
     *
     * @return
     */
    public TemplateData getData() {
        Field[] fields = ReflectionUtils.getAllField(this.getClass());
        for (Field field : fields) {
            String key = field.getName();
            String value = (String) ReflectionUtils.getFieldValue(this, key);
            Item item = new Item(value);
            this.put(key, item);
        }
        return this;
    }

    public class Item {
        private String value;
        private String color = "#000000";

        public Item(String value) {
            this.value = value;
        }

        public Item(String value, String color) {
            this.value = value;
            this.color = color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getColor() {
            return color;
        }
    }
}
