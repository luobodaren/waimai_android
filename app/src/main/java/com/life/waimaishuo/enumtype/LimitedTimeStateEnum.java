package com.life.waimaishuo.enumtype;

public enum LimitedTimeStateEnum {

    STARTING("已开抢",1),
    ROBBING("正在热抢",2),
    NO_START("未开始",3),
    SALE_OUT("已抢完",4);

    private String state;
    private int code;

    LimitedTimeStateEnum(String state, int code) {
        this.state = state;
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public int getCode() {
        return code;
    }

}
