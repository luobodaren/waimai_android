package com.life.waimaishuo.mvvm.view.fragment;

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

import com.life.base.utils.LogUtil;
import com.life.waimaishuo.R;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.base.XPageFragment;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xpage.utils.TitleUtils;

@Page
public abstract class BaseFragment extends XPageFragment {

    protected static int SHOW_STATUS_BAR = 1; //显示
    protected static int HIDE_STATUS_BAR = 0; //不显示
    protected static int NO_HANDLE_STATUS_BAR = -1;
    protected int mStatusBarShowType = NO_HANDLE_STATUS_BAR; //不处理

    private int mStatusBarLightMode = StatusBarUtils.STATUS_BAR_MODE_NO_HANDLE;

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
    protected TitleBar initTitleBar() {
        return TitleUtils.addTitleBarDynamic(mRootView.findViewById(R.id.my_ll_content_view),
                getPageTitle(), v -> popToBack());
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
                        onLifecycleCreate();
                        break;
                    case ON_START:
                        onLifecycleStart();
                        break;
                    case ON_RESUME:
                        onLifecycleResume();
                        break;
                    case ON_PAUSE:
                        onLifecyclePause();
                        break;
                    case ON_STOP:
                        onLifecycleStop();
                        break;
                    case ON_DESTROY:
                        onLifecycleDestroy();
                        break;
                    case ON_ANY:
                        onLifecycleAny();
                        break;
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mHandler != null){
            mHandler.removeCallbacksAndMessages(null);
        }
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

    public void setStatusBarLightMode(int mStatusBarLightMode) {
        this.mStatusBarLightMode = mStatusBarLightMode;
//        changeStatusBarMode();
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
        changeStatusBarMode();
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
        if(statusBarShowType == SHOW_STATUS_BAR){
            Window window = getActivity().getWindow();
//            mStatusBarShowType = SHOW_STATUS_BAR;
            StatusBarUtils.showStatusBar(window);
            LogUtil.d(getPageTitle() + " 状态栏：显示！");
        }else if(statusBarShowType == HIDE_STATUS_BAR){
            Window window = getActivity().getWindow();
//            mStatusBarShowType = HIDE_STATUS_BAR;
            StatusBarUtils.hideStatusBar(window);
            LogUtil.d(getPageTitle() + " 状态栏：隐藏！");
        }else if(statusBarShowType == NO_HANDLE_STATUS_BAR){
            LogUtil.d(getPageTitle() + " 状态栏：不处理显示隐藏！");
        } else{
            LogUtil.e(getPageTitle() + " 状态栏：显示类型值错误！");
        }
    }

    protected void changeStatusBarMode() {
        if(mStatusBarLightMode == StatusBarUtils.STATUS_BAR_MODE_LIGHT){
            StatusBarUtils.setStatusBarLightMode(getActivity());
            LogUtil.d(getPageTitle() + " 状态栏 setLightMode");
        }else if(mStatusBarLightMode == StatusBarUtils.STATUS_BAR_MODE_DARK){
            StatusBarUtils.setStatusBarDarkMode(getActivity());
            LogUtil.d(getPageTitle() + " 状态栏 setDarkMode");
        }else if(mStatusBarLightMode == StatusBarUtils.STATUS_BAR_MODE_NO_HANDLE){
            LogUtil.d(getPageTitle() + " 状态栏 Mode 不处理");
        } else{
            LogUtil.e(getPageTitle() + " 状态栏 设置字体模式出错");
        }
    }

    private void initMyHandle() {
        mHandler = new Handler(Looper.getMainLooper());
    }


    protected void onLifecycleCreate(){}
    protected void onLifecycleStart(){}
    protected void onLifecycleResume(){}
    protected void onLifecyclePause(){}
    protected void onLifecycleStop(){}
    protected void onLifecycleDestroy(){}
    protected void onLifecycleAny(){}

}