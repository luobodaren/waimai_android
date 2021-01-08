package com.life.waimaishuo.enumtype;

import java.util.ArrayList;
import java.util.List;

public enum OrderStateEnum {

    UN_PAY("未付款",1),
    UN_DELIVER("待配送",2),
    DELIVER("配送中",3),
    FINISH("已完成",4),
    AFTER_SALES("售后",5),
    CLOSE("交易关闭",6);

    private String state;
    private int code;

    OrderStateEnum(String state, int i) {
        this.state = state;
        this.code = i;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static OrderStateEnum valueOf(int code){
        for(OrderStateEnum orderStateEnum : values()){
            if(orderStateEnum.code == code){
                return orderStateEnum;
            }
        }
        return null;
    }

    public static String getState(int code){
        OrderStateEnum orderStateEnum = valueOf(code);
        if(orderStateEnum != null){
            return orderStateEnum.getState();
        }
        return null;
    }

    public static String[] getStates(){
        List<String> states = new ArrayList<>();
        for(OrderStateEnum orderStateEnum : values()){
            states.add(orderStateEnum.getState());
        }
        return states.toArray(new String[]{});
    }
}
