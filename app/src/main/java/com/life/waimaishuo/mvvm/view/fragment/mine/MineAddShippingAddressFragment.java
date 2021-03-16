package com.life.waimaishuo.mvvm.view.fragment.mine;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.Observable;

import com.amap.api.services.core.PoiItem;
import com.life.base.utils.LogUtil;
import com.life.waimaishuo.Global;
import com.life.waimaishuo.R;
import com.life.waimaishuo.bean.api.request.bean.AddShippingAddress;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.databinding.FragmentMineAddNewShippingAddressBinding;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mine.MineAddShippingAddressViewModel;
import com.life.waimaishuo.util.MyDataBindingUtil;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "新增地址", anim = CoreAnim.slide)
public class MineAddShippingAddressFragment extends BaseFragment {

    private FragmentMineAddNewShippingAddressBinding mBinding;
    private MineAddShippingAddressViewModel mViewModel;

    private PoiItem poiItem;    //保存选择地址所返回兴趣点

    @Override
    protected BaseViewModel setViewModel() {
        if (mViewModel == null) {
            mViewModel = new MineAddShippingAddressViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentMineAddNewShippingAddressBinding) mViewDataBinding;
        mBinding.setViewModel(mViewModel);
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
        addCallBack();

        mBinding.btCancel.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                setFragmentResult(Constant.RESULT_CODE_FALSE, null);
                popToBack();
            }
        });
        mBinding.btSave.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                saveNewShippingAddress();
            }
        });

        mBinding.tvRegionValue.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                openPageForResult(MineLocationAddressFragment.class, null, Constant.REQUEST_CODE_LOCATION_ADDRESS);
            }
        });
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == Constant.RESULT_CODE_SUCCESS) {
            if (requestCode == Constant.REQUEST_CODE_LOCATION_ADDRESS) {
                //获取到地址信息
                poiItem = data.getParcelableExtra(MineLocationAddressFragment.RESULT_KEY);
                if (poiItem != null) {
                    mViewModel.regionObservable.set(poiItem.getProvinceName() + poiItem.getCityName()
                            + poiItem.getAdName());
                    mViewModel.locationDetailObservable.set(poiItem.getSnippet());
                }else{
                    LogUtil.d("没有返回兴趣点数据");
                }
            }
        }
    }

    private void addCallBack() {
        MyDataBindingUtil.addCallBack(this, mViewModel.onAddShippingAddressObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (mViewModel.onAddShippingAddressObservable.get() == BaseModel.NotifyChangeRequestCallBack.REQUEST_SUCCESS) {
                    setFragmentResult(Constant.RESULT_CODE_SUCCESS, null);
                    popToBack();
                } else {
                    mHandler.post(() -> Toast.makeText(requireContext(), "添加地址失败 T.T", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void initTitle() {
        mBinding.layoutTitle.tvTitle.setText(getPageName());
    }

    /**
     * 保存新地址
     */
    private void saveNewShippingAddress() {
        String name = mViewModel.consigneeNameObservable.get();
        String phone = mViewModel.consigneePhoneObservable.get();
        String region = mViewModel.regionObservable.get();
        String addressDetail = mViewModel.locationDetailObservable.get();
        int isDefault = mBinding.sbSetDefaultAddress.isChecked() ? 1 : 0;
        if (poiItem == null && (name == null || "".equals(name)) && (phone == null || "".equals(phone))
                && (region == null || "".equals(region)) && (addressDetail == null || "".equals(addressDetail))) {
            Toast.makeText(requireContext(), "请补全信息！", Toast.LENGTH_SHORT).show();
            return;
        }

        AddShippingAddress addShippingAddress = new AddShippingAddress(
                poiItem.getProvinceName(), poiItem.getCityName(), poiItem.getAdName(), addressDetail,
                poiItem.getPostcode(), String.valueOf(poiItem.getLatLonPoint().getLatitude()),
                String.valueOf(poiItem.getLatLonPoint().getLongitude()), name, phone,
                Global.getUser().getId(), isDefault);

        mViewModel.requestAddNewShippingAddress(addShippingAddress);
    }

}
