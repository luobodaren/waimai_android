package com.life.waimaishuo.constant;

public class ApiConstant {

    public final static String DOMAIN_NAME = "https://api.waimai.life/api";

    /**
     * 接口默认需要传入的json字符串 不可缺少
     */
    public final static String DEFAULT_BASE_JSON_INFO = "{ \"reqData\":\"\"}";

    public final static String API_WAIMAI_MAIN_SLIDESHOW = "/deliveryhomepage/getdeliveryslideshow";
    public final static String API_WAIMAI_MAIN_SEARCH_TAG = "/sysseachtag/appgetsysseachtag";
    public final static String API_WAIMAI_MAIN_KING_KONG_AREGION = "/deliveryhomepage/getkingkongarearegion";
    public final static String API_WAIMAI_MAIN_EXCLUSIVE_BREAKFAST = "/activitymarketing/exclusivebreakfast";
    public final static String API_WAIMAI_MAIN_ACTIVITY_REGION = "/deliveryhomepage/getactiveregion";
}
