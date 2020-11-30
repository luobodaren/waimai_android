package com.example.myapplication.mvvm.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Looper;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.myapplication.R;
import com.example.myapplication.mvvm.vm.BaseViewModel;
import com.example.myapplication.util.ActivityCollector;
import com.example.myapplication.util.StatusBarUtils;


public abstract class BaseActivity extends com.example.base.activity.BaseActivity {

    //获取TAG的activity名称
    protected final String TAG = this.getClass().getSimpleName();
    //是否显示标题栏
    private boolean isShowTitle = true;
    //是否显示状态栏
    private boolean isShowStatusBar = true;
    //是否允许旋转屏幕
    private boolean isAllowScreenRotate = true;
    //是否设置沉浸式状态栏
    private boolean isTransluecnt = true;
    //是否留出状态栏宽度
    private boolean isFitWindow = true;
    //封装Toast对象
    private static Toast toast;
    public Context context;

    public ViewDataBinding mViewDataBinding;
    public BaseViewModel baseViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        //activity管理
        ActivityCollector.addActivity(this);

        //MVVM 绑定布局
        mViewDataBinding = DataBindingUtil.setContentView(this,initLayout());
        baseViewModel = setViewModel();
        bindViewModel();

        initActivityAttritube();

        if (!isShowTitle) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        if (!isShowStatusBar) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                    , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else{
            if(isTransluecnt){
                StatusBarUtils.translucent(this,getResources().getColor(R.color.transparent));
                if(isFitWindow){
                    StatusBarUtils.fitStatusBarHight(this);
                }
            }
        }

        //设置屏幕是否可旋转
        if (!isAllowScreenRotate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        baseViewModel.init();

        initView();
    }

    /**
     * 初始化布局
     *
     * @return 布局id
     */
    protected abstract int initLayout();

    /**
     * 设置viewModel
     */
    protected abstract BaseViewModel setViewModel();

    /**
     * 绑定ViewModel
     */
    protected abstract void bindViewModel();

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
        isTransluecnt = transluecnt;
    }

    /**
     * 是否为状态栏留出空间
     * @param fitWindow
     */
    public void setFitWindow(boolean fitWindow) {
        isFitWindow = fitWindow;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewDataBinding.unbind();
        //activity管理
        ActivityCollector.removeActivity(this);
    }


}
