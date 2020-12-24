package com.life.waimaishuo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.life.waimaishuo.R;
import com.xuexiang.xui.widget.flowlayout.BaseTagAdapter;

public class CommentTypeTagAdapter extends BaseTagAdapter<String, TextView> {
    public CommentTypeTagAdapter(Context context) {
        super(context);
    }

    @Override
    protected TextView newViewHolder(View convertView) {
        return (TextView) convertView.findViewById(R.id.tv_tag);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.adapter_tag_item_comment_type;
    }

    @Override
    protected void convert(TextView textView, String item, int position) {
        textView.setText(item);
    }


}
