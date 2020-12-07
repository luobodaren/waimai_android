package com.example.myapplication.mvvm.view.fragment;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.example.base.utils.LogUtil;
import com.example.myapplication.mvvm.vm.BaseViewModel;
import com.example.myapplication.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.base.XPageFragment;

@Page
public abstract class BaseFragment extends XPageFragment {

    protected static int SHOW_STATUS_BAR = 1; //显示
    protected static int HIDE_STATUS_BAR = 0; //不显示
    protected int mStatusBarShowType = SHOW_STATUS_BAR; //默认显示

    private int mStatusBarLightMode = StatusBarUtils.STATUS_BAR_MODE_DARK;

    private boolean isFitStatusBarHeight = false;

    public ViewDataBinding mViewDataBinding;
    public BaseViewModel baseViewModel;

    //用于UI更新
    protected Handler mHandler;



    protected abstract BaseViewModel setViewModel();

    protected abstract void bindViewModel();

    @Override
    protected View inflateView(LayoutInflater inflater, ViewGroup container) {
        mViewDataBinding = DataBindingUtil.inflate(inflater,getLayoutId(),container,false);
        baseViewModel = setViewModel();
        bindViewModel();
        View view = mViewDataBinding.getRoot();
        return view;
    }

    @Override
    protected void initArgs() {
        initMyHandle();
    }

    @Override
    protected void initPage() {
        initTitleBar();
        fitStatusBarHeight();
        initViews();
        initListeners();

    }

    @Override
    protected void initViews() {
        if(baseViewModel != null){
            baseViewModel.init();
        }
    }

    @Override
    protected void initListeners() {

        /*getFragmentManager()
                .beginTransaction()
                .setMaxLifecycle(this, Lifecycle.State.CREATED)
                .commit();*/

        getLifecycle().addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                LogUtil.d(getPageTitle() + event.name());
                switch (event){
                    case ON_CREATE:
                        break;
                    case ON_START:
                        break;
                    case ON_RESUME:
                        onLifecycleResume();
                        break;
                    case ON_PAUSE:
                        break;
                    case ON_STOP:
                        break;
                    case ON_DESTROY:
                        break;
                    case ON_ANY:
                        break;
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onFragmentShow();
        }else{
            onFragmentHide();
        }
    }

    public void setFitStatusBarHeight(boolean fitStatusBarHeight) {
        isFitStatusBarHeight = fitStatusBarHeight;
    }

    protected void fitStatusBarHeight(){
        if(isFitStatusBarHeight){
            StatusBarUtils.fitStatusBarHeight(this);
        }
    }

    public void setmStatusBarLightMode(int mStatusBarLightMode) {
        this.mStatusBarLightMode = mStatusBarLightMode;
//        setStatusBarMode();
    }


    protected boolean isShowStatusBar() {
        return mStatusBarShowType == SHOW_STATUS_BAR;
    }

    protected boolean isHideStatusBar() {
        return mStatusBarShowType == HIDE_STATUS_BAR;
    }

    /**
     * fragment显示的监听
     */
    protected void onFragmentShow(){
        setStatusBarMode();
        setStatusBarShowByType(mStatusBarShowType);
    }

    /**
     * fragment的隐藏
     */
    protected void onFragmentHide(){

    }

    /**
     * 根据状态值改变状态栏显示状态
     * @param statusBarShowType
     */
    protected void setStatusBarShowByType(int statusBarShowType){
        Activity activity = getActivity();
        if(activity == null){
            LogUtil.e(getPageName() + " 状态栏：activity为null");
            return;
        }
        Window window = getActivity().getWindow();
        if(statusBarShowType == SHOW_STATUS_BAR){
            mStatusBarShowType = SHOW_STATUS_BAR;
            StatusBarUtils.showStatusBar(window);
            LogUtil.d(getPageTitle() + " 状态栏：显示！");
        }else if(statusBarShowType == HIDE_STATUS_BAR){
            mStatusBarShowType = HIDE_STATUS_BAR;
            StatusBarUtils.hideStatusBar(window);
            LogUtil.d(getPageTitle() + " 状态栏：隐藏！");
        }else{
            LogUtil.e(getPageTitle() + " 状态栏：显示类型值错误！");
        }
    }

    protected void setStatusBarMode() {
        if(mStatusBarLightMode == StatusBarUtils.STATUS_BAR_MODE_LIGHT){
            StatusBarUtils.setStatusBarLightMode(getActivity());
            LogUtil.d(getPageTitle() + " 状态栏 setLightMode");
        }else if(mStatusBarLightMode == StatusBarUtils.STATUS_BAR_MODE_DARK){
            StatusBarUtils.setStatusBarDarkMode(getActivity());
            LogUtil.d(getPageTitle() + " 状态栏 setDarkMode");
        }else{
            LogUtil.e(getPageTitle() + " 状态栏 设置字体模式出错");
        }
    }

    private void initMyHandle() {
        mHandler = new Handler(Looper.getMainLooper());
    }


    protected void onLifecycleCreate(){}
    protected void onLifecycleStart(){}
    protected void onLifecycleResume(){}
    protected void onLifecyclePuase(){}
    protected void onLifecycleStop(){}
    protected void onLifecycleDestroy(){}
    protected void onLifecycleAny(){}

}
