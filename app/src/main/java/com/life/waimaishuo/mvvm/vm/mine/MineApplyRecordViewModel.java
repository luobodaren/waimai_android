package com.life.waimaishuo.mvvm.vm.mine;

import com.life.waimaishuo.bean.ApplyRecord;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mine.MineApplyRecordModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class MineApplyRecordViewModel extends BaseViewModel {

    private MineApplyRecordModel mModel;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new MineApplyRecordModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    public List<ApplyRecord> getRecordList(int recordType, String listState) {
        List<ApplyRecord> list = new ArrayList<>();
        list.add(new ApplyRecord("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1004682078,2886860504&fm=26&gp=0.jpg","张飞","已完成","2021-1-29"));
        list.add(new ApplyRecord("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1004682078,2886860504&fm=26&gp=0.jpg","张飞","已完成","2021-1-29"));
        list.add(new ApplyRecord("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1004682078,2886860504&fm=26&gp=0.jpg","张飞","已完成","2021-1-29"));
        return list;
    }
}
