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

    public final static int CAN_SCROLL_DISTANCE_ADJUST_TOP_VIEW = -1; //可滚动距离根据TOP_VIEW而定

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
    private int mCanScrollDistance = 0;
    /**
     * 自定义父布局可滚动的距离
     */
    private int mCustomCanScrollDistance = CAN_SCROLL_DISTANCE_ADJUST_TOP_VIEW;

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
        typedArray.recycle();

        setOrientation(LinearLayout.VERTICAL);
        setNestedScrollingEnabled(true);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangeListener != null) {
            mOnScrollChangeListener.onScroll(((float) t) / getCanScrollDistance());
        }
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);

    }

    /* ----------NestedScrollingChild2 接口实现----------*/

    /**
     * 重写是否支持嵌套滚动的方法
     *
     * @param enabled
     */
    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        getScrollingChildHelper().setNestedScrollingEnabled(enabled);
    }

    /**
     * 自身是否支持嵌套滚动
     *
     * @return
     */
    @Override
    public boolean isNestedScrollingEnabled() {
        return getScrollingChildHelper().isNestedScrollingEnabled();
    }

    /**
     * 开始内部嵌套滚动，返回值为是否可以内部嵌套滚动，参数为滚动方向
     *
     * @param axes
     * @param type
     * @return
     */
    @Override
    public boolean startNestedScroll(int axes, int type) {
        return getScrollingChildHelper().startNestedScroll(axes, type);
        //LogUtil.d("startNestedScroll result:" + result + " ViewId:" + getTag());
        //return result;
    }

    /**
     * 停止内部嵌套滚动
     *
     * @param type
     */
    @Override
    public void stopNestedScroll(int type) {
        getScrollingChildHelper().stopNestedScroll(type);
    }

    /**
     * 是否已经有支持其内部嵌套滚动的父view
     *
     * @param type
     * @return
     */
    @Override
    public boolean hasNestedScrollingParent(int type) {
        return getScrollingChildHelper().hasNestedScrollingParent(type);
    }

    /**
     * 在内部嵌套滚动时，派发给支持其嵌套滚动的parent，使其有机会做一些滚动的处理
     * 参数为x/y轴已消费的和未消费的距离
     *
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     * @param offsetInWindow
     * @param type
     * @return
     */
    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow, int type) {
        return getScrollingChildHelper().dispatchNestedScroll(dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed, offsetInWindow, type);
    }

    /**
     * 在内部嵌套滚动前，派发给支持其嵌套滚动的parent，使其有机会做一些滚动的预处理
     * dx,dy为可以消耗的距离，consumed为已经消耗的距离
     * 返回值为支持其嵌套滚动的parent是否消费了部分距离
     *
     * @param dx
     * @param dy
     * @param consumed
     * @param offsetInWindow
     * @param type
     * @return
     */
    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow, int type) {
        return getScrollingChildHelper().dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow,
                type);
        //LogUtil.d("dispatchNestedPreScroll result:" + result + " ViewId:" + getTag());
        //return result;
    }

    /**
     * 在内部嵌套自由滑动时，派发给支持其嵌套滚动的parent，使其有机会做一些自由滑动的处理
     *
     * @param velocityX
     * @param velocityY
     * @param consumed
     * @return
     */
    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return getScrollingChildHelper().dispatchNestedFling(velocityX, velocityY, consumed);
    }

    /**
     * 在内部嵌套自由滑动前，派发给支持其嵌套滚动的parent，使其有机会做一些自由滑动的预处理
     * 返回值为支持其嵌套滚动的parent是否消费了fling事件
     *
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return getScrollingChildHelper().dispatchNestedPreFling(velocityX, velocityX);
    }

    /* ---------NestedScrollingParent2 接口实现----------*/

    /**
     * 决定了当前控件是否能接收到其内部View(非并非是直接子View)滑动时的参数 返回接受横向或纵向
     * 是否接受此次的内部嵌套滚动
     * target是想要内部滚动的view，child是包含target的parent的直接子view
     *
     * @param child
     * @param target
     * @param axes
     * @param type
     * @return
     */
    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        boolean result = (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0; //接受垂直滚动
        //LogUtil.d("onStartNestedScroll result:" + result + " ViewId:" + getTag());
        return result;
    }

    /**
     * 接受内部滚动后，做一些预处理工作
     *
     * @param child
     * @param target
     * @param axes
     * @param type
     */
    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        getScrollingParentHelper().onNestedScrollAccepted(child, target, axes, type);
    }

    /**
     * 停止了内部滚动
     *
     * @param target
     * @param type
     */
    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        getScrollingParentHelper().onStopNestedScroll(target, type);
    }

    /**
     * 内部嵌套滚动开始，根据已消费和未消费的距离参数进行应用
     *
     * @param target
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     * @param type
     */
    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        if (dyUnconsumed < 0) {
            //表示已经向下滑动到头，这里不用区分手势还是fling
            int scrollY = getScrollY();
            if (scrollY >= -dyUnconsumed) {
                scrollBy(0, dyUnconsumed);
            } else {
                scrollBy(0, -scrollY);
                int[] offsetInWindow = new int[2];
                dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed + scrollY, offsetInWindow, type);
            }
        }
    }

    /**
     * 内部嵌套滚动开始前做一些预处理，主要是根据dx,dy，将自己要消费的距离计算出来，告知target(通过consumed一层层记录实现)
     *
     * @param target
     * @param dx
     * @param dy
     * @param consumed
     * @param type
     */
    @SuppressLint("WrongConstant")
    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        // consumed[0] 水平消耗的距离，consumed[1] 垂直消耗的距离

        int canScrollY = dy;
        int canScrollX = dx;
        //LogUtil.d("onNestedPreScroll ViewId:" + getTag());

        if (mIsNeedResetCanScrollDistance) {
            calculateCanScrollDistance();
            mIsNeedResetCanScrollDistance = false;
        }

        //若向上滚动 交由parent处理
        if (canScrollY > 0) {
            //LogUtil.d("向上滚动");
            if (startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, type)) {//先由parent处理滚动
                int[] consumed_2 = new int[2];
                if (dispatchNestedPreScroll(canScrollX, canScrollY, consumed_2, mScrollOffset, type)) {
                    //如果父控件需要消耗，则处理父控件消耗的部分数据
                    canScrollY -= consumed_2[1];
                    canScrollX -= consumed_2[0];
                }
            }


            if (canScrollY > 0) { //parent不处理或处理剩下的距离 交由自己处理
                if (mTopView instanceof NestedScrollView) { //若topView也为scrollView 则交由顶部处理
                    if (mTopView.canScrollVertically(1)) {
                        int topViewCanScrollY = 1;  //剩余可向下滚动的距离
                        if (canScrollY > topViewCanScrollY) {
                            mTopView.scrollBy(0, topViewCanScrollY);
                            canScrollY -= topViewCanScrollY;
                        } else {
                            mTopView.scrollBy(0, canScrollY);
                            consumed[1] = dy;
                            return;
                        }
                    }
                }

                //则先判断自身的滚动情况
                //如果隐藏topView
                boolean hideTop = canScrollY > 0 && getScrollY() < getCanScrollDistance();
                //如果显示，必须要子view不能向下滑动后，才能交给自身滑动
                boolean showTop = canScrollY < 0 && getScrollY() > 0 && !target.canScrollVertically(1);
                //LogUtil.d(getTag() + " hideTop:" + hideTop + " showTop:" + showTop);
                if (hideTop || showTop) {
                    int myCanScrollY = getCanScrollDistance() - getScrollY();
                    if (myCanScrollY > canScrollY) {
                        scrollBy(0, canScrollY);
                        consumed[1] = dy;
                    } else {
                        scrollBy(0, myCanScrollY);
                        consumed[1] = dy - (canScrollY - myCanScrollY);
                    }
                }

            }

        }


        /*else {
            //则先判断自身的滚动情况
            //如果隐藏topView
            boolean hideTop = canScrollY > 0 && getScrollY() < getCanScrollDistance();
            //如果显示，必须要子view不能向下滑动后，才能交给自身滑动
            boolean showTop = canScrollY < 0 && getScrollY() > 0 && !target.canScrollVertically(1);
            //LogUtil.d(getTag() + " hideTop:" + hideTop + " showTop:" + showTop);
            if (hideTop || showTop) {
                scrollBy(0, canScrollY);
                consumed[1] = dy;
            }
        }*/

        /*//父布局与topView均处理完滚动

        //则先判断自身的滚动情况
        //如果隐藏topView
        boolean hideTop = canScrollY > 0 && getScrollY() < getCanScrollDistance();
        //如果显示，必须要子view不能向下滑动后，才能交给自身滑动
        boolean showTop = canScrollY < 0 && getScrollY() > 0 && !target.canScrollVertically(1);
        //LogUtil.d(getTag() + " hideTop:" + hideTop + " showTop:" + showTop);
        if (hideTop || showTop) {
            scrollBy(0, canScrollY);
            consumed[1] = dy;
        }
        if(!showTop && !hideTop){
            int[] consumed_3 = new int[2];
            boolean result = startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL,type); //先由parent处理滚动
            if(result && dispatchNestedPreScroll(canScrollX,canScrollY,consumed_3,mScrollOffset,type)){
                //如果父控件需要消耗，则处理父控件消耗的部分数据
                canScrollY -= consumed_3[1];
                canScrollX -= consumed_3[0];
                consumed[1] = dy - canScrollY;
                consumed[0] = dx - canScrollX;
            }
        }*/
    }


    /**
     * 内部嵌套滑动开始，consumed参数为target是否消费了fling事件，parent可以根据此来做出自己的选择
     * 返回值为parent自己是否消费了fling事件
     *
     * @param target
     * @param velocityX
     * @param velocityY
     * @param consumed
     * @return
     */
    @Override
    public boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    /**
     * 嵌套滑动时，如果父View处理了fling,那子view就没有办法处理fling了，所以这里要返回为false
     */
    @Override
    public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {
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
            //LogUtil.d("mContentLayout height:" + mContentLayout.getMeasuredHeight());
            //}
        }

        ViewGroup.LayoutParams lp = mAdaptiveSizeView.getLayoutParams();
        /*if(mCustomCanScrollDistance != CAN_SCROLL_DISTANCE_ADJUST_TOP_VIEW){
            lp.height = (int) UIUtils.getInstance().scalePx(mCustomCanScrollDistance);
        }else{

        }*/
        if (mContentLayout != null) {
            lp.height = getMeasuredHeight() - mNavigationView.getMeasuredHeight()
                    - mContentLayout.getMeasuredHeight();
            //LogUtil.d("getMeasuredHeight:" + getMeasuredHeight() + "  mNavigationView:" + mNavigationView.getMeasuredHeight() + "  mContentLayout:" + mContentLayout.getMeasuredHeight() + "  mainTabBarHeight:" + mainTabBarHeight + " StatusBarHeight:" + StatusBarUtils.getStatusBarHeight(getContext()));

        } else {
            lp.height = getMeasuredHeight() - mNavigationView.getMeasuredHeight();
            //LogUtil.d("getMeasuredHeight:" + getMeasuredHeight() + "  mNavigationView:" + mNavigationView.getMeasuredHeight() + "  mainTabBarHeight:" + mainTabBarHeight + " StatusBarHeight:" + StatusBarUtils.getStatusBarHeight(getContext()));
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
        if (getScrollY() != y) {
            super.scrollTo(x, y);
        }
    }

    private int getCanScrollDistance() {
        int canScrollDistance;
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
        //LogUtil.d("canScrollDistance=" + mCanScrollDistance + " ViewId:" + getTag());
    }

    private NestedScrollingParentHelper getScrollingParentHelper() {
        if (mNestedScrollingParentHelper == null) {
            mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        }
        return mNestedScrollingParentHelper;
    }

    private NestedScrollingChildHelper getScrollingChildHelper() {
        if (mNestedScrollingChildHelper == null) {
            mNestedScrollingChildHelper = new NestedScrollingChildHelper(this);
            mNestedScrollingChildHelper.setNestedScrollingEnabled(true);    //默认设置支持内部滚动
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

    public void setCustomCanScrollDistance(int distance) {
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
