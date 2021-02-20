/*
 * Copyright (C) 2020 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.life.waimaishuo.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingChild2;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;

import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.base.views.UiAdapterLinearLayout;
import com.life.waimaishuo.R;
import com.xuexiang.xui.utils.StatusBarUtils;

/**
 * 支持导航栏与viewpager 导航栏吸附
 * <p>
 * 支持顶部（TopView）滚动布局
 * 支持多个StickyNavigationLayout嵌套使用（即adapterSizeView为另一个StickyNavigationLayout），需要代码中动态addView（使得StickyNavigationLayout可以拿到parent,进行嵌套滚动判断）
 */
public class StickyNavigationLayout extends UiAdapterLinearLayout implements NestedScrollingParent2, NestedScrollingChild2 {

    public final static float CAN_SCROLL_DISTANCE_ADJUST_TOP_VIEW = -1; //可滚动距离根据TOP_VIEW而定

    private NestedScrollingParentHelper mNestedScrollingParentHelper;
    private NestedScrollingChildHelper mNestedScrollingChildHelper;
    private final int[] mScrollOffset = new int[2];
    private final int[] mNestedOffsets = new int[2];

    private View mTopView;
    private View mNavigationView;
    private View mContentLayout;    //位于Navigation(TabBar)与AdaptiveView(ViewPager)中间的布局
    private View mAdaptiveSizeView;

    private boolean mIsNeedResetCanScrollDistance = false;  //设置重新计算可滚动高度，在开始滚动时重新获取
    private boolean mIsFitStatusBar;    //若全屏 且topView中动态添加了状态栏高度的view(为状态栏腾出空间的view),需要设置该值为true

    private OnScrollChangeListener mOnScrollChangeListener;

    /**
     * 父控件可以滚动的距离
     */
    private float mCanScrollDistance = 0f;
    /**
     * 自定义父布局可滚动的距离
     */
    private float mCustomCanScrollDistance = CAN_SCROLL_DISTANCE_ADJUST_TOP_VIEW;

    public StickyNavigationLayout(Context context) {
        this(context, null);
    }

