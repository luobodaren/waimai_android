package com.life.waimaishuo.adapter.tagAdapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.life.waimaishuo.R;
import com.xuexiang.xui.widget.flowlayout.BaseTagAdapter;

public class MallShopClassificationTagAdapter extends BaseTagAdapter<String, TextView> {

    public MallShopClassificationTagAdapter(Context context) {
        super(context);
    }

    @Override
    protected TextView newViewHolder(View convertView) {
        return (TextView)convertView.findViewById(R.id.tv_tag);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.adapter_tag_item_mall_shop_classifycation;
    }

    @Override
    protected void convert(TextView holder, String item, int position) {
        holder.setText(item);
    }
}
