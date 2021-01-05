package com.life.waimaishuo.adapter.tagAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.life.waimaishuo.R;
import com.xuexiang.xui.widget.flowlayout.BaseTagAdapter;

public class ScreenTagAdapter extends BaseTagAdapter<String, TextView>{

    public ScreenTagAdapter(Context context) {
        super(context);
    }

    @Override
    protected TextView newViewHolder(View convertView) {
        return convertView.findViewById(R.id.tv_tag);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.adapter_tag_item_screen;
    }

    @Override
    protected void convert(TextView holder, String item, int position) {
        holder.setText(item);

        if(position == 0){
            ((ViewGroup.MarginLayoutParams)holder.getLayoutParams()).setMarginStart(0);
        }
    }
}
