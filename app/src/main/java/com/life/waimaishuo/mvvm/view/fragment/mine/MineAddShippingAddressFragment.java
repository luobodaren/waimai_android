package com.life.waimaishuo.mvvm.view.fragment.mine;

import android.content.res.ColorStateList;
import android.view.View;

import com.life.waimaishuo.R;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.databinding.FragmentMineAddNewShippingAddressBinding;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mine.MineAddShippingAddressViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "新增地址", anim = CoreAnim.slide)
public class MineAddShippingAddressFragment extends BaseFragment {

    private FragmentMineAddNewShippingAddressBinding mBinding;
    private MineAddShippingAddressViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new MineAddShippingAddressViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentMineAddNewShippingAddressBinding) mViewDataBinding;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_add_new_shipping_address;
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
        mBinding.btCancel.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                popToBackWithFailResult();
            }
        });
        mBinding.btSave.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                // TODO: 2021/1/25 上传新增地址信息

                //成功
                resultCode = Constant.RESULT_CODE_SUCCESS;
                popToBack();
            }
        });
    }

    private void popToBackWithFailResult(){
        resultCode = Constant.RESULT_CODE_FALSE;
        popToBack();
    }

    private void initTitle(){
        mBinding.layoutTitle.tvTitle.setText(getPageName());
    }

}
