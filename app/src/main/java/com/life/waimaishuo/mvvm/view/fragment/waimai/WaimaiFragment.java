package com.life.waimaishuo.mvvm.view.fragment.waimai;


import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.BR;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.BaseBannerAdapter;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.adapter.tagAdapter.SearchRecordTagWaimaiAdapter;
import com.life.waimaishuo.bean.ExclusiveShopData;
import com.life.waimaishuo.bean.SearchRecord;
import com.life.waimaishuo.bean.ui.IconStrData;
import com.life.waimaishuo.bean.ui.LimitedTimeGoodsData;
import com.life.waimaishuo.databinding.FragmentWaimaiBinding;
import com.life.waimaishuo.databinding.ItemRecyclerWaimaiFoodTypeBinding;
import com.life.waimaishuo.databinding.ItemRecyclerWaimaiFoodTypeSmallBinding;
import com.life.waimaishuo.enumtype.SortTypeEnum;
import com.life.waimaishuo.mvvm.view.activity.SearchActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.view.fragment.MessageFragment;
import com.life.waimaishuo.mvvm.view.fragment.city.CityPickerDialogFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.WaiMaiViewModel;
import com.life.waimaishuo.util.MyDataBindingUtil;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.util.Utils;
import com.life.waimaishuo.views.MyTabSegmentTab;
import com.life.waimaishuo.views.SortTypeView;
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
import com.xuexiang.xui.widget.tabbar.TabSegment;

import java.lang.ref.WeakReference;

import static android.Manifest.permission_group.LOCATION;

@Page(name = "外卖", anim = CoreAnim.fade)
public class WaimaiFragment extends BaseFragment {

    private FragmentWaimaiBinding binding;
    private WaiMaiViewModel mViewModel;

