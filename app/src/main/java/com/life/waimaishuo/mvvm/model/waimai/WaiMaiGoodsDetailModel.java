package com.life.waimaishuo.mvvm.model.waimai;

import com.life.base.utils.GsonUtil;
import com.life.base.utils.LogUtil;
import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.bean.api.request.WaiMaiShopReqData;
import com.life.waimaishuo.constant.ApiConstant;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.util.net.HttpUtils;

public class WaiMaiGoodsDetailModel extends BaseModel {

    public Goods goods;

    public void requestGoodsDetail(RequestCallBack<Object> requestCallBack, WaiMaiShopReqData.WaiMaiSimpleReqData reqData) {
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_GOODS_DELIVER_DETAIL, GsonUtil.toJsonString(reqData), true, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                goods = null;
                if (!"".equals(data)) {
                    goods = GsonUtil.parserJsonToBean(data, Goods.class);

                    goods.setGoodsImgUrl(HttpUtils.changeToHttps(goods.getGoodsImgUrl()));
                    goods.setGoodsMoreImgs(HttpUtils.changeToHttps(goods.getGoodsMoreImgs()));
                }
                requestCallBack.onSuccess(null);
            }

            @Override
            public void onError(int errorType, Throwable error) {
                LogUtil.e("requestGoodsDetail error:" + error.getMessage());
                goods = null;
                requestCallBack.onFail(error.getMessage());
            }
        });
    }

}
