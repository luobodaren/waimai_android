package com.life.waimaishuo.constant;

public class Constant {

    /**
     * 页面类型 1：外卖 2：商城
     * 用于页面复用时判断
     */
    public static final String PAGE_TYPE = "page_type";
    public static final int PAGE_TYPE_WAIMAI = 1;
    public static final int PAGE_TYPE_MALL = 2;

    /**
     * openPageForResult的请求码
     */
    public static final int REQUEST_CODE_CHOSE_RED_PACKET = 1000;
    public static final int REQUEST_CODE_ORDER_NOTE = 1001;
    public static final int REQUEST_CODE_ADD_NEW_ADDRESS = 1002;
    public static final int REQUEST_CODE_FILLING_LOGISTICS = 1003;
    public static final int REQUEST_CODE_SELECT_CATEGORY = 1004;
    /**
     * page 返回的 结果码
     */
    public static final int RESULT_CODE_SUCCESS = 0;
    public static final int RESULT_CODE_FALSE = -1;

    /**
     * 个人头像拍照
     */
    public static final int REQUEST_CODE_HEAD_PORTRAIT_TAKE_PICTURE = 1000;

    //PopWindow 显示时常
    public static final int POP_WINDOW_SHOW_TIME = 1000;

    public static final String ERROR_MSG_NO_BUNDLE = "没有传入bundle 无法确定页面类型";
    public static final String ERROR_MSG_BUNDLE_DATA_ERROR = "bundle 数据错误";


}
