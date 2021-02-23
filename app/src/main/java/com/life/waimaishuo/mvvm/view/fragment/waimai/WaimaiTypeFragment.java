package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Observable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.Global;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.SelectedPositionRecyclerViewAdapter;
import com.life.waimaishuo.bean.api.request.WaiMaiReqData;
import com.life.waimaishuo.bean.api.request.bean.RecommendReqData;
import com.life.waimaishuo.bean.ui.ImageUrlNameData;
import com.life.waimaishuo.databinding.FragmentWaimaiTypeBinding;
import com.life.waimaishuo.enumtype.SortTypeEnum;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.WaiMaiTypeViewModel;
import com.life.waimaishuo.util.MyDataBindingUtil;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.views.SortTypeView;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

// FIXME: 2021/2/20 从全部分类打开时 存在不显示顶部recycler的情况

@Page(name = "外卖子类型页", anim = CoreAnim.slide)
public class WaimaiTypeFragment extends BaseFragment {

    public static final String BUNDLE_FOOD_TYPE_STR_KEY = "my_food_type";
    public static final String BUNDLE_SUB_TYPE_STR_KEY = "my_sub_type";

    private WaiMaiTypeViewModel mViewModel;
    private FragmentWaimaiTypeBinding mBinding;

    private WaimaiRecommendedFragment recommendedFragment;

    private String mFoodType;   //外卖首页点击传入类型
    private String mSelectedSubType;    //选中类型子集
    private boolean isSubFormAllType = false;

    private SelectedPositionRecyclerViewAdapter<ImageUrlNameData> adapter;

    @Override
    protected BaseViewModel setViewModel() {
        if (mViewModel == null) {
            mViewModel = new WaiMaiTypeViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentWaimaiTypeBinding) mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_waimai_type;
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setFitStatusBarHeight(true);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_DARK);
        setStatusBarShowType(SHOW_STATUS_BAR);

