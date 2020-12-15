package com.life.waimaishuo.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.life.waimaishuo.R;
import com.xuexiang.xui.widget.flowlayout.BaseTagAdapter;

public class PreferentialFlowTagAdapter extends BaseTagAdapter<String, TextView> {

    public PreferentialFlowTagAdapter(Context context) {
        super(context);
    }

    @Override
    protected TextView newViewHolder(View convertView) {
        return (TextView) convertView.findViewById(R.id.tv_tag);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.adapter_tag_item_preferential;
    }

    @Override
    protected void convert(TextView textView, String item, int position) {
        textView.setText(item);
    }
}
