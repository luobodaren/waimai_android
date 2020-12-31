package com.life.waimaishuo.views.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IntRange;

import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;

public class OrderProcessView extends FrameLayout {

    float drawableSize = 40f;

    TextView tv_order_state;
    TextView tv_make_status;
    TextView tv_take_status;
    ImageView iv_first_dotted_line;
    ImageView iv_second_dotted_line;

    Drawable orderPlaceDrawable;
    Drawable orderUnPlaceDrawable;
    Drawable foodUnMakeDrawable;
    Drawable foodMadeDrawable;
    Drawable unTakeDrawable;
    Drawable tookDrawable;
    Drawable redDottedLineDrawable;
    Drawable grayDottedLineDrawable;

    public OrderProcessView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        View view = View.inflate(context, R.layout.layout_order_process, null);
        tv_order_state = view.findViewById(R.id.tv_order_state);
        tv_make_status = view.findViewById(R.id.tv_make_status);
        tv_take_status = view.findViewById(R.id.tv_take_status);
        iv_first_dotted_line = view.findViewById(R.id.iv_first_dotted_line);
        iv_second_dotted_line = view.findViewById(R.id.iv_second_dotted_line);
        this.addView(view);

    }

    private boolean isInitDrawable = false;
    private void initDrawable() {
        if(!isInitDrawable){
            isInitDrawable = true;

            int size = (int) UIUtils.getInstance(getContext()).scalePx(getDrawableSize());

            orderPlaceDrawable = getContext().getResources().getDrawable(R.drawable.ic_order_confim);
            orderPlaceDrawable.setBounds(0,0,size,size);
            orderUnPlaceDrawable = getContext().getResources().getDrawable(R.drawable.ic_order_un_confim);
            orderUnPlaceDrawable.setBounds(0,0,size,size);
            foodMadeDrawable = getContext().getResources().getDrawable(R.drawable.ic_time_red);
            foodMadeDrawable.setBounds(0,0,size,size);
            foodUnMakeDrawable = getContext().getResources().getDrawable(R.drawable.ic_time_gray);
            foodUnMakeDrawable.setBounds(0,0,size,size);
            unTakeDrawable = getContext().getResources().getDrawable(R.drawable.ic_check_round_gray);
            unTakeDrawable.setBounds(0,0,size,size);
            tookDrawable = getContext().getResources().getDrawable(R.drawable.ic_check_round_red);
            tookDrawable.setBounds(0,0,size,size);
            redDottedLineDrawable = getContext().getResources().getDrawable(R.mipmap.ic_dotted_line_red);
            grayDottedLineDrawable = getContext().getResources().getDrawable(R.mipmap.ic_dotted_line_gray);
        }
    }

    public float getDrawableSize() {
        return drawableSize;
    }

    public void setDrawableSize(float drawableSize) {
        this.drawableSize = drawableSize;
    }

    public void setProcess(@IntRange(from = 1, to = 3) int process) {
        initDrawable();

        if (process >= 1) {
            tv_order_state.setTextColor(getResources().getColor(R.color.colorTheme));
            tv_order_state.setCompoundDrawables(null,orderPlaceDrawable,null,null);
            iv_first_dotted_line.setImageDrawable(redDottedLineDrawable);
            if(process >= 2){
                tv_make_status.setTextColor(getResources().getColor(R.color.colorTheme));
                tv_make_status.setCompoundDrawables(null,foodMadeDrawable,null,null);
                iv_second_dotted_line.setImageDrawable(redDottedLineDrawable);
                if(process >= 3){
                    tv_take_status.setTextColor(getResources().getColor(R.color.colorTheme));
                    tv_take_status.setCompoundDrawables(null,tookDrawable,null,null);
                }else{
                    tv_take_status.setTextColor(getResources().getColor(R.color.text_tip));
                    tv_take_status.setCompoundDrawables(null,unTakeDrawable,null,null);
                }
            }else{
                tv_make_status.setTextColor(getResources().getColor(R.color.text_tip));
                tv_make_status.setCompoundDrawables(null,foodUnMakeDrawable,null,null);
                iv_second_dotted_line.setImageDrawable(grayDottedLineDrawable);
                tv_take_status.setTextColor(getResources().getColor(R.color.text_tip));
                tv_take_status.setCompoundDrawables(null,unTakeDrawable,null,null);
            }
        } else{
            //do nothing 理论上不会走到这里
        }
    }

}
