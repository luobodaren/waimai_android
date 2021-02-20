package com.life.waimaishuo.mvvm.model.waimai;

import com.life.base.utils.GsonUtil;
import com.life.base.utils.LogUtil;
import com.life.base.utils.net.HttpUtils;
import com.life.waimaishuo.MyApplication;
import com.life.waimaishuo.R;
import com.life.waimaishuo.bean.ExclusiveShopData;
import com.life.waimaishuo.bean.SearchTag;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.bean.api.SimpleString;
import com.life.waimaishuo.bean.api.request.WaiMaiReqData;
import com.life.waimaishuo.bean.api.respon.SecondKillTime;
import com.life.waimaishuo.bean.ui.ImageUrlNameData;
import com.life.waimaishuo.bean.ui.LimitedTimeGoodsData;
import com.life.waimaishuo.constant.ApiConstant;
import com.life.waimaishuo.mvvm.model.BaseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class WaimaiModel extends BaseModel {

    public List<String> mBannerItemList = new ArrayList<>();        //轮播图地址
    public SearchTag[] searchTags = new SearchTag[]{};              //搜索标签
    public List<ImageUrlNameData> mFoodTypeList = new ArrayList<>();//食物类型（金刚区）
    public List<ImageUrlNameData> mActivityRegion = new ArrayList<>();
    public final String[] defaultRecommendTitle = MyApplication.getMyApplication().getResources().getStringArray(R.array.default_waimai_recommend_titles);    //默认推荐列表
    public List<String> recommendTitle = new ArrayList<>();
    public List<Shop> mShopList = new ArrayList<>();
    public List<ExclusiveShopData> mExclusiveShopDataList = new ArrayList<>();
    public List<LimitedTimeGoodsData> mLimitedTimeGoodsDataList = new ArrayList<>();

    public SecondKillTime secondKillTime = new SecondKillTime();   //限时秒杀时间

    /**
     * 轮播图
     *
     * @param requestCallBack
     * @param timeOutRequestTime
     */
    public void requestBannerItemList(RequestCallBack<Object> requestCallBack, int timeOutRequestTime) {
        timeOutRequestTime--;
        int count = timeOutRequestTime;
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_MAIN_SLIDESHOW, ApiConstant.DEFAULT_BASE_JSON_INFO, false, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                mBannerItemList.clear();
                if (!"".equals(data)) {
                    List<ImageUrlNameData> list = GsonUtil.parserJsonToArrayBeans(data, "sysDecorations", ImageUrlNameData.class);
                    String url;
                    for (ImageUrlNameData imageUrlNameData : list) {
                        url = HttpUtils.changeToHttps(imageUrlNameData.getImgUrl());
                        imageUrlNameData.setImgUrl(url);
                        mBannerItemList.add(url);
                    }
                    requestCallBack.onSuccess(list);
                } else {
                    requestCallBack.onSuccess(null);
                }
            }

            @Override
            public void onError(Throwable error) {
                mBannerItemList.clear();
                LogUtil.e("requestBannerItemList error:" + error.getMessage() + count);
                if (error instanceof TimeoutException) {
                    if (count >= 0) {
                        requestBannerItemList(requestCallBack, count); //再执行一次
                    } else {
                        requestCallBack.onFail(error.getMessage());
                    }
                } else {
                    requestCallBack.onFail(error.getMessage());
                }
            }
        });
    }

    /**
     * 搜索标签
     *
     * @param requestCallBack
     */
    public void requestSearchTag(RequestCallBack<Object> requestCallBack, int timeOutRequestTime) {
        timeOutRequestTime--;
        int count = timeOutRequestTime;
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_MAIN_SEARCH_TAG, ApiConstant.DEFAULT_BASE_JSON_INFO, false, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                if (!"".equals(data)) {
                    List<SearchTag> list = GsonUtil.parserJsonToArrayBeans(data, SearchTag.class);
                    searchTags = list.toArray(new SearchTag[]{});
                    requestCallBack.onSuccess(searchTags);
                } else {
                    requestCallBack.onSuccess(null);
                }
            }

            @Override
            public void onError(Throwable error) {
                if(searchTags.length > 0)
                    searchTags = new SearchTag[]{};
                LogUtil.e("requestSearchTag error:" + error.getMessage() + count);
                if (error instanceof TimeoutException) {
                    if (count >= 0) {
                        requestSearchTag(requestCallBack, count); //再执行一次
                    } else {
                        requestCallBack.onFail(error.getMessage());
                    }
                } else {
                    requestCallBack.onFail(error.getMessage());
                }
            }
        });
    }

    /**
     * 金刚区
     *
     * @param requestCallBack
     * @param timeOutRequestTime
     */
    public void requestKingKongArea(RequestCallBack<Object> requestCallBack, int timeOutRequestTime) {
        timeOutRequestTime--;
        int count = timeOutRequestTime;
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_MAIN_KING_KONG_AREGION, ApiConstant.DEFAULT_BASE_JSON_INFO, false, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                if (!"".equals(data)) {
                    mFoodTypeList = GsonUtil.parserJsonToArrayBeans(data, "sysDecorations", ImageUrlNameData.class);
                    for (ImageUrlNameData imageUrlNameData : mFoodTypeList) {
                        imageUrlNameData.getImageUrlNameData().setUrl(
                                HttpUtils.changeToHttps(imageUrlNameData.getImageUrlNameData().getUrl()));
                    }
                    requestCallBack.onSuccess(mFoodTypeList);
                } else {
                    mFoodTypeList.clear();
                    requestCallBack.onSuccess(null);
                }
            }

            @Override
            public void onError(Throwable error) {
                mFoodTypeList.clear();
                LogUtil.e("requestKingKongArea error:" + error.getMessage() + count);
                if (error instanceof TimeoutException) {
                    if (count >= 0) {
                        requestKingKongArea(requestCallBack, count);
                    } else {
                        requestCallBack.onFail(error.getMessage());
                    }
                } else {
                    requestCallBack.onFail(error.getMessage());
                }
            }
        });
    }

    /**
     * 专属早餐
     * @param requestCallBack
     * @param timeOutRequestTime
     */
    public void getExclusiveBreakfast(RequestCallBack<Object> requestCallBack, int timeOutRequestTime) {
        timeOutRequestTime--;
        int count = timeOutRequestTime;
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_MAIN_EXCLUSIVE_BREAKFAST, ApiConstant.DEFAULT_BASE_JSON_INFO, false, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                if (!"".equals(data)) {
                    mExclusiveShopDataList = GsonUtil.parserJsonToArrayBeans(data, "list", ExclusiveShopData.class);
                    requestCallBack.onSuccess(mExclusiveShopDataList);
                } else {
                    requestCallBack.onSuccess(null);
                }
            }

            @Override
            public void onError(Throwable error) {
                LogUtil.e("requestKingKongArea error:" + error.getMessage() + count);
                mExclusiveShopDataList.clear();
                if (error instanceof TimeoutException) {
                    if (count >= 0) {
                        getExclusiveBreakfast(requestCallBack, count);
                    } else {
                        requestCallBack.onFail(error.getMessage());
                    }
                } else {
                    requestCallBack.onFail(error.getMessage());
                }
            }
        });
    }

    public void requestActivityRegion(RequestCallBack<Object> requestCallBack, int timeOutRequestTime){
        timeOutRequestTime--;
        int count = timeOutRequestTime;
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_MAIN_ACTIVITY_REGION, ApiConstant.DEFAULT_BASE_JSON_INFO, false, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                if (!"".equals(data)) {
                    mActivityRegion = GsonUtil.parserJsonToArrayBeans(data,"sysDecorations",ImageUrlNameData.class);
                    for (ImageUrlNameData imageUrlNameData:mActivityRegion) {
                        imageUrlNameData.getImageUrlNameData().setImgUrl(
                                HttpUtils.changeToHttps(imageUrlNameData.getImageUrlNameData().getImgUrl()));
                    }
                    requestCallBack.onSuccess(mActivityRegion);
                } else {
                    requestCallBack.onSuccess(null);
                }
            }

            @Override
            public void onError(Throwable error) {
                LogUtil.e("requestActivityRegion error:" + error.getMessage() + count);
                mActivityRegion.clear();
                if (error instanceof TimeoutException) {
                    if (count >= 0) {
                        requestActivityRegion(requestCallBack, count);
                    } else {
                        requestCallBack.onFail(error.getMessage());
                    }
                } else {
                    requestCallBack.onFail(error.getMessage());
                }
            }
        });
    }

    public void requestRecommendTitle(RequestCallBack<Object> requestCallBack, int timeOutRequestTime) {
        timeOutRequestTime--;
        int count = timeOutRequestTime;
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_MAIN_RECOMMEND_TITLE, ApiConstant.DEFAULT_BASE_JSON_INFO, false, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                if (!"".equals(data)) {
                    List<SimpleString> simpleStrings = GsonUtil.parserJsonToArrayBeans(data,SimpleString.class);
                    List<String> stringList = new ArrayList<>();
                    for (SimpleString simpleString:simpleStrings) {
                        stringList.add(simpleString.getName());
                    }
                    recommendTitle.addAll(stringList);
                    requestCallBack.onSuccess(simpleStrings);
                } else {
                    requestCallBack.onSuccess(null);
                }
            }

            @Override
            public void onError(Throwable error) {
                LogUtil.e("requestRecommendTitle error:" + error.getMessage() + count);
                recommendTitle.clear();
                if (error instanceof TimeoutException) {
                    if (count >= 0) {
                        requestRecommendTitle(requestCallBack, count);
                    } else {
                        requestCallBack.onFail(error.getMessage());
                    }
                } else {
                    requestCallBack.onFail(error.getMessage());
                }
            }
        });
    }

    public void requestSecondKillTime(NotifyChangeRequestCallBack requestCallBack, WaiMaiReqData.WaiMaiSecondKillReqData reqData, int timeOutRequestTime) {
        timeOutRequestTime--;
        int count = timeOutRequestTime;
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_MAIN_SECOND_KILL, GsonUtil.toJsonString(reqData), false, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                if (!"".equals(data) && !"null".equals(data)) {
                    secondKillTime = GsonUtil.parserJsonToArrayBean(data, SecondKillTime.class);
                    requestCallBack.onSuccess(secondKillTime);
                } else {
                    secondKillTime.setAllTimeDefault();
                    requestCallBack.onSuccess(null);
                }
            }

            @Override
            public void onError(Throwable error) {
                LogUtil.e("requestSecondKillTime error:" + error.getMessage() + count);
                secondKillTime.setAllTimeDefault();
                if (error instanceof TimeoutException) {
                    if (count >= 0) {
                        requestSecondKillTime(requestCallBack, reqData, count);
                    } else {
                        requestCallBack.onFail(error.getMessage());
                    }
                } else {
                    requestCallBack.onFail(error.getMessage());
                }
            }
        });
    }
}
