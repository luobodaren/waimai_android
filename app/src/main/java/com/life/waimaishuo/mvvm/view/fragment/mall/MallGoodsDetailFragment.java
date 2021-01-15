package com.life.waimaishuo.mvvm.view.fragment.mall;

import android.os.Bundle;

import com.life.waimaishuo.R;
import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.databinding.FragmentMallGoodsDetailBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mall.MallGoodsDetailViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "商城商品详情页", anim = CoreAnim.slide)
public class MallGoodsDetailFragment extends BaseFragment {

    private final static String KEY_GOODS_DATA = "goods";

    private FragmentMallGoodsDetailBinding mBinding;
    private MallGoodsDetailViewModel mViewModel;

    public static void openPageWithGoodsId(BaseFragment baseFragment, Goods goods) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_GOODS_DATA,goods);
        baseFragment.openPage(MallGoodsDetailFragment.class,bundle);
    }

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new MallGoodsDetailViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentMallGoodsDetailBinding)mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mall_goods_detail;
    }

    @Override
    protected void initArgs() {
        super.initArgs();

        setFitStatusBarHeight(true);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_DARK);

        Bundle bundle = getArguments();
        if(bundle == null){
            throw new Error("获取不到order数据，无法进行页面展示");
        }
        mViewModel.setGoods(bundle.getParcelable(KEY_GOODS_DATA));
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();

    }

    @Override
    protected void initListeners() {
        super.initListeners();
    }
}
