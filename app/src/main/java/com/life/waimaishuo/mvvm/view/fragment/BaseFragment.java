package com.life.waimaishuo.mvvm.view.fragment;

import android.app.Activity;
import android.content.Intent;
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
import com.life.waimaishuo.bean.event.MessageEvent;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.util.MyDataBindingUtil;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.base.XPageFragment;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xpage.utils.TitleUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@Page
public abstract class BaseFragment extends XPageFragment {

    private int mStatusBarLightMode = StatusBarUtils.STATUS_BAR_MODE_NO_HANDLE;
    private int mStatusBarShowType = NO_HANDLE_STATUS_BAR; //不处理

    private boolean isRegisterEventBus = false;
    private boolean isFitStatusBarHeight = false;

    private boolean isFirstRequestData = true;  //用于第一次开启页面加载数据

    protected static int SHOW_STATUS_BAR = 1; //显示
    protected static int HIDE_STATUS_BAR = 0; //不显示
    protected static int NO_HANDLE_STATUS_BAR = -1;

    //用于UI更新
    protected Handler mHandler;

    public ViewDataBinding mViewDataBinding;
    public BaseViewModel baseViewModel;

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
    public void popToBack() {
        super.popToBack();
    }

    @Override
    protected void initArgs() {
        initMyHandle();

        if(baseViewModel != null){
            baseViewModel.init();
        }
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
        if(isRegisterEventBus){
            EventBus.getDefault().register(this);
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
                LogUtil.d(event.name() + getPageTitle());
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

        View back = mRootView.findViewById(R.id.iv_back);
        if(back != null){
            back.setOnClickListener(new BaseActivity.OnSingleClickListener() {
                @Override
                public void onSingleClick(View view) {
                    popToBack();
                }
            });
        }

    }

    /**
     * EventBus在主线程中接收信息
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEvent(MessageEvent messageEvent){

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mHandler != null){
            mHandler.removeCallbacksAndMessages(null);
        }
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }

        MyDataBindingUtil.removeFragmentCallBack(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {   // FIXME: 2021/1/25 方法被弃用 寻找替换方法
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onFragmentShow();
        }else{
            onFragmentHide();
        }
    }

    private void initMyHandle() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    protected void fitStatusBarHeight(){
        if(isFitStatusBarHeight){
            StatusBarUtils.fitStatusBarHeight(this);
        }
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
            LogUtil.e(getPageTitle() + " 状态栏：activity为null");
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

    /**
     * 根据配置的状态栏状态，改变状态栏颜色
     */
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

    protected void onLifecycleCreate(){}
    protected void onLifecycleStart(){}
    protected void onLifecycleResume(){
        if(isFirstRequestData){
            isFirstRequestData = false;
            firstRequestData();
        }
    }
    protected void onLifecyclePause(){}
    protected void onLifecycleStop(){}
    protected void onLifecycleDestroy(){}
    protected void onLifecycleAny(){}

    /**
     * 第一次网络请求数据
     */
    protected void firstRequestData(){}

    public void setStatusBarLightMode(int mStatusBarLightMode) {
        this.mStatusBarLightMode = mStatusBarLightMode;
//        changeStatusBarMode();
    }

    public void setFitStatusBarHeight(boolean fitStatusBarHeight) {
        isFitStatusBarHeight = fitStatusBarHeight;
    }

    public void setStatusBarShowType(int statusBarShowType){
        mStatusBarShowType = statusBarShowType;
    }

    public void setRegisterEventBus(boolean isRegisterEventBus){
        this.isRegisterEventBus = isRegisterEventBus;
    }

    /**
     * 设置view 是否可见  取值visible、gone
     * @param view
     * @param isVisibility
     */
    public static void setViewVisibility(View view,boolean isVisibility){
        if(isVisibility){
            if(view.getVisibility() != View.VISIBLE){
                view.setVisibility(View.VISIBLE);
            }
        }else{
            if(view.getVisibility() != View.GONE){
                view.setVisibility(View.GONE);
            }
        }
    }
}
