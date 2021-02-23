package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
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
import com.life.waimaishuo.bean.api.request.bean.RecommendReqData;
import com.life.waimaishuo.databinding.ItemRecyclerWaimaiRecommendGoodsBinding;
import com.life.waimaishuo.databinding.ItemRecyclerWaimaiRecommendShopBinding;
import com.life.waimaishuo.enumtype.SortTypeEnum;
import com.life.waimaishuo.mvvm.view.fragment.BaseRecyclerFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.WaiMaiRecommendedViewModel;
import com.life.waimaishuo.util.MyDataBindingUtil;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.widget.statelayout.StatusLoader;

import java.util.ArrayList;
import java.util.List;

@Page(name = "推荐列表")
public class WaimaiRecommendedFragment extends BaseRecyclerFragment<Shop> {

    private WaiMaiRecommendedViewModel mViewModel;

    private final int PAGE_COUNT = 10;    //一页的显示个数

    private String title = "";
    private int queryType = 0;
    private WaiMaiReqData.WaiMaiRecommendReqData waiMaiRecommendReqData;

    @Override
    protected int getLayoutId() {
        if(queryType == 0){
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
        if(queryType == 0)
            return R.layout.item_recycler_waimai_recommend_shop;
        else
            return R.layout.item_recycler_waimai_recommend_goods;

    }

    @Override
    protected RecyclerView.LayoutManager getRecyclerLayoutManager() {
        if(queryType == 0){
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
        if(queryType == 0){
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
        if(queryType == 0){
            ItemRecyclerWaimaiRecommendShopBinding binding = (ItemRecyclerWaimaiRecommendShopBinding)viewDataBinding;
            String monSalesCount = getString(R.string.sale_count_a_month)+item.getMonSalesVolume();
            String distanceAndTime = getString(R.string.dist_time_and_distance,item.getDistTime()+"",item.getDistance()+"");
            String describe = getString(R.string.price_start_deliver_delivering_avg,item.getMinPrice(),item.getDeliveryPrice(),item.getAvgPrice());
            binding.scoreLayout.tvScore.setText(item.getFavorable_rate());
            binding.tvSaleCount.setText(monSalesCount);
            binding.tvDistanceAndTime.setText(distanceAndTime);
            binding.tvShopDescribe.setText(describe);
            if(binding.recyclerTag.getAdapter() == null){
                List<String> tagList = new ArrayList<>();
                String[] tags = item.getTag_value().split(",");
                for (String tag:tags) {
                    tagList.add(tag);
                }
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
        ((BaseQuickAdapter)mRecyclerView.getAdapter()).setOnItemClickListener(
                (adapter, view, position) -> {
                    openPage(ShopDetailFragment.class);
                });

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

    /**
     * 初始化请求内容
     */
    private void initData(){
        waiMaiRecommendReqData = new WaiMaiReqData.WaiMaiRecommendReqData(new RecommendReqData(
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

    public void setReqData(RecommendReqData reqData) {
        this.waiMaiRecommendReqData.reqData = reqData;
    }

    public void setTitle(String title){
        if(title.equals( MyApplication.getMyApplication().getResources().getStringArray(
                R.array.default_waimai_recommend_titles)[0])){
            queryType = 0;
        }else{
            queryType = 1;
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
        waiMaiRecommendReqData.reqData.setActivityType(activityType);
    }

    public void setScreenData(String minAvgPrice, String maxAvgPrice){
        waiMaiRecommendReqData.reqData.setMinAvgPrice(minAvgPrice);
        waiMaiRecommendReqData.reqData.setMaxAvgPrice(maxAvgPrice);
    }

}
