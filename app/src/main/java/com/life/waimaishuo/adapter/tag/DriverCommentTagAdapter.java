package com.life.waimaishuo.adapter.tag;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.life.waimaishuo.R;
import com.xuexiang.xui.widget.flowlayout.BaseTagAdapter;

public class DriverCommentTagAdapter extends BaseTagAdapter<String, TextView> {
    public DriverCommentTagAdapter(Context context) {
        super(context);
    }

    @Override
    protected TextView newViewHolder(View convertView) {
        return (TextView) convertView.findViewById(R.id.tv_tag);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.adapter_tag_item_driver_comment;
    }

    @Override
    protected void convert(TextView textView, String item, int position) {
        textView.setText(item);
    }

}
