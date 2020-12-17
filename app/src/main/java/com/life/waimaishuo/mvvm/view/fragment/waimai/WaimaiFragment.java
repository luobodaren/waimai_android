package com.life.waimaishuo.mvvm.view.fragment.waimai;


import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.adapter.SearchRecordTagWaimaiAdapter;
import com.life.waimaishuo.bean.ExclusiveShopData;
import com.life.waimaishuo.bean.SearchRecord;
import com.life.waimaishuo.bean.ui.IconStrData;
import com.life.waimaishuo.bean.ui.LimitedTimeGoodsData;
import com.life.waimaishuo.databinding.FragmentWaimaiBinding;
import com.life.waimaishuo.databinding.ItemWaimaiRecyclerFoodTypeBinding;
import com.life.waimaishuo.databinding.ItemWaimaiRecyclerFoodTypeSmallBinding;
import com.life.waimaishuo.enumtype.SortTypeEnum;
import com.life.waimaishuo.mvvm.view.activity.SearchActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.view.fragment.MessageFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.WaiMaiViewModel;
import com.life.waimaishuo.util.LocationService;
import com.life.waimaishuo.util.MyDataBindingUtil;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.util.Utils;
import com.life.waimaishuo.views.MyTabSegmentTab;
import com.life.waimaishuo.views.SortTypeView;
import com.xuexiang.citypicker.CityPicker;
import com.xuexiang.citypicker.adapter.OnLocationListener;
import com.xuexiang.citypicker.adapter.OnPickListener;
import com.xuexiang.citypicker.model.City;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.tabbar.TabSegment;

import java.util.List;

import static android.Manifest.permission_group.LOCATION;

@Page(name = "外卖", anim = CoreAnim.fade)
public class WaimaiFragment extends BaseFragment {

    FragmentWaimaiBinding binding;
    WaiMaiViewModel mViewModel;

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
        binding.simpleImageBanner
                .setSource(mViewModel.getBannerItemList())
                .setOnItemClickListener((view,t,position)->{
                    Toast.makeText(getContext(),"点击了轮播图",Toast.LENGTH_SHORT).show();
                })
                .setIsOnePageLoop(false).startScroll();
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
                outRect.top = (5 < position && position < 10) ? top_interval_40:top_interval_32;
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
                openPage(WaimaiExclusiveFragment.class);
            }
        });

        View view = View.inflate(getContext(),R.layout.head_cart_view,null);
        ((TextView)view.findViewById(R.id.left_text)).setText("专属早餐");
        ((TextView)view.findViewById(R.id.right_tv)).setText("更多好店");
        ((ImageView)view.findViewById(R.id.right_iv)).setImageResource(R.drawable.ic_arrow_right_gray);
//        adapter.addHeaderView(view);

        int spanCount = 2;
        binding.recyclerMyExclusive.setAdapter(adapter);
//        binding.recyclerMyExclusive.setLayoutManager(
//                Utils.getGridLayoutManagerWithHead(getContext(),spanCount));
        binding.recyclerMyExclusive.setLayoutManager(
                Utils.getGridLayoutManagerAdapterHeight(getContext(),spanCount,binding.recyclerMyExclusive));
        binding.recyclerSecondsKill.addItemDecoration(new RecyclerView.ItemDecoration(){
            int top_interval = -1;
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                if(top_interval == -1)
                    top_interval = (int)UIUtils.getInstance(getContext()).scalePx(40);
                outRect.top = position >= spanCount ? top_interval:0;
            }
        });
    }

    /**
     * 初始化显示秒杀界面
     */
    private void initLimitedTimeRecycler() {
        MyBaseRecyclerAdapter<LimitedTimeGoodsData> adapter =
                new MyBaseRecyclerAdapter<LimitedTimeGoodsData>(R.layout.item_simple_goods
                        ,mViewModel.getLimitedTimeGoodsData(),null) {
                    @Override
                    protected void initView(BaseViewHolder helper, LimitedTimeGoodsData item) {
                        Glide.with(WaimaiFragment.this)
                                .load(item.getShopIconStr())
                                .centerCrop()
                                .placeholder(R.drawable.ic_waimai_brand)
                                .into(((ImageView)helper.getView(R.id.iv_goods_img)));
                        helper.setText(R.id.tv_goods_name,item.getShopName());
                    }
                };

        binding.recyclerSecondsKill.setAdapter(adapter);
//        binding.recyclerSecondsKill.setLayoutManager(
//                Utils.getGridLayoutManagerWithHead(getContext(),2));

        binding.recyclerSecondsKill.setLayoutManager(
                Utils.getGridLayoutManagerAdapterHeight(getContext(),2,binding.recyclerSecondsKill));
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
        binding.stickyView.setPadding(space, 0, space, 0);
        binding.stickyView.setDefaultNormalColor(getResources().getColor(R.color.text_tip));
        binding.stickyView.setDefaultSelectedColor(getResources().getColor(R.color.text_normal));
        binding.stickyView.setTabTextSize(textSizeNormal);
        binding.stickyView.setupWithViewPager(binding.adaptiveSizeView,true);

        binding.adaptiveSizeView.setOffscreenPageLimit(titles.length - 1);
        binding.adaptiveSizeView.setAdapter(adapter);
    }

    private void addSortViewClickListener() {
        binding.contentLayout.setPreferentialTab(mViewModel.getPreferential());
        binding.contentLayout.setOnSortTypeChangeListener(new SortTypeView.onSortTypeChangeListener() {
            @Override
            public void onSortPopShow() {

            }

            @Override
            public void onSortTypeChange(SortTypeEnum sortTypeEnum) {

            }

            @Override
            public void onPreferentialChange(List<Integer> selectedList) {

            }
        });
    }

    private void refreshSortType(SortTypeEnum sortType){
        binding.contentLayout.setSortType(sortType);
    }

