package com.example.myapplication.mvvm.view.fragment.waimai;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentShopDetailBinding;
import com.example.myapplication.mvvm.view.fragment.BaseFragment;
import com.example.myapplication.mvvm.vm.BaseViewModel;
import com.example.myapplication.mvvm.vm.waimai.ShopDetailViewModel;
import com.example.myapplication.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;

@Page(name = "店铺详情页", anim = CoreAnim.slide)
public class ShopDetailFragment extends BaseFragment {

    ShopDetailViewModel viewModel;

    @Override
    protected BaseViewModel setViewModel() {
        if(viewModel == null){
            viewModel = new ShopDetailViewModel();
        }
        return viewModel;
    }

    @Override
    protected void bindViewModel() {
        ((FragmentShopDetailBinding)mViewDataBinding).setViewModel(viewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shop_detail;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setFitStatusBarHeight(true);
        setmStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_DARK);
    }

    @Override
    protected void initViews() {
        super.initViews();
    }

    @Override
    protected void onLifecycleResume() {
        super.onLifecycleResume();
        changeStatusBarMode();
    }
}
