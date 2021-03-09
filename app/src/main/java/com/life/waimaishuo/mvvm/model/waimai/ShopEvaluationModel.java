package com.life.waimaishuo.mvvm.model.waimai;

import com.life.base.utils.GsonUtil;
import com.life.waimaishuo.bean.Comment;
import com.life.waimaishuo.bean.api.request.WaiMaiShopReqData;
import com.life.waimaishuo.bean.api.request.bean.ShopEvaluateReqBean;
import com.life.waimaishuo.bean.api.respon.ShopEvaluation;
import com.life.waimaishuo.bean.api.respon.ShopStatEvaluate;
import com.life.waimaishuo.constant.ApiConstant;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.util.net.HttpUtils;

import java.util.ArrayList;
import java.util.List;

public class ShopEvaluationModel extends BaseModel {

    public ShopStatEvaluate shopStatEvaluate;
    public ShopEvaluation shopEvaluation;

    public List<Comment> getCommentsList() {
        if (shopEvaluation != null) {
            return shopEvaluation.getList();
        }
        return null;
    }

    public ShopStatEvaluate getShopStatEvaluate(){
        return shopStatEvaluate;
    }

    public void requestStatEvaluate(RequestCallBack<Object> requestCallBack, int shopId) {
        WaiMaiShopReqData.WaiMaiSimpleReqData reqData = new WaiMaiShopReqData.WaiMaiSimpleReqData(shopId);
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_SHOP_STAT_EVALUATE, GsonUtil.toJsonString(reqData), true, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                shopStatEvaluate = null;
                if (!"".equals(data)) {
                    shopStatEvaluate = GsonUtil.parserJsonToBean(data, ShopStatEvaluate.class);
                    requestCallBack.onSuccess(data);
                } else {
                    requestCallBack.onFail(data);
                }
            }

            @Override
            public void onError(int type, Throwable error) {
                shopStatEvaluate = null;
                requestCallBack.onFail(error.getMessage());
            }
        });
    }

    public List<String> getCommentsType() {
        List<String> commentType = new ArrayList<>();
        commentType.add("全部");
        commentType.add("有图");
        return commentType;
    }

    /**
     * 请求店铺评价
     *
     * @param requestCallBack
     * @param shopId
     * @param pageNum
     * @param pageSize
     */
    public void requestShopEvaluate(RequestCallBack<Object> requestCallBack, int shopId, int pageNum, int pageSize, int commentTypePosition) {
        int hasIcon = 0;
        if(commentTypePosition == 1){
            hasIcon = 1;
        }
        WaiMaiShopReqData.WaiMaiEvaluateReqData reqData =
                new WaiMaiShopReqData.WaiMaiEvaluateReqData(new ShopEvaluateReqBean(shopId, pageNum, pageSize, hasIcon));
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_SHOP_EVALUATE, GsonUtil.gsonString(reqData), true, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                shopEvaluation = null;
                if (!"".equals(data)) {
                    shopEvaluation = GsonUtil.parserJsonToBean(data, ShopEvaluation.class);
                    for (Comment comment : shopEvaluation.getList()) {
                        int i = 0;
                        int size = comment.getEvaluatePhotoList().size();
                        for (;i < size; i++) {  //评价图片列表http转https
                            comment.getEvaluatePhotoList().set(i,
                                    HttpUtils.changeToHttps(comment.getEvaluatePhotoList().get(i)));
                        }
                        comment.setHeadPortrait(HttpUtils.changeToHttps(comment.getHeadPortrait()));
                    }
                    requestCallBack.onSuccess(shopEvaluation);
                } else {
                    requestCallBack.onFail(data);
                }
            }

            @Override
            public void onError(int type, Throwable error) {
                shopEvaluation = null;
                requestCallBack.onFail(error.getMessage());
            }
        });
    }

}