//    @Permission(LOCATION)
    private void pickCity() {
        /*CityPicker.from(this)
                .enableAnimation(mEnableAnimation)
                .setAnimationStyle(mAnim)
                .setLocatedCity(null)
                .setHotCities(mHotCities)
                .setOnPickListener(new OnPickListener() {

                    OnBDLocationListener mListener = new OnBDLocationListener();

                    @Override
                    public void onPick(int position, City data) {
                        tvCurrent.setText(String.format("当前城市：%s，%s", data.getName(), data.getCode()));
                        XToastUtils.toast(String.format("点击的数据：%s，%s", data.getName(), data.getCode()));
                        LocationService.stop(mListener);
                    }

                    @Override
                    public void onCancel() {
                        XToastUtils.toast("取消选择");
                        LocationService.stop(mListener);
                    }

                    @Override
                    public void onLocate(final OnLocationListener locationListener) {
                        //开始定位
                        mListener.setOnLocationListener(locationListener);
                        LocationService.start(mListener);
//                                new Handler().postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        //模拟定位成功
//                                        locationListener.onLocationChanged(new LocatedCity("深圳", "广东", "101280601"), LocateState.SUCCESS);
//                                    }
//                                }, 5000);
                    }

                })
                .show();*/
    }

    private BaseRecyclerAdapter<IconStrData> getFoodRecyclerAdapter() {
        return new BaseRecyclerAdapter<IconStrData>(mViewModel.getMyFoodDataList()){

            private int mViewType[] = {1,2};    //1 大图  2 小图

            @Override
            protected void bindData(@NonNull RecyclerViewHolder holder, int position, IconStrData item) {
                if(holder.getItemViewType() == mViewType[0]){
                    ItemWaimaiRecyclerFoodTypeBinding binding
                            = ItemWaimaiRecyclerFoodTypeBinding.bind(holder.itemView);
                    binding.setItem(item);
                }else{
                    ItemWaimaiRecyclerFoodTypeSmallBinding binding
                            = ItemWaimaiRecyclerFoodTypeSmallBinding.bind(holder.itemView);
                    binding.setItem(item);
                }
            }

            @Override
            protected int getItemLayoutId(int viewType) {
                if(viewType == mViewType[0]){
                    return R.layout.item_waimai_recycler_food_type;
                }else if(viewType == mViewType[1]){
                    return R.layout.item_waimai_recycler_food_type_small;
                }
                return R.layout.item_waimai_recycler_food_type;//默认
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
        return new MyBaseRecyclerAdapter<ExclusiveShopData>(R.layout.item_waimai_exclusive_shop
                ,mViewModel.getExclusiveShopData(),null) {
            @Override
            protected void initView(BaseViewHolder helper, ExclusiveShopData item) {
                helper.setText(R.id.tv_shopName,item.getShopName());
                helper.setText(R.id.tv_recent,item.getRecent());
                Glide.with(WaimaiFragment.this)
                        .load(item.getShopIconStr())
                        .centerCrop()
                        .placeholder(R.drawable.ic_waimai_brand)
                        .into((ImageView)helper.getView(R.id.iv_shopIcon));
            }
        };
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
