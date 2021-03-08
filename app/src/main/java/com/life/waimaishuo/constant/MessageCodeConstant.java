package com.life.waimaishuo.constant;

public class MessageCodeConstant {

    /**
     * 切换app主页显示的内容（外卖，商场，订单，我的）
     */
    public final static int MAIN_TAB_BAR_PAGE_CHANGE_WAIMAI = 1000;
    public final static int MAIN_TAB_BAR_PAGE_CHANGE_MALL = 1001;
    public final static int MAIN_TAB_BAR_PAGE_CHANGE_ORDER = 1002;
    public final static int MAIN_TAB_BAR_PAGE_CHANGE_MINE = 1003;

    /**
     * 加入购物车
     */
    public final static int SHOPPING_CART_ADD_WITH_GOODS = 211;
    public final static int SHOPPING_CART_ADD_WITH_GOODS_DIRECT = 212;    //通过goods去调用加入购物车接口 需要设置的变量需要设置好
    public final static int SHOPPING_CART_ADD_WITH_SHOPPING_DATA = 213;   //通过购物车数据Item区调用加入购物车接口
    public final static int SHOPPING_CART_REDUCE_WITH_SHOPPING_DATA = 214;   //通过购物车数据Item区调用加入购物车接口

    /**
     * 加入购物车 成功或失败
     */
    public final static int SHOPPING_CART_ADD_SUCCESS = 2111;
    public final static int SHOPPING_CART_ADD_FALSE = 2112;
    public final static int SHOPPING_CART_CHANGE_SUCCESS = 2113;
    public final static int SHOPPING_CART_CHANGE_FALSE = 2114;

    /**
     * 请求购物车数据
     */
    public final static int SHOPPING_CART_REQUEST_DATA = 22;
    /**
     * 更新购物车信息
     */
    public final static int SHOPPING_CART_DATA_UPDATE = 23;
    /**
     * 清空购物车
     */
    public final static int SHOPPING_CART_DEL_LIST = 24;  //执行清空购物车操作
    public final static int SHOPPING_CART_DEL_LIST_SUCCESS = 241;
    public final static int SHOPPING_CART_DEL_LIST_FALSE = 242;


}
