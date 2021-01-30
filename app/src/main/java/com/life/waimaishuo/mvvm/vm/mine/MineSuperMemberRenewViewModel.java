package com.life.waimaishuo.mvvm.vm.mine;

import com.life.waimaishuo.bean.MemberPackage;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mine.MineSuperMemberCenterModel;
import com.life.waimaishuo.mvvm.model.mine.MineSuperMemberRenewModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class MineSuperMemberRenewViewModel extends BaseViewModel {

    private MineSuperMemberRenewModel mModel;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new MineSuperMemberRenewModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    public List<MemberPackage> getMemberPackageData() {
        List<MemberPackage> list = new ArrayList<>();
        list.add(new MemberPackage("月卡会员","15","首月特惠"));
        list.add(new MemberPackage("季卡会员","45",""));
        list.add(new MemberPackage("年卡会员","150","包年特惠"));
        return list;
    }
}
