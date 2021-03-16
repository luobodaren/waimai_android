package com.life.waimaishuo.mvvm.vm.waimai;

import androidx.databinding.ObservableInt;

import com.life.waimaishuo.bean.Coupon;
import com.life.waimaishuo.bean.RedPacket;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.OrderRedPacketModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class OrderSelectedRedPacketViewModel extends BaseViewModel {

    OrderRedPacketModel mModel;

    public ObservableInt requestCouponObserver = new ObservableInt();

    @Override
    public BaseModel getModel() {
        if (mModel == null) {
            mModel = new OrderRedPacketModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    public void requestCoupon(int shopId, int pageSize, int pageNum) {
        mModel.requestCoupon(new BaseModel.NotifyChangeRequestCallBack(requestCouponObserver), shopId, pageSize, pageNum);
    }

    public List<Coupon> getCouponList(){
        return mModel.couponList;
    }
}