        mFoodType = getArguments().getString(BUNDLE_FOOD_TYPE_STR_KEY);
        mSelectedSubType = getArguments().getString(BUNDLE_SUB_TYPE_STR_KEY);
        if(mSelectedSubType != null && !"".equals(mSelectedSubType)){
            isSubFormAllType = true;
        }
    }

    @Override
    protected void onFragmentShow() {
        super.onFragmentShow();
    }

    @Override
    protected void initViews() {
        super.initViews();
        initTitleView();

        initTopBgImg();

        initSubtypeRecycler();
        initSortView();
        initShopContent();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        addCallBack();

        adapter.setSelectedListener((holder, item, isCancel) -> {
            mSelectedSubType = item.getName();
            mBinding.recyclerFoodSubtype.smoothScrollToPosition(adapter.getSelectedPosition());
            handleSelectedSign(2);   //重置recyclerView底部延伸栏状态
            refreshSortType(SortTypeEnum.SCORE);
            refreshShopContent();
        });

        addOnScrollListener(mBinding.recyclerFoodSubtype);
    }

    @Override
    protected void firstRequestData() {
        super.firstRequestData();

        mViewModel.refreshSubTypeTitles(mFoodType);

    }

    private void addCallBack() {
        MyDataBindingUtil.addCallBack(this, mViewModel.subtypeObservableInt, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                LogUtil.d("title size:" + mViewModel.getSubtypeTitles().size());
                mHandler.post(() -> {
                    adapter.setData(mViewModel.getSubtypeTitles());
                    adapter.notifyDataSetChanged();
                    if(mViewModel.getSubtypeTitles().size() > 0){
                        //判断是否需要设置选中的子类型为第一个（例：由全部类型点击进来时，无需设置）
                        if(!isSubFormAllType){
                            mSelectedSubType = mViewModel.getSubtypeTitles().get(0).getName();
                        }else{
                            //由全部类型点击进入 且存在该子类型时 无需设置，但下次刷新需要设置
                            isSubFormAllType = false;
                            boolean isContain = false;
                            int position = 0;
                            //计算位置 滚动到该位置
                            for(ImageUrlNameData imageUrlNameData: mViewModel.getSubtypeTitles()){
                                if(imageUrlNameData.getName().equals(mSelectedSubType)){
                                    isContain = true;
                                    break;
                                }
                                position++;
                            }
                            if(isContain){  //存在该子类型时
                                adapter.setSelectedPosition(position);
                                mBinding.recyclerFoodSubtype.scrollToPosition(position);
                            }else{
                                mSelectedSubType = mViewModel.getSubtypeTitles().get(0).getName();
                            }
                        }
                    }else{
                        mSelectedSubType = "";
                    }
                    refreshShopContent();  //刷新内容
                });
            }
        });

    }

    /**
     * 隐藏分享按钮
     */
    private void initTitleView() {
        View view = getRootView().findViewById(R.id.iv_share);
        if (view != null) {
            view.setVisibility(View.GONE);
        }
    }

    private void initTopBgImg(){
        int bgImgId = R.mipmap.png_bg_food_type_drink;
        String[] waimaiFoodTypes = getResources().getStringArray(R.array.waimai_food_types);
        int i = 0;
        for(String type : waimaiFoodTypes){
            if(mFoodType.equals(type)){
                bgImgId = mViewModel.getTopBgImgId(i);
                break;
            }
            i++;
        }
        mBinding.ivTopBgImage.setImageResource(bgImgId);
    }

    LinearLayoutManager layoutManager;
    private void initSubtypeRecycler() {
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mBinding.recyclerFoodSubtype.setLayoutManager(layoutManager);

        adapter = getSubtypeRecyclerAdapter();
        mBinding.recyclerFoodSubtype.setAdapter(adapter);
        mBinding.recyclerFoodSubtype.addItemDecoration(new RecyclerView.ItemDecoration() {
            int left_interval_22 = (int) UIUtils.getInstance().scalePx(22);
            int left_interval_8 = (int) UIUtils.getInstance().scalePx(8);

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                outRect.left = (position == 0) ? left_interval_22 : left_interval_8;
                outRect.right = (position == state.getItemCount() - 1) ? left_interval_22 : 0;
            }
        });

        //往上移 覆盖部分选中标志 已达到选中标志滚动，背景延伸的效果
        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) mBinding.stickyNavigationLayout.getLayoutParams();
        layoutParams.topMargin = -getResources().getDimensionPixelOffset(R.dimen.my_card_view_default_radius);
    }

    private SelectedPositionRecyclerViewAdapter<ImageUrlNameData> getSubtypeRecyclerAdapter() {
        return new SelectedPositionRecyclerViewAdapter<ImageUrlNameData>(mViewModel.getSubtypeTitles()) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_waimai_food_subtype;
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, boolean selected, ImageUrlNameData item) {
                View view = holder.getView(R.id.ll_selected_sign);
                if (view.getVisibility() != View.VISIBLE)
                    view.setVisibility(View.VISIBLE);

                Glide.with(holder.getView(R.id.iv_subtype_icon).getContext())
                        .load(item.getImgUrl())
                        .centerCrop()
                        .into((ImageView) holder.getView(R.id.iv_subtype_icon));
                holder.setText(R.id.tv_subtype_name, item.getName());

                if (selected) {
                    holder.setBackgroundRes(R.id.tv_subtype_name, R.drawable.sr_bg_full_corners_white);
                    holder.setTextColor(R.id.tv_subtype_name, getResources().getColor(R.color.colorTheme));
                    holder.setVisible(R.id.iv_selected_sign, true);
                    holder.setVisible(R.id.view_tl_tr_24_radius, true);
                } else {
                    holder.setBackgroundColor(R.id.tv_subtype_name, getResources().getColor(R.color.transparent));
                    holder.setTextColor(R.id.tv_subtype_name, getResources().getColor(R.color.white));
                    holder.setVisible(R.id.iv_selected_sign, false);
                    holder.setVisible(R.id.view_tl_tr_24_radius, false);
                }
            }
        };
    }

    private void addOnScrollListener(RecyclerView recyclerFoodSubtype) {
        recyclerFoodSubtype.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                handleSelectedSign(1);
            }
        });
    }

    private int currentSelectedPosition = -1;
    private View selectedView;
    private int halfWidth = -1;

    /**
     * 处理店铺内容背景样式
     * 根据外部recyclerView的选中与滚动状态决定
     *
     * @param type 1:滚动模式， 2:直接重置为在中间显示
     */
    private void handleSelectedSign(int type) {
        if (type == 2) {
            setStickyNavigationLayoutBackground(R.drawable.sr_bg_tl_tr_8dp);
            return;
        }
        int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
        int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
        if (currentSelectedPosition != adapter.getSelectedPosition()) { //需要保证切换选中position时，滚动到对应位置
            currentSelectedPosition = adapter.getSelectedPosition();
            //拿到当前选中的View,计算宽度
            selectedView = layoutManager.getChildAt(adapter.getSelectedPosition() - firstVisiblePosition);
            if(selectedView == null){//若selectedView不在屏幕内，会拿不到，选中第一个代替
                LogUtil.e("获取不到选中的View，使用第一个View宽度代替计算");
                selectedView = layoutManager.getChildAt(0);
            }
            if (halfWidth == -1) {
                halfWidth = selectedView.getWidth() / 2;  //仅计算一次
            }
        }

        if (firstVisiblePosition > currentSelectedPosition) { //左滑消失
            setStickyNavigationLayoutBackground(R.drawable.sr_bg_tr_8dp);
        } else if (lastVisiblePosition < currentSelectedPosition) {    //右滑消失
            setStickyNavigationLayoutBackground(R.drawable.sr_bg_tl_8dp);
        } else {
            //选中的itemView显示在屏幕上
            Rect rect = new Rect();
            if (firstVisiblePosition == adapter.getSelectedPosition()) {  //第一个为选中的
                selectedView = layoutManager.getChildAt(0);
                selectedView.getGlobalVisibleRect(rect);
                if ((rect.right - rect.left) < halfWidth) {
                    setStickyNavigationLayoutBackground(R.drawable.sr_bg_tr_8dp);
                } else {
                    setStickyNavigationLayoutBackground(R.drawable.sr_bg_tl_tr_8dp);
                }
            } else if (lastVisiblePosition == adapter.getSelectedPosition()) { //最后一个为选中的
                selectedView = layoutManager.getChildAt(layoutManager.getChildCount() - 1);
                if (selectedView.getGlobalVisibleRect(rect)) {
                    //若显示
                    if ((rect.right - rect.left) < halfWidth) {
                        setStickyNavigationLayoutBackground(R.drawable.sr_bg_tl_8dp);
                    } else {
                        setStickyNavigationLayoutBackground(R.drawable.sr_bg_tl_tr_8dp);
                    }
                } else {
                    //若不显示
                    setStickyNavigationLayoutBackground(R.drawable.sr_bg_tl_8dp);
                }
            } else {  //显示在中间
                setStickyNavigationLayoutBackground(R.drawable.sr_bg_tl_tr_8dp);
            }
        }
    }

    private int currentStickyLayoutBackgroundId = -1;

    private void setStickyNavigationLayoutBackground(@DrawableRes int drawableId) {
        if (currentStickyLayoutBackgroundId != drawableId) {
            currentStickyLayoutBackgroundId = drawableId;
            mBinding.stickyNavigationLayout.setBackgroundResource(drawableId);
        }
    }

    private void initSortView() {
        mBinding.stickyView.setPreferentialTab(mViewModel.getPreferential());
        mBinding.stickyView.setOnSortTypeChangeListener(new SortTypeView.onSortTypeChangeListener() {
            @Override
            public void onSortPopShow() {
                // TODO: 2020/12/16 StickyView滚动到最上面 
            }

            @Override
            public void onSortTypeChange(SortTypeEnum sortTypeEnum) {
                refreshShopContent();
            }

            @Override
            public void onPreferentialChange(int selectedPosition) {
                refreshShopContent();
            }

            @Override
            public void onScreenChange() {

            }

        });
    }

    private void initShopContent() {
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        recommendedFragment = (WaimaiRecommendedFragment) mViewModel.getRecommendedFragment();
        ft.add(R.id.adaptive_size_view, recommendedFragment, null);
        ft.commit();
    }

    private void refreshSortType(SortTypeEnum sortType) {
        mBinding.stickyView.setSortType(sortType);
    }

    /**
     * 重置内容
     */
    private void refreshShopContent() {
        LogUtil.d("重置内容:" + mFoodType + "-" + mSelectedSubType);

        recommendedFragment.setActivityType(mBinding.stickyView.getSelectedPreferential());
        recommendedFragment.setSortRules(mBinding.stickyView.getCurrentSortTypeEnum());
        recommendedFragment.setShopCategory(mFoodType + "-" + mSelectedSubType);

        recommendedFragment.refreshListDate();
    }

    public static void openPage(BaseFragment baseFragment, String foodType, String subType) {
        Bundle bundle = new Bundle();
        bundle.putString(WaimaiTypeFragment.BUNDLE_FOOD_TYPE_STR_KEY, foodType);
        bundle.putString(WaimaiTypeFragment.BUNDLE_SUB_TYPE_STR_KEY, subType);
        baseFragment.openPage(WaimaiTypeFragment.class, bundle);
    }


}
