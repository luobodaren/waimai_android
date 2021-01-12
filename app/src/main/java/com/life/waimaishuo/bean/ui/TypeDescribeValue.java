package com.life.waimaishuo.bean.ui;

/**
 * 供 RecyclerView显示的item为三项文字 使用
 */
public class TypeDescribeValue {

    String type;
    String describe;
    String value;

    public TypeDescribeValue() {
    }

    public TypeDescribeValue(String type, String describe, String value) {
        this.type = type;
        this.describe = describe;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
