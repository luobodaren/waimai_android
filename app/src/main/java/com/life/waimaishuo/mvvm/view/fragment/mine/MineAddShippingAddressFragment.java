package com.life.waimaishuo.mvvm.view.fragment.mine;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.life.waimaishuo.R;
import com.life.waimaishuo.bean.api.request.bean.AddShippingAddress;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.databinding.FragmentMineAddNewShippingAddressBinding;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.view.fragment.city.CityPickerDialogFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mine.MineAddShippingAddressViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.citypicker.CityPicker;
import com.xuexiang.citypicker.adapter.OnLocationListener;
import com.xuexiang.citypicker.adapter.OnPickListener;
import com.xuexiang.citypicker.model.City;
import com.xuexiang.citypicker.model.LocateState;
import com.xuexiang.citypicker.model.LocatedCity;
import com.xuexiang.xaop.annotation.Permission;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

import java.lang.ref.WeakReference;

import static android.Manifest.permission_group.LOCATION;

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

                setFragmentResult(Constant.RESULT_CODE_FALSE,null);
                popToBack();
            }
        });
        mBinding.btSave.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                // TODO: 2021/1/25 上传新增地址信息

                //AddShippingAddress addShippingAddress = new AddShippingAddress()

                //成功
                setFragmentResult(Constant.RESULT_CODE_SUCCESS,null);
                popToBack();
            }
        });

        mBinding.tvRegionValue.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                openPageForResult(MineLocalAddressFragment.class,null,Constant.REQUEST_CODE_LOCAL_ADDRESS);
            }
        });
    }

    private void initTitle(){
        mBinding.layoutTitle.tvTitle.setText(getPageName());
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if(resultCode == Constant.RESULT_CODE_SUCCESS){
            if(requestCode == Constant.REQUEST_CODE_LOCAL_ADDRESS){
                //获取到地址信息
            }
        }
    }
}
