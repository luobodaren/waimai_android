package com.life.waimaishuo.adapter.tag;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.life.waimaishuo.R;
import com.xuexiang.xui.widget.flowlayout.BaseTagAdapter;

public class MallShopSignAndClassificationTagAdapter extends BaseTagAdapter<String, TextView> {

    public MallShopSignAndClassificationTagAdapter(Context context) {
        super(context);
    }

    @Override
    protected TextView newViewHolder(View convertView) {
        return (TextView)convertView.findViewById(R.id.tv_tag);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.adapter_tag_item_mall_shop_sign_and_classifycation;
    }

    @Override
    protected void convert(TextView holder, String item, int position) {
        holder.setText(item);
    }
}
