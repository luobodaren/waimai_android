package com.life.waimaishuo.mvvm.view.fragment.mall;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.BR;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.databinding.FragmentMallShopRecommendBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mall.MallShopViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "商城店铺推荐页")
public class MallShopRecommendFragment extends BaseFragment {

    private FragmentMallShopRecommendBinding mBinding;

    @Override
    protected BaseViewModel setViewModel() {
        return baseViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentMallShopRecommendBinding)mViewDataBinding;
        mBinding.setViewModel((MallShopViewModel) baseViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mall_shop_recommend;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setFitStatusBarHeight(false);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_NO_HANDLE);
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();

        initGoodsTypeRecycler();
        initGoodsRecycler();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
    }

    private void initGoodsTypeRecycler(){
        mBinding.recyclerGoodsType.setLayoutManager(
                new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false));
        mBinding.recyclerGoodsType.setAdapter(
                new MyBaseRecyclerAdapter(R.layout.item_recycler_mall_shop_recommend_goods_type,
                        ((MallShopViewModel)baseViewModel).getGoodsTypeData(), com.life.waimaishuo.BR.item));
        mBinding.recyclerGoodsType.addItemDecoration(new RecyclerView.ItemDecoration() {
            int interval = (int) UIUtils.getInstance().scalePx(getResources().getDimensionPixelOffset(R.dimen.interval_size_xs));

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = interval;
                if(parent.getChildAdapterPosition(view) == state.getItemCount()-1){
                    outRect.right = interval;
                }
            }
        });
    }

    private void initGoodsRecycler(){
        mBinding.recyclerGoods.setLayoutManager(
                new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.VERTICAL,false));
        mBinding.recyclerGoods.setAdapter(
                new MyBaseRecyclerAdapter(R.layout.item_recycler_mall_shop_recommend_goods,
                        ((MallShopViewModel)baseViewModel).getGoodsData(),com.life.waimaishuo.BR.item));
        mBinding.recyclerGoods.addItemDecoration(new RecyclerView.ItemDecoration() {
            int interval = (int) UIUtils.getInstance().scalePx(getResources().getDimensionPixelOffset(R.dimen.interval_size_xs));
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = interval;
            }
        });

    }
}
