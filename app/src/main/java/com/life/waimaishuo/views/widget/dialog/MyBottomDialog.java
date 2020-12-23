package com.life.waimaishuo.views.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.xuexiang.xui.logs.UILog;
import com.xuexiang.xui.utils.Utils;

public class MyBottomDialog extends Dialog {

    // 动画时长
    private final static int ANIMATION_DURATION = 200;
    // 持有 ContentView，为了做动画
    private View mContentView;
    private boolean mIsAnimating = false;

    private OnBottomDialogShowListener mOnBottomDialogShowListener;

    public MyBottomDialog(@NonNull Context context) {
        super(context);
    }

    public MyBottomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected MyBottomDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //noinspection ConstantConditions
        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 在底部，宽度撑满
        int screenWidth = (int) UIUtils.getInstance(getContext()).getDisplayMetricsWidth();
        int screenHeight = (int) UIUtils.getInstance(getContext()).getDisplayMetricsHeight();
        LogUtil.e("screenWidth:" + screenWidth + " screenHeight:" + screenHeight);
        params.gravity = Gravity.BOTTOM | Gravity.CENTER;
        params.width = Math.min(screenWidth, screenHeight);
        getWindow().setAttributes(params);
        setCanceledOnTouchOutside(true);
    }

    @Override
    public void setContentView(int layoutResId) {
        mContentView = LayoutInflater.from(getContext()).inflate(layoutResId, null);
        super.setContentView(mContentView);
    }

    @Override
    public void setContentView(@NonNull View view, ViewGroup.LayoutParams params) {
        mContentView = view;
        super.setContentView(view, params);
    }

    @Override
    public void setContentView(@NonNull View view) {
        mContentView = view;
        super.setContentView(view);
    }

    public View getContentView() {
        return mContentView;
    }

    /**
     * BottomSheet升起动画
     */
    private void animateUp() {
        if (mContentView == null) {
            return;
        }
        TranslateAnimation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f
        );
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(translate);
        set.addAnimation(alpha);
        set.setInterpolator(new DecelerateInterpolator());
        set.setDuration(ANIMATION_DURATION);
        set.setFillAfter(true);
        mContentView.startAnimation(set);
    }

    /**
     * BottomSheet降下动画
     */
    private void animateDown() {
        if (mContentView == null) {
            return;
        }
        TranslateAnimation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f
        );
        AlphaAnimation alpha = new AlphaAnimation(1, 0);
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(translate);
        set.addAnimation(alpha);
        set.setInterpolator(new DecelerateInterpolator());
        set.setDuration(ANIMATION_DURATION);
        set.setFillAfter(true);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mIsAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mIsAnimating = false;
                /**
                 * Bugfix： Attempting to destroy the window while drawing!
                 */
                mContentView.post(new Runnable() {
                    @Override
                    public void run() {
                        // java.lang.IllegalArgumentException: View=com.android.internal.policy.PhoneWindow$DecorView{22dbf5b V.E...... R......D 0,0-1080,1083} not attached to window manager
                        // 在dismiss的时候可能已经detach了，简单try-catch一下
                        try {
                            MyBottomDialog.super.dismiss();
                        } catch (Exception e) {
                            UILog.w("dismiss error\n" + Log.getStackTraceString(e));
                        }
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mContentView.startAnimation(set);
    }

    @Override
    public void show() {
        super.show();
        animateUp();
        if (mOnBottomDialogShowListener != null) {
            mOnBottomDialogShowListener.onShow();
        }
    }

    @Override
    public void dismiss() {
        if (mIsAnimating) {
            return;
        }
        animateDown();
    }

    public void setOnBottomDialogShowListener(OnBottomDialogShowListener onBottomDialogShowListener) {
        mOnBottomDialogShowListener = onBottomDialogShowListener;
    }

    public interface OnBottomDialogShowListener {
        void onShow();
    }

}
