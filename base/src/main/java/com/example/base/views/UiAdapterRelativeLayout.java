package com.example.base.views;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;

import com.example.base.utils.UIUtils;

public class UiAdapterRelativeLayout extends RelativeLayout {
    public UiAdapterRelativeLayout(Context context) {
        super(context);
    }

    public UiAdapterRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UiAdapterRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public UiAdapterRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        autoAdapterUI();
    }

    /**
     * 自动适配UI
     */
    private void autoAdapterUI(){
        float scaleX = UIUtils.getInstance(getContext()).getHorValue();
        float scaleY = UIUtils.getInstance(getContext()).getVerValue();
        int count = this.getChildCount();
        for(int i = 0; i < count; i++){
            View child = getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
            layoutParams.width = (int) (layoutParams.width * scaleX);
            layoutParams.height = (int) (layoutParams.height * scaleY);
            layoutParams.leftMargin = (int) (layoutParams.leftMargin * scaleX);
            layoutParams.rightMargin = (int) (layoutParams.rightMargin * scaleX);
            layoutParams.topMargin = (int) (layoutParams.topMargin * scaleY);
            layoutParams.bottomMargin = (int) (layoutParams.bottomMargin * scaleY);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

}
