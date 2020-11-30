package com.example.myapplication.mvvm.vm.order;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.base.utils.LogUtil;
import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.adapter.MyBaseRecyclerAdapter;
import com.example.myapplication.adapter.order.MallUnPayViewHolder;
import com.example.myapplication.adapter.order.OrderViewHolder;
import com.example.myapplication.bean.Goods;
import com.example.myapplication.bean.Order;
import com.example.myapplication.databinding.ItemGoodInfoBinding;
import com.example.myapplication.enumtype.OrderState;
import com.example.myapplication.mvvm.model.BaseModel;
import com.example.myapplication.mvvm.vm.BaseRecyclerViewModel;
import com.example.myapplication.mvvm.vm.BaseViewModel;

import java.util.List;

public class OrderItemViewModel extends BaseRecyclerViewModel {

    Order order;

    @Override
    public void bindModel(BaseViewHolder mBaseViewHolder, BaseModel baseModel, MyBaseRecyclerAdapter adapter) {
        if(mBaseViewHolder instanceof MallUnPayViewHolder){
            Adapter adapter1 = new Adapter(R.layout.item_good_info,order.getGoodsList(),null);

            View headView = View.inflate(MyApplication.getMyApplication(),R.layout.head_order_unpay,null);
            Glide.with(MyApplication.getMyApplication())
                    .load(order.getShopIconUrl())
                    .centerCrop()
                    .placeholder(R.drawable.ic_waimai_brand)
                    .into((ImageView) headView.findViewById(R.id.iv_shopIcon));
            ((TextView)headView.findViewById(R.id.tv_shopName)).setText(order.getShopName());
            ((TextView)headView.findViewById(R.id.tv_order_date)).setText(order.getOrderCreateTime());
            ((TextView)headView.findViewById(R.id.tv_order_state)).setText(OrderState.getState(order.getOrderState()));

            adapter1.addHeaderView(headView);

            View footView = View.inflate(MyApplication.getMyApplication(),R.layout.foot_order_unpay,null);
            adapter1.addFooterView(footView);

            ((MallUnPayViewHolder)mBaseViewHolder).recyclerView.setLayoutManager(
                    new LinearLayoutManager(MyApplication.getMyApplication(),
                            LinearLayoutManager.VERTICAL,false));
            ((MallUnPayViewHolder)mBaseViewHolder).recyclerView.setAdapter(adapter1);

        }else if(mBaseViewHolder instanceof OrderViewHolder){
            // TODO: 2020/11/30 根据不同type显示不同界面
            LogUtil.e("ViewHolder 匹配失败111111111111111111");
        }else{
            LogUtil.e("ViewHolder 匹配失败");
        }
    }

    @Override
    public BaseModel getModel() {
        return null;
    }

    @Override
    public void initData() {

    }

    public void setData(Order data) {
        this.order = data;
    }

    public Order getData() {
        return order;
    }

    public static class ViewHolder extends BaseViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class Adapter extends MyBaseRecyclerAdapter<ViewHolder> {

        public Adapter(int layoutResId, @Nullable List data, @NonNull List<BaseRecyclerViewModel> mItemViewModelList) {
            super(layoutResId, data, mItemViewModelList);
        }

        public Adapter(@Nullable List data, @NonNull List<BaseRecyclerViewModel> mItemViewModelList) {
            super(data, mItemViewModelList);
        }

        public Adapter(int layoutResId, @NonNull List<BaseRecyclerViewModel> mItemViewModelList) {
            super(layoutResId, mItemViewModelList);
        }

        @Override
        protected void convert(@NonNull ViewHolder helper, Object item) {
            super.convert(helper, item);

            int position = getData().indexOf(item);
            Goods goods = order.getGoodsList().get(position);
            ItemGoodInfoBinding itemGoodInfoBinding = ItemGoodInfoBinding.bind(helper.itemView);
            Glide.with(MyApplication.getMyApplication())
                    .load(goods.getGoodsImgUrl())
                    .centerCrop()
                    .placeholder(R.drawable.ic_waimai_brand)
                    .into(itemGoodInfoBinding.ivGoodsImg);
            itemGoodInfoBinding.setGoods(goods);
        }
    }

}
