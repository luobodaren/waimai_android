package com.life.waimaishuo.mvvm.view.fragment.waimai;


import android.content.Intent;
import android.graphics.Rect;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.databinding.ViewDataBinding;
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
import com.life.base.utils.TimeUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.BR;
import com.life.waimaishuo.Global;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.BaseBannerAdapter;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.adapter.MyFragmentPagerAdapter;
import com.life.waimaishuo.adapter.SelectedPositionRecyclerViewAdapter;
import com.life.waimaishuo.adapter.statelayout.CustomSingleViewAdapter;
import com.life.waimaishuo.adapter.tag.SearchRecordTagWaimaiAdapter;
import com.life.waimaishuo.bean.ExclusiveShopData;
import com.life.waimaishuo.bean.SearchTag;
import com.life.waimaishuo.bean.api.respon.SecondKillTime;
import com.life.waimaishuo.bean.ui.ImageUrlNameData;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.databinding.FragmentWaimaiAdaptiveSizeViewBinding;
import com.life.waimaishuo.databinding.FragmentWaimaiBinding;
import com.life.waimaishuo.databinding.ItemRecyclerWaimaiFoodTypeBinding;
import com.life.waimaishuo.databinding.ItemWaimaiExclusiveShopBinding;
import com.life.waimaishuo.enumtype.SortTypeEnum;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.view.activity.SearchActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.view.fragment.BaseStatusLoaderFragment;
import com.life.waimaishuo.mvvm.view.fragment.MessageFragment;
import com.life.waimaishuo.mvvm.view.fragment.city.CityPickerDialogFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.WaiMaiViewModel;
import com.life.waimaishuo.util.MyDataBindingUtil;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.util.TimeCountRefresh;
import com.life.waimaishuo.util.Utils;
import com.life.waimaishuo.util.amap.LocationUtil;
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
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission_group.LOCATION;
import static com.xuexiang.xui.widget.statelayout.StatusLoader.STATUS_LOAD_SUCCESS;

@Page(name = "外卖", anim = CoreAnim.fade)
public class WaimaiFragment extends BaseStatusLoaderFragment {

    private FragmentWaimaiBinding binding;
    private WaiMaiViewModel mViewModel;

    private WeakReference<FragmentManager> mFragmentManager;
    private LocatedCity mLocatedCity = null;

    //主要内容
    //自适应大小布局-动态引入
    private FragmentWaimaiAdaptiveSizeViewBinding adaptiveViewBinding;

    //适配器-轮播图
    private BaseBannerAdapter baseBannerAdapter;
    //适配器-搜索标签
    private SearchRecordTagWaimaiAdapter searchTagAdapter;
    //适配器-金刚区
    private BaseRecyclerAdapter<ImageUrlNameData> kingKongAreaAdapter;
    //适配器-专属早餐
    private MyBaseRecyclerAdapter<ExclusiveShopData> exclusiveShopAdapter;
    //适配器-子标签
    private SelectedPositionRecyclerViewAdapter<String> childSignRecyclerAdapter;

    //适配器-ViewPager
    private MyFragmentPagerAdapter<BaseFragment> viewPagerAdapter;
    private FragmentManager fm;//viewpager的fragmentManager

    //限时秒杀计时器
    private TimeCountRefresh mSecondKillTiming;
    private TimeCountRefresh.OnTimerProgressListener mTimerProgressListener;    //进度监听器
    private TimeCountRefresh.OnTimerFinishListener mTimerFinishListener;    //及时结束监听器

    //由于动画原因导致无法通过view本身立刻获取选中位置，需要设置变量保存
    //主标签选中的位置
    private int pageTypeSelectedIndex = 0;
    //子标签选中的String
    private String child_sign = "";

    //保存排序缓存
    private SparseArray<SortTypeEnum> sortTypeEnumMap = new SparseArray<>();

