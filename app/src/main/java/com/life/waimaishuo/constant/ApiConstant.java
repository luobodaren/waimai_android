package com.life.waimaishuo.constant;

public class ApiConstant {

    public final static String DOMAIN_NAME = "https://api.waimai.life/api";

    /**
     * 接口默认需要传入的json字符串 不可缺少
     */
    public final static String DEFAULT_BASE_JSON_INFO = "{ \"reqData\":\"\"}";
    public final static String WAIMAI_SECOND_KILL_JSON_INFO = "{ \"reqData\":5}";  //获取外卖类型的限时秒杀时间
    public final static String MALL_SECOND_KILL_JSON_INFO = "{ \"reqData\":3}";  //获取商场类型的限时秒杀时间

    public final static String API_WAIMAI_SEARCCH = "searchDeliveryGoods/searchGoodsOrShopApp"; //搜索接口 品牌专区也使用此接口

    public final static String API_WAIMAI_MAIN_SLIDESHOW = "/deliveryhomepage/getdeliveryslideshow";
    public final static String API_WAIMAI_MAIN_SEARCH_TAG = "/sysseachtag/appgetsysseachtag";
    public final static String API_WAIMAI_MAIN_KING_KONG_AREGION = "/deliveryhomepage/getkingkongarearegion";
    public final static String API_WAIMAI_MAIN_EXCLUSIVE_BREAKFAST = "/activitymarketing/exclusivebreakfast";
    public final static String API_WAIMAI_MAIN_ACTIVITY_REGION = "/deliveryhomepage/getactiveregion";
    public final static String API_WAIMAI_MAIN_RECOMMEND_TITLE = "/common/getFirstPageType";
    public final static String API_WAIMAI_MAIN_SECOND_KILL = "/activitymarketing/getseckilltimebytype";

    public final static String API_WAIMAI_MAIN_GOODS_LIST = "/searchDeliveryGoods/selectDeliveryGoodsApp";  //外卖 发现好物
    public final static String API_WAIMAI_MAIN_SHIOP_LIST = "/searchDeliveryGoods/selectShopListApp";       //外卖 推荐商家
    public final static String API_WAIMAI_ZERO_DELIVER = "/activitymarketing/selZeroDist";       //外卖 零元配送

    public final static String API_WAIMAI_TYPE_GET_SUBTYPE_BY_NAME = "/goodstype/getgtsubsetbysuperiorname";    //根据上级NAME查询子集
    public final static String API_WAIMAI_TYPE_GET_SUBTYPE_BY_ID = "/goodstype/getgtsubsetbysuperiorid";    //根据上级NAME查询子集

    public final static String API_WAIMAI_ALL_TYPE = "/common/gettakeouttype";  //全部分类


}
