package com.life.waimaishuo.mvvm.vm.waimai;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableInt;

import com.life.waimaishuo.bean.ExclusiveShopData;
import com.life.waimaishuo.bean.SearchTag;
import com.life.waimaishuo.bean.api.request.WaiMaiReqData;
import com.life.waimaishuo.bean.api.respon.SecondKillTime;
import com.life.waimaishuo.bean.ui.ImageUrlNameData;
import com.life.waimaishuo.bean.ui.LimitedTimeGoodsData;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.WaimaiModel;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.view.fragment.waimai.WaimaiRecommendedFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.xuexiang.citypicker.model.HotCity;

import java.util.ArrayList;
import java.util.List;

public class WaiMaiViewModel extends BaseViewModel {

    public BaseObservable goToSearchObservable = new BaseObservable();

    public ObservableInt bannerUpdateObservable = new ObservableInt();
    public ObservableInt searchTagObservable = new ObservableInt();
    public ObservableInt kingKongAreaObservable = new ObservableInt();
    public ObservableInt exclusiveBreakfastObservable = new ObservableInt();
    public ObservableInt activityRegionObservable = new ObservableInt();
    public ObservableInt recommendTitleObservable = new ObservableInt();
    public ObservableInt secondKillTimeObservable = new ObservableInt();

    private WaimaiRecommendedFragment recommendedFragment;
    private WaimaiModel mModel;


    @Override
    public BaseModel getModel() {
        if (mModel == null) {
            mModel = new WaimaiModel();
        }
        return mModel;
    }

    @Override
    public void initData() {
//        initFoodRecyclerData();
    }

    /**
     * 刷新搜索标签
     */
    public void refreshSearchTag() {
        mModel.requestSearchTag(new BaseModel.NotifyChangeRequestCallBack(searchTagObservable));
    }

    /**
     * 刷新轮播图
     */
    public void refreshBannerItemList() {
        mModel.requestBannerItemList(new BaseModel.NotifyChangeRequestCallBack(bannerUpdateObservable));
    }

    /**
     * 刷新金刚区
     */
    public void refreshKingKongArea() {
        mModel.requestKingKongArea(new BaseModel.NotifyChangeRequestCallBack(kingKongAreaObservable));
    }

    /**
     * 刷新专属早餐
     */
    public void refreshExclusiveBreakfast(){
        mModel.getExclusiveBreakfast(new BaseModel.NotifyChangeRequestCallBack(exclusiveBreakfastObservable));
    }

    /**
     * 刷新活动区
     */
    public void refreshActivityRegion(){
        mModel.requestActivityRegion(new BaseModel.NotifyChangeRequestCallBack(activityRegionObservable));
    }

    /**
     * 刷新推荐tab
     */
    public void refreshRecommendedTitle(){
        mModel.requestRecommendTitle(new BaseModel.NotifyChangeRequestCallBack(recommendTitleObservable));
    }

    /**
     * 秒杀时间
     */
    public void refreshSecondKillTime() {
        mModel.requestSecondKillTime(new BaseModel.NotifyChangeRequestCallBack(secondKillTimeObservable),
                new WaiMaiReqData.WaiMaiSecondKillReqData(5));
    }

    private void initFoodRecyclerData() {
        /*mFoodTypeList.add(new IconStrData(R.mipmap.ic_food_type_health, "健康美食"));
        mFoodTypeList.add(new IconStrData(R.mipmap.ic_food_type_drink, "甜品饮品"));
        mFoodTypeList.add(new IconStrData(R.mipmap.ic_food_type_mark, "超时便利"));
        mFoodTypeList.add(new IconStrData(R.mipmap.ic_food_type_fruit, "蔬菜水果"));
        mFoodTypeList.add(new IconStrData(R.mipmap.ic_food_type_medicine, "送药上门"));

        mFoodTypeList.add(new IconStrData(R.mipmap.ic_food_type_hamburg, "汉堡蔬菜"));
        mFoodTypeList.add(new IconStrData(R.mipmap.ic_food_type_japanese, "日韩料理"));
        mFoodTypeList.add(new IconStrData(R.mipmap.ic_food_type_my_city, "同城零售"));
        mFoodTypeList.add(new IconStrData(R.mipmap.ic_food_type_self_help, "快速自取"));
        mFoodTypeList.add(new IconStrData(R.mipmap.ic_food_type_sala, "沙拉轻食"));

        mFoodTypeList.add(new IconStrData(R.mipmap.ic_food_type_tea, "下午茶"));
        mFoodTypeList.add(new IconStrData(R.mipmap.ic_food_type_tea_shop, "小吃馆"));
        mFoodTypeList.add(new IconStrData(R.mipmap.ic_food_type_local, "地方菜"));
        mFoodTypeList.add(new IconStrData(R.mipmap.ic_food_type_cosmetics, "化妆品"));
        mFoodTypeList.add(new IconStrData(R.mipmap.ic_food_type_all, "全部分类"));*/
    }


    /**
     * 搜索栏点击
     *
     * @param view
     */
    public void onSearchBtClick(View view) {
        goToSearchObservable.notifyChange();
    }

    public SearchTag[] getSearchTag() {
        return mModel.searchTags;
    }

    public List<String> getBannerItemList() {
        return mModel.mBannerItemList;
    }

    public List<ImageUrlNameData> getMyFoodDataList() {
        return mModel.mFoodTypeList;
    }

    public List<ImageUrlNameData> getActivityRegion(){
        return mModel.mActivityRegion;
    }

    public String[] getDefaultTitle() {
        return mModel.defaultRecommendTitle;
    }

    public BaseFragment getRecommendedFragment(String title) {
        /*if(recommendedFragment == null){
            recommendedFragment = new WaimaiRecommendedFragment();
            recommendedFragment.setData(mShopList);
        }
        return recommendedFragment;*/

        WaimaiRecommendedFragment fragment = new WaimaiRecommendedFragment();
        fragment.setTitle(title);
        return fragment;
    }

    public List<ExclusiveShopData> getExclusiveShopData() {
        return mModel.mExclusiveShopDataList;
    }

    public List<String> getChildSignData() {
        return mModel.recommendTitle;
    }

    public List<LimitedTimeGoodsData> getLimitedTimeGoodsData() {
//        mModel.mLimitedTimeGoodsDataList.add(new LimitedTimeGoodsData("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640", "网红人气美谢限时抢购"));
//        mModel.mLimitedTimeGoodsDataList.add(new LimitedTimeGoodsData("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640", "网红人气美谢限时抢购"));
        return mModel.mLimitedTimeGoodsDataList;
    }

    public List<String> getPreferential() {
        return Constant.PREFERENTIAL_TABS;
    }

    public SecondKillTime getSecondKillTime(){
        return mModel.secondKillTime;
    }

    public List<HotCity> getHotCities() {
        List<HotCity> hotCities = new ArrayList<>();
        hotCities.add(new HotCity("北京", "北京", "10101"));
        hotCities.add(new HotCity("上海", "上海", "10102"));
        hotCities.add(new HotCity("广州", "广东", "10120"));
        hotCities.add(new HotCity("深圳", "广东", "10128"));
        hotCities.add(new HotCity("杭州", "浙江", "10121"));
        return hotCities;
    }

    public List<String> getScreenData() {
        List<String> screenData = new ArrayList<>();
        screenData.add("优惠活动");
        screenData.add("优惠活动");
//        screenData.add("商家服务");
//        screenData.add("商家服务");
//        screenData.add("品质");
//        screenData.add("品质");
        screenData.add("人均价格带");
        screenData.add("人均价格带");
        return screenData;
    }

}
