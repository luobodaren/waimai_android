/*
 * Copyright (C) 2019 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.life.waimaishuo.adapter.config;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.ObservableInt;
import androidx.databinding.ViewDataBinding;

import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.bean.ui.LinkageShopGoodsGroupedItemInfo;
import com.life.waimaishuo.databinding.AdapterLinkageSecondaryLinearBinding;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryFooterViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryHeaderViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryViewHolder;
import com.kunminx.linkage.bean.BaseGroupedItem;
import com.kunminx.linkage.contract.ILinkageSecondaryAdapterConfig;
import com.life.waimaishuo.listener.OnSecondaryShopGoodsItemClickListener;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.util.TextUtil;

public class CustomLinkageSecondaryShopGoodsAdapterConfig<T extends BaseGroupedItem.ItemInfo> implements ILinkageSecondaryAdapterConfig<LinkageShopGoodsGroupedItemInfo> {

    private static final int SPAN_COUNT = 3;

    private Context mContext;

    private OnSecondaryShopGoodsItemClickListener mItemClickListener;

    private int textSize_one;
    private int textSize_two;

    public CustomLinkageSecondaryShopGoodsAdapterConfig(OnSecondaryShopGoodsItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public CustomLinkageSecondaryShopGoodsAdapterConfig setOnItemClickListner(OnSecondaryShopGoodsItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
        return this;
    }

    @Override
    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public int getGridLayoutId() {
        return R.layout.adapter_linkage_secondary_grid;
    }

    @Override
    public int getLinearLayoutId() {
        return R.layout.adapter_linkage_secondary_linear;
    }

    @Override
    public int getHeaderLayoutId() {
        return R.layout.adapter_secondary_header_waimai_shop_goods;
    }

    @Override
    public int getFooterLayoutId() {
        return R.layout.adapter_linkage_empty_footer;
    }

    @Override
    public int getHeaderTextViewId() {
        return R.id.secondary_header;
    }

    @Override
    public int getSpanCountOfGridMode() {
        return SPAN_COUNT;
    }

    @Override
    public void onBindViewHolder(final LinkageSecondaryViewHolder holder,
                                 final BaseGroupedItem<LinkageShopGoodsGroupedItemInfo> item) {
        //DataBinding 绑定布局
        /*if(!isGetModeType){
            isGridMode = mLinkageRecyclerView.get().isGridMode();
            isGetModeType = true;
        }
        if(isGridMode){
            AdapterLinkageSecondaryGridBinding binding = AdapterLinkageSecondaryGridBinding.bind(holder.itemView);  //这样绑定会导致重复绑定 然后报错
            binding.setItem(item.info);
            binding.setViewModel(mShopOrderDishesViewModel.get());
        }else{
            AdapterLinkageSecondaryLinearBinding binding = AdapterLinkageSecondaryLinearBinding.bind(holder.itemView);
        }*/

        ViewDataBinding binding = DataBindingUtil.bind(holder.itemView);
        binding.setVariable(com.life.waimaishuo.BR.item, item.info);

        //处理item点击事件
        //判断显示“选规格”按钮还是 加减数量
        if (binding instanceof AdapterLinkageSecondaryLinearBinding) {
            AdapterLinkageSecondaryLinearBinding binding1 = (AdapterLinkageSecondaryLinearBinding) binding;

            /*-------------- 设置监听 --------------*/
            if (item.info.getUiUpdateObservable() == null) { //添加UI更新界面
                ObservableInt observableInt = new ObservableInt();
                observableInt.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
                    @Override
                    public void onPropertyChanged(Observable sender, int propertyId) {
                        //更新UI
                        updateItemUI(item.info, binding1);
                    }
                });
                item.info.setUiUpdateObservable(observableInt);
            }

            binding1.clGoodsItem.setOnClickListener(v -> {
                if (mItemClickListener != null) {
                    mItemClickListener.onSecondaryItemClick(holder, binding1.clGoodsItem, item);
                }
            });

            item.info.getGoods().getChoiceNumberObservable().set(String.valueOf(item.info.getGoods().getChoiceNumber()));
            binding1.layoutGoodsPriceAndBuy.layoutGoodsOptionAddShoppingCart.setGoods(item.info.getGoods());

            binding1.layoutGoodsPriceAndBuy.layoutGoodsOptionChose.btChoseSpecification
                    .setOnClickListener(new BaseActivity.OnSingleClickListener() {
                        @Override
                        public void onSingleClick(View view) {
                            if (mItemClickListener != null)
                                mItemClickListener.onSecondaryChildClick(holder, view, item);
                        }
                    });
            binding1.layoutGoodsPriceAndBuy.layoutGoodsOptionAddShoppingCart.ivAdd
                    .setOnClickListener(new BaseActivity.OnSingleClickListener() {
                        @Override
                        public void onSingleClick(View view) {
                            if (mItemClickListener != null)
                                mItemClickListener.onSecondaryChildClick(holder, view, item);
                        }
                    });
            binding1.layoutGoodsPriceAndBuy.layoutGoodsOptionAddShoppingCart.ivRemove
                    .setOnClickListener(new BaseActivity.OnSingleClickListener() {
                        @Override
                        public void onSingleClick(View view) {
                            if (mItemClickListener != null)
                                mItemClickListener.onSecondaryChildClick(holder, view, item);
                        }
                    });

            updateItemUI(item.info, binding1);

            /*-------------- 设置数据 --------------*/
            //设置优惠信息
            if (item.info.getGoods().getDiscount() != null && !"".equals(item.info.getGoods().getDiscount())) {
                binding1.llPreferential.tvDiscount.setText(mContext.getString(R.string.discount, item.info.getGoods().getDiscount()));
            } else {
                binding1.llPreferential.llContent.setVisibility(View.GONE);
            }

            if(textSize_one == 0)
                textSize_one = (int) UIUtils.getInstance().scalePx(16);
            if(textSize_two == 0)
                textSize_two = (int) UIUtils.getInstance().scalePx(12);

            String discountPrice = String.format("￥%s", item.info.getGoods().getSpecialPrice());
            binding1.layoutGoodsPriceAndBuy.tvGoodsDiscountPrice.setText(
                    TextUtil.getAbsoluteSpannable(discountPrice,textSize_one,0,1));

            String linePrice = String.format("￥%s", item.info.getGoods().getLinePrice());
            binding1.layoutGoodsPriceAndBuy.tvGoodsPrePrice.setText(
                    TextUtil.getAbsoluteSpannable(linePrice,textSize_two,0,1));
            TextUtil.setStrikeThrough(binding1.layoutGoodsPriceAndBuy.tvGoodsPrePrice);
        }

    }

    @Override
    public void onBindHeaderViewHolder(LinkageSecondaryHeaderViewHolder holder,
                                       BaseGroupedItem<LinkageShopGoodsGroupedItemInfo> item) {

        ((TextView) holder.getView(R.id.secondary_header)).setText(item.header);
    }

    @Override
    public void onBindFooterViewHolder(LinkageSecondaryFooterViewHolder holder,
                                       BaseGroupedItem<LinkageShopGoodsGroupedItemInfo> item) {

    }

    /**
     * 更新Item UI
     */
    private void updateItemUI(LinkageShopGoodsGroupedItemInfo item, AdapterLinkageSecondaryLinearBinding binding) {
        if (item.getGoods().getIsChooseSpecs() == 0 && item.getGoods().getChoiceNumber() == 0) {
            BaseFragment.setViewVisibility(binding.layoutGoodsPriceAndBuy.layoutGoodsOptionAddShoppingCart.llContent, false);
            BaseFragment.setViewVisibility(binding.layoutGoodsPriceAndBuy.layoutGoodsOptionChose.llContent, true);
        } else {
            binding.layoutGoodsPriceAndBuy.layoutGoodsOptionAddShoppingCart.tvCount.setText(
                    String.valueOf(item.getGoods().getChoiceNumber()));    //设置选中数量
            BaseFragment.setViewVisibility(binding.layoutGoodsPriceAndBuy.layoutGoodsOptionAddShoppingCart.llContent, true);
            BaseFragment.setViewVisibility(binding.layoutGoodsPriceAndBuy.layoutGoodsOptionChose.llContent, false);
        }
    }

}
