package com.life.waimaishuo.mvvm.vm.mine;

import android.content.Context;

import androidx.databinding.ObservableInt;

import com.life.waimaishuo.Global;
import com.life.waimaishuo.R;
import com.life.waimaishuo.bean.ui.TypeCountData;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mine.MineModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class MineViewModel extends BaseViewModel {

    MineModel myModel;

    public ObservableInt onRequestUserInfo = new ObservableInt();

    @Override
    public BaseModel getModel() {
        myModel = new MineModel();
        return myModel;
    }

    @Override
    public void initData() {

    }

    public String getTopDataValue(int position){
        switch (position){
            case 0:
                return "10";
            case 1:
                return "20";
            case 2:
                return "30";
            case 3:
                return "25";
        }
        return "";
    }

    public void requestUserInfo() {
        myModel.requestUserInfo(new BaseModel.NotifyChangeRequestCallBack(onRequestUserInfo));
    }

    public List<TypeCountData> getTopDataList(Context context, List<TypeCountData> typeCountData){
       if(typeCountData == null){   //初始化
           typeCountData = new ArrayList<>();
           String[] orderStateStr = context.getResources().getStringArray(R.array.mine_top_data_list_str);
           for (int i = 0; i < orderStateStr.length; i++) {
               typeCountData.add(new TypeCountData(orderStateStr[i],"0"));
           }
       }
        if(Global.getUser() != null){   //设值
            int number = 0;
            for (int i = 0; i < typeCountData.size(); i++) {
                if(i == 3)  //todo 红包数量 其余数量没找到
                    number = Global.getUser().getCouponNumber();
                typeCountData.get(i).setNumber(String.valueOf(number));
            }
        }
        return typeCountData;
    }
}
