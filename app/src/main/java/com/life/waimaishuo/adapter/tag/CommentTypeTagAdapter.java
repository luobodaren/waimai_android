package com.life.waimaishuo.adapter.tag;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.life.base.utils.LogUtil;
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

        /*if(getInitSelectedPositions().contains(position)){ //当前为选中的view
            textView.setSelected(true);
            textView.setTextColor(textView.getContext().getResources().getColor(R.color.colorTheme));
            LogUtil.d("当前为选中的view" + position);
        }else{
            textView.setSelected(false);
            textView.setTextColor(textView.getContext().getResources().getColor(R.color.text_normal));
            LogUtil.d("未选中的view" + position);

        }*/
    }


}
