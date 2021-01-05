package com.life.waimaishuo.adapter.tagAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.life.waimaishuo.R;
import com.xuexiang.xui.widget.flowlayout.BaseTagAdapter;

public class CashBackTagAdapter extends BaseTagAdapter<String, TextView> {
    public CashBackTagAdapter(Context context) {
        super(context);
    }

    @Override
    protected TextView newViewHolder(View convertView) {
        return (TextView) convertView.findViewById(R.id.tv_tag);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.adapter_tag_item_cash_back;
    }

    @Override
    protected void convert(TextView textView, String item, int position) {
        textView.setText(item);
        if(position == 0){
            ((ViewGroup.MarginLayoutParams)textView.getLayoutParams()).setMarginStart(0);
        }
    }


}
