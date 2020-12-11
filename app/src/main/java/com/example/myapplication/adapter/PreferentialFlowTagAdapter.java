package com.example.myapplication.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xuexiang.xui.widget.flowlayout.BaseTagAdapter;

/**
 * @author xuexiang
 * @date 2017/11/21 上午10:44
 */
public class PreferentialFlowTagAdapter extends BaseTagAdapter<String, TextView> {

    public PreferentialFlowTagAdapter(Context context) {
        super(context);
    }

    @Override
    protected TextView newViewHolder(View convertView) {
//        return (TextView) convertView.findViewById(R.id.tv_tag);
        return null;

    }

    @Override
    protected int getLayoutId() {
//        return R.layout.adapter_item_tag;
        return 1;
    }

    @Override
    protected void convert(TextView textView, String item, int position) {
        textView.setText(item);
    }
}
