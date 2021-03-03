package com.life.waimaishuo.bean.api.request;

import com.life.waimaishuo.bean.api.request.bean.ShoppingCartOption;

public class WaiMaiShopReqData  {

    /**
     * 大部分外卖接口仅需要传入店铺ID
     */
    public static class WaiMaiSimpleReqData extends BaseReqData<Integer> {
        public WaiMaiSimpleReqData(Integer reqData) {
            super(reqData);
        }
    }

    /**
     * 加入购物车
     */
    public static class WaiMaiShoppingCartOption extends BaseReqData<ShoppingCartOption>{
        public WaiMaiShoppingCartOption(ShoppingCartOption reqData) {
            super(reqData);
        }
    }

//    /**
//     * 获取店铺信息
//     */
//    public static class WaiMaiShopInfoReqData extends BaseReqData<Integer> {
//        public WaiMaiShopInfoReqData(Integer reqData) {
//            super(reqData);
//        }
//    }
//
//    /**
//     * 是否成为会员
//     */
//    public static class WaiMaiIsShopFansReqData extends BaseReqData<Integer>{
//        public WaiMaiIsShopFansReqData(Integer reqData) {
//            super(reqData);
//        }
//    }
//
//    /**
//     * 成为会员
//     */
//    public static class WaiMaiJoinShopFansReqData extends BaseReqData<Integer>{
//        public WaiMaiJoinShopFansReqData(Integer reqData) {
//            super(reqData);
//        }
//    }
//
//    /**
//     * 是否收藏店铺
//     */
//    public static class WaiMaiIsCollectShop extends BaseReqData<Integer>{
//        public WaiMaiIsCollectShop(Integer reqData) {
//            super(reqData);
//        }
//    }

}
