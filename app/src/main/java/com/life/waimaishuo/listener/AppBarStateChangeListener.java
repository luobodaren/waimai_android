package com.life.waimaishuo.listener;

import com.google.android.material.appbar.AppBarLayout;

/**
 * AppBarLayout的滚动监听器
 * 用于监听便捷判断当前滚动状态
 */
public abstract class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {

    private State mCurrentState = State.IDLE;

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0) {
            if (mCurrentState != State.EXPANDED) {
                onStateChanged(appBarLayout, State.EXPANDED);
            }
            mCurrentState = State.EXPANDED;
        } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
            if (mCurrentState != State.COLLAPSED) {
                onStateChanged(appBarLayout, State.COLLAPSED);
            }
            mCurrentState = State.COLLAPSED;
        } else {
            if (mCurrentState != State.IDLE) {
                onStateChanged(appBarLayout, State.IDLE);
            }
            mCurrentState = State.IDLE;
        }
    }

    public State getmCurrentState() {
        return mCurrentState;
    }

    public abstract void onStateChanged(AppBarLayout appBarLayout, State state);

    public enum State {
        EXPANDED,   //展开
        COLLAPSED,  //折叠
        IDLE    //中间状态
    }

}
