package com.life.waimaishuo.mvvm.view.fragment.mine;

import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;

import com.life.waimaishuo.R;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.databinding.FragmentMineCreateShopBinding;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mine.MineCreateShopViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "新建店铺", anim = CoreAnim.slide)
public class MineCreateShopFragment extends BaseFragment {

    private FragmentMineCreateShopBinding mBinding;
    private MineCreateShopViewModel mViewModel;


    @Override
    protected BaseViewModel setViewModel() {
        if (mViewModel == null) {
            mViewModel = new MineCreateShopViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentMineCreateShopBinding) mViewDataBinding;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_create_shop;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setFitStatusBarHeight(true);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_LIGHT);
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();

        initTitle();


    }

    @Override
    protected void initListeners() {
        super.initListeners();

        mBinding.layoutTitle.ivBack.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                popToBack();
            }
        });

        mBinding.clBusinessCategory.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                MineSelectCategoryFragment.openMyPage(MineCreateShopFragment.this, Constant.REQUEST_CODE_SELECT_CATEGORY);
            }
        });
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if(requestCode == Constant.REQUEST_CODE_SELECT_CATEGORY){
            if(resultCode == Constant.RESULT_CODE_SUCCESS){
                String type = data.getStringExtra(MineSelectCategoryFragment.RESULT_KEY_CATEGORY_TYPE);
                mBinding.tvBusinessCategory.setText(type);  // FIXME: 2021/1/26
            }
        }
    }

    private void initTitle() {
        mBinding.layoutTitle.tvTitle.setText(R.string.shop_info);
    }

}
