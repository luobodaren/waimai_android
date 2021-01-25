package com.life.waimaishuo.views.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
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

import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;

/**
 * 顶部
 * 显示到状态栏
 * 显示后activity窗口不变灰
 */
public class TopLightBackgroundDialog extends Dialog {

    // 动画时长
    private final static int ANIMATION_DURATION = 300;

    private View mContentView;
    private boolean mIsAnimating;

    public TopLightBackgroundDialog(@NonNull Context context) {
        super(context, R.style.myLightBackGroundDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //noinspection ConstantConditions
        getWindow().getDecorView().setPadding(0, 0, 0, 0);


        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.TOP | Gravity.CENTER;
        params.flags = WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;   //显示到状态栏下

        int screenWidth = (int) UIUtils.getInstance().getDisplayMetricsWidth();
        int screenHeight = (int) UIUtils.getInstance().getDisplayMetricsHeight();
        params.width = Math.min(screenWidth, screenHeight);
        getWindow().setAttributes(params);
        setCanceledOnTouchOutside(true);
    }

    @Override
    public void setContentView(int layoutResID) {
        mContentView = LayoutInflater.from(getContext()).inflate(layoutResID, null);
        super.setContentView(mContentView);
    }

    @Override
    public void setContentView(@NonNull View view) {
        mContentView = view;
        super.setContentView(view);
    }

    @Override
    public void show() {
        super.show();
        animateShow();
    }

    @Override
    public void dismiss() {
        if (mIsAnimating) {
            return;
        }
        animateHide();
    }

    /**
     * 弹出
     */
    private void animateShow() {
        if (mContentView == null) {
            return;
        }
        TranslateAnimation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0f
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
     * 关闭动画
     */
    private void animateHide() {
        if (mContentView == null) {
            return;
        }
        TranslateAnimation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1f
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
                        try {   //预防在dismiss时已detach
                            TopLightBackgroundDialog.super.dismiss();
                        } catch (Exception e) {
                            LogUtil.w("dismiss error\n" + e.getMessage());
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

}