    private WeakReference<FragmentManager> mFragmentManager;
    private LocatedCity mLocatedCity = null;

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
    protected void initViews() {
        super.initViews();

        initMyLocation();

        initBanner();
        initSearchView();
        initFoodTypeRecycler();

        initMyExclusiveRecycler();
        initLimitedTimeRecycler();

        initNavigationTab();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        addCallBack();

        addSortViewClickListener();
        binding.adaptiveSizeView.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                refreshTabViewStyle(binding.stickyView,mViewModel.getRecommendedTitle(),position);
                refreshSortType(SortTypeEnum.SCORE);
                // TODO: 2020/12/3 刷新内容

            }
        });
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
        setFitStatusBarHeight(true);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_DARK);
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    private void initMyLocation() {
        mViewModel.mLocation.set(getString(R.string.location_unknow));
    }

    private void addCallBack() {
        MyDataBindingUtil.addCallBack(this, mViewModel.goToLocat, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                pickCity();
            }
        });

        MyDataBindingUtil.addCallBack(this, mViewModel.goToSearch, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                LogUtil.d("跳转搜索页");
//                openNewPage(SearchHistoryFragment.class,SearchActivity.class);
                startActivity(new Intent(getContext(), SearchActivity.class));  // FIXME: 2020/11/30
//                startActivity();
            }
        });

        MyDataBindingUtil.addCallBack(this, mViewModel.goToMessage, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                LogUtil.d("跳转消息页");
//                PageOption.to(MessageFragment.class)
//                        .setNewActivity(true).setAnim(CoreAnim.slide).open(WaimaiFragment.this);
                openPage(MessageFragment.class);
//                openPage(Mess)
//                openNewPage(SearchHistoryFragment.class,SearchActivity.class);
//                startActivity(new Intent(getContext(), SearchActivity.class));
//                startActivity();
            }
        });
    }

    /**
     * 初始化历史搜索View
     */
    private void initSearchView(){
        SearchRecord[] searchRecord = mViewModel.getSearchRecord();
        SearchRecordTagWaimaiAdapter mAdapter;
        binding.searchRecord.setLayoutManager(Utils.getFlexboxLayoutManager(getContext()));
        binding.searchRecord.setAdapter(mAdapter = new SearchRecordTagWaimaiAdapter());
        mAdapter.refresh(searchRecord);
    }

    /**
     * 初始化轮播图
     */
    private void initBanner(){
        BaseBannerAdapter mAdapterHorizontal
                = new BaseBannerAdapter(mViewModel.getBannerItemList(),R.layout.adapter_banner_image_item_waimai);
        mAdapterHorizontal.setOnBannerItemClickListener(position ->
                Toast.makeText(getContext(),"点击了轮播图：" + position,Toast.LENGTH_SHORT).show());
        binding.bannerLayout.setAdapter(mAdapterHorizontal);
        binding.bannerLayout.setItemSpace((int) UIUtils.getInstance(getContext()).scalePx(
                getResources().getDimensionPixelSize(R.dimen.interval_size_xs)));
    }

    /**
     * 初始化食物类型Recycler
     */
    private void initFoodTypeRecycler(){
        int spanCount = 5;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),spanCount
                , LinearLayoutManager.VERTICAL,false);
        BaseRecyclerAdapter<IconStrData> adapter = getFoodRecyclerAdapter();
        adapter.setOnItemClickListener((itemView, item, position) -> {
            if(position == adapter.getItemCount()-1){
                openPage(WaimaiAllTypeFragment.class);  //最后一个为全部类型
            }else{  //打开子类型
                Bundle bundle = new Bundle();
                bundle.putString(WaimaiTypeFragment.BUNDLE_FOOD_TYPE_STR_KEY,item.getIconType());
                openPage(WaimaiTypeFragment.class,bundle);
            }
        });
        binding.recyclerFoodType.setAdapter(adapter);
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
                    top_interval_40 = (int)UIUtils.getInstance(getContext()).scalePx(40);
                if(top_interval_32 == -1)
                    top_interval_32 = (int)UIUtils.getInstance(getContext()).scalePx(32);
                outRect.top = position < 5 ? 0 : (position < 10 ? top_interval_40 : top_interval_32);
            }
        });
    }

    /**
     * 初始化专属recycler
     */
    private void initMyExclusiveRecycler() {
        MyBaseRecyclerAdapter<ExclusiveShopData> adapter = getExclusiveRecyclerAdapter();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                openPage(ExclusiveBreakfastFragment.class);
            }
        });

        View view = View.inflate(getContext(),R.layout.head_exclusive_recycler,null);
        ((TextView)view.findViewById(R.id.text_left)).setText("专属早餐");
        ((TextView)view.findViewById(R.id.tv_right)).setText("更多好店");
        ((ImageView)view.findViewById(R.id.iv_right)).setImageResource(R.drawable.ic_arrow_right_gray);
        adapter.addHeaderView(view);

        int spanCount = 2;
        binding.recyclerMyExclusive.setAdapter(adapter);
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
                    top_interval = (int)UIUtils.getInstance(getContext()).scalePx(40);
                if(position > 0){
                    outRect.top = top_interval;
//                    if((position - adapter.getHeaderLayoutCount()) % 2 == 1){
//                        outRect.left
//                    }
                }
            }
        });
    }

    /**
     * 初始化显示秒杀界面
     */
    private void initLimitedTimeRecycler() {
        MyBaseRecyclerAdapter<LimitedTimeGoodsData> adapter =
                new MyBaseRecyclerAdapter<>(R.layout.item_simple_goods
                        ,mViewModel.getLimitedTimeGoodsData(), com.life.waimaishuo.BR.item);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(position % 2 == 0){
                    openPage(LimitedTimeGoodsFragment.class);
                }else{
                    openPage(ZeroDividerFragment.class);
                }
            }
        });
        binding.recyclerSecondsKill.setAdapter(adapter);
        binding.recyclerSecondsKill.setLayoutManager(new GridLayoutManager(getContext(),2));

