package com.life.waimaishuo.enumtype;

public enum OrderStateEnum {

    UNPAY("未付款",1),
    UNDELIVER("待配送",2),
    DELIVER("配送中",3),
    FINISH("已完成",4),
    AFTER_SALES("售后",5)
    ;

    private String state;
    private int code;

    OrderStateEnum(String state, int i) {
        this.state = state;
        code = i;
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

    public static String getState(int code){
        if(code == UNPAY.code){
            return UNPAY.state;
        }
        if(code == UNDELIVER.code){
            return UNPAY.state;
        }
        if(code == DELIVER.code){
            return UNPAY.state;
        }
        if(code == FINISH.code){
            return UNPAY.state;
        }
        if(code == AFTER_SALES.code){
            return UNPAY.state;
        }
        return "";
    }
}
