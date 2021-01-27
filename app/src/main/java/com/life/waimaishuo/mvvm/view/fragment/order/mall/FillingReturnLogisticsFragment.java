package com.life.waimaishuo.mvvm.view.fragment.order.mall;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.life.waimaishuo.R;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.databinding.FragmentFillingLogisticsOrderBinding;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "退货退款——填写物流单号")
public class FillingReturnLogisticsFragment extends BaseFragment {

    FragmentFillingLogisticsOrderBinding mBinding;

    @Override
    protected BaseViewModel setViewModel() {
        return null;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentFillingLogisticsOrderBinding) mViewDataBinding;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_filling_logistics_order;
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
        initSubmitButton();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
    }

    private void initTitle(){
        mBinding.layoutTitle.tvTitle.setText(R.string.refund_return);
        setViewVisibility(mBinding.layoutTitle.ivShare,false);
    }

    private void initSubmitButton(){
        mBinding.btSubmit.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                String logisticsNumber = mBinding.etFillingLogisticsNumber.getText().toString();
                if(logisticsNumber.length() <= 0){
                    Toast.makeText(requireContext(), "物流单号不可为空", Toast.LENGTH_SHORT).show();
                }else{
                    String phoneNumber = mBinding.etFillingPhoneNumber.getText().toString();
                    if(phoneNumber.length() <= 0){
                        Toast.makeText(requireContext(), "联系手机号不可为空", Toast.LENGTH_SHORT).show();
                    }else{
                        // FIXME: 2021/1/12 网络请求 填写物流单号成功则返回
                        resultCode = Constant.RESULT_CODE_SUCCESS;
                        popToBack();
                    }
                }
            }
        });
    }

}