    {
        mTimerProgressListener = timeLong -> {
            LogUtil.d(timeLong + "");
            String[] times = new String[3];

            float minute = ((float) timeLong) / TimeUtil.ONE_MIN_MILLISECONDS;
            int minute_int = (int) minute;
            times[2] = String.valueOf((int) ((minute - minute_int) * 60));
            float hour = minute / 60;
            int hour_int = (int) hour;
            times[1] = String.valueOf((int) ((hour - hour_int) * 60));
            times[0] = String.valueOf(hour_int);
            setSecondKillTime(times);
        };
        mTimerFinishListener = () -> {
            LogUtil.d("限时秒杀计时结束");
            if (mSecondKillTiming != null) {
                mSecondKillTiming.cancel();
                mSecondKillTiming = null;
            }
        };
    }

    @Override
    protected BaseViewModel setViewModel() {
        mViewModel = new WaiMaiViewModel();
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        binding = ((FragmentWaimaiBinding) mViewDataBinding);
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

        adaptiveViewBinding.viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                LogUtil.d("OnPageChangeListener position:" + position);
                pageTypeSelectedIndex = position;
                refreshTabAndViewPager(mViewModel.getDefaultTitle(), position, false);
                // TODO: 2020/12/3 刷新内容
                notifyRecommendFragmentRefresh(false);
            }
        });

        binding.layoutTitle.llLocal.setOnClickListener(v -> pickCity());

        binding.layoutTitle.ivMessage.setOnClickListener(v -> openPage(MessageFragment.class));

        //金刚区装修点击
        kingKongAreaAdapter.setOnItemClickListener((itemView, item, position) -> {
            if (position == kingKongAreaAdapter.getItemCount() - 1) {
                openPage(WaimaiAllTypeFragment.class);  //最后一个为全部类型
            } else {  //打开子类型
                WaimaiTypeFragment.openPage(WaimaiFragment.this,item.getName(),"");
            }
        });

        childSignRecyclerAdapter.setSelectedListener((holder, item, isCancel) -> {
            if (isCancel) {
                child_sign = "";
            } else {
                child_sign = item;
            }
            adaptiveViewBinding.childSignRecycler.scrollToPosition(childSignRecyclerAdapter.getData().indexOf(item));
            notifyRecommendFragmentRefresh(false);
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

        //动态添加adaptiveView
        View adaptiveView = View.inflate(requireContext(), R.layout.fragment_waimai_adaptive_size_view, null);
        adaptiveViewBinding = FragmentWaimaiAdaptiveSizeViewBinding.bind(adaptiveView);
        binding.adaptiveSizeView.addView(adaptiveView);

        setDefaultSecondKillTime();//设置默认限时秒杀时间

        initMyLocation();

        initBanner();
        initSearchView();
        initFoodTypeRecycler();

        adaptiveViewBinding.stickyNavigationLayout.setCustomCanScrollDistance(UIUtils.getInstance().scalePx(700));
        initMyExclusiveRecycler();
        initActivityRegion();
        initNavigationTab();
        initChildSignRecycler();
        initSortTypeView();

        showLoading();
    }


    @Override
    protected View getWrapView() {
        return adaptiveViewBinding.flState;
    }

    @Override
    protected StatusLoader.Adapter getStatusLoaderAdapter() {
        return new CustomSingleViewAdapter();
    }

    @Override
    protected void onLifecycleResume() {
        super.onLifecycleResume();

        //开始定位
        startLocation();

        mViewModel.refreshSecondKillTime();
    }

    @Override
    protected void onLifecycleStop() {
        super.onLifecycleStop();
        LocationUtil.stopLocation(false);
        mSecondKillTiming.cancel();
    }

    @Override
    protected void firstRequestData() {
        super.firstRequestData();
        //请求数据
        mViewModel.refreshSearchTag();
        mViewModel.refreshBannerItemList();
        mViewModel.refreshKingKongArea();

        // TODO: 2021/2/19 以下两个请求可改为链式调用
        mViewModel.refreshExclusiveBreakfast();
        mViewModel.refreshActivityRegion();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MyDataBindingUtil.removeFragmentCallBack(this);
        LocationUtil.stopLocation(true);
    }

    private void initMyLocation() {
        setMyLocationData();
    }

    private void startLocation() {
        LocationUtil.intervalLocation(aMapLocation -> {
            LogUtil.d(aMapLocation.toStr());
            if (aMapLocation.getErrorCode() == 0) {
                Global.LocationProvince = aMapLocation.getProvince();
                Global.LocationCity = aMapLocation.getCity();
                Global.LocationDistrict = aMapLocation.getDistrict();
                Global.latitude = aMapLocation.getLatitude();
                Global.longitude = aMapLocation.getLongitude();
            } else {
                LogUtil.e("定位失败");
            }

            mHandler.post(this::setMyLocationData);

        }, 30000, null);
    }

    /**
     * 设置我的位置信息
     */
    private void setMyLocationData() {
        if (Global.LocationDistrict != null && !"".equals(Global.LocationDistrict)) {
            binding.layoutTitle.tvLocation.setText(Global.LocationDistrict);
        } else if (Global.LocationCity != null && !"".equals(Global.LocationCity)) {
            binding.layoutTitle.tvLocation.setText(Global.LocationCity);
        } else {
            binding.layoutTitle.tvLocation.setText(R.string.location_unknow);
        }
    }

    /**
     * 设置默认限时秒杀时间
     */
    private void setDefaultSecondKillTime() {
        if (adaptiveViewBinding != null) {
            adaptiveViewBinding.layoutActivityRegion.layoutCounting.tvHour.setText(R.string.default_second_kill_time);
            adaptiveViewBinding.layoutActivityRegion.layoutCounting.tvMinute.setText(R.string.default_second_kill_time);
            adaptiveViewBinding.layoutActivityRegion.layoutCounting.tvSeconds.setText(R.string.default_second_kill_time);
        }
    }

    /**
     * 设置限时秒杀时间
     *
     * @param times 小时分钟秒
     */
    private String currentSetHour;
    private String currentSetMinute;

    private void setSecondKillTime(String[] times) {
        try {
            if (adaptiveViewBinding != null) {
                if (!times[0].equals(currentSetHour)) {
                    currentSetHour = times[0];
                    adaptiveViewBinding.layoutActivityRegion.layoutCounting.tvHour.setText(
                            currentSetHour.length() == 1 ? "0" + currentSetHour : currentSetHour);
                }
                if (!times[1].equals(currentSetMinute)) {
                    currentSetMinute = times[1];
                    adaptiveViewBinding.layoutActivityRegion.layoutCounting.tvMinute.setText(
                            currentSetMinute.length() == 1 ? "0" + currentSetMinute : currentSetMinute);
                }
                adaptiveViewBinding.layoutActivityRegion.layoutCounting.tvSeconds.setText(
                        times[2].length() == 1 ? "0" + times[2] : times[2]);
            }
        } catch (Error error) {
            LogUtil.e("设置限时秒杀时间失败 error:" + error.getMessage());
        }
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
                            = new BaseBannerAdapter(mViewModel.getBannerItemList(), R.layout.adapter_banner_image_item_waimai);
                    adaptiveViewBinding.bannerLayout.setAdapter(baseBannerAdapter);
                    adaptiveViewBinding.stickyNavigationLayout.setNeedResetCanScrollDistance(true);
                });
            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.searchTagObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.post(() -> {
                    searchTagAdapter.refresh(mViewModel.getSearchTag());
                    adaptiveViewBinding.stickyNavigationLayout.setNeedResetCanScrollDistance(true);
                });
            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.kingKongAreaObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.post(() -> {
                    kingKongAreaAdapter.getData().clear();
                    if (mViewModel.getMyFoodDataList().size() > 0) {
                        setViewVisibility(adaptiveViewBinding.recyclerFoodType, true);
                        kingKongAreaAdapter.getData().addAll(mViewModel.getMyFoodDataList());
                        kingKongAreaAdapter.notifyDataSetChanged();
                    } else {
                        setViewVisibility(adaptiveViewBinding.recyclerFoodType, false);
                    }
                    adaptiveViewBinding.stickyNavigationLayout.setNeedResetCanScrollDistance(true);
                });
            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.exclusiveBreakfastObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.post(() -> {
                    if (mViewModel.getExclusiveShopData().size() <= 0) {
                        setViewVisibility(adaptiveViewBinding.recyclerMyExclusive, false);
                    } else {
                        setViewVisibility(adaptiveViewBinding.recyclerMyExclusive, true);
                        showBottomContent();
                    }
                });
            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.activityRegionObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.post(() -> {
                    if (mViewModel.activityRegionObservable.get() == BaseModel.NotifyChangeRequestCallBack.REQUEST_FALSE) {
                        showError();
                    } else {
                        setActivityRegionData(mViewModel.getActivityRegion());
                        if (mViewModel.getActivityRegion().size() > 0) {
                            showBottomContent();
                        }
                    }
                });
            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.recommendTitleObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.post(() -> {
                    if (mViewModel.getChildSignData().size() > 0) {
                        setViewVisibility(adaptiveViewBinding.childSignRecycler, true);
                        child_sign = mViewModel.getChildSignData().get(0);
                    } else {
                        setViewVisibility(adaptiveViewBinding.childSignRecycler, false);
                        child_sign = "";
                    }
                    childSignRecyclerAdapter.notifyDataSetChanged();

                    //刷新fragment
                    notifyRecommendFragmentRefresh(true);

                    //refreshTabAndViewPager(mViewModel.getRecommendedTitle(),0,true);
                });
            }
        });

        MyDataBindingUtil.addCallBack(this, mViewModel.secondKillTimeObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                //if(mViewModel.secondKillTimeObservable.get() == BaseModel.NotifyChangeRequestCallBack.REQUEST_FALSE){   //失败
                mHandler.post(() -> {
                    SecondKillTime secondKillTime = mViewModel.getSecondKillTime();
                    secondKillTime.setOverTime("2021-2-20 13:00:00");
                    if (secondKillTime != null && !"".equals(secondKillTime.getOverTime())) {    //有数据
                        int result = TimeUtil.compare_date(TimeUtil.getCurrentDate(TimeUtil.dateFormatYMDHMS), secondKillTime.getOverTime());
                        if (result == -1) {   //秒杀活动未结束
                            long times = TimeUtil.getExpired(secondKillTime.getOverTime());
                            if (mSecondKillTiming != null) {
                                mSecondKillTiming.cancel();
                            }
                            mSecondKillTiming = new TimeCountRefresh(times, 1000);
                            mSecondKillTiming.setOnTimerProgressListener(mTimerProgressListener);
                            mSecondKillTiming.setOnTimerFinishListener(mTimerFinishListener);
                            mSecondKillTiming.start();
                        }
                    } else {
                        LogUtil.d("没有限时秒杀时间");
                        if (mSecondKillTiming != null) {
                            mSecondKillTiming.cancel();
                            mSecondKillTiming = null;
                        }
                        setDefaultSecondKillTime();
                    }
                });
            }
        });
    }

    /**
     * 初始化历史搜索View
     */
    private void initSearchView() {
        SearchTag[] searchTag = mViewModel.getSearchTag();
        adaptiveViewBinding.searchRecord.setLayoutManager(Utils.getFlexboxLayoutManager(getContext()));
        adaptiveViewBinding.searchRecord.setAdapter(searchTagAdapter = new SearchRecordTagWaimaiAdapter());
        searchTagAdapter.refresh(searchTag);
    }

    /**
     * 初始化轮播图
     */
    private void initBanner() {
        //String[] url = {"https://cjwm-pic.oss-cn-beijing.aliyuncs.com/wms2fa52615-d6e9-d5ff-88e4-f7281e29af31.jpg"};
        baseBannerAdapter
                = new BaseBannerAdapter(mViewModel.getBannerItemList(), R.layout.adapter_banner_image_item_waimai);
        baseBannerAdapter.setOnBannerItemClickListener(position ->
                Toast.makeText(getContext(), "点击了轮播图：" + position, Toast.LENGTH_SHORT).show());
        adaptiveViewBinding.bannerLayout.setAdapter(baseBannerAdapter);
        adaptiveViewBinding.bannerLayout.setItemSpace((int) UIUtils.getInstance().scalePx(
                getResources().getDimensionPixelSize(R.dimen.interval_size_xs)));
    }

    /**
     * 初始化食物类型Recycler
     */
    private void initFoodTypeRecycler() {
        int spanCount = 5;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), spanCount
                , LinearLayoutManager.VERTICAL, false);
        kingKongAreaAdapter = getFoodRecyclerAdapter();
        adaptiveViewBinding.recyclerFoodType.setAdapter(kingKongAreaAdapter);
        adaptiveViewBinding.recyclerFoodType.setLayoutManager(gridLayoutManager);
        adaptiveViewBinding.recyclerFoodType.addItemDecoration(new RecyclerView.ItemDecoration() {
            int top_interval_40 = -1;
            int top_interval_32 = -1;

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                if (top_interval_40 == -1)
                    top_interval_40 = (int) UIUtils.getInstance().scalePx(40);
                if (top_interval_32 == -1)
                    top_interval_32 = (int) UIUtils.getInstance().scalePx(32);
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

        View view = View.inflate(getContext(), R.layout.head_exclusive_recycler, null);
        ((TextView) view.findViewById(R.id.text_left)).setText(R.string.exclusive_breakfast);
        ((TextView) view.findViewById(R.id.tv_right)).setText(R.string.more_shop);
        ((ImageView) view.findViewById(R.id.iv_right)).setImageResource(R.drawable.ic_arrow_right_gray);
        exclusiveShopAdapter.addHeaderView(view);

        int spanCount = 2;
        adaptiveViewBinding.recyclerMyExclusive.setAdapter(exclusiveShopAdapter);
        adaptiveViewBinding.recyclerMyExclusive.setLayoutManager(
                Utils.getGridLayoutManagerWithHead(getContext(), spanCount));
        adaptiveViewBinding.recyclerMyExclusive.addItemDecoration(new RecyclerView.ItemDecoration() {
            int top_interval = -1;

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                if (top_interval == -1)
                    top_interval = (int) UIUtils.getInstance().scalePx(40);
                if (position > 0) {
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
    private void initActivityRegion() {
        setActivityRegionData(mViewModel.getActivityRegion());
    }

    /**
     * 初始化子标签
     */
    private void initChildSignRecycler() {
        childSignRecyclerAdapter = new SelectedPositionRecyclerViewAdapter<String>(mViewModel.getChildSignData()) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_recycler_waimai_child_sign;
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, boolean selected, String item) {
                holder.setText(R.id.tv_sign, item);
                if (selected) {
                    holder.setTextColor(R.id.tv_sign, holder.itemView.getContext().getResources().getColor(R.color.text_normal));
                } else {
                    holder.setTextColor(R.id.tv_sign, holder.itemView.getContext().getResources().getColor(R.color.text_tip));
                }
            }
        };
        childSignRecyclerAdapter.setCancelable(true);

        adaptiveViewBinding.childSignRecycler.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        adaptiveViewBinding.childSignRecycler.setAdapter(childSignRecyclerAdapter);
        adaptiveViewBinding.childSignRecycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            int interval_24 = (int) UIUtils.getInstance().scalePx(24);
            int interval_40 = (int) UIUtils.getInstance().scalePx(40);

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent.getChildAdapterPosition(view) == 0) {
                    outRect.left = interval_24;
                } else {
                    outRect.left = interval_40;
                }
            }
        });
    }

    /**
     * 初始化排序
     */
    private void initSortTypeView() {
        adaptiveViewBinding.sortTypeView.setPreferentialTab(mViewModel.getPreferential());
        adaptiveViewBinding.sortTypeView.setScreenData(mViewModel.getScreenData());
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
        String[] titles = mViewModel.getDefaultTitle();
        fm = getChildFragmentManager();
        viewPagerAdapter = new MyFragmentPagerAdapter<>(fm);

        addTab(adaptiveViewBinding.stickyView, viewPagerAdapter, titles, textSizeSelected, textSizeNormal, 0);
        adaptiveViewBinding.stickyView.setHasIndicator(false);
        adaptiveViewBinding.stickyView.setMode(TabSegment.MODE_SCROLLABLE);
        adaptiveViewBinding.stickyView.setItemSpaceInScrollMode(space);
        adaptiveViewBinding.stickyView.setDefaultNormalColor(getResources().getColor(R.color.text_tip));
        adaptiveViewBinding.stickyView.setDefaultSelectedColor(getResources().getColor(R.color.text_normal));
        adaptiveViewBinding.stickyView.setTabTextSize(textSizeNormal);
        adaptiveViewBinding.stickyView.setupWithViewPager(adaptiveViewBinding.viewPager, false);
        adaptiveViewBinding.stickyView.selectTab(0);

        adaptiveViewBinding.viewPager.setOffscreenPageLimit(titles.length - 1);
        adaptiveViewBinding.viewPager.setAdapter(viewPagerAdapter);
    }

    /**
     * 设置活动专区数据
     *
     * @param imageUrlNameDataList
     */
    private void setActivityRegionData(List<ImageUrlNameData> imageUrlNameDataList) {
        if (imageUrlNameDataList.size() <= 0) {
            LogUtil.d("没有活动专区数据");
            setViewVisibility(adaptiveViewBinding.layoutActivityRegion.clActivityLayout, false);
            return;
        } else {
            setViewVisibility(adaptiveViewBinding.layoutActivityRegion.clActivityLayout, true);
        }
        int size = imageUrlNameDataList.size();
        ImageUrlNameData temp;
        for (int index = 0; index < size; index++) {
            if (index == 0) {
                temp = imageUrlNameDataList.get(index);
                Glide.with(requireContext())
                        .load(temp.getImageUrlNameData().getImgUrl())
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .into(adaptiveViewBinding.layoutActivityRegion.ivBrandZone);
                adaptiveViewBinding.layoutActivityRegion.tvBrandZoneGoodsName.setText(temp.getImageUrlNameData().getDescribe());
            } else if (index == 1) {
                temp = imageUrlNameDataList.get(index);
                LogUtil.d("setActivityRegionData " + temp.getImageUrlNameData().getImgUrl());
                Glide.with(requireContext())
                        .load(temp.getImageUrlNameData().getImgUrl())
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .into(adaptiveViewBinding.layoutActivityRegion.ivLimitKill);
                adaptiveViewBinding.layoutActivityRegion.tvLimitedKillGoodsName.setText(temp.getImageUrlNameData().getDescribe());
            } else if (index == 2) {
                temp = imageUrlNameDataList.get(index);
                Glide.with(requireContext())
                        .load(temp.getImageUrlNameData().getImgUrl())
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .into(adaptiveViewBinding.layoutActivityRegion.ivZeroDeliver);
                adaptiveViewBinding.layoutActivityRegion.tvZeroDeliverGoodsName.setText(temp.getImageUrlNameData().getDescribe());
            }
        }

    }

    private void addSortViewClickListener() {
        adaptiveViewBinding.sortTypeView.setOnSortTypeChangeListener(new SortTypeView.onSortTypeChangeListener() {
            @Override
            public void onSortPopShow() {

            }

            @Override
            public void onSortTypeChange(SortTypeEnum sortTypeEnum) {
                notifyRecommendFragmentRefresh(false);
            }

            @Override
            public void onPreferentialChange(int selectedPosition) {
                notifyRecommendFragmentRefresh(false);
            }

            @Override
            public void onScreenChange() {
                notifyRecommendFragmentRefresh(false);
            }
        });
    }

    /**
     * 清楚排序缓存
     */
    private void cleanSortTypeMap() {
        sortTypeEnumMap.clear();
    }

    /**
     * 更新排序界面
     */
    private void refreshSortType(int position) {
        SortTypeEnum sortTypeEnum = sortTypeEnumMap.get(position);
        if (sortTypeEnum == null) {
            sortTypeEnum = SortTypeEnum.SCORE;
            sortTypeEnumMap.put(position, sortTypeEnum);
        }
        refreshSortType(sortTypeEnum);
    }

    /**
     * 更新排序界面
     */
    private void refreshSortType(SortTypeEnum sortType) {
        adaptiveViewBinding.sortTypeView.setSortType(sortType);
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

    private void showCityPickerDialog() {
        if (mFragmentManager == null) {
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

    private OnPickListener getOnPickListener() {
        return new OnPickListener() {
//                    OnBDLocationListener mListener = new OnBDLocationListener();

            @Override
            public void onPick(int position, City data) {
                mLocatedCity = new LocatedCity(data.getName(), data.getProvince(), data.getCode());

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
                locationListener.onLocationChanged(new LocatedCity("深圳", "广东", "101280601"), LocateState.SUCCESS);
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
        return new BaseRecyclerAdapter<ImageUrlNameData>(mViewModel.getMyFoodDataList()) {
            private int mViewType[] = {1, 2};    //1 大图  2 小图

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
                if (position > 9) {
                    return mViewType[1];
                } else {
                    return mViewType[0];
                }
            }
        };
    }

    private MyBaseRecyclerAdapter<ExclusiveShopData> getExclusiveRecyclerAdapter() {
        return new MyBaseRecyclerAdapter<ExclusiveShopData>(R.layout.item_waimai_exclusive_shop
                , mViewModel.getExclusiveShopData(), BR.item) {
            @Override
            protected void initView(ViewDataBinding viewDataBinding, BaseViewHolder helper, ExclusiveShopData item) {
                super.initView(viewDataBinding, helper, item);
                ItemWaimaiExclusiveShopBinding binding = (ItemWaimaiExclusiveShopBinding) viewDataBinding;
                binding.tvRecent.setText(getString(R.string.recent_place_order_count, String.valueOf(item.getRecent())));
                if (item.getGoodsList().size() > 0) {
                    Glide.with(helper.itemView.getContext())
                            .load(item.getGoodsList().get(0).getGoodsImgUrl())
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .into(binding.ivGoodsIcon);
                }
            }
        };
    }

    /**
     * 更新TabBar样式
     *
     * @param titles
     * @param position
     * @param refreshContent 若更新内容，则会重新创建tab、fragment等，否则仅更新tab样式
     */
    private void refreshTabAndViewPager(String[] titles, int position, boolean refreshContent) {
        if (refreshContent) {
            cleanSortTypeMap();
            refreshSortType(position);    //先重新刷新排序内容
            resetTabAndViewPager(titles, position);
        } else {
            refreshSortType(position);    //先重新刷新排序内容
            resetTab(titles, position);
        }
        adaptiveViewBinding.stickyView.notifyDataChanged();
    }

    /**
     * 添加tab
     *
     * @param tabSegment
     * @param adapter
     * @param titles
     * @param textSizeSelected
     * @param textSizeNormal
     * @param selectedPosition
     */
    private void addTab(TabSegment tabSegment, FragmentAdapter<BaseFragment> adapter,
                        String[] titles, int textSizeSelected, int textSizeNormal, int selectedPosition) {
        int position = 0;
        for (String title : titles) {
            MyTabSegmentTab tab = new MyTabSegmentTab(title);
            tab.setTextSize(position == selectedPosition ? textSizeSelected : textSizeNormal);
            tab.setTextColor(getResources().getColor(R.color.text_tip), getResources().getColor(R.color.text_normal));
            adapter.addFragment(mViewModel.getRecommendedFragment(title), title);
            tabSegment.addTab(tab);
            position++;
        }
    }

    int textSizeSelectedScale = 0;
    int textSizeNormalScale = 0;

    /**
     * 仅更新 title 样式（切换选中时调用，内容不变）
     *
     * @param titles
     * @param selectedPosition
     */
    private void resetTab(String[] titles,
                          int selectedPosition) {
        adaptiveViewBinding.stickyView.reset();
        int position = 0;
        if (textSizeSelectedScale == 0 || textSizeNormalScale == 0) {
            textSizeSelectedScale = (int) UIUtils.getInstance().scalePx(textSizeSelected);
            textSizeNormalScale = (int) UIUtils.getInstance().scalePx(textSizeNormal);
        }
        for (String title : titles) {
            MyTabSegmentTab tab = new MyTabSegmentTab(title);
            tab.setTextSize(position == selectedPosition ? textSizeSelectedScale : textSizeNormalScale);
            adaptiveViewBinding.stickyView.addTab(tab);
            position++;
        }
        //adaptiveViewBinding.stickyView.selectTab(selectedPosition);   //不更改index 由于内部又动画实现，无法立刻获取到正确的index
    }

    /**
     * 更新title viewPager内容(重新创建)
     *
     * @param titles
     * @param selectedPosition
     */
    private void resetTabAndViewPager(String[] titles, int selectedPosition) {
        if (textSizeSelectedScale == 0 || textSizeNormalScale == 0) {
            textSizeSelectedScale = (int) UIUtils.getInstance().scalePx(textSizeSelected);
            textSizeNormalScale = (int) UIUtils.getInstance().scalePx(textSizeNormal);
        }

        adaptiveViewBinding.stickyView.reset();

        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment baseFragment : fm.getFragments()) {
            ft.detach(baseFragment);
            ft.remove(baseFragment);
        }
        ft.commitNow();
        viewPagerAdapter.getTitleList().clear();
        viewPagerAdapter.getFragmentList().clear();
        addTab(adaptiveViewBinding.stickyView, viewPagerAdapter, titles, textSizeSelectedScale, textSizeNormalScale, selectedPosition);
        //adaptiveViewBinding.stickyView.selectTab(selectedPosition); //不更改index 由于内部又动画实现，无法立刻获取到正确的index
        viewPagerAdapter.notifyDataSetChanged();

    }

    /**
     * 当专属早餐与活动区域获取到数据后，展示底部内容
     */
    private void showBottomContent() {
        if (mHolder.getCurState() != STATUS_LOAD_SUCCESS) {
            setViewVisibility(adaptiveViewBinding.stickyView, true);
            setViewVisibility(adaptiveViewBinding.contentLayout, true);

            showContent();
            adaptiveViewBinding.stickyNavigationLayout.setNeedResetCanScrollDistance(true);
            adaptiveViewBinding.stickyNavigationLayout.setCustomCanScrollDistance(StickyNavigationLayout.CAN_SCROLL_DISTANCE_ADJUST_TOP_VIEW);
            adaptiveViewBinding.stickyNavigationLayout.scrollTo(0, 0);  // FIXME: 2021/2/1 优化显示动画 滚动过快 高度出错

            //请求子标签
            mViewModel.refreshRecommendedTitle();
        }
    }

    /**
     * 通知碎片刷新数据
     * both,刷新全部，否则刷新当前显示碎片的内容
     */
    private void notifyRecommendFragmentRefresh(boolean both) {
        WaimaiRecommendedFragment fragment;
        if (both) {
            for (BaseFragment baseFragment : viewPagerAdapter.getFragmentList()) {
                fragment = (WaimaiRecommendedFragment) baseFragment;
                refreshRecommendedFragment(fragment);
            }
        } else {
            //int position = adaptiveViewBinding.stickyView.getSelectedIndex();
            if (pageTypeSelectedIndex < 0 || !(viewPagerAdapter.getFragmentList().size() > pageTypeSelectedIndex)) {
                LogUtil.e("刷新失败，下标出错或超出 position:" + pageTypeSelectedIndex);
                return;
            }
            fragment =
                    (WaimaiRecommendedFragment) viewPagerAdapter.getFragmentList().get(pageTypeSelectedIndex);
            refreshRecommendedFragment(fragment);
        }
    }

    /**
     * 刷新推荐fragment的数据
     *
     * @param fragment
     */
    private void refreshRecommendedFragment(WaimaiRecommendedFragment fragment) {
        //解析优惠活动标签
        String[] selectedStrings = adaptiveViewBinding.sortTypeView.getSelectedPreferential();
        List<String> activityTypeList = new ArrayList<>();
        if (selectedStrings.length > 0) {
            LogUtil.d(selectedStrings[0]);
            for (String s : selectedStrings) {
                if (Constant.PREFERENTIAL_TABS.contains(s)) {
                    activityTypeList.add(
                            Constant.PREFERENTIAL_TABS_INDEX.get(Constant.PREFERENTIAL_TABS.indexOf(s)));
                }
            }
        }
        if (activityTypeList.size() > 0) {
            fragment.setActivityType(activityTypeList.toArray(selectedStrings));
        } else {
            fragment.setActivityType(new String[]{});
        }

        fragment.setSortRules(adaptiveViewBinding.sortTypeView.getCurrentSortTypeEnum());
        fragment.setShopCategory(child_sign);

        fragment.setScreenData(String.valueOf(adaptiveViewBinding.sortTypeView.getMinPrice()),
                String.valueOf(adaptiveViewBinding.sortTypeView.getMaxPrice()));

        fragment.refreshListDate();
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
