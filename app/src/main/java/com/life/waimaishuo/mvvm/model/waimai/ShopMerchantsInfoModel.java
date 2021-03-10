package com.life.waimaishuo.mvvm.model.waimai;

import com.life.base.utils.GsonUtil;
import com.life.base.utils.LogUtil;
import com.life.waimaishuo.bean.api.request.WaiMaiReqData;
import com.life.waimaishuo.bean.api.request.WaiMaiShopReqData;
import com.life.waimaishuo.bean.api.respon.WaiMaiShopBrandStory;
import com.life.waimaishuo.constant.ApiConstant;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.util.net.HttpUtils;

public class ShopMerchantsInfoModel extends BaseModel {

    public WaiMaiShopBrandStory brandStory;

    /**
     * 请求品牌故事网页地址
     * @param requestCallBack
     * @param shopId
     */
    public void requestBrandStory(RequestCallBack<Object> requestCallBack, int shopId) {
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_SHOP_BRAND_STORY,
                GsonUtil.gsonString(new WaiMaiShopReqData.WaiMaiSimpleReqData(shopId)), true,
                new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String data) {
                        brandStory = null;
                        if(!"".equals(data)){
                            brandStory = GsonUtil.parserJsonToBean(data, WaiMaiShopBrandStory.class);
                            requestCallBack.onSuccess(brandStory);
                        }else{
                            requestCallBack.onFail(data);
                        }
                    }

                    @Override
                    public void onError(int type, Throwable error) {
                        brandStory = null;
                        requestCallBack.onFail(error.getMessage());
                    }
                });

    }

}
