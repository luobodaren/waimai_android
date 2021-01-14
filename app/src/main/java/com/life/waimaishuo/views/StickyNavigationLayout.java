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

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
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
 *
 * 顶部支持滚动布局
 */
public class StickyNavigationLayout extends UiAdapterLinearLayout implements NestedScrollingParent2 {

    private NestedScrollingParentHelper mNestedScrollingParentHelper;

    private View mTopView;
    private View mNavigationView;
    private View mContentLayout;    //位于Navigation(TabBar)与AdaptiveView(ViewPager)中间的布局
    private View mAdaptiveSizeView;

    private boolean mIsFitStatusBar;

    private OnScrollChangeListener mOnScrollChangeListener;

    /**
     * 父控件可以滚动的距离
     */
    private float mCanScrollDistance = 0f;

    public StickyNavigationLayout(Context context) {
        this(context, null);
    }

    public StickyNavigationLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickyNavigationLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StickyNavigationLayout);
        mIsFitStatusBar = typedArray.getBoolean(R.styleable.StickyNavigationLayout_fitStatusBar,false);
        setOrientation(LinearLayout.VERTICAL);
        mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if(mTopView instanceof NestedScrollView){ //若topView也为scrollView 则交由顶部处理
            if(target.getId() == mTopView.getId()){
                if(dy > 0) { //若向上滚动
                    if (mTopView.canScrollVertically(1)) {
                        mTopView.scrollBy(0, dy);
                        consumed[1] = dy;
                        return;
                    }
                }else{  //向下 交由父布局处理后，再又topView处理
                    if(getScrollY() > 0){
                        scrollBy(0,dy);
                        consumed[1] = dy;
                        return;
                    }
                }
            }
        }

        //若topView不处理滚动 则先判断父布局的滚动情况
        //如果子view欲向上滑动，则先交给父view滑动
        boolean hideTop = dy > 0 && getScrollY() < mCanScrollDistance;
        //如果子view欲向下滑动，必须要子view不能向下滑动后，才能交给父view滑动
        boolean showTop = dy < 0 && getScrollY() >= 0 && !target.canScrollVertically(-1);
        if (hideTop || showTop) {
            scrollBy(0, dy);
            // consumed[0] 水平消耗的距离，consumed[1] 垂直消耗的距离
            consumed[1] = dy;
        }
    }


    /**
     * 决定了当前控件是否能接收到其内部View(非并非是直接子View)滑动时的参数 返回接受横向或纵向
     * @param child
     * @param target
     * @param axes
     * @param type
     * @return
     */
    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes, type);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        mNestedScrollingParentHelper.onStopNestedScroll(target, type);
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        if (dyUnconsumed < 0) {
            //表示已经向下滑动到头，这里不用区分手势还是fling
            scrollBy(0, dyUnconsumed);
        }
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
        mTopView = findViewById(R.id.top_view);
        mNavigationView = findViewById(R.id.sticky_view);
        mContentLayout = findViewById(R.id.content_layout);
        mAdaptiveSizeView = findViewById(R.id.adaptive_size_view);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //先测量一次
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if(mContentLayout != null){ //计算contentLayout大小
            if(mContentLayout.getMeasuredHeight() == 0){
                mContentLayout.measure(0,0);
                ViewGroup.LayoutParams lp = mContentLayout.getLayoutParams();
                lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                lp.height = mContentLayout.getMeasuredHeight();
                mContentLayout.setLayoutParams(lp);
                LogUtil.e("mContentLayout height:" + mContentLayout.getMeasuredHeight());
            }
        }

        //ViewPager修改后的高度
        ViewGroup.LayoutParams lp = mAdaptiveSizeView.getLayoutParams();
        if(mContentLayout != null){
            lp.height = getMeasuredHeight() - mNavigationView.getMeasuredHeight()
                    - mContentLayout.getMeasuredHeight();
//            LogUtil.d("getMeasuredHeight:" + getMeasuredHeight() + "  mNavigationView:" + mNavigationView.getMeasuredHeight() + "  mContentLayout:" + mContentLayout.getMeasuredHeight() + "  mainTabBarHeight:" + mainTabBarHeight + " StatusBarHeight:" + StatusBarUtils.getStatusBarHeight(getContext()));

        }else{
            lp.height = getMeasuredHeight() - mNavigationView.getMeasuredHeight();
//            LogUtil.d("getMeasuredHeight:" + getMeasuredHeight() + "  mNavigationView:" + mNavigationView.getMeasuredHeight() + "  mainTabBarHeight:" + mainTabBarHeight + " StatusBarHeight:" + StatusBarUtils.getStatusBarHeight(getContext()));
        }
        LogUtil.d("viewPagerHeight:" + lp.height);
        mAdaptiveSizeView.setLayoutParams(lp);

        //因为ViewPager修改了高度，所以需要重新测量
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if(mTopView != null){
            mCanScrollDistance += mTopView.getMeasuredHeight();
        }
        if(mIsFitStatusBar){
            mCanScrollDistance += StatusBarUtils.getStatusBarHeight(getContext());
        }

//        if(mCanScrollDistance > UIUtils.getInstance().getDisplayMetricsHeight()){
//            mCanScrollDistance = UIUtils.getInstance().getDisplayMetricsHeight()
//                    - StatusBarUtils.getStatusBarHeight(getContext());
//        }

        LogUtil.d("StickyNavigation canScrollDistance=" + mCanScrollDistance);
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > mCanScrollDistance) {
            y = (int) mCanScrollDistance;
        }
        if (mOnScrollChangeListener != null) {
            mOnScrollChangeListener.onScroll(y / mCanScrollDistance);
        }
        if (getScrollY() != y) {
            super.scrollTo(x, y);
        }
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
