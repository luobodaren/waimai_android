package com.life.waimaishuo.adapter.tag;

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

import java.util.List;

public class SpecificationWaiMaiTagAdapter extends BaseTagAdapter<String, TextView> {

    Drawable selected;
    Drawable unSelected;

    public int selectedIndex = -1;

    public SpecificationWaiMaiTagAdapter(Context context) {
        super(context);
        initDrawable();
    }

    @Override
    protected TextView newViewHolder(View convertView) {
        TextView tagView = convertView.findViewById(R.id.tv_tag);
        tagView.setTag(convertView.findViewById(R.id.iv_selected_sign));
        return tagView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.adapter_tag_item_specification_waimai;
    }

    @Override
    protected void convert(TextView textView, String item, int position) {
        textView.setText(item);
        if(position == 0){
            ((ViewGroup.MarginLayoutParams)textView.getLayoutParams()).setMarginStart(0);
        }

        ImageView likeIv = (ImageView) textView.getTag();
        List<Integer> selectedPositions = getInitSelectedPositions();

        if(selectedPositions.contains(position)){ //当前为选中的view
            LogUtil.d("当前为选中的view" + position);
            likeIv.setVisibility(View.VISIBLE);
            textView.setCompoundDrawables(selected,null,null,null);
        }else{
            LogUtil.d("未选中的view" + position);
            if(likeIv.getVisibility() == View.VISIBLE){
                likeIv.setVisibility(View.GONE);
            }
            textView.setCompoundDrawables(unSelected,null,null,null);

        }
    }

    private void initDrawable() {
        int drawableSize = (int) UIUtils.getInstance().scalePx(
                getContext().getResources().getDimensionPixelSize(R.dimen.specification_selete_sign_size));
        selected = getContext().getResources().getDrawable(R.drawable.ic_selected_sign);
        unSelected = getContext().getResources().getDrawable(R.drawable.ic_round_gray);
        selected.setBounds(0,0,drawableSize,drawableSize);
        unSelected.setBounds(0,0,drawableSize,drawableSize);
    }

}
