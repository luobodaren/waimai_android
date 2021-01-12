package com.life.waimaishuo.mvvm.vm.order;

import androidx.databinding.ObservableField;

import com.life.waimaishuo.R;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.bean.ui.IconStrData;
import com.life.waimaishuo.bean.ui.MallGoods;
import com.life.waimaishuo.bean.ui.MallOrder;
import com.life.waimaishuo.bean.ui.TypeDescribeValue;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.order.MallConfirmOrderModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class MallConfirmOrderViewModel extends BaseViewModel {

    private MallConfirmOrderModel mModel;

    public ObservableField<String> consigneeNameObservable = new ObservableField<>();
    public ObservableField<String> consigneePhoneObservable = new ObservableField<>();
    public ObservableField<String> consigneeLocationObservable = new ObservableField<>();

    @Override
    public BaseModel getModel() {
        if(mModel == null) {
            mModel = new MallConfirmOrderModel();
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

    public List<MallOrder> getMallOrder(){
        List<MallOrder> mallOrderList = new ArrayList<>();

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

        List<TypeDescribeValue> typeDescribeValueList = new ArrayList<>();
        TypeDescribeValue typeDescribeValue = new TypeDescribeValue("配送方式","普通配送","快递 免邮");
        typeDescribeValueList.add(typeDescribeValue);
        typeDescribeValue = new TypeDescribeValue("店铺优惠","满减活动：满800减100","-￥100");
        typeDescribeValueList.add(typeDescribeValue);
        typeDescribeValue = new TypeDescribeValue("会员优惠","会员活动：限时98折","-￥10");
        typeDescribeValueList.add(typeDescribeValue);

        MallOrder mallOrder = new MallOrder();
        mallOrder.setShop(shop);
        mallOrder.setMallGoodsList(mallGoodsList);
        mallOrder.setTypeDescribeValueList(typeDescribeValueList);
        mallOrderList.add(mallOrder);

        mallOrder = new MallOrder();
        mallOrder.setShop(shop);
        mallOrder.setMallGoodsList(mallGoodsList);
        mallOrder.setTypeDescribeValueList(typeDescribeValueList);
        mallOrderList.add(mallOrder);

        return mallOrderList;
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
}
