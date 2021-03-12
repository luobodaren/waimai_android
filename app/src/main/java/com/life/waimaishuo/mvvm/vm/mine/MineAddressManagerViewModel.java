package com.life.waimaishuo.mvvm.vm.mine;

import com.life.waimaishuo.bean.Address;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mine.MineAddressManagerModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class MineAddressManagerViewModel extends BaseViewModel {

    private MineAddressManagerModel mModel;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new MineAddressManagerModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    public void setAddressData(List<Address> addressList){
        mModel.addressList = addressList;
    }

    public List<Address> getAddressData() { // FIXME: 2021/1/22 添加“失效地址整理”有序排序
        return mModel.addressList;
    }
}
