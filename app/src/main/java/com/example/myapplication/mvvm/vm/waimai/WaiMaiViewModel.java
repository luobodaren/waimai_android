package com.example.myapplication.mvvm.vm.waimai;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableInt;

import com.example.myapplication.R;
import com.example.myapplication.bean.ExclusiveShopData;
import com.example.myapplication.bean.SearchRecord;
import com.example.myapplication.bean.Shop;
import com.example.myapplication.bean.ui.IconStrData;
import com.example.myapplication.bean.ui.LimitedTimeGoodsData;
import com.example.myapplication.mvvm.model.BaseModel;
import com.example.myapplication.mvvm.model.waimai.WaimaiModel;
import com.example.myapplication.mvvm.view.fragment.BaseFragment;
import com.example.myapplication.mvvm.view.fragment.waimai.RecommendedFragment;
import com.example.myapplication.mvvm.vm.BaseRecyclerViewModel;
import com.example.myapplication.mvvm.vm.BaseViewModel;
import com.xuexiang.xui.widget.banner.widget.banner.BannerItem;

import java.util.ArrayList;
import java.util.List;

public class WaiMaiViewModel extends BaseViewModel {

    public BaseObservable goToSearch = new ObservableInt();
    public BaseObservable goToMessage = new ObservableInt();

    String mLocation;
    private RecommendedFragment recommendedFragment;

    private List<Shop> mShopList = new ArrayList<>();
    private List<IconStrData> mFoodTypeList = new ArrayList<>();
    private List<BannerItem> mBannerItemList = new ArrayList<>();
    private List<ExclusiveShopData> mExclusiveShopDataList = new ArrayList<>();
    private List<LimitedTimeGoodsData> mLimitedTimeGoodsDataList = new ArrayList<>();


    @Override
    public BaseModel getModel() {
        if(mMode == null){
            mMode = new WaimaiModel();
        }
        return mMode;
    }

    @Override
    public void initData() {
        initBannerList();
        initFoodRecyclerData();
    }

    /**
     * 搜索栏点击
     * @param view
     */
    public void onSearchBtClick(View view) {
        goToSearch.notifyChange();
    }

    /**
     * 消息点击
     * @param view
     */
    public void onMessageIvClick(View view){
        goToMessage.notifyChange();
    }

    public List<BannerItem> getBannerItemList() {
        return mBannerItemList;
    }

    public List<IconStrData> getMyFoodDataList() {
        return mFoodTypeList;
    }

    public BaseFragment getRecommendedFragment() {
        /*if(recommendedFragment == null){
            recommendedFragment = new RecommendedFragment();
            recommendedFragment.setData(mShopList);
        }
        return recommendedFragment;*/

        List<Shop> list = new ArrayList<>();
        list.add(new Shop("嘉禾一品粥(国展店)"));
        list.add(new Shop("嘉禾一品粥(国展店)"));
        list.add(new Shop("嘉禾一品粥(国展店)"));
        list.add(new Shop("嘉禾一品粥(国展店)"));
        list.add(new Shop("嘉禾一品粥(国展店)"));
        list.add(new Shop("嘉禾一品粥(国展店)"));
        list.add(new Shop("嘉禾一品粥(国展店)"));

        RecommendedFragment fragment = new RecommendedFragment();
        fragment.setData(list);
        return fragment;
    }

    public SearchRecord[] getSearchRecord() {
        return new SearchRecord[]{
                new SearchRecord(1L,"外卖", System.currentTimeMillis())
                ,new SearchRecord(2L,"秒杀吧", System.currentTimeMillis())
                ,new SearchRecord(3L,"饮料", System.currentTimeMillis())
                ,new SearchRecord(1L,"外卖", System.currentTimeMillis())
                ,new SearchRecord(2L,"秒杀吧", System.currentTimeMillis())
                ,new SearchRecord(3L,"饮料", System.currentTimeMillis())
                ,new SearchRecord(1L,"外卖", System.currentTimeMillis())
                ,new SearchRecord(2L,"秒杀吧", System.currentTimeMillis())
                ,new SearchRecord(3L,"饮料", System.currentTimeMillis())
                ,new SearchRecord(4L,"免费配送", System.currentTimeMillis())};
    }

