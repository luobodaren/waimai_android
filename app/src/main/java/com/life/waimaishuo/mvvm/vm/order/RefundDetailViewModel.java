package com.life.waimaishuo.mvvm.vm.order;

import androidx.databinding.ObservableField;

import com.life.waimaishuo.bean.Order;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.order.RefundDetailModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class RefundDetailViewModel extends BaseViewModel {

    private RefundDetailModel mModel;

    public ObservableField<String> goodsImageUrlObservable = new ObservableField<>();
    public ObservableField<String> goodsNameUrlObservable = new ObservableField<>();
    public ObservableField<String> goodsSpecificationObservable = new ObservableField<>();

    public ObservableField<String> merchantsNamePhoneNumberObservable = new ObservableField<>();
    public ObservableField<String> merchantsAddressObservable = new ObservableField<>();

    public ObservableField<String> refundStateObservable = new ObservableField<>();
    public ObservableField<String> refundStateDetailObservable = new ObservableField<>();

    public ObservableField<String> applicationResultObservable = new ObservableField<>();
    public ObservableField<String> applicationResultDetailObservable = new ObservableField<>();

    private Order order;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new RefundDetailModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

        // FIXME: 2021/1/11 后续对接口时删除或修改初始化
        refundStateObservable.set("请等待商家处理");
        refundStateDetailObservable.set("您已成功发起退款申请，等待商家处理");

        applicationResultObservable.set("商家已同意退货退款，等待寄回商品");
        applicationResultDetailObservable.set("退款金额已返回您的账户");

        merchantsNamePhoneNumberObservable.set("张三   12345678912");
        merchantsAddressObservable.set("山西省 太原市 小店区 南站国际商务港北区1号楼阿里巴巴创新中心");

        goodsImageUrlObservable.set(order.getGoodsList().get(0).getGoodsImgUrl());
        goodsNameUrlObservable.set("欧舒丹甜蜜樱花沐浴啫喱/身体甜蜜樱花沐浴…");
        goodsSpecificationObservable.set("颜色分类：黄色");
    }

    List<String> orderRefundInfoList = new ArrayList<>();
    public List<String> getRefundOrderInfo() {
        orderRefundInfoList.add("退款原因：质量不好");
        orderRefundInfoList.add("退款金额：￥199.00");
        orderRefundInfoList.add("申请时间：2020-06-19 15:10");
        return orderRefundInfoList;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}
