package com.life.waimaishuo.constant;

public class ApiConstant {

    public final static String DOMAIN_NAME = "https://api.waimai.life/api";

    /**
     * 接口默认需要传入的json字符串 不可缺少
     */
    public final static String DEFAULT_BASE_JSON_INFO = "{ \"reqData\":\"\"}";
    public final static String WAIMAI_SECOND_KILL_JSON_INFO = "{ \"reqData\":5}";  //获取外卖类型的限时秒杀时间
    public final static String MALL_SECOND_KILL_JSON_INFO = "{ \"reqData\":3}";  //获取商场类型的限时秒杀时间

    public final static String API_LOGIN = "/appuser/notelogin";

    public final static String API_GET_VERIFICATION = "/common/sendsms";

    public final static String API_WAIMAI_COLLECT_SHOP_OR_GOOD = "/appusercollect/addusercollect";    //收藏店铺或商品
    public final static String API_WAIMAI_CANCEL_COLLECT_SHOP_OR_GOOD = "/appusercollect/cancelusercollect";    //取消收藏店铺或商品

    public final static String API_WAIMAI_SEARCH = "/searchDeliveryGoods/searchGoodsOrShopApp"; //搜索接口 品牌专区也使用此接口

    //--------------外卖---------------//
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

    public final static String API_WAIMAI_TYPE_GET_SUBTYPES_BY_NAME = "/goodstype/getgtsubsetbysuperiorname";    //根据上级NAME查询子集
    public final static String API_WAIMAI_TYPE_GET_SUBTYPES_BY_ID = "/goodstype/getgtsubsetbysuperiorid";    //根据上级NAME查询子集
    public final static String API_WAIMAI_TYPE_GET_SUBTYPE_BY_NAME = "/goodstype/getOnebysuperiorname";  //更具类型名称获取信息

    public final static String API_WAIMAI_ALL_TYPE = "/common/gettakeouttype";  //全部分类

    public final static String API_WAIMAI_SHOPINFO = "/deliveryShopFirst/getShopInfoApp";   //店铺首页信息
    public final static String API_WAIMAI_IS_JOIN_SHOP_FANS = "/shopfans/isJoinShopFans";   //是否成为会员
    public final static String API_WAIMAI_JOIN_SHOP_FANS = "/shopfans/joinShopFans";        //成为会员

    public final static String API_WAIMAI_IS_COLLECT_SHOP = "/appusercollect/isusercollectshop";    //是否收藏店铺
    public final static String API_WAIMAI_COLLECT_SHOP = "/appusercollect/isusercollectshop";    //收藏店铺

    public final static String API_WAIMAI_GET_SHOP_GOODS_GROUP_LIST = "/deliveryShopFirst/selectShopGroupList";   //查询外卖店铺首页的分组以及商品列表

    public final static String API_WAIMAI_GOODS_DELIVER_DETAIL = "/deliveryShopFirst/getGoodsDeliveryDetail";   //外卖商品详情
    public final static String API_WAIMAI_GET_GOODS_SPECIFICATION = "/deliveryShopFirst/getDeliveryGoodsSpecs";

    public final static String API_WAIMAI_GET_SHOPPING_CART = "/deliverycart/getcartbyshopid";  //获取购物车
    public final static String API_WAIMAI_ADD_SHOPPING_CART = "/deliverycart/adddeliverycart";  //加入购物车
    public final static String API_WAIMAI_CHANGE_SHOPPING_CART = "/deliverycart/upddeliverycart";  //修改购物车
    public final static String API_WAIMAI_DEL_SHOPPING_CART_LIST = "/deliverycart/delListDeliveryCart";  //清空指定店铺购物车

    public final static String API_WAIMAI_SHOP_STAT_EVALUATE = "/deliveryShopFirst/getStatEvaluateByShopId";    //店铺总评分信息
    public final static String API_WAIMAI_SHOP_EVALUATE = "/deliveryShopFirst/selectGoodsEvaluateApp";  //店铺评价内容
}