//        binding.recyclerSecondsKill.setLayoutManager(
//                Utils.getGridLayoutManagerAdapterHeight(getContext(),2,binding.recyclerSecondsKill));
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

        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());

        addTab(binding.stickyView,adapter,titles);   // FIXME: 2020/12/10 第一次进入 拿不到tab 设置的大小
        binding.stickyView.setHasIndicator(false);
        binding.stickyView.setMode(TabSegment.MODE_SCROLLABLE);
        binding.stickyView.setItemSpaceInScrollMode(space);
        binding.stickyView.setDefaultNormalColor(getResources().getColor(R.color.text_tip));
        binding.stickyView.setDefaultSelectedColor(getResources().getColor(R.color.text_normal));
        binding.stickyView.setTabTextSize(textSizeNormal);
        binding.stickyView.setupWithViewPager(binding.adaptiveSizeView,true);

        binding.adaptiveSizeView.setOffscreenPageLimit(titles.length - 1);
        binding.adaptiveSizeView.setAdapter(adapter);
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
                mViewModel.mLocation.set(data.getName());
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

    private BaseRecyclerAdapter<IconStrData> getFoodRecyclerAdapter() {
        return new BaseRecyclerAdapter<IconStrData>(mViewModel.getMyFoodDataList()){

            private int mViewType[] = {1,2};    //1 大图  2 小图

            @Override
            protected void bindData(@NonNull RecyclerViewHolder holder, int position, IconStrData item) {
                if(holder.getItemViewType() == mViewType[0]){
                    ItemRecyclerWaimaiFoodTypeBinding binding
                            = ItemRecyclerWaimaiFoodTypeBinding.bind(holder.itemView);
                    binding.setItem(item);
                }else{
                    ItemRecyclerWaimaiFoodTypeSmallBinding binding
                            = ItemRecyclerWaimaiFoodTypeSmallBinding.bind(holder.itemView);
                    binding.setItem(item);
                }
            }

            @Override
            protected int getItemLayoutId(int viewType) {
                if(viewType == mViewType[0]){
                    return R.layout.item_recycler_waimai_food_type;
                }else if(viewType == mViewType[1]){
                    return R.layout.item_recycler_waimai_food_type_small;
                }
                return R.layout.item_recycler_waimai_food_type;//默认
            }

            @NonNull
            @Override
            protected RecyclerViewHolder getViewHolder(@NonNull ViewGroup parent, int viewType) {
                return super.getViewHolder(parent, viewType);
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
        return new MyBaseRecyclerAdapter<>(R.layout.item_waimai_exclusive_shop
                ,mViewModel.getExclusiveShopData(), BR.item);
    }

    /**
     * 更新TabBar样式
     */
    private void refreshTabViewStyle(TabSegment tabSegment,String[] titles,int position) {
        tabSegment.reset();
        resetTab(tabSegment,titles,position);

        refreshSortType(position);
        tabSegment.notifyDataChanged();
    }

    private void addTab(TabSegment tabSegment, FragmentAdapter<BaseFragment> adapter,
                        String[] titles){
        boolean isFirstItem = true;
        for (String title : titles) {
            MyTabSegmentTab tab = new MyTabSegmentTab(title);
            if(isFirstItem){
                tab.setTextSize(textSizeSelected);
                isFirstItem = false;
            }else{
                tab.setTextSize(textSizeNormal);
            }
            adapter.addFragment(mViewModel.getRecommendedFragment(), title);
            tabSegment.addTab(tab);
        }
    }

    /**
     * 仅更新 title 若需要改变个数 需要重写方法
     * @param tabSegment
     * @param titles
     * @param selectedPosition
     */
    private void resetTab(TabSegment tabSegment,String[] titles,
                          int selectedPosition){
        int position = 0;
        int textSizeSelectedScale = (int) UIUtils.getInstance(getContext()).scalePx(textSizeSelected);
        int textSizeNormalScale = (int) UIUtils.getInstance(getContext()).scalePx(textSizeNormal);
        for (String title : titles) {
            MyTabSegmentTab tab = new MyTabSegmentTab(title);
            tab.setTextSize(position == selectedPosition ? textSizeSelectedScale :textSizeNormalScale);
            tabSegment.addTab(tab);
            position++;
        }
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
//            SearchRecord record = mDBService.queryForColumnFirst("content", query);
//            if (record == null) {
//                record = new SearchRecord().setContent(query).setTime(DateUtils.getNowMills());
//                mDBService.insert(record);
//            } else {
//                record.setTime(DateUtils.getNowMills());
//                mDBService.updateData(record);
//            }
//            refreshRecord();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MyDataBindingUtil.removeFragmentCallBack(this);
    }
}
