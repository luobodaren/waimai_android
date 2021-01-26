package com.life.waimaishuo.mvvm.vm.mine;

import com.life.waimaishuo.bean.RedPacket;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mine.MineRedPackageModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class MineRedPackageViewModel extends BaseViewModel {

    private MineRedPackageModel mModel;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new MineRedPackageModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    public List<RedPacket> getRedPacketData() {
        List<RedPacket> list = new ArrayList<>();
        list.add(new RedPacket(1,"嘉禾一品粥 (国展店）","¥24","满45可用","限收货手机号为18231875432","限2020-06-12至2020-06-12使用",true,"仅限果蔬商家使用",true));
        list.add(new RedPacket(2,"嘉禾一品粥 (国展店）","¥24","满45可用","限收货手机号为18231875432","限2020-06-12至2020-06-12使用",true,"仅限果蔬商家使用",true));
        list.add(new RedPacket(3,"嘉禾一品粥 (国展店）","¥24","满45可用","限收货手机号为18231875432","限2020-06-12至2020-06-12使用",false,"仅限果蔬商家使用",false));
        return list;
    }
}
