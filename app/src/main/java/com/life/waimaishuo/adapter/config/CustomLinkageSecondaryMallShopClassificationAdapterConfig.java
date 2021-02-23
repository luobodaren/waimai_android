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
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.kunminx.linkage.LinkageRecyclerView;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryFooterViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryHeaderViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryViewHolder;
import com.kunminx.linkage.bean.BaseGroupedItem;
import com.kunminx.linkage.contract.ILinkageSecondaryAdapterConfig;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.tag.MallShopSignAndClassificationTagAdapter;
import com.life.waimaishuo.bean.ui.LinkageMallShopClassificationGroupedItemInfo;
import com.life.waimaishuo.listener.OnSecondaryMallShopClassificationItemClickListener;
import com.life.waimaishuo.util.TextUtil;
import com.xuexiang.xui.widget.flowlayout.FlowTagLayout;

import java.lang.ref.WeakReference;

public class CustomLinkageSecondaryMallShopClassificationAdapterConfig<T extends BaseGroupedItem.ItemInfo> implements ILinkageSecondaryAdapterConfig<LinkageMallShopClassificationGroupedItemInfo> {

    private static final int SPAN_COUNT = 3;

    private Context mContext;

    private OnSecondaryMallShopClassificationItemClickListener mItemClickListener;


    public CustomLinkageSecondaryMallShopClassificationAdapterConfig(
            OnSecondaryMallShopClassificationItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public CustomLinkageSecondaryMallShopClassificationAdapterConfig setOnItemClickListner(
            OnSecondaryMallShopClassificationItemClickListener itemClickListener) {
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
        return R.layout.adapter_linkage_secondary_linear_mall_shop_classification;
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
                                 final BaseGroupedItem<LinkageMallShopClassificationGroupedItemInfo> item) {
        //DataBinding 绑定布局
        /*if(!isGetModeType){
            isGridMode = mLinkageRecyclerView.get().isGridMode();
            isGetModeType = true;
        }
        if(isGridMode){
            AdapterLinkageSecondaryGridBinding binding = AdapterLinkageSecondaryGridBinding.bind(holder.itemView);  //这样绑定会导致重复绑定 然后报错
            binding.setItem(item.info);
            binding.setViewModel(mMallShopViewModel.get());
        }else{
            AdapterLinkageSecondaryLinearBinding binding = AdapterLinkageSecondaryLinearBinding.bind(holder.itemView);
        }*/

        ViewDataBinding binding = DataBindingUtil.bind(holder.itemView);
        binding.setVariable(com.life.waimaishuo.BR.item,item.info);

        ViewGroup viewGroup = holder.getView(R.id.iv_goods_item);
        viewGroup.setOnClickListener(v -> {
            if (mItemClickListener != null) {
                mItemClickListener.onSecondaryItemClick(holder, viewGroup, item);
            }
        });

        ((TextView)holder.itemView.findViewById(R.id.tv_goods_price)).setText(
                TextUtil.getAbsoluteSpannable("￥" + item.info.getPrice(),
                        (int) UIUtils.getInstance().scalePx(24),0,1));

        initItemFlowTag(holder.itemView.findViewById(R.id.flowTagLayout),item.info.getDescribeTags());

    }

    @Override
    public void onBindHeaderViewHolder(LinkageSecondaryHeaderViewHolder holder,
                                       BaseGroupedItem<LinkageMallShopClassificationGroupedItemInfo> item) {

        ((TextView) holder.getView(R.id.secondary_header)).setText(item.header);
    }

    @Override
    public void onBindFooterViewHolder(LinkageSecondaryFooterViewHolder holder,
                                       BaseGroupedItem<LinkageMallShopClassificationGroupedItemInfo> item) {

    }

    private void initItemFlowTag(FlowTagLayout flowTagLayout,String[] tags){
        flowTagLayout.setAdapter(new MallShopSignAndClassificationTagAdapter(flowTagLayout.getContext()));
        flowTagLayout.addTags(tags);
    }

}
