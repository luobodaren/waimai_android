package com.life.waimaishuo.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.xuexiang.xui.widget.flowlayout.FlowTagLayout;

public class MyFlowTagLayout extends FlowTagLayout {

    public MyFlowTagLayout(Context context) {
        super(context);
    }

    public MyFlowTagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFlowTagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int flowWidth = getWidth();

        int childLeft = getPaddingStart();
        int childTop = getPaddingTop();
        //遍历子控件，记录每个子view的位置
        for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
            View childView = getChildAt(i);

            //跳过View.GONE的子View
            if (childView.getVisibility() == View.GONE) {
                continue;
            }

            //获取到测量的宽和高
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            //因为子View可能设置margin，这里要加上margin的距离
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
            int realChildWidth = childWidth + lp.leftMargin + lp.rightMargin;
            int realChildHeight = childHeight + lp.topMargin + lp.bottomMargin;
            if (childLeft + realChildWidth > flowWidth - getPaddingLeft() - getPaddingRight()) {
                //换行处理
                childTop += realChildHeight;
                childLeft = 0;
            }
            //布局
            if (isRtl()) {
                int end = flowWidth - (childLeft + lp.getMarginStart());
                int top = childTop + lp.topMargin;
                int start = end - childWidth;
                int bottom = top + childHeight;
                childView.layout(start, top, end, bottom);
            } else {
                int left = childLeft + lp.getMarginStart();
                int top = childTop + lp.topMargin;
                int right = left + childWidth;
                int bottom = top + childHeight;
                childView.layout(left, top, right, bottom);
            }
            childLeft += realChildWidth;
        }
    }

    private boolean isRtl() {
        return getLayoutDirection() == LAYOUT_DIRECTION_RTL;
    }
}
