package com.life.waimaishuo.adapter.tagAdapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.xuexiang.xui.widget.flowlayout.BaseTagAdapter;

public class MallSpecificationTagAdapter extends BaseTagAdapter<String, TextView> {

    public int selectedIndex = -1;

    public MallSpecificationTagAdapter(Context context) {
        super(context);
    }

    @Override
    protected TextView newViewHolder(View convertView) {
        TextView tagView = convertView.findViewById(R.id.tv_tag);
        tagView.setTag(convertView.findViewById(R.id.iv_selected_sign));
        return tagView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.adapter_tag_item_specification_mall;
    }

    @Override
    protected void convert(TextView textView, String item, int position) {
        textView.setText(item);

        if(this.selectedIndex == position){ //当前为选中的view
            textView.setSelected(true);
            textView.setTextColor(textView.getContext().getResources().getColor(R.color.colorTheme));
            LogUtil.d("当前为选中的view" + position);
        }else{
            textView.setSelected(false);
            textView.setTextColor(textView.getContext().getResources().getColor(R.color.text_normal));
            LogUtil.d("未选中的view" + position);

        }
    }


}
