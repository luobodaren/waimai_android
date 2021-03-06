package com.life.waimaishuo.mvvm.vm.waimai;

import android.view.View;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.life.base.utils.LogUtil;
import com.life.waimaishuo.R;
import com.life.waimaishuo.bean.Address;
import com.life.waimaishuo.bean.PreferentialActivity;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.bean.ui.IconStrData;
import com.life.waimaishuo.bean.ui.OrderItemFoods;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.WaiMaiConfirmOrderModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class WaiMaiConfirmOrderViewModel extends BaseViewModel {

    WaiMaiConfirmOrderModel mModel;

    public ObservableInt onRequestShippingAddressObservable = new ObservableInt();

    public ObservableInt onAccessTimeClickObservable = new ObservableInt();
    public ObservableInt onPayTypeClickObservable = new ObservableInt();
    public ObservableInt onChoseTablewareClickObservable = new ObservableInt();

    public ObservableField<String> pickUpTimeObservable = new ObservableField<>();
    public ObservableField<String> tablewareObservable = new ObservableField<>();
    public ObservableField<String> orderPlaceTimeObservable = new ObservableField<>();
    public ObservableField<String> payTypeObservable = new ObservableField<>();
    public ObservableField<String> orderNumberObservable = new ObservableField<>();
    public ObservableField<String> takeOrderNumberObservable = new ObservableField<>();

    public ObservableField<String> redPacketPriceValueObservable = new ObservableField<>();
    public ObservableField<String> orderNoteObservable = new ObservableField<>();

    public ObservableField<String> deliveryTimeObservable = new ObservableField<>();
    public ObservableField<String> harvestAddressObservable = new ObservableField<>();
    public ObservableField<String> distributionTypeObservable = new ObservableField<>();

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new WaiMaiConfirmOrderModel();
        }
        return mModel;
    }

    @Override
    public void initData() {
        pickUpTimeObservable.set("?????????????????????"); // FIXME: 2020/12/31 ???????????????
        tablewareObservable.set("?????????????????????");

        takeOrderNumberObservable.set("4394");
    }

    public void onAccessTimeClick(View view){
        onAccessTimeClickObservable.notifyChange();
    }

    public void onPayTypeClick(View view){
        onPayTypeClickObservable.notifyChange();
    }

    public void onChoseTablewareClick(View view){
        onChoseTablewareClickObservable.notifyChange();
    }

    Shop shop;
    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop){
        this.shop = shop;
    }

    public List<IconStrData> getPayTypeList() {
        List<IconStrData> iconStrData = new ArrayList<>();
        iconStrData.add(new IconStrData(R.drawable.ic_alipay,"??????"));
        iconStrData.add(new IconStrData(R.drawable.ic_alipay,"?????????"));
        iconStrData.add(new IconStrData(R.drawable.ic_alipay,"QQ??????"));
        iconStrData.add(new IconStrData(R.drawable.ic_alipay,"????????????"));
        iconStrData.add(new IconStrData(R.drawable.ic_alipay,"???????????????"));
        return iconStrData;
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
            tempStringList.add("????????????");
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
            LogUtil.e("getRightPikUpTimeDataByIndex index ??????????????????");
            return new ArrayList<>();
        }
    }

    public List<OrderItemFoods> getOrderFoods() {
        List<OrderItemFoods> list = new ArrayList<>();
        list.add(new OrderItemFoods("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640","???????????????","+????????????+??????+???","??2","???18.00"));
        list.add(new OrderItemFoods("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640","???????????????","+????????????+??????+???","??2","???18.00"));
        list.add(new OrderItemFoods("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640","???????????????","+????????????+??????+???","??2","???18.00"));
        list.add(new OrderItemFoods("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640","???????????????","+????????????+??????+???","??2","???18.00"));
        return list;
    }

    public List<PreferentialActivity> getPreferentialList() {
        List<PreferentialActivity> list = new ArrayList<>();
        list.add(new PreferentialActivity("?????????","?????????","???4.5"));
        list.add(new PreferentialActivity("?????????","????????????","???4.5"));
        list.add(new PreferentialActivity("??????","????????????","???4.5"));
        return list;
    }

    /**
     * ??????????????????
     */
    public void requestShippingAddress(){
        mModel.requestShoppingAddress(new BaseModel.NotifyChangeRequestCallBack(onRequestShippingAddressObservable));
    }

    public List<Address> getShippingAddress(){
        return mModel.addressList;
    }
}
