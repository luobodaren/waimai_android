package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.GsonUtil;
import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.Global;
import com.life.waimaishuo.MyApplication;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.ImageViewAdapter;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.adapter.statelayout.CustomSingleViewAdapter;
import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.bean.api.request.WaiMaiReqData;
import com.life.waimaishuo.bean.api.request.bean.RecommendReqBean;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.databinding.ItemRecyclerWaimaiRecommendGoodsBinding;
import com.life.waimaishuo.databinding.ItemRecyclerWaimaiRecommendShopBinding;
import com.life.waimaishuo.enumtype.SortTypeEnum;
import com.life.waimaishuo.mvvm.view.fragment.BaseRecyclerFragment;
import com.life.waimaishuo.mvvm.view.fragment.LoginFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.WaiMaiRecommendedViewModel;
import com.life.waimaishuo.util.MyDataBindingUtil;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.widget.statelayout.StatusLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Page(name = "推荐列表")
public class WaimaiRecommendedFragment extends BaseRecyclerFragment<Shop> {

    public static final String KEY_OPEN_SHOP_ID = "bundle_key_open_shop_id";

    public static final int QUERY_TYPE_SHOP = 1;
    public static final int QUERY_TYPE_GOODS = 2;
    public static final int QUERY_TYPE_ZERO_DELIVER = 3;

    private WaiMaiRecommendedViewModel mViewModel;

    /**
     * 执行openPageForResult 需要携带的数据或标识（供页面返回时进行处理）
     * 保存点击店铺的店铺Id,登录成功后可进行页面跳转
     */
    private final Map<Integer,Bundle> bundleMap = new HashMap<>();

    private final int PAGE_COUNT = 10;    //推荐内容一页的显示个数

    private String title = "";
    private int queryType = QUERY_TYPE_SHOP;
    private WaiMaiReqData.WaiMaiRecommendReqData waiMaiRecommendReqData;

    private int customItemLayoutId = 0;

