package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.SelectedPositionRecyclerViewAdapter;
import com.life.waimaishuo.bean.ui.IconStrData;
import com.life.waimaishuo.databinding.FragmentWaimaiZeroPriceDeliverBinding;
import com.life.waimaishuo.enumtype.SortTypeEnum;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.WaiMaiZeroDividerViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.views.SortTypeView;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "0元配送", anim = CoreAnim.slide)
public class ZeroDividerFragment extends BaseFragment {

    private FragmentWaimaiZeroPriceDeliverBinding mBinding;
    private WaiMaiZeroDividerViewModel mViewModel;

    //推荐内容fragment
    private WaimaiRecommendedFragment recommendedFragment;

    private SelectedPositionRecyclerViewAdapter<IconStrData> adapter;
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
        mBinding = (FragmentWaimaiZeroPriceDeliverBinding) mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_waimai_zero_price_deliver;
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

        initTitle();

        //initSubtypeRecycler();    //不需要顶部recycler了
        setViewVisibility(mBinding.recyclerFoodSubtype,false);

        initSortView();
        initShopContent();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        if(adapter != null){
            adapter.setSelectedListener((holder, item, isCancel) -> {
                mFoodType = item.getIconType();
                mBinding.recyclerFoodSubtype.scrollToPosition(adapter.getSelectedPosition());
                refreshSortType(SortTypeEnum.SCORE);
                refreshShopContent();
            });
        }

        mBinding.stickyView.setOnSortTypeChangeListener(new SortTypeView.onSortTypeChangeListener() {
            @Override
            public void onSortPopShow() {
                // TODO: 2020/12/16 StickyView滚动到最上面
            }

            @Override
            public void onSortTypeChange(SortTypeEnum sortTypeEnum) {
                recommendedFragment.setSortRules(sortTypeEnum);
                refreshShopContent();
            }

            @Override
            public void onPreferentialChange(int selectedPosition) {
                refreshShopContent();
            }

            @Override
            public void onScreenChange() {
                recommendedFragment.setActivityType(mBinding.stickyView.getSelectedPreferential());
                recommendedFragment.setScreenData(String.valueOf(mBinding.stickyView.getMinPrice()),
                        String.valueOf(mBinding.stickyView.getMaxPrice()));
                refreshShopContent();
            }

        });
    }

    @Override
    protected void firstRequestData() {
        super.firstRequestData();

        refreshShopContent();
    }

    private void initTitle(){
        mBinding.layoutTitle.tvTypeName.setText(getPageName());
    }

    private void initSubtypeRecycler(){
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        mBinding.recyclerFoodSubtype.setLayoutManager(layoutManager);

        adapter = getSubtypeRecyclerAdapter();
        mBinding.recyclerFoodSubtype.setAdapter(adapter);
        mBinding.recyclerFoodSubtype.addItemDecoration(new RecyclerView.ItemDecoration() {
            int left_interval_22 = (int) UIUtils.getInstance().scalePx(22);
            int left_interval_8 = (int)UIUtils.getInstance().scalePx(8);

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                outRect.left = (position == 0) ? left_interval_22 : left_interval_8;
                outRect.right = (position == state.getItemCount()-1) ? left_interval_22 : 0;
            }
        });
    }

    private void initSortView(){
        mBinding.stickyView.setPreferentialTab(mViewModel.getPreferential());
        mBinding.stickyView.setScreenData(mViewModel.getScreenData());
    }

    private void initShopContent() {
        FragmentManager fm= getChildFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        recommendedFragment = mViewModel.getRecommendedFragment(requireContext());
        ft.add(R.id.adaptive_size_view, recommendedFragment,null);
        ft.commit();
    }

    private SelectedPositionRecyclerViewAdapter<IconStrData> getSubtypeRecyclerAdapter() {
        return new SelectedPositionRecyclerViewAdapter<IconStrData>(mViewModel.getSubtypeTitles()){
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_waimai_zero_deliver_subtype;
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
        recommendedFragment.refreshListDate();
    }
}
