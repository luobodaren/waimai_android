package com.life.waimaishuo.mvvm.vm.waimai;

import androidx.databinding.ObservableField;

import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.OrderNoteModel;
import com.life.waimaishuo.mvvm.model.waimai.OrderRedPacketModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class OrderNoteViewModel extends BaseViewModel {

    OrderNoteModel mModel;

    public ObservableField<String> orderNoteObservable = new ObservableField<>();
    public ObservableField<String> orderNoteTextNumberObservable = new ObservableField<>();

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new OrderNoteModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    public List<String> getRedPacketData() {
        List<String> list = new ArrayList<>();
        list.add("最新");
        list.add("不要辣");
        list.add("不要香菜");
        list.add("最新");
        list.add("不要辣");
        list.add("不要香菜");
        return list;
    }

}
