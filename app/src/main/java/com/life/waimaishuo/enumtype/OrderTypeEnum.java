package com.life.waimaishuo.enumtype;

import java.util.ArrayList;
import java.util.List;

public enum OrderTypeEnum {

    WAI_MAI("外卖",1),
    MALL("商城",2);

    private static String[] states;

    String name;
    int code;

    OrderTypeEnum(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public static OrderTypeEnum getState(int code){
        for(OrderTypeEnum orderTypeEnum:values()){
            if(orderTypeEnum.getCode() == code){
                return orderTypeEnum;
            }
        }
        return null;
    }

    public static String[] getStateStrings(){
        List<String> states = new ArrayList<>();
        for(OrderTypeEnum orderTypeEnum : values()){
            states.add(orderTypeEnum.getName());
        }
        return states.toArray(new String[]{});
    }
}
