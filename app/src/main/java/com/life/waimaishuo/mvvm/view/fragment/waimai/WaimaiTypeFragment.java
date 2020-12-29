package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.SelectedPositionRecylerViewAdapter;
import com.life.waimaishuo.bean.ui.IconStrData;
import com.life.waimaishuo.databinding.FragmentWaimaiTypeBinding;
import com.life.waimaishuo.enumtype.SortTypeEnum;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.WaiMaiTypeViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.views.SortTypeView;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

import java.util.List;

@Page(name = "外卖子类型页",anim = CoreAnim.slide)
public class WaimaiTypeFragment extends BaseFragment {

    public static final String BUNDLE_FOOD_TYPE_STR_KEY = "my_food_type";
    private String mFoodType;

    private WaiMaiTypeViewModel mViewModel;
    private FragmentWaimaiTypeBinding mBinding;

    SelectedPositionRecylerViewAdapter<IconStrData> adapter;

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
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
        setStatusBarShowByType(SHOW_STATUS_BAR);

        mFoodType = getArguments().getString(BUNDLE_FOOD_TYPE_STR_KEY);
    }

    @Override
    protected void onFragmentShow() {
        super.onFragmentShow();
    }

    @Override
    protected void initViews() {
        super.initViews();
        initTitleView();
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
        adapter.setSelectedListener((holder, item) -> {
            mFoodType = item.getIconType();
            mBinding.recyclerFoodSubtype.scrollToPosition(adapter.getSelectedPosition());
            handleSelectedSign();   //重置recyclerView底部延伸栏状态
            refreshSortType(SortTypeEnum.SCORE);
            refreshShopContent();
        });

        addOnScrollListener(mBinding.recyclerFoodSubtype);
    }

    /**
     * 隐藏分享按钮
     */
    private void initTitleView(){
        View view = getRootView().findViewById(R.id.iv_share);
        if(view != null){
            view.setVisibility(View.GONE);
        }
    }

    LinearLayoutManager layoutManager;
    private void initSubtypeRecycler(){
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        mBinding.recyclerFoodSubtype.setLayoutManager(layoutManager);

        adapter = getSubtypeRecyclerAdapter();
        mBinding.recyclerFoodSubtype.setAdapter(adapter);
        mBinding.recyclerFoodSubtype.addItemDecoration(new RecyclerView.ItemDecoration() {
            int left_interval_22 = (int)UIUtils.getInstance(getContext()).scalePx(22);
            int left_interval_8 = (int)UIUtils.getInstance(getContext()).scalePx(8);

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                outRect.left = (position == 0) ? left_interval_22 : left_interval_8;
                outRect.right = (position == state.getItemCount()-1) ? left_interval_22 : 0;
            }
        });

        //往上移 覆盖部分选中标志 已达到选中标志滚动，背景延伸的效果
        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) mBinding.stickyNavigationLayout.getLayoutParams();
        layoutParams.topMargin = -getResources().getDimensionPixelOffset(R.dimen.waimai_subtype_content_bg_radius);

        initFirstPosition();
    }

    /**
     * 初始化页面展示的初始数据
     */
    private void initFirstPosition() {
        if(mFoodType != null && !"".equals(mFoodType)){
            int position = 0;
            for (IconStrData iconStrData : adapter.getData()) {
                if(mFoodType.equals(iconStrData.getIconType())){
                    mBinding.recyclerFoodSubtype.scrollToPosition(position);
                    adapter.setSelectedPosition(position);
//                    handleSelectedSign();   //重置recyclerView底部延伸栏状态 未进行绘制无法调用 也无需调用
//                    refreshSortType(SortTypeEnum.SCORE);
//                    refreshShopContent();
                    break;
                }
                position++;
            }
        }
    }


    private SelectedPositionRecylerViewAdapter<IconStrData> getSubtypeRecyclerAdapter() {
        return new SelectedPositionRecylerViewAdapter<IconStrData>(mViewModel.getSubtypeTitles()){
            @Override
            public int getLayoutId() {
                return R.layout.item_waimai_food_subtype;
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, boolean selected, IconStrData item) {
                View view = holder.getView(R.id.ll_selected_sign);
                if(view.getVisibility() != View.VISIBLE)
                    view.setVisibility(View.VISIBLE);

                holder.setImageResource(R.id.iv_subtype_icon,item.getResImgId());
                holder.setText(R.id.tv_subtype_name,item.getIconType());
                if(selected){
                    holder.setBackgroundRes(R.id.tv_subtype_name,R.drawable.sr_bg_full_corners_white);
                    holder.setTextColor(R.id.tv_subtype_name,getResources().getColor(R.color.colorTheme));
                    holder.setVisible(R.id.iv_selected_sign,true);
                    holder.setVisible(R.id.view_tl_tr_24_radius,true);
                }else{
                    holder.setBackgroundColor(R.id.tv_subtype_name,getResources().getColor(R.color.transparent));
                    holder.setTextColor(R.id.tv_subtype_name,getResources().getColor(R.color.white));
                    holder.setVisible(R.id.iv_selected_sign,false);
                    holder.setVisible(R.id.view_tl_tr_24_radius,false);
                }
            }
        };
    }

    private void addOnScrollListener(RecyclerView recyclerFoodSubtype) {
        recyclerFoodSubtype.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                handleSelectedSign();
            }
        });
    }

    private int currentSelectedPosition = -1;
    private View selectedView;
    private int halfWidth = -1;
    /**
     * 处理店铺内容背景样式
     * 根据外部recyclerView的选中与滚动状态决定
     */
    private void handleSelectedSign(){
        int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
        int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
        if(currentSelectedPosition != adapter.getSelectedPosition()){
            currentSelectedPosition = adapter.getSelectedPosition();
            selectedView = layoutManager.getChildAt(adapter.getSelectedPosition()-firstVisiblePosition);
            if(halfWidth == -1){
                halfWidth = selectedView.getWidth()/2;  //仅计算一次
            }
        }

        if(firstVisiblePosition > currentSelectedPosition){ //左滑消失
            setStickyNavigationLayoutBackground(R.drawable.sr_bg_tr_24radius);
        }else if(lastVisiblePosition < currentSelectedPosition){    //右滑消失
            setStickyNavigationLayoutBackground(R.drawable.sr_bg_tl_24radius);
        }else{
            //选中的itemView显示在屏幕上
            Rect rect = new Rect();
            if(firstVisiblePosition == adapter.getSelectedPosition()){  //第一个为选中的
                selectedView = layoutManager.getChildAt(0);
                selectedView.getGlobalVisibleRect(rect);
                if((rect.right-rect.left) < halfWidth){
                    setStickyNavigationLayoutBackground(R.drawable.sr_bg_tr_24radius);
                }else{
                    setStickyNavigationLayoutBackground(R.drawable.sr_bg_tl_tr_24radius);
                }
            }else if(lastVisiblePosition == adapter.getSelectedPosition()){ //最后一个为选中的
                selectedView = layoutManager.getChildAt(layoutManager.getChildCount() - 1);
                if(selectedView.getGlobalVisibleRect(rect)){
                    //若显示
                    if((rect.right-rect.left) < halfWidth){
                        setStickyNavigationLayoutBackground(R.drawable.sr_bg_tl_24radius);
                    }else{
                        setStickyNavigationLayoutBackground(R.drawable.sr_bg_tl_tr_24radius);
                    }
                }else{
                    //若不显示
                    setStickyNavigationLayoutBackground(R.drawable.sr_bg_tl_24radius);
                }
            }else{  //显示在中间
                setStickyNavigationLayoutBackground(R.drawable.sr_bg_tl_tr_24radius);
            }
        }
    }

    private int currentStickyLayoutBackgroundId = -1;
    private void setStickyNavigationLayoutBackground(@DrawableRes int drawableId){
        if(currentStickyLayoutBackgroundId != drawableId){
            currentStickyLayoutBackgroundId = drawableId;
            mBinding.stickyNavigationLayout.setBackgroundResource(drawableId);
        }
    }

    private void initSortView(){
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

        });
    }

    private void initShopContent() {
        FragmentManager fm= getChildFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();

        ft.add(R.id.adaptive_size_view,mViewModel.getRecommendedFragment(),null);
        ft.commit();
    }

    private void refreshSortType(SortTypeEnum sortType){
        mBinding.stickyView.setSortType(sortType);
    }

    /**
     * 重置内容（排序 或重新获取列表数据）
     */
    private void refreshShopContent() {
        LogUtil.e("重置内容");
    }

}
