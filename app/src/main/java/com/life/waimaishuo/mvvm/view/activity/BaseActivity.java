package com.life.waimaishuo.mvvm.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.life.waimaishuo.util.ActivityCollector;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.base.XPageActivity;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;


public abstract class BaseActivity extends XPageActivity {

    //获取TAG的activity名称
    protected final String TAG = this.getClass().getSimpleName();
    //是否显示标题栏
    private boolean isShowTitle = true;
    //是否显示状态栏
    private boolean isShowStatusBar = true;
    //是否允许旋转屏幕
    private boolean isAllowScreenRotate = true;
    //是否设置沉浸式状态栏
    private boolean isTranslucent = true;
    //是否留出状态栏宽度
    private boolean isFitStatusBarHeight = true;
    //封装Toast对象
    private static Toast toast;
    public Context context;
//
//    public ViewDataBinding mViewDataBinding;
//    public BaseViewModel baseViewModel;

    @Override
    protected void attachBaseContext(Context newBase) {
        //XUI注入字体
        //XUI1.1.4版本之后
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
        //XUI1.1.3版本及之前
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        //activity管理
        ActivityCollector.addActivity(this);

        //MVVM 绑定布局
//        mViewDataBinding = DataBindingUtil.setContentView(this,initLayout());
//        baseViewModel = setViewModel();
//        bindViewModel();

        initActivityAttritube();

        if (!isShowTitle) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        if (!isShowStatusBar) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                    , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else{
            if(isTranslucent){
                StatusBarUtils.translucent(this, Color.TRANSPARENT);
                if(isFitStatusBarHeight){
                    StatusBarUtils.fitStatusBarHeight(this);
                }
            }
        }

        //设置屏幕是否可旋转
        if (!isAllowScreenRotate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

//        baseViewModel.init();

        initView();
    }

    /**
     * 设置Activity属性
     */
    protected abstract void initActivityAttritube();

    /**
     * 初始化view
     */
    protected abstract void initView();

    /**
     * 设置是否显示标题栏
     *
     * @param showTitle true or false
     */
    public void setShowTitle(boolean showTitle) {
        isShowTitle = showTitle;
    }

    /**
     * 设置是否显示状态栏
     *
     * @param showStatusBar true or false
     */
    public void setShowStatusBar(boolean showStatusBar) {
        isShowStatusBar = showStatusBar;
    }

    /**
     * 设置沉浸式状态栏
     * @param transluecnt
     */
    public void setTransluecnt(boolean transluecnt) {
        isTranslucent = transluecnt;
    }

    /**
     * 是否为状态栏留出空间
     * @param fitStatusBarHeight
     */
    public void setFitStatusBarHeight(boolean fitStatusBarHeight) {
        isFitStatusBarHeight = fitStatusBarHeight;
    }

    /**
     * 是否允许屏幕旋转
     *
     * @param allowScreenRotate true or false
     */
    public void setAllowScreenRotate(boolean allowScreenRotate) {
        isAllowScreenRotate = allowScreenRotate;
    }

    /**
     * 显示提示  toast
     *
     * @param msg 提示信息
     */
    @SuppressLint("ShowToast")
    public void showToast(String msg) {
        try {
            if (null == toast) {
                toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            } else {
                toast.setText(msg);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    toast.show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            //解决在子线程中调用Toast的异常情况处理
            Looper.prepare();
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mViewDataBinding.unbind();
        //activity管理
        ActivityCollector.removeActivity(this);
    }

    /**
     * 保证同一按钮在1秒内只会响应一次点击事件
     */
    public abstract static class OnSingleClickListener implements View.OnClickListener {
        //两次点击按钮之间的间隔，目前为1000ms
        private static final int MIN_CLICK_DELAY_TIME = 1000;
        private long lastClickTime;

        public abstract void onSingleClick(View view);

        @Override
        public void onClick(View view) {
            long curClickTime = System.currentTimeMillis();
            if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
                lastClickTime = curClickTime;
                onSingleClick(view);
            }
        }
    }

    public View getContentView(){
        return this.getWindow().getDecorView().findViewById(android.R.id.content);
    }

    /**
     * 同一按钮在短时间内可重复响应点击事件
     */
    public abstract static class OnMultiClickListener implements View.OnClickListener {
        public abstract void onMultiClick(View view);

        @Override
        public void onClick(View v) {
            onMultiClick(v);
        }
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && null != imm) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘
     */
    public void showSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && null != imm) {
            imm.showSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
