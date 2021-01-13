package com.life.waimaishuo.mvvm.vm.order;

import android.os.Parcelable;

import androidx.databinding.ObservableField;

import com.life.waimaishuo.bean.Order;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.bean.ui.MallGoods;
import com.life.waimaishuo.bean.ui.MallOrder;
import com.life.waimaishuo.bean.ui.TypeDescribeValue;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.order.OrderDetailModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class MallOrderDetailViewModel extends BaseViewModel {

    OrderDetailModel mModel;

    public ObservableField<String> consigneeNameObservable = new ObservableField<>();
    public ObservableField<String> consigneePhoneObservable = new ObservableField<>();
    public ObservableField<String> consigneeLocationObservable = new ObservableField<>();

    Order order;

    @Override
    public BaseModel getModel() {
        if (mModel == null) {
            mModel = new OrderDetailModel();
        }
        return mModel;
    }

    @Override
    public void initData() {
        consigneeNameObservable.set("张三");
        consigneePhoneObservable.set("12345678912");
        consigneeLocationObservable.set("山西省 太原市 小店区 南站国际商务港北区\n" +
                "1号楼阿里巴巴创新中心");
    }

    MallOrder mallOrder;

    public MallOrder getMallOrder() {
        Shop shop = new Shop();
        shop.setShop_name("老王的水杯");
        shop.setShop_head_portrait("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640");
        List<MallGoods> mallGoodsList = new ArrayList<>();
        MallGoods mallGoods = new MallGoods();
        mallGoods.setGoodsCount("2");
        mallGoods.setGoodsImgUrl("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640");
        mallGoods.setGoodsName("生活在左2020春夏新款绣花桑蚕丝真丝连衣裙女装修身中长款长裙子生活在左2020春夏新款绣花桑蚕丝真丝连衣裙女装修身中长款长裙子···");
        mallGoods.setGoodsPrice("999");
        mallGoods.setGoodsSpecification("白色，L");
        mallGoodsList.add(mallGoods);
        mallGoods = new MallGoods();
        mallGoods.setGoodsCount("2");
        mallGoods.setGoodsImgUrl("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640");
        mallGoods.setGoodsName("生活在左2020春夏新款绣花桑蚕丝真丝连衣裙女装修身中长款长裙子生活在左2020春夏新款绣花桑蚕丝真丝连衣裙女装修身中长款长裙子···");
        mallGoods.setGoodsPrice("999");
        mallGoods.setGoodsSpecification("白色，L");
        mallGoodsList.add(mallGoods);

        List<TypeDescribeValue> shopOrderInfo = new ArrayList<>();
        TypeDescribeValue typeDescribeValue = new TypeDescribeValue("购买数量", "", "1");
        shopOrderInfo.add(typeDescribeValue);
        typeDescribeValue = new TypeDescribeValue("店铺优惠", "", "-￥100");
        shopOrderInfo.add(typeDescribeValue);
        typeDescribeValue = new TypeDescribeValue("会员优惠", "", "-￥10");
        shopOrderInfo.add(typeDescribeValue);

        List<TypeDescribeValue> orderInfo = new ArrayList<>();
        TypeDescribeValue typeDescribeValue1 = new TypeDescribeValue("订单编号", "3242342424", "复制");
        orderInfo.add(typeDescribeValue1);
        typeDescribeValue1 = new TypeDescribeValue("支付流水", "123344455553242342424", "");
        orderInfo.add(typeDescribeValue1);
        typeDescribeValue1 = new TypeDescribeValue("创建时间", "2020-06-10 12:39:10", "");
        orderInfo.add(typeDescribeValue1);

        mallOrder = new MallOrder();
        mallOrder.setShop(shop);
        mallOrder.setMallGoodsList(mallGoodsList);
        mallOrder.setShopOrderInfo(shopOrderInfo);
        mallOrder.setOrderInfo(orderInfo);
        return mallOrder;


    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
