package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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
import com.life.waimaishuo.databinding.FragmentZeroPriceDividerBinding;
import com.life.waimaishuo.enumtype.SortTypeEnum;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.WaiMaiZeroDividerViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.WaimaiLimitedViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.views.SortTypeView;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

import java.util.List;

@Page(name = "0元配送", anim = CoreAnim.slide)
public class WaiMaiZeroDividerFragment extends BaseFragment {

    private FragmentZeroPriceDividerBinding mBinding;
    private WaiMaiZeroDividerViewModel mViewModel;

    private SelectedPositionRecylerViewAdapter<IconStrData> adapter;
    private LinearLayoutManager layoutManager;

    private String mFoodType;   //选中的食物类别

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new WaiMaiZeroDividerViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentZeroPriceDividerBinding) mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zero_price_divider;
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
        initSubtypeRecycler();
        initSortView();
        initShopContent();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        adapter.setSelectedListener((holder, item) -> {
            mFoodType = item.getIconType();
            mBinding.recyclerFoodSubtype.scrollToPosition(adapter.getSelectedPosition());
            refreshSortType(SortTypeEnum.SCORE);
            refreshShopContent();
        });
    }

    private void initSubtypeRecycler(){
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        mBinding.recyclerFoodSubtype.setLayoutManager(layoutManager);

        adapter = getSubtypeRecyclerAdapter();
        mBinding.recyclerFoodSubtype.setAdapter(adapter);
        mBinding.recyclerFoodSubtype.addItemDecoration(new RecyclerView.ItemDecoration() {
            int left_interval_22 = -1;
            int left_interval_8 = -1;

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                if(left_interval_22 == -1){
                    left_interval_22 = (int) UIUtils.getInstance(getContext()).scalePx(22);
                }
                if(left_interval_8 == -1){
                    left_interval_8 = (int)UIUtils.getInstance(getContext()).scalePx(8);
                }
                outRect.left = (position == 0) ? left_interval_22 : left_interval_8;
                outRect.right = (position == state.getItemCount()-1) ? left_interval_22 : 0;
            }
        });

        //往上移 覆盖部分选中标志 已达到选中标志滚动，背景延伸的效果
        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) mBinding.stickyNavigationLayout.getLayoutParams();
        layoutParams.topMargin = -getResources().getDimensionPixelOffset(R.dimen.waimai_subtype_content_bg_radius);
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
            public void onPreferentialChange(List<Integer> selectedList) {
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

    private SelectedPositionRecylerViewAdapter<IconStrData> getSubtypeRecyclerAdapter() {
        return new SelectedPositionRecylerViewAdapter<IconStrData>(mViewModel.getSubtypeTitles()){
            @Override
            public int getLayoutId() {
                return R.layout.item_waimai_food_subtype;
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, boolean selected, IconStrData item) {
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
