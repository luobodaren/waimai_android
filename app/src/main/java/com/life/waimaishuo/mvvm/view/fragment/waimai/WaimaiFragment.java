package com.life.waimaishuo.mvvm.view.fragment.waimai;


import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.BR;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.BaseBannerAdapter;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.adapter.statelayout.CustomSingleViewAdapter;
import com.life.waimaishuo.adapter.tag.SearchRecordTagWaimaiAdapter;
import com.life.waimaishuo.bean.ExclusiveShopData;
import com.life.waimaishuo.bean.SearchTag;
import com.life.waimaishuo.bean.ui.ImageUrlNameData;
import com.life.waimaishuo.databinding.FragmentWaimaiBinding;
import com.life.waimaishuo.databinding.ItemRecyclerWaimaiFoodTypeBinding;
import com.life.waimaishuo.enumtype.SortTypeEnum;
import com.life.waimaishuo.mvvm.view.activity.SearchActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.view.fragment.BaseStatusLoaderFragment;
import com.life.waimaishuo.mvvm.view.fragment.MessageFragment;
import com.life.waimaishuo.mvvm.view.fragment.city.CityPickerDialogFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.WaiMaiViewModel;
import com.life.waimaishuo.util.MyDataBindingUtil;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.util.Utils;
import com.life.waimaishuo.views.MyTabSegmentTab;
import com.life.waimaishuo.views.SortTypeView;
import com.life.waimaishuo.views.StickyNavigationLayout;
import com.xuexiang.citypicker.adapter.OnLocationListener;
import com.xuexiang.citypicker.adapter.OnPickListener;
import com.xuexiang.citypicker.model.City;
import com.xuexiang.citypicker.model.LocateState;
import com.xuexiang.citypicker.model.LocatedCity;
import com.xuexiang.xaop.annotation.Permission;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.statelayout.StatusLoader;
import com.xuexiang.xui.widget.tabbar.TabSegment;

import java.lang.ref.WeakReference;
import java.util.List;

import static android.Manifest.permission_group.LOCATION;

@Page(name = "外卖", anim = CoreAnim.fade)
public class WaimaiFragment extends BaseStatusLoaderFragment {

    private FragmentWaimaiBinding binding;
    private WaiMaiViewModel mViewModel;

    private WeakReference<FragmentManager> mFragmentManager;
    private LocatedCity mLocatedCity = null;

    //适配器-轮播图
    private BaseBannerAdapter baseBannerAdapter;
    //适配器-搜索标签
    private SearchRecordTagWaimaiAdapter searchTagAdapter;
    //适配器-金刚区
    private BaseRecyclerAdapter<ImageUrlNameData> kingKongAreaAdapter;
    //适配器-专属早餐
    private MyBaseRecyclerAdapter<ExclusiveShopData> exclusiveShopAdapter;

    //适配器-ViewPager
    private FragmentAdapter<BaseFragment> viewPagerAdapter;
    /**
     * 第一次加载数据
     */
    private boolean firstRefreshData = true;

    @Override
    protected BaseViewModel setViewModel() {
        mViewModel = new WaiMaiViewModel();
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        binding = ((FragmentWaimaiBinding)mViewDataBinding);
        binding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_waimai;
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        addCallBack();

        addSortViewClickListener();
        binding.viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                refreshTabAndViewPager(binding.stickyView,mViewModel.getRecommendedTitle(),position,false);
                refreshSortType(SortTypeEnum.SCORE);
                // TODO: 2020/12/3 刷新内容

            }
        });

        binding.layoutTitle.llLocal.setOnClickListener(v -> pickCity());

        binding.layoutTitle.ivMessage.setOnClickListener(v -> openPage(MessageFragment.class));

