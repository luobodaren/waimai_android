package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.Observable;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.databinding.ItemRecyclerGoodsMatchBinding;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.view.fragment.BaseRecyclerFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.RecommendGoodsViewModel;
import com.life.waimaishuo.util.MyDataBindingUtil;
import com.life.waimaishuo.util.TextUtil;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.adapter.recyclerview.GridDividerItemDecoration;

import java.util.List;

@Page(name = "搭配")
public class GoodsMatchFragment extends BaseRecyclerFragment<Goods> {

    private final static String BUNDLE_KEY_SHOP_ID = "bundle_key_shop_id";
    private final static String BUNDLE_KEY_GOODS_ID = "bundle_key_goods_id";

    RecommendGoodsViewModel mViewModel;

    Shop shop;
    Goods goods;

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new RecommendGoodsViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void initArgs() {
        super.initArgs();

        Bundle bundle = getArguments();
        if(bundle != null){
            shop = bundle.getParcelable(BUNDLE_KEY_SHOP_ID);
            goods = bundle.getParcelable(BUNDLE_KEY_GOODS_ID);
        }
        if(shop == null || goods == null){
            throw new Error("shop 或 goods null");
        }
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_recycler_goods_match;
    }

    @Override
    protected RecyclerView.LayoutManager getRecyclerLayoutManager() {
        return new GridLayoutManager(requireContext(),2, LinearLayoutManager.VERTICAL,false);
    }

    @Override
    protected List getListData() {
        return mViewModel.getGoodsList();
    }

    @Override
    protected int getVariableId() {
        return com.life.waimaishuo.BR.goods;
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new GridDividerItemDecoration(requireContext(),2,
                (int) UIUtils.getInstance().scalePx(
                        getResources().getDimensionPixelSize(R.dimen.shop_grid_recycler_item_padding)));
    }

    int scale16 = (int) UIUtils.getInstance().scalePx(16);
    int scale12 = (int) UIUtils.getInstance().scalePx(12);
    @Override
    protected void onRecyclerBindViewHolder(ViewDataBinding viewDataBinding, BaseViewHolder helper, Goods item) {
        if(viewDataBinding instanceof ItemRecyclerGoodsMatchBinding){
            ItemRecyclerGoodsMatchBinding binding = (ItemRecyclerGoodsMatchBinding) viewDataBinding;

            binding.tvGoodsSaleInfo.setText(
                    getString(R.string.shop_mon_sale_count_and_favorable_rate,String.valueOf(item.getMonSalesVolume()),item.getFavorable_rate()));

            String price = "￥" + item.getLinePrice();
            binding.layoutGoodsPrice.tvGoodsPrePrice.setText(
                    TextUtil.getAbsoluteSpannable(price,scale16,0,1));
            TextUtil.setStrikeThrough(binding.layoutGoodsPrice.tvGoodsPrePrice);

            price = "￥" + item.getSpecialPrice();
            binding.layoutGoodsPrice.tvGoodsDiscountPrice.setText(
                    TextUtil.getAbsoluteSpannable(price,scale12,0,1));

            setViewVisibility(binding.layoutGoodsPrice.layoutGoodsOptionChose.llContent,true);
            setViewVisibility(binding.layoutGoodsPrice.layoutGoodsOptionAddShoppingCart.llContent,false);
            binding.layoutGoodsPrice.layoutGoodsOptionChose.btChoseSpecification.setText(R.string.go_to_buy);

            BaseActivity.OnSingleClickListener onSingleClickListener = new BaseActivity.OnSingleClickListener() {
                @Override
                public void onSingleClick(View view) {
                    GoodsDetailFragment.openPage(GoodsMatchFragment.this,shop,item);
                }
            };

            binding.layoutGoodsPrice.layoutGoodsOptionChose.btChoseSpecification.setOnClickListener(onSingleClickListener);
            binding.llContent.setOnClickListener(onSingleClickListener);
        }
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        addCallBack();
    }

    @Override
    protected void firstRequestData() {
        super.firstRequestData();

        mViewModel.requestCommendGoods(shop.getShopId(), goods.getBrandId(), 1, 10);
    }

    private void addCallBack(){

        MyDataBindingUtil.addCallBack(this, mViewModel.onGetCommendGoodsObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.post(() -> {
                    recyclerAdapter.setNewData(mViewModel.getGoodsList());
                    recyclerAdapter.notifyDataSetChanged();
                });
            }
        });

    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public static void openPage(BaseFragment baseFragment, int shopId, int goodsID){
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_KEY_SHOP_ID,shopId);
        bundle.putInt(BUNDLE_KEY_GOODS_ID,goodsID);
        baseFragment.openPage(GoodsMatchFragment.class, bundle);
    }

}
