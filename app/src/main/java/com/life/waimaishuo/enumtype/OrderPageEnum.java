package com.life.waimaishuo.enumtype;

import java.util.ArrayList;
import java.util.List;

public enum OrderPageEnum {

    ALL("全部订单",1),
    UN_PAY("待付款",2),
    UN_DELIVER("待配送",3),
    DELIVER("配送中",4),
    FINISH("已完成",5),
    AFTER_SALES("售后",6);

    private static String[] states;

    String state;
    int code;

    OrderPageEnum(String state, int code) {
        this.state = state;
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public int getCode() {
        return code;
    }

    public static OrderPageEnum getState(int code){
        for(OrderPageEnum orderPageEnum:values()){
            if(orderPageEnum.getCode() == code){
                return orderPageEnum;
            }
        }
        return null;
    }

    public static String[] getStateStrings(){
        List<String> states = new ArrayList<>();
        for(OrderPageEnum orderPageEnum : values()){
            states.add(orderPageEnum.getState());
        }
        return states.toArray(new String[]{});
    }
}
