package com.life.waimaishuo.mvvm.model.waimai;

import com.life.base.utils.GsonUtil;
import com.life.base.utils.LogUtil;
import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.bean.api.request.WaiMaiShopReqData;
import com.life.waimaishuo.bean.api.request.bean.ShopCommendGoodsReqBean;
import com.life.waimaishuo.constant.ApiConstant;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.util.net.HttpUtils;

import java.util.ArrayList;
import java.util.List;

public class RecommendGoodsModel extends BaseModel {

    public List<Goods> commendGoods = new ArrayList<>();

    public void requestCommendGoods(RequestCallBack<Object> requestCallBack, int shopId, int brandId, int pageNum, int pageSize) {
        WaiMaiShopReqData.WaiMaiShopCommendGoods reqData = new WaiMaiShopReqData.WaiMaiShopCommendGoods(
                new ShopCommendGoodsReqBean(shopId,brandId,pageNum,pageSize));
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_GOODS_DETAIL_COMMEND_GOODS, GsonUtil.gsonString(reqData), true, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                if (!"".equals(data)) {
                    Integer total = GsonUtil.getIntNoteJsonString(data,"total");
                    String listData = GsonUtil.getStringNoteJsonString(data,"list");
                    if((total != null && total > 0) || (!"null".equals(listData) && !"".equals(listData))){
                        commendGoods = GsonUtil.parserJsonToArrayBeans(listData, Goods.class);
                        for (Goods goods:commendGoods) {
                            goods.setGoodsImgUrl(HttpUtils.changeToHttps(goods.getGoodsImgUrl()));
                        }
                    }else{
                        commendGoods.clear();
                    }
                }
                requestCallBack.onSuccess(null);
            }

            @Override
            public void onError(int errorType, Throwable error) {
                LogUtil.e("requestCommendGoods error:" + error.getMessage());
                commendGoods.clear();
                requestCallBack.onFail(error.getMessage());
            }
        });
    }

}
