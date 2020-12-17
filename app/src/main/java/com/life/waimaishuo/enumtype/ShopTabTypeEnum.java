package com.life.waimaishuo.enumtype;

public enum ShopTabTypeEnum {

    ORDER_DISHES("点餐"),
    EVALUATION("评价"),
    MERCHANT("商家");

    private String name;

    ShopTabTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
