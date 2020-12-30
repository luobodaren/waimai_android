package com.life.waimaishuo.mvvm.vm.waimai;

import android.view.View;

import androidx.databinding.ObservableInt;

import com.life.base.utils.LogUtil;
import com.life.waimaishuo.R;
import com.life.waimaishuo.bean.Order;
import com.life.waimaishuo.bean.ui.IconStrData;
import com.life.waimaishuo.bean.ui.OrderItemFoods;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.WaiMaiConfirmModel;
import com.life.waimaishuo.mvvm.view.fragment.waimai.WaiMaiConfirmOrderFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class WaiMaiConfirmOrderViewModel extends BaseViewModel {

    WaiMaiConfirmModel mModel;

    public ObservableInt onAccessTimeClick = new ObservableInt();
    public ObservableInt onPayTypeClick = new ObservableInt();
    public ObservableInt onTimePick = new ObservableInt();

    private int currentAccessType = WaiMaiConfirmOrderFragment.ORDER_ACCESS_WAIMAI; //默认外卖配送

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new WaiMaiConfirmModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    public void onAccessTimeClick(View view){
        onAccessTimeClick.notifyChange();
    }

    public void onPayTypeClick(View view){
        onPayTypeClick.notifyChange();
    }

    Order order;
    public Order getOrderInfo() {

        return order;
    }

    public List<IconStrData> getPayTypeList() {
        List<IconStrData> iconStrData = new ArrayList<>();
        iconStrData.add(new IconStrData(R.drawable.ic_alipay,"微信"));
        iconStrData.add(new IconStrData(R.drawable.ic_alipay,"支付宝"));
        iconStrData.add(new IconStrData(R.drawable.ic_alipay,"QQ钱包"));
        iconStrData.add(new IconStrData(R.drawable.ic_alipay,"微信好友"));
        iconStrData.add(new IconStrData(R.drawable.ic_alipay,"支付宝好友"));
        return iconStrData;
    }


    public void setCurrentAccessType(int currentAccessType) {
        this.currentAccessType = currentAccessType;
    }

    public int getCurrentAccessType() {
        return currentAccessType;
    }

    List<String> stringList;
    List<List<String>> timesList;
    public List<String> getLeftPickUpTimeData() {
        if(stringList == null){
            stringList = new ArrayList<>();
            stringList.add("19:00~21:00");
            stringList.add("21:00~23:00");
        }
        return stringList;
    }

    public List<String> getRightPikUpTimeDataByIndex(int indexOf) {
        if(timesList == null){
            timesList = new ArrayList<>();

            List<String> tempStringList = new ArrayList<>();
            tempStringList.add("立即取餐");
            tempStringList.add("19:15");
            tempStringList.add("19:30");
            tempStringList.add("19:45");
            tempStringList.add("20:00");
            tempStringList.add("20:15");
            timesList.add(tempStringList);

            tempStringList = new ArrayList<>();
            tempStringList.add("21:00");
            tempStringList.add("21:15");
            tempStringList.add("21:30");
            tempStringList.add("21:45");
            tempStringList.add("22:00");
            tempStringList.add("22:15");
            timesList.add(tempStringList);
        }
        if(indexOf <= stringList.size()-1){
            return timesList.get(indexOf);
        }else{
            LogUtil.e("getRightPikUpTimeDataByIndex index 越界！！！！");
            return new ArrayList<>();
        }
    }

    public List<OrderItemFoods> getOrderFoods() {
        List<OrderItemFoods> list = new ArrayList<>();
        list.add(new OrderItemFoods("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640","五香茶叶蛋","+免费配料+香菜+葱","×2","￥18.00"));
        list.add(new OrderItemFoods("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640","五香茶叶蛋","+免费配料+香菜+葱","×2","￥18.00"));
        list.add(new OrderItemFoods("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640","五香茶叶蛋","+免费配料+香菜+葱","×2","￥18.00"));
        list.add(new OrderItemFoods("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640","五香茶叶蛋","+免费配料+香菜+葱","×2","￥18.00"));
        return list;
    }
}
