package com.life.waimaishuo.mvvm.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.life.base.utils.LogUtil;
import com.life.waimaishuo.R;
import com.life.waimaishuo.databinding.LayoutDialogDirectFunctionBinding;
import com.life.waimaishuo.mvvm.view.fragment.common.MainFragment;
import com.life.waimaishuo.util.ActivityCollector;
import com.life.waimaishuo.util.MyDataBindingUtil;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.views.widget.dialog.TopLightBackgroundDialog;
import com.xuexiang.xpage.base.XPageActivity;
import com.xuexiang.xpage.core.CoreSwitchBean;

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

        initActivityAttribute();

        if (!isShowTitle) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        if (!isShowStatusBar) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                    , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            if (isTranslucent) {
                StatusBarUtils.translucent(this, Color.TRANSPARENT);
                if (isFitStatusBarHeight) {
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


    TopLightBackgroundDialog directFunctionDialog;
    int dialogClickId = 0;

    private void initDirectFunctionDialog() {
        if (directFunctionDialog == null) {
            directFunctionDialog = new TopLightBackgroundDialog(this);
            View view = LayoutInflater.from(this).inflate(R.layout.layout_dialog_direct_function
                    , null);
            initDirectView(view);

            directFunctionDialog.setContentView(view);
            directFunctionDialog.setOnDismissListener(dialog -> {
                Bundle bundle = new Bundle();
                switch (dialogClickId) {
                    case R.id.iv_close:
                        break;
                    case R.id.ll_waimai:
                        bundle.putInt(MainFragment.KEY_PAGE_POSITION_STRING, 0);
                        gotoPage(new CoreSwitchBean("主页", bundle));
                        break;
                    case R.id.ll_mall:
                        bundle.putInt(MainFragment.KEY_PAGE_POSITION_STRING, 1);
                        gotoPage(new CoreSwitchBean("主页", bundle));
                        break;
                    case R.id.ll_mine:
                        bundle.putInt(MainFragment.KEY_PAGE_POSITION_STRING, 3);
                        gotoPage(new CoreSwitchBean("主页", bundle));
                        break;
                    case R.id.ll_shopping_cart:
                        gotoPage(new CoreSwitchBean("商城-购物车", bundle));
                        break;
                    case R.id.ll_message:
                        gotoPage(new CoreSwitchBean("消息中心", bundle));
                        break;
                    case R.id.ll_share:
//                            gotoPage(new CoreSwitchBean("", null));
                        break;
                    case R.id.ll_feedback:
                        gotoPage(new CoreSwitchBean("意见反馈", null));
                        break;
                }

            });
        }
    }

    private void initDirectView(View view) {
        LayoutDialogDirectFunctionBinding binding = DataBindingUtil.bind(view);
        if (binding != null) {
            BaseActivity.OnSingleClickListener singleClickListener = new BaseActivity.OnSingleClickListener() {
                @Override
                public void onSingleClick(View view) {
                    LogUtil.d("DirectFunctionDialog onClick: id" + view.getId());
                    dialogClickId = view.getId();
                    directFunctionDialog.dismiss(); //执行任何点击均关闭dialog
                }
            };
            binding.ivClose.setOnClickListener(singleClickListener);
            binding.llWaimai.setOnClickListener(singleClickListener);
            binding.llMall.setOnClickListener(singleClickListener);
            binding.llMine.setOnClickListener(singleClickListener);
            binding.llShoppingCart.setOnClickListener(singleClickListener);
            binding.llMessage.setOnClickListener(singleClickListener);
            binding.llShare.setOnClickListener(singleClickListener);
            binding.llFeedback.setOnClickListener(singleClickListener);
        }
    }

    public void showFunctionOfDirectDialog() {
        LogUtil.d("asdadsfasdfasfasfasdfasd");
        initDirectFunctionDialog();
        if (!directFunctionDialog.isShowing()) {
            directFunctionDialog.show();
        }
    }

    /**
     * 设置Activity属性
     */
    protected abstract void initActivityAttribute();

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
     *
     * @param transluecnt
     */
    public void setTransluecnt(boolean transluecnt) {
        isTranslucent = transluecnt;
    }

    /**
     * 是否为状态栏留出空间
     *
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
        MyDataBindingUtil.removeActivityCallBack(this);
    }

    /**
     * 保证同一按钮在1秒内只会响应一次点击事件
     */
    public abstract static class OnSingleClickListener implements View.OnClickListener {
        //两次点击按钮之间的间隔，目前为1000ms
        private static final int MIN_CLICK_DELAY_TIME = 1000;
        private int customClickDelayTime = -1;
        private long lastClickTime;

        public abstract void onSingleClick(View view);

        public OnSingleClickListener() {
        }

        public OnSingleClickListener(int customClickDelayTime) {
            this.customClickDelayTime = customClickDelayTime;
        }

        @Override
        public void onClick(View view) {
            long curClickTime = System.currentTimeMillis();
            if (customClickDelayTime != -1) {
                if((curClickTime - lastClickTime) >= customClickDelayTime){
                    lastClickTime = curClickTime;
                    onSingleClick(view);
                }
            } else {
                if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
                    lastClickTime = curClickTime;
                    onSingleClick(view);
                }
            }
        }
    }

    public View getContentView() {
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