    @Override
    protected int getLayoutId() {
        if(queryType == QUERY_TYPE_SHOP || queryType == QUERY_TYPE_ZERO_DELIVER){
            return super.getLayoutId();
        }
        return R.layout.fragment_base_recycler_grid_16px;
    }

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new WaiMaiRecommendedViewModel();
        }
        return mViewModel;
    }

    /**
     * 由于该fragment是用在
     * @param inflater
     * @param container
     * @return
     */
    @Override
    protected View inflateView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(getLayoutId(),null);
        mViewDataBinding = DataBindingUtil.bind(view);
        baseViewModel = setViewModel();
        bindViewModel();
        return view;
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();
        showLoading();

        initData();
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_DARK);
    }

    @Override
    protected int getItemLayoutId() {
        if(customItemLayoutId != 0)
            return customItemLayoutId;
        if(queryType == QUERY_TYPE_SHOP || queryType == QUERY_TYPE_ZERO_DELIVER)
            return R.layout.item_recycler_waimai_recommend_shop;
        else
            return R.layout.item_recycler_waimai_recommend_goods;

    }

    @Override
    protected RecyclerView.LayoutManager getRecyclerLayoutManager() {
        if(queryType == QUERY_TYPE_SHOP || queryType == QUERY_TYPE_ZERO_DELIVER){
            return new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        }else{  // FIXME: 2021/2/5 修改为瀑布流布局
            return new GridLayoutManager(requireContext(),2,LinearLayoutManager.VERTICAL,false);
        }
    }

    @Override
    protected List<Shop> getListData() {
        return mViewModel.getListData(queryType);
    }

    @Override
    protected int getVariableId() {
        return com.life.waimaishuo.BR.item;  // FIXME: 2020/12/28 后续修改成正确的值
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
        if(queryType == QUERY_TYPE_SHOP || queryType == QUERY_TYPE_ZERO_DELIVER){
            return new RecyclerView.ItemDecoration() {
                int top_interval =(int)UIUtils.getInstance().scalePx(
                        getContext().getResources().getDimensionPixelOffset(R.dimen.interval_size_xs));
                @Override
                public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    int position = parent.getChildAdapterPosition(view);
                    if(position != 0) {
                        outRect.top = top_interval;
                    }
                    if(position == state.getItemCount()-1){   //最后一项
                        outRect.bottom = top_interval;
                    }
                }
            };
        }else{
            return null;
        }
    }

    @Override
    protected void onRecyclerBindViewHolder(ViewDataBinding viewDataBinding, BaseViewHolder helper, Shop item) {
        if(queryType == QUERY_TYPE_SHOP || queryType == QUERY_TYPE_ZERO_DELIVER){
            ItemRecyclerWaimaiRecommendShopBinding binding = (ItemRecyclerWaimaiRecommendShopBinding)viewDataBinding;
            String monSalesCount = getString(R.string.sale_count_a_month)+item.getMonSalesVolume();
            String distanceAndTime = getString(R.string.dist_time_and_distance,item.getDistTime()+"",item.getDistance()+"");
            String describe = getString(R.string.price_start_deliver_delivering_avg,item.getMinPrice(),item.getDeliveryPrice(),item.getAvgPrice());
            binding.scoreLayout.tvScore.setText(item.getFavorable_rate());
            binding.tvSaleCount.setText(monSalesCount);
            binding.tvDistanceAndTime.setText(distanceAndTime);
            binding.tvShopDescribe.setText(describe);
            if(binding.recyclerTag.getAdapter() == null){
                String[] tags = item.getTag_value().split(",");
                List<String> tagList = new ArrayList<>(Arrays.asList(tags));
                binding.recyclerTag.setLayoutManager(new LinearLayoutManager(helper.itemView.getContext(),LinearLayoutManager.HORIZONTAL,false));
                binding.recyclerTag.setAdapter(new MyBaseRecyclerAdapter<String>(R.layout.adapter_tag_item_cash_back,tagList){
                    @Override
                    protected void initView(ViewDataBinding viewDataBinding, BaseViewHolder helper, String item) {
                        helper.setText(R.id.tv_tag,item);
                    }
                });
            }
        }else{
            ItemRecyclerWaimaiRecommendGoodsBinding binding = (ItemRecyclerWaimaiRecommendGoodsBinding) viewDataBinding;
            Goods goods = item.getGoodsInfoList().get(0);
            if(goods != null){
                String monthSale = getString(R.string.sale_count_a_month) + goods.getMonSalesVolume();
                ImageViewAdapter.setBitSrc(binding.ivGoodsImg,goods.getGoodsImgUrl());
                binding.tvGoodsName.setText(goods.getName());
                binding.tvGoodsPrice.setText(goods.getPrice());
                binding.tvCountOfPay.setText(monthSale);
            }

        }

    }

    @Override
    protected StatusLoader.Adapter getStatusLoaderAdapter() {
        return new CustomSingleViewAdapter();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        if(recyclerAdapter != null){
            recyclerAdapter.setOnItemClickListener(
                    (adapter, view, position) -> {
                        int shopId = recyclerAdapter.getData().get(position).getShopId();
                        if(!Global.isLogin){
                            Bundle bundle = new Bundle();
                            bundle.putInt(KEY_OPEN_SHOP_ID,shopId);
                            bundleMap.put(Constant.REQUEST_CODE_LOGIN,bundle);
                            openPageForResult(LoginFragment.class,bundle,Constant.REQUEST_CODE_LOGIN);
                        }else{
                            ShopDetailFragment.openPage(this,shopId);
                        }
                    });
        }
        MyDataBindingUtil.addCallBack(this, mViewModel.onRequestListObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.post(() -> {
                    if("-1".equals(mViewModel.onRequestListObservable.get())){
                        showError();
                    }else{
                        List<Shop> shopList = mViewModel.getListData(queryType);
                        if(shopList.size() > 0){
                            showContent();
                            recyclerAdapter.getData().clear();
                            recyclerAdapter.getData().addAll(shopList);
                            recyclerAdapter.notifyDataSetChanged();
                        }else{
                            showEmpty();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onLifecycleResume() {
        super.onLifecycleResume();
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if(requestCode == Constant.REQUEST_CODE_LOGIN){
            if(resultCode == Constant.RESULT_CODE_SUCCESS){
                //跳转外卖店铺界面
                if(bundleMap.containsKey(Constant.REQUEST_CODE_LOGIN)){
                    int shopId = bundleMap.get(Constant.REQUEST_CODE_LOGIN).getInt(KEY_OPEN_SHOP_ID,-1);
                    if(shopId != -1){
                        mHandler.post(() -> ShopDetailFragment.openPage(WaimaiRecommendedFragment.this,shopId));
                    }else{
                        //do nothing
                    }
                }

            }
        }
    }

    /**
     * 初始化请求内容
     */
    private void initData(){
        waiMaiRecommendReqData = new WaiMaiReqData.WaiMaiRecommendReqData(new RecommendReqBean(
                Global.LocationProvince,Global.LocationCity,Global.LocationDistrict,
                Global.userLonAndLat,
                1,PAGE_COUNT,queryType));
    }

    /**
     * 刷新数据
     */
    public void refreshListDate(){
        if(waiMaiRecommendReqData.reqData != null){
            LogUtil.d("refreshListDate" + GsonUtil.toJsonString(waiMaiRecommendReqData));
            mViewModel.refreshListData(waiMaiRecommendReqData);
        }
    }

    public void setCustomItemLayoutId(@LayoutRes int customItemLayoutId){
        this.customItemLayoutId = customItemLayoutId;
    }

    public void setReqData(RecommendReqBean reqData) {
        this.waiMaiRecommendReqData.reqData = reqData;
    }

    /**
     * 设置title 并且确定页面的搜索类型
     * @param title
     */
    public void setTitle(String title){
        if(title.equals( MyApplication.getMyApplication().getResources().getStringArray(
                R.array.default_waimai_recommend_titles)[0])){
            queryType = QUERY_TYPE_SHOP;
        }else if(title.equals( MyApplication.getMyApplication().getResources().getStringArray(
                R.array.default_waimai_recommend_titles)[1])){
            queryType = QUERY_TYPE_GOODS;
        }else{
            queryType = QUERY_TYPE_ZERO_DELIVER;
        }
        this.title = title;
        if(waiMaiRecommendReqData != null && waiMaiRecommendReqData.reqData != null){
            if(waiMaiRecommendReqData.reqData != null){
                waiMaiRecommendReqData.reqData.setQueryType(queryType);
            }
        }
    }

    public void setShopCategory(String sign){
        if(waiMaiRecommendReqData != null && waiMaiRecommendReqData.reqData != null){
            waiMaiRecommendReqData.reqData.setShopCategory(sign);
        }
    }

    /**
     * 设置排序功能 默认是按综合排序
     * @param sortTypeEnum
     */
    public void setSortRules(SortTypeEnum sortTypeEnum){
        int sortRules = 5;
        switch (sortTypeEnum){
            case SCORE:
                sortRules = 5;
                break;
            case STARTING_SHIPPING_PRICE_LOWEST:
                sortRules = 1;
                break;
            case DELIVERY_FASTEST:
                sortRules = 2;
                break;
            case DELIVERY_PRICE_LOWEST:
                sortRules = 3;
                break;
            case PER_CAPITA_PRICE_LOWEST:
                sortRules = 6;
                break;
            case PER_CAPITA_PRICE_HIGHEST:
                sortRules = 7;
                break;
            case DISTANCE:
                sortRules = 4;
                break;
            case SALES:
                sortRules = 0;
                break;
        }
        waiMaiRecommendReqData.reqData.setSortRules(sortRules);
    }

    /**
     * 设置活动满减
     * @param activityType
     */
    public void setActivityType(String[] activityType){
        //解析优惠活动标签
        List<String> activityTypeList = new ArrayList<>();
        if (activityType.length > 0) {
            LogUtil.d(activityType[0]);
            for (String s : activityType) {
                if (Constant.PREFERENTIAL_TABS.contains(s)) {
                    activityTypeList.add(
                            Constant.PREFERENTIAL_TABS_INDEX.get(Constant.PREFERENTIAL_TABS.indexOf(s)));
                }
            }
        }

        waiMaiRecommendReqData.reqData.setActivityType(activityTypeList.toArray(activityType));
    }

    public void setScreenData(String minAvgPrice, String maxAvgPrice){
        waiMaiRecommendReqData.reqData.setMinAvgPrice(minAvgPrice);
        waiMaiRecommendReqData.reqData.setMaxAvgPrice(maxAvgPrice);
    }

}
