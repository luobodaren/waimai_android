package com.life.waimaishuo.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.xuexiang.xui.widget.flowlayout.FlowTagLayout;

/**
 * 在FlowTagLayout上进行修改
 * 使得换行后取消第一项间隔
 */
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int padding = (int) UIUtils.getInstance().scalePx(getResources().getDimensionPixelSize(R.dimen.interval_size_xs));

        //获取Padding
        // 获得它的父容器为它设置的测量模式和大小
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        //FlowLayout最终的宽度和高度值
        int resultWidth = 0;
        int resultHeight = 0;

        //测量时每一行的宽度
        int lineWidth = 0;
        //测量时每一行的高度，加起来就是FlowLayout的高度
        int lineHeight = 0;

        //遍历每个子元素
        for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
            View childView = getChildAt(i);
            //测量每一个子view的宽和高
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);

            //获取到测量的宽和高
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            //因为子View可能设置margin，这里要加上margin的距离
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
            int realChildWidth = childWidth + lp.leftMargin + lp.rightMargin;
            int realChildHeight = childHeight + lp.topMargin + lp.bottomMargin;

            realChildWidth += padding;//添加宽度间隙
            //如果当前一行的宽度加上要加入的子view的宽度大于父容器给的宽度，就换行
            if ((lineWidth + realChildWidth) > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                //换行
                resultWidth = Math.max(lineWidth, realChildWidth);
                realChildHeight += padding;//添加高度间隙

                resultHeight += realChildHeight ;
                //换行了，lineWidth和lineHeight重新算
                lineWidth = realChildWidth;
                lineHeight = realChildHeight;
            } else {
                //不换行，直接相加
                lineWidth += realChildWidth;
                //每一行的高度取二者最大值
                lineHeight = Math.max(lineHeight, realChildHeight);
            }

            //遍历到最后一个的时候，肯定走的是不换行
            if (i == childCount - 1) {
                resultWidth = Math.max(lineWidth, resultWidth);
                resultHeight += lineHeight;
            }

        }
        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : resultWidth,
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : resultHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        boolean isFirstLine = true;
        int padding = (int) UIUtils.getInstance().scalePx(getResources().getDimensionPixelSize(R.dimen.interval_size_xs));

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

            realChildWidth += padding;//修改宽度位置
            if (childLeft + realChildWidth > flowWidth - getPaddingLeft() - getPaddingRight()) {
                //换行处理
                isFirstLine = false;

                realChildHeight += padding; //修改高度位置

                childTop += realChildHeight;
                childLeft = getPaddingLeft();

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