//        binding.myLlContentView.setOnScrollChangeListener(moveRatio -> {
//            if(moveRatio == 1){
//                if(!isHideStatusBar()){
//                    setStatusBarShowByType(HIDE_STATUS_BAR);
//                }
//            } else {
//                if(!isShowStatusBar()){
//                    setStatusBarShowByType(SHOW_STATUS_BAR);
//                }
//            }
//        });
    }


    @Override
    protected void initArgs() {
        super.initArgs();
        setFitStatusBarHeight(true);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_DARK);
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();

        initMyLocation();

        initBanner();
        initSearchView();
        initFoodTypeRecycler();

        binding.stickyNavigationLayout.setCustomCanScrollDistance(UIUtils.getInstance().scalePx(700));
        initMyExclusiveRecycler();
        initActivityRegion();
        initNavigationTab();

        showLoading();
    }


    @Override
    protected View getWrapView() {
        return binding.flState;
    }

    @Override
    protected StatusLoader.Adapter getStatusLoaderAdapter() {
        return new CustomSingleViewAdapter();
    }

    @Override
    protected void onLifecycleResume() {
        super.onLifecycleResume();

        if(firstRefreshData){
            firstRefreshData = false;
            //请求数据
            mViewModel.refreshSearchTag();
            mViewModel.refreshBannerItemList();
            mViewModel.refreshKingKongArea();
            mViewModel.refreshExclusiveBreakfast();
            mViewModel.refreshActivityRegion();
            mViewModel.refreshRecommendedTitle();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MyDataBindingUtil.removeFragmentCallBack(this);
    }

    private void initMyLocation() {
        binding.layoutTitle.tvLocation.setText(R.string.location_unknow);
    }

    private void addCallBack() {
        MyDataBindingUtil.addCallBack(this, mViewModel.goToSearchObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                LogUtil.d("跳转搜索页");
//                openNewPage(SearchHistoryFragment.class,SearchActivity.class);
                startActivity(new Intent(getContext(), SearchActivity.class));  // FIXME: 2020/11/30
//                startActivity();
            }
        });

        //-----------------  以下为网络请求回调 ----------------//
        MyDataBindingUtil.addCallBack(this, mViewModel.bannerUpdateObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.post(() -> {
                    baseBannerAdapter
                            = new BaseBannerAdapter(mViewModel.getBannerItemList(),R.layout.adapter_banner_image_item_waimai);
                    binding.bannerLayout.setAdapter(baseBannerAdapter);
                    binding.stickyNavigationLayout.setNeedResetCanScrollDistance(true);
                });
            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.searchTagObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.post(() -> {
                    searchTagAdapter.refresh(mViewModel.getSearchTag());
                    binding.stickyNavigationLayout.setNeedResetCanScrollDistance(true);
                });
            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.kingKongAreaObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.post(() -> {
                    kingKongAreaAdapter.getData().clear();
                    if(mViewModel.getMyFoodDataList().size() > 0){
                        setViewVisibility(binding.recyclerFoodType,true);
                        kingKongAreaAdapter.getData().addAll(mViewModel.getMyFoodDataList());
                        kingKongAreaAdapter.notifyDataSetChanged();
                    }else{
                        setViewVisibility(binding.recyclerFoodType,false);
                    }
                    binding.stickyNavigationLayout.setNeedResetCanScrollDistance(true);
                });
            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.exclusiveBreakfastObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.post(() -> {
                    if(mViewModel.getExclusiveShopData().size() <= 0){
                        setViewVisibility(binding.recyclerMyExclusive,false);
                    }else{
                        setViewVisibility(binding.recyclerMyExclusive,true);
                    }
                });
            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.activityRegionObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.post(()-> setActivityRegionData(mViewModel.getActivityRegion()));
            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.recommendTitleObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.post(()->{
                    //int scrollY = binding.stickyNavigationLayout.getScrollY();
                    setViewVisibility(binding.stickyView,true);
                    setViewVisibility(binding.contentLayout,true);
                    binding.stickyNavigationLayout.setNeedResetCanScrollDistance(true);
                    binding.stickyNavigationLayout.setCustomCanScrollDistance(StickyNavigationLayout.CAN_SCROLL_DISTANCE_ADJUST_TOP_VIEW);
                    binding.stickyNavigationLayout.scrollTo(0, 0);  // FIXME: 2021/2/1 优化显示动画 滚动过快 高度出错
                    showContent();

                    refreshTabAndViewPager(binding.stickyView,mViewModel.getRecommendedTitle(),0,true);
                });
            }
        });
    }

    /**
     * 初始化历史搜索View
     */
    private void initSearchView(){
        SearchTag[] searchTag = mViewModel.getSearchTag();
        binding.searchRecord.setLayoutManager(Utils.getFlexboxLayoutManager(getContext()));
        binding.searchRecord.setAdapter(searchTagAdapter = new SearchRecordTagWaimaiAdapter());
        searchTagAdapter.refresh(searchTag);
    }

    /**
     * 初始化轮播图
     */
    private void initBanner(){
        //String[] url = {"https://cjwm-pic.oss-cn-beijing.aliyuncs.com/wms2fa52615-d6e9-d5ff-88e4-f7281e29af31.jpg"};
        baseBannerAdapter
                = new BaseBannerAdapter(mViewModel.getBannerItemList(),R.layout.adapter_banner_image_item_waimai);
        baseBannerAdapter.setOnBannerItemClickListener(position ->
                Toast.makeText(getContext(),"点击了轮播图：" + position,Toast.LENGTH_SHORT).show());
        binding.bannerLayout.setAdapter(baseBannerAdapter);
        binding.bannerLayout.setItemSpace((int) UIUtils.getInstance().scalePx(
                getResources().getDimensionPixelSize(R.dimen.interval_size_xs)));
    }

    /**
     * 初始化食物类型Recycler
     */
    private void initFoodTypeRecycler(){
        int spanCount = 5;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),spanCount
                , LinearLayoutManager.VERTICAL,false);
        kingKongAreaAdapter = getFoodRecyclerAdapter();
        kingKongAreaAdapter.setOnItemClickListener((itemView, item, position) -> {
            if(position == kingKongAreaAdapter.getItemCount()-1){
                openPage(WaimaiAllTypeFragment.class);  //最后一个为全部类型
            }else{  //打开子类型
                Bundle bundle = new Bundle();
                bundle.putString(WaimaiTypeFragment.BUNDLE_FOOD_TYPE_STR_KEY,item.getName());
                openPage(WaimaiTypeFragment.class,bundle);
            }
        });
        binding.recyclerFoodType.setAdapter(kingKongAreaAdapter);
        binding.recyclerFoodType.setLayoutManager(gridLayoutManager);
        binding.recyclerFoodType.addItemDecoration(new RecyclerView.ItemDecoration() {
            int top_interval_40 = -1;
            int top_interval_32 = -1;
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                if(top_interval_40 == -1)
                    top_interval_40 = (int)UIUtils.getInstance().scalePx(40);
                if(top_interval_32 == -1)
                    top_interval_32 = (int)UIUtils.getInstance().scalePx(32);
                outRect.top = position < 5 ? 0 : (position < 10 ? top_interval_40 : top_interval_32);
            }
        });
    }

    /**
     * 初始化专属recycler
     */
    private void initMyExclusiveRecycler() {
        exclusiveShopAdapter = getExclusiveRecyclerAdapter();
        exclusiveShopAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                openPage(ExclusiveBreakfastFragment.class);
            }
        });

        View view = View.inflate(getContext(),R.layout.head_exclusive_recycler,null);
        ((TextView)view.findViewById(R.id.text_left)).setText(R.string.exclusive_breakfast);
        ((TextView)view.findViewById(R.id.tv_right)).setText(R.string.more_shop);
        ((ImageView)view.findViewById(R.id.iv_right)).setImageResource(R.drawable.ic_arrow_right_gray);
        exclusiveShopAdapter.addHeaderView(view);

        int spanCount = 2;
        binding.recyclerMyExclusive.setAdapter(exclusiveShopAdapter);
        binding.recyclerMyExclusive.setLayoutManager(
                Utils.getGridLayoutManagerWithHead(getContext(),spanCount));
        binding.recyclerMyExclusive.addItemDecoration(new RecyclerView.ItemDecoration(){
            int top_interval = -1;
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                if(top_interval == -1)
                    top_interval = (int)UIUtils.getInstance().scalePx(40);
                if(position > 0){
                    outRect.top = top_interval;
//                    if((position - kingKongAreaAdapter.getHeaderLayoutCount()) % 2 == 1){
//                        outRect.left
//                    }
                }
            }
        });
    }

    /**
     * 初始化活动专区
     */
    private void initActivityRegion(){
        setActivityRegionData(mViewModel.getActivityRegion());
    }

    private int space;
    private int textSizeSelected;
    private int textSizeNormal;
    /**
     * 初始化粘性导航栏
     */
    private void initNavigationTab() {

        space = getResources().getDimensionPixelSize(R.dimen.waimai_tabbar_item_space);
        textSizeSelected = getResources().getDimensionPixelSize(R.dimen.waimai_tabbar_item_text_size_selected);
        textSizeNormal = getResources().getDimensionPixelSize(R.dimen.waimai_tabbar_item_text_size);
        String[] titles = mViewModel.getRecommendedTitle();

        viewPagerAdapter = new FragmentAdapter<>(getChildFragmentManager());

        addTab(binding.stickyView,viewPagerAdapter,titles,textSizeSelected,textSizeNormal,0);
        binding.stickyView.setHasIndicator(false);
        binding.stickyView.setMode(TabSegment.MODE_SCROLLABLE);
        binding.stickyView.setItemSpaceInScrollMode(space);
        binding.stickyView.setDefaultNormalColor(getResources().getColor(R.color.text_tip));
        binding.stickyView.setDefaultSelectedColor(getResources().getColor(R.color.text_normal));
//        binding.stickyView.setTabTextSize(textSizeNormal);
        binding.stickyView.setupWithViewPager(binding.viewPager,false);

        binding.viewPager.setOffscreenPageLimit(titles.length - 1);
        binding.viewPager.setAdapter(viewPagerAdapter);
    }

    private void setActivityRegionData(List<ImageUrlNameData> imageUrlNameDataList){
        if(imageUrlNameDataList.size() <= 0){
            LogUtil.d("没有活动专区数据");
            setViewVisibility(binding.layoutActivityRegion.clActivityLayout,false);
            return;
        }else{
            setViewVisibility(binding.layoutActivityRegion.clActivityLayout,true);
        }
        int size = imageUrlNameDataList.size();
        ImageUrlNameData temp;
        for (int index = 0; index < size; index++) {
            if(index == 0){
                temp = imageUrlNameDataList.get(index);
                Glide.with(requireContext())
                        .load(temp.getImageUrlNameData().getImgUrl())
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .into(binding.layoutActivityRegion.ivBrandZone);
                binding.layoutActivityRegion.tvBrandZoneGoodsName.setText(temp.getImageUrlNameData().getDescribe());
            }else if(index == 1){
                temp = imageUrlNameDataList.get(index);
                LogUtil.d("setActivityRegionData " + temp.getImageUrlNameData().getImgUrl());
                Glide.with(requireContext())
                        .load(temp.getImageUrlNameData().getImgUrl())
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .into(binding.layoutActivityRegion.ivLimitKill);
                binding.layoutActivityRegion.tvLimitedKillGoodsName.setText(temp.getImageUrlNameData().getDescribe());
            }else if(index == 2){
                temp = imageUrlNameDataList.get(index);
                Glide.with(requireContext())
                        .load(temp.getImageUrlNameData().getImgUrl())
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .into(binding.layoutActivityRegion.ivZeroDeliver);
                binding.layoutActivityRegion.tvZeroDeliverGoodsName.setText(temp.getImageUrlNameData().getDescribe());
            }
        }

    }

    private void addSortViewClickListener() {
        binding.contentLayout.setPreferentialTab(mViewModel.getPreferential());
        binding.contentLayout.setScreenData(mViewModel.getScreenData());
        binding.contentLayout.setOnSortTypeChangeListener(new SortTypeView.onSortTypeChangeListener() {
            @Override
            public void onSortPopShow() {

            }

            @Override
            public void onSortTypeChange(SortTypeEnum sortTypeEnum) {

            }

            @Override
            public void onPreferentialChange(int selectedPosition) {

            }


        });
    }

    private void refreshSortType(SortTypeEnum sortType){
        binding.contentLayout.setSortType(sortType);
    }
    @Permission(LOCATION)
    private void pickCity() {
        /*CityPicker.from(this)
                .enableAnimation(true)
                .setAnimationStyle(R.style.CityPickerAnimation)
                .setLocatedCity(locatedCity)
                .setHotCities(mViewModel.getHotCities())
                .setOnPickListener()
        .show();*/
        showCityPickerDialog();
    }

    private void showCityPickerDialog(){
        if(mFragmentManager == null){
            mFragmentManager = new WeakReference<>(this.getChildFragmentManager());
        }
        FragmentTransaction ft = mFragmentManager.get().beginTransaction();
        final Fragment prev = mFragmentManager.get().findFragmentByTag("CityPicker");
        if (prev != null) {
            ft.remove(prev).commit();
            ft = mFragmentManager.get().beginTransaction();
        }
        ft.addToBackStack(null);

        final CityPickerDialogFragment cityPickerFragment =
                CityPickerDialogFragment.newInstance(true);
        cityPickerFragment.setLocatedCity(mLocatedCity);
        cityPickerFragment.setHotCities(mViewModel.getHotCities());
        cityPickerFragment.setAnimationStyle(R.style.CityPickerAnimation);
        cityPickerFragment.setOnPickListener(getOnPickListener());
        cityPickerFragment.show(ft, "CityPicker");
    }

    private OnPickListener getOnPickListener(){
        return new OnPickListener() {
//                    OnBDLocationListener mListener = new OnBDLocationListener();

            @Override
            public void onPick(int position, City data) {
                mLocatedCity = new LocatedCity(data.getName(),data.getProvince(),data.getCode());

                binding.layoutTitle.tvLocation.setText(data.getName());
//                        tvCurrent.setText(String.format("当前城市：%s，%s", data.getName(), data.getCode()));
//                        XToastUtils.toast(String.format("点击的数据：%s，%s", data.getName(), data.getCode()));
//                        BaiDuLocationService.stop(mListener);
            }

            @Override
            public void onCancel() {
                Toast.makeText(getContext(), "取消选择", Toast.LENGTH_SHORT).show();
//                        BaiDuLocationService.stop(mListener);
            }

            @Override
            public void onLocate(final OnLocationListener locationListener) {
                //开始定位
                locationListener.onLocationChanged(new LocatedCity("深圳","广东","101280601"), LocateState.SUCCESS);
//                        mListener.setOnLocationListener(locationListener);
//                        BaiDuLocationService.start(mListener);
//                                new Handler().postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        //模拟定位成功
//                                        locationListener.onLocationChanged(new LocatedCity("深圳", "广东", "101280601"), LocateState.SUCCESS);
//                                    }
//                                }, 5000);
            }

        };
    }

    private BaseRecyclerAdapter<ImageUrlNameData> getFoodRecyclerAdapter() {
        return new BaseRecyclerAdapter<ImageUrlNameData>(mViewModel.getMyFoodDataList()){
            private int mViewType[] = {1,2};    //1 大图  2 小图

            @Override
            protected void bindData(@NonNull RecyclerViewHolder holder, int position, ImageUrlNameData item) {
                ItemRecyclerWaimaiFoodTypeBinding binding
                        = ItemRecyclerWaimaiFoodTypeBinding.bind(holder.itemView);
                binding.setItem(item);
                /*if(holder.getItemViewType() == mViewType[0]){
                    ItemRecyclerWaimaiFoodTypeBinding binding
                            = ItemRecyclerWaimaiFoodTypeBinding.bind(holder.itemView);
                    binding.setItem(item);
                }else{
                    ItemRecyclerWaimaiFoodTypeSmallBinding binding
                            = ItemRecyclerWaimaiFoodTypeSmallBinding.bind(holder.itemView);
                    binding.setItem(item);
                }*/
            }

            @Override
            protected int getItemLayoutId(int viewType) {
                return R.layout.item_recycler_waimai_food_type;
                /*if(viewType == mViewType[0]){
                    return R.layout.item_recycler_waimai_food_type;
                }else{
                    return R.layout.item_recycler_waimai_food_type_small;
                }*/
            }

            @Override
            public int getItemViewType(int position) {
                if(position > 9){
                    return mViewType[1];
                }else{
                    return mViewType[0];
                }
            }
        };
    }

    private MyBaseRecyclerAdapter<ExclusiveShopData> getExclusiveRecyclerAdapter() {
        return new MyBaseRecyclerAdapter<ExclusiveShopData>(R.layout.item_waimai_exclusive_shop
                ,mViewModel.getExclusiveShopData(), BR.item){
            @Override
            protected void initView(BaseViewHolder helper, ExclusiveShopData item) {
                super.initView(helper, item);
                helper.setText(R.id.tv_recent,getString(R.string.recent_place_order_count,String.valueOf(item.getRecent())));
                if(item.getGoodsList().size() > 0){
                    Glide.with(helper.itemView.getContext())
                            .load(item.getGoodsList().get(0).getGoodsImgUrl())
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .into((ImageView) helper.getView(R.id.iv_goodsIcon));
                }
            }
        };
    }

    /**
     * 更新TabBar样式
     * @param tabSegment
     * @param titles
     * @param position
     * @param refreshContent 若更新内容，则会重新创建tab、fragment等，否则仅更新tab样式
     */
    private void refreshTabAndViewPager(TabSegment tabSegment,String[] titles,int position, boolean refreshContent) {
        if(refreshContent){
            resetTabAndViewPager(tabSegment,titles,position);
        }else {
            resetTab(tabSegment,titles,position);
        }
        refreshSortType(position);
    }


    private void addTab(TabSegment tabSegment, FragmentAdapter<BaseFragment> adapter,
                        String[] titles, int textSizeSelected, int textSizeNormal, int selectedPosition){
        //注释代码 保存已有的fragment 添加新fragment(未完成)
       /* List<String> saveTitle = new ArrayList<>();
        List<String> currentTitleList = adapter.getTitleList();
        if(currentTitleList != null && currentTitleList.size() > 0){
            for(String title:currentTitleList){
                boolean hasThisTitle = false;
                for(String newTitle:titles){
                    if(newTitle.equals(title)){
                        hasThisTitle = true;
                        break;
                    }
                }
                if(hasThisTitle){
                    saveTitle.add(title);
                }
            }
        }

        //保存有用的Fragment
        List<BaseFragment> baseFragmentList = new ArrayList<>();
        if(saveTitle.size() > 0){
            for (String string:saveTitle) {
                baseFragmentList.add(adapter.getFragmentList().get(currentTitleList.indexOf(string)));
            }
        }
        adapter.getFragmentList().clear();
        adapter.setFragments(baseFragmentList);

        //添加新的Tab
        tabSegment.*/

       int position = 0;
        for (String title : titles) {
            MyTabSegmentTab tab = new MyTabSegmentTab(title);
            if(position == selectedPosition){
                tab.setTextSize(textSizeSelected);
            }else{
                tab.setTextSize(textSizeNormal);
            }
            adapter.addFragment(mViewModel.getRecommendedFragment(title), title);
            tabSegment.addTab(tab);
            position++;
        }
    }

    int textSizeSelectedScale = 0;
    int textSizeNormalScale = 0;
    /**
     * 仅更新 title 样式（切换选中时调用，内容不变）
     * @param tabSegment
     * @param titles
     * @param selectedPosition
     */
    private void resetTab(TabSegment tabSegment,String[] titles,
                          int selectedPosition){
        int position = 0;
        if(textSizeSelectedScale == 0 || textSizeNormalScale == 0){
            textSizeSelectedScale = (int) UIUtils.getInstance().scalePx(textSizeSelected);
            textSizeNormalScale = (int) UIUtils.getInstance().scalePx(textSizeNormal);
        }
        for (String title : titles) {
            MyTabSegmentTab tab = new MyTabSegmentTab(title);
            tab.setTextSize(position == selectedPosition ? textSizeSelectedScale :textSizeNormalScale);
            tabSegment.addTab(tab);
            position++;
        }
        tabSegment.notifyDataChanged();
    }

    /**
     * 更新title viewPager内容(重新创建)
     * @param tabSegment
     * @param titles
     * @param selectedPosition
     */
    private void resetTabAndViewPager(TabSegment tabSegment,String[] titles,
                          int selectedPosition){
        tabSegment.reset();
        if(textSizeSelectedScale == 0 || textSizeNormalScale == 0){
            textSizeSelectedScale = (int) UIUtils.getInstance().scalePx(textSizeSelected);
            textSizeNormalScale = (int) UIUtils.getInstance().scalePx(textSizeNormal);
        }
        viewPagerAdapter.getTitleList().clear();
        viewPagerAdapter.getFragmentList().clear();
        addTab(tabSegment,viewPagerAdapter,titles,textSizeSelectedScale,textSizeNormalScale,selectedPosition);

        viewPagerAdapter.notifyDataSetChanged();
        tabSegment.notifyDataChanged();
    }

    /**
     * 更新排序界面
     */
    private void refreshSortType(int position) {

    }

    /**
     * 点击查询结果之后
     *
     * @param query
     */
    private void onQueryResult(String query) {
        //直接跳转到指定页面
//        openPage(query);
//        try {
//            SearchTag record = mDBService.queryForColumnFirst("content", query);
//            if (record == null) {
//                record = new SearchTag().setName(query).setCreateTime(DateUtils.getNowMills());
//                mDBService.insert(record);
//            } else {
//                record.setCreateTime(DateUtils.getNowMills());
//                mDBService.updateData(record);
//            }
//            refreshRecord();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

}
