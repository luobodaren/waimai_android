package com.life.waimaishuo.adapter.tag;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.life.base.utils.LogUtil;
import com.life.waimaishuo.R;
import com.xuexiang.xui.widget.flowlayout.BaseTagAdapter;

public class SpecificationMallTagAdapter extends BaseTagAdapter<String, TextView> {

    public int selectedIndex = -1; //本地保持选中的index备份 避免当notifyDataSetChange时丢失了选中下标

    public SpecificationMallTagAdapter(Context context) {
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