    public String[] getRecommendedTitle() {
        return new String[]{"推荐商家", "发现好物", "饮品","美妆","超市","测试1","测试2","测试3"};
    }

    private void initBannerList(){
        BannerItem bannerItem1 = new BannerItem();
        bannerItem1.setImgUrl("https://chinese.aljazeera.net/wp-content/uploads/2020/02/8bbea808-13f9-4466-b2a5-2a8207989cf6.jpeg");
        BannerItem bannerItem2 = new BannerItem();
        bannerItem2.setImgUrl("http://5b0988e595225.cdn.sohucs.com/images/20190130/314cbd6c9c5c496297e45f518ea0aee1.jpeg");
        BannerItem bannerItem3 = new BannerItem();
        bannerItem3.setImgUrl("https://pimage.cqcb.com/d/file/wealth/2019-12-26/0b069da3298226a21a2d553ad6a89c80.jpg");
        mBannerItemList.add(bannerItem1);
        mBannerItemList.add(bannerItem2);
        mBannerItemList.add(bannerItem3);
    }

    private void initFoodRecyclerData(){
        mFoodTypeList.add(new IconStrData(R.mipmap.ic_food_health,"健康美食"));
        mFoodTypeList.add(new IconStrData(R.mipmap.ic_food_drink,"甜品饮品"));
        mFoodTypeList.add(new IconStrData(R.mipmap.ic_food_mark,"超时便利"));
        mFoodTypeList.add(new IconStrData(R.mipmap.ic_food_fruit,"蔬菜水果"));
        mFoodTypeList.add(new IconStrData(R.mipmap.ic_food_medicine,"送药上门"));

        mFoodTypeList.add(new IconStrData(R.mipmap.ic_food_hamburg,"汉堡蔬菜"));
        mFoodTypeList.add(new IconStrData(R.mipmap.ic_food_japanese,"日韩料理"));
        mFoodTypeList.add(new IconStrData(R.mipmap.ic_food_my_city,"同城零售"));
        mFoodTypeList.add(new IconStrData(R.mipmap.ic_food_self_help,"快速自取"));
        mFoodTypeList.add(new IconStrData(R.mipmap.ic_food_sala,"沙拉轻食"));

        mFoodTypeList.add(new IconStrData(R.mipmap.ic_food_tea,"下午茶"));
        mFoodTypeList.add(new IconStrData(R.mipmap.ic_food_tea_shop,"小吃馆"));
        mFoodTypeList.add(new IconStrData(R.mipmap.ic_food_local,"地方菜"));
        mFoodTypeList.add(new IconStrData(R.mipmap.ic_food_cosmetics,"化妆品"));
        mFoodTypeList.add(new IconStrData(R.mipmap.ic_food_all_type,"全部分类"));
    }

    public List getExclusiveShopData() {
        mExclusiveShopDataList.add(new ExclusiveShopData(
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2424071932,228392684&fm=15&gp=0.jpg",
                "星巴克代购",
                "最近有一万人下单了呢"));
        mExclusiveShopDataList.add(new ExclusiveShopData(
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2424071932,228392684&fm=15&gp=0.jpg",
                "星巴克代购",
                "最近有一万人下单了呢"));
        mExclusiveShopDataList.add(new ExclusiveShopData(
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2424071932,228392684&fm=15&gp=0.jpg",
                "星巴克代购",
                "最近有一万人下单了呢"));
        mExclusiveShopDataList.add(new ExclusiveShopData(
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2424071932,228392684&fm=15&gp=0.jpg",
                "星巴克代购",
                "最近有一万人下单了呢"));
        return mExclusiveShopDataList;
    }

    public List<LimitedTimeGoodsData> getLimitedTimeGoodsData() {
        mLimitedTimeGoodsDataList.add(new LimitedTimeGoodsData("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2424071932,228392684&fm=15&gp=0.jpg","网红人气美谢限时抢购"));
        mLimitedTimeGoodsDataList.add(new LimitedTimeGoodsData("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2424071932,228392684&fm=15&gp=0.jpg","网红人气美谢限时抢购"));
        return mLimitedTimeGoodsDataList;
    }
}
