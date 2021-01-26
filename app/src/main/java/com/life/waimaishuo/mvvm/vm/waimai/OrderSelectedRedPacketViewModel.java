package com.life.waimaishuo.mvvm.vm.waimai;

import com.life.waimaishuo.bean.RedPacket;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.OrderRedPacketModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class OrderSelectedRedPacketViewModel extends BaseViewModel {

    OrderRedPacketModel mModel;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new OrderRedPacketModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    public List<RedPacket> getRedPacketData() {
        List<RedPacket> list = new ArrayList<>();
        list.add(new RedPacket(1,"嘉禾一品粥 (国展店）神经学家电话专享红包","¥24","满45可用","限收货手机号为18231875432","限2020-06-12至2020-06-12使用",true,"仅限果蔬商家使用",true));
        list.add(new RedPacket(2,"嘉禾一品粥 (国展店）神经学家电话专享红包","¥24","满45可用","限收货手机号为18231875432","限2020-06-12至2020-06-12使用",true,"仅限果蔬商家使用",true));
        list.add(new RedPacket(3,"嘉禾一品粥 (国展店）神经学家电话专享红包","¥24","满45可用","限收货手机号为18231875432","限2020-06-12至2020-06-12使用",false,"仅限果蔬商家使用",false));
        return list;
    }

}
