package com.life.waimaishuo.views.widget.pop;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.xuexiang.xui.widget.popupwindow.PopWindow;

import static com.xuexiang.xui.utils.ResUtils.getResources;

public class MyCheckRoundTextInfoPop extends PopWindow {

    private TextView mTvInfo;
    private String mInfo;
    private boolean mIsCheck;


    public MyCheckRoundTextInfoPop(Context context,String info,boolean isCheck) {
        super(context, R.layout.pop_read_all_message);
        mInfo = info;
        mIsCheck = isCheck;
        init();
    }

    public void showAtCenter(View parent){
        showAtLocation(parent, Gravity.CENTER,0,0);
    }

    private void init(){
//        getContentView().setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));

        int drawableSize = (int) UIUtils.getInstance().scalePx(getResources().getDimensionPixelSize(R.dimen.message_all_read_check_round_size));
        Drawable drawable = getContext().getResources().getDrawable(R.drawable.ic_check_round);
        drawable.setBounds(0,0, drawableSize, drawableSize);

        mTvInfo = getContentView().findViewById(R.id.iv_text);
        if(mIsCheck)
            mTvInfo.setCompoundDrawables(null, drawable,null,null);
        else    // FIXME: 2020/12/22 添加打叉drawable
            mTvInfo.setCompoundDrawables(null,null,null,null);
        mTvInfo = (TextView)this.findViewById(R.id.iv_text);
        mTvInfo.setText(mInfo);

    }


}
