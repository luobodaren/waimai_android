package com.life.waimaishuo.adapter.tag;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.xuexiang.xui.widget.flowlayout.BaseTagAdapter;

public class ScreenTagAdapter extends BaseTagAdapter<String, View>{

    public ScreenTagAdapter(Context context) {
        super(context);
    }

    @Override
    protected View newViewHolder(View convertView) {
        return convertView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.adapter_tag_item_screen;
    }

    @Override
    protected void convert(View holder, String item, int position) {
        ((TextView)holder.findViewById(R.id.tv_tag)).setText(item);


        if(position == 0){
            holder.setPadding(0, 24,0,0);
            //((ViewGroup.MarginLayoutParams)holder.getLayoutParams()).setMarginStart(0);
        }
    }
}