    public StickyNavigationLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickyNavigationLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StickyNavigationLayout);
        mIsFitStatusBar = typedArray.getBoolean(R.styleable.StickyNavigationLayout_fitStatusBar, false);
        setOrientation(LinearLayout.VERTICAL);
        setNestedScrollingEnabled(true);
    }

    /* NestedScrollingChild2 接口实现 */
    @Override
    public boolean startNestedScroll(int axes, int type) {
        boolean result = getScrollingChildHelper().startNestedScroll(axes,type);
        LogUtil.d("startNestedScroll result:" + result + " ViewId:" + getTag());
        return result;
    }

    @Override
    public void stopNestedScroll(int type) {
        getScrollingChildHelper().stopNestedScroll(type);
    }

    @Override
    public boolean hasNestedScrollingParent(int type) {
        return getScrollingChildHelper().hasNestedScrollingParent(type);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow, int type) {
        return getScrollingChildHelper().dispatchNestedScroll(dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed, offsetInWindow,type);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow, int type) {
        boolean result = getScrollingChildHelper().dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow,
                type);
        LogUtil.d("dispatchNestedPreScroll result:" + result + " ViewId:" + getTag());
        return result;
    }

    /* NestedScrollingParent2 接口实现 */
    @SuppressLint("WrongConstant")
    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        // consumed[0] 水平消耗的距离，consumed[1] 垂直消耗的距离

        LogUtil.d("onNestedPreScroll ViewId:" + getTag());

        if (mIsNeedResetCanScrollDistance) {
            calculateCanScrollDistance();
            mIsNeedResetCanScrollDistance = false;
        }

        if(dy > 0){ //若向上滚动
            LogUtil.d("向上滚动");

            startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL,type); //先由parent处理滚动
            if(dispatchNestedPreScroll(dx,dy,consumed,mScrollOffset,type)){
                //如果父控件需要消耗，则处理父控件消耗的部分数据
                dy -= consumed[1];
                dx -= consumed[0];
            }

            if (mTopView instanceof NestedScrollView) { //若topView也为scrollView 则交由顶部处理
                if (target.getId() == mTopView.getId()) {
                    if (mTopView.canScrollVertically(1)) {
                        mTopView.scrollBy(0, dy);
                        consumed[1] = dy;
                        return;
                    }
                    /*if (dy > 0) {

                    } else {
                        LogUtil.d("向下滚动");
                        if (getScrollY() > 0) {
                            scrollBy(0, dy);
                            consumed[1] = dy;
                            return;
                        } else {
                            mTopView.scrollBy(0, dy);
                            consumed[1] = dy;
                            return;
                        }
                    }*/
                }
            }

        }

        //父布局与topView均处理完滚动

        //则先判断自身的滚动情况
        //如果隐藏topView
        boolean hideTop = dy > 0 && getScrollY() < getCanScrollDistance();
        //如果显示，必须要子view不能向下滑动后，才能交给自身滑动
        boolean showTop = dy < 0 && getScrollY() >= 0 && !target.canScrollVertically(-1);
        if (hideTop || showTop) {
            if(showTop){    //显示顶部
                if(getScrollY() == 0){
                    //若已显示完全，交由父布局处理
                    startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL,type); //先由parent处理滚动
                    if(dispatchNestedPreScroll(dx,dy,consumed,mScrollOffset,type)){
                        //如果父控件需要消耗，则处理父控件消耗的部分数据
                        dy -= consumed[1];
                        dx -= consumed[0];
                    }
                    return;
                }
            }
            scrollBy(0, dy);
            consumed[1] = dy;
        }



    }

    /**
     * 决定了当前控件是否能接收到其内部View(非并非是直接子View)滑动时的参数 返回接受横向或纵向
     *
     * @param child
     * @param target
     * @param axes
     * @param type
     * @return
     */
    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        boolean result = (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
        LogUtil.d("onStartNestedScroll result:" + result + " ViewId:" + getTag());
        return result;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        getScrollingParentHelper().onNestedScrollAccepted(child, target, axes, type);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        getScrollingParentHelper().onStopNestedScroll(target, type);
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        if (dyUnconsumed < 0) {
            //表示已经向下滑动到头，这里不用区分手势还是fling
            scrollBy(0, dyUnconsumed);
        }
    }

    /**
     * 重写是否支持嵌套滚动的方法
     * @param enabled
     */
    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        getScrollingChildHelper().setNestedScrollingEnabled(true);
    }

    /**
     * 嵌套滑动时，如果父View处理了fling,那子view就没有办法处理fling了，所以这里要返回为false
     */
    @Override
    public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //此处findViewById只会找到布局文件中的ViewId,代码中动态引入的是不会被寻找到的  待验证？
        mTopView = findViewById(R.id.top_view);
        mNavigationView = findViewById(R.id.sticky_view);
        mContentLayout = findViewById(R.id.content_layout);
        mAdaptiveSizeView = findViewById(R.id.adaptive_size_view);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //先测量一次
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mContentLayout != null) { //计算contentLayout大小
            //if (mContentLayout.getMeasuredHeight() == 0) {    //仅测量一次会导致在View设置可见或隐藏时大小出错
                mContentLayout.measure(0, 0);
                ViewGroup.LayoutParams lp = mContentLayout.getLayoutParams();
                lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                lp.height = mContentLayout.getMeasuredHeight();
                mContentLayout.setLayoutParams(lp);
                LogUtil.d("mContentLayout height:" + mContentLayout.getMeasuredHeight());
            //}
        }

        ViewGroup.LayoutParams lp = mAdaptiveSizeView.getLayoutParams();
        if(mCustomCanScrollDistance != CAN_SCROLL_DISTANCE_ADJUST_TOP_VIEW){
            lp.height = (int) UIUtils.getInstance().scalePx(mCustomCanScrollDistance);
        }else{
            if (mContentLayout != null) {
                lp.height = getMeasuredHeight() - mNavigationView.getMeasuredHeight()
                        - mContentLayout.getMeasuredHeight();
                //LogUtil.d("getMeasuredHeight:" + getMeasuredHeight() + "  mNavigationView:" + mNavigationView.getMeasuredHeight() + "  mContentLayout:" + mContentLayout.getMeasuredHeight() + "  mainTabBarHeight:" + mainTabBarHeight + " StatusBarHeight:" + StatusBarUtils.getStatusBarHeight(getContext()));

            } else {
                lp.height = getMeasuredHeight() - mNavigationView.getMeasuredHeight();
                //LogUtil.d("getMeasuredHeight:" + getMeasuredHeight() + "  mNavigationView:" + mNavigationView.getMeasuredHeight() + "  mainTabBarHeight:" + mainTabBarHeight + " StatusBarHeight:" + StatusBarUtils.getStatusBarHeight(getContext()));
            }
        }
        //ViewPager修改后的高度
        mAdaptiveSizeView.setLayoutParams(lp);
        //LogUtil.d("viewPagerHeight:" + lp.height);

        //因为ViewPager修改了高度，所以需要重新测量
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        calculateCanScrollDistance();
    }

    @Override
    public void scrollTo(int x, int y) {
        float canScrollDistance = getCanScrollDistance();

        if (y < 0) {
            y = 0;
        }
        if (y > canScrollDistance) {
            y = (int) canScrollDistance;
        }
        if (mOnScrollChangeListener != null) {
            mOnScrollChangeListener.onScroll(y / canScrollDistance);
        }
        if (getScrollY() != y) {
            super.scrollTo(x, y);
        }
    }

    private float getCanScrollDistance() {
        float canScrollDistance;
        if (mCustomCanScrollDistance != CAN_SCROLL_DISTANCE_ADJUST_TOP_VIEW) {
            canScrollDistance = mCustomCanScrollDistance;
        } else {
            canScrollDistance = mCanScrollDistance;
        }
        return canScrollDistance;
    }

    /**
     * 计算可滚动的距离
     */
    private void calculateCanScrollDistance() {
        if (mTopView != null) {
            mCanScrollDistance = mTopView.getMeasuredHeight();
        }
        if (mIsFitStatusBar) {
            mCanScrollDistance += StatusBarUtils.getStatusBarHeight(getContext());
        }
        LogUtil.d("canScrollDistance=" + mCanScrollDistance + " ViewId:" + getTag());
    }

    private NestedScrollingParentHelper getScrollingParentHelper(){
        if(mNestedScrollingParentHelper == null){
            mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        }
        return mNestedScrollingParentHelper;
    }

    private NestedScrollingChildHelper getScrollingChildHelper() {
        if (mNestedScrollingChildHelper == null) {
            mNestedScrollingChildHelper = new NestedScrollingChildHelper(this);
        }
        return mNestedScrollingChildHelper;
    }

    /**
     * 重新设置可滚动距离标志
     * 改变了TopView内容大小时调用
     *
     * @param b
     */
    public void setNeedResetCanScrollDistance(boolean b) {
        mIsNeedResetCanScrollDistance = b;
    }

    public void setCustomCanScrollDistance(float distance) {
        mCustomCanScrollDistance = distance;
    }

    /**
     * 滑动监听
     */
    public interface OnScrollChangeListener {
        /**
         * 移动监听
         *
         * @param moveRatio 移动比例 范围0-1 0表示未滑动 1表示滑动完全完成
         */
        void onScroll(float moveRatio);
    }

    public StickyNavigationLayout setOnScrollChangeListener(OnScrollChangeListener listener) {
        mOnScrollChangeListener = listener;
        return this;
    }

}
