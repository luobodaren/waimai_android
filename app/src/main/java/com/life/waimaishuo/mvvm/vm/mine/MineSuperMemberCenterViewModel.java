package com.life.waimaishuo.mvvm.vm.mine;

import com.life.waimaishuo.bean.RedPacket;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mine.MineSuperMemberCenterModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class MineSuperMemberCenterViewModel extends BaseViewModel {

    private MineSuperMemberCenterModel mModel;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new MineSuperMemberCenterModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    public List<RedPacket> getRedPacketData() {
        List<RedPacket> list = new ArrayList<>();
        list.add(new RedPacket("会员专属红包","5","6金币兑换",false));
        list.add(new RedPacket("会员专属红包","5","6金币兑换",false));
        list.add(new RedPacket("会员专属红包","5","6金币兑换",false));
        return list;
    }
}
