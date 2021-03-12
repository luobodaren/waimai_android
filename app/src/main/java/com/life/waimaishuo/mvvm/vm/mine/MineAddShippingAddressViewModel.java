package com.life.waimaishuo.mvvm.vm.mine;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.life.waimaishuo.bean.api.request.bean.AddShippingAddress;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mine.MineAddShippingAddressModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

public class MineAddShippingAddressViewModel extends BaseViewModel {

    private MineAddShippingAddressModel mModel;

    public ObservableInt onAddShippingAddressObservable = new ObservableInt();

    public ObservableField<String> consigneeNameObservable = new ObservableField<>();
    public ObservableField<String> consigneePhoneObservable = new ObservableField<>();
    public ObservableField<String> regionObservable = new ObservableField<>();
    public ObservableField<String> locationDetailObservable = new ObservableField<>();

    @Override
    public BaseModel getModel() {
        if (mModel == null) {
            mModel = new MineAddShippingAddressModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    public void requestAddNewShippingAddress(AddShippingAddress addShippingAddress) {
        mModel.requestAddShippingAddress(new BaseModel.NotifyChangeRequestCallBack(onAddShippingAddressObservable), addShippingAddress);
    }

}
