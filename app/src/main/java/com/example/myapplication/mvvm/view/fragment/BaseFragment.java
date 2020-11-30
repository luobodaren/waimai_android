package com.example.myapplication.mvvm.view.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.myapplication.mvvm.vm.BaseViewModel;
import com.example.myapplication.util.StatusBarUtils;
import com.xuexiang.xpage.utils.TitleBar;

public abstract class BaseFragment extends com.example.base.fragment.BaseFragment {

    public ViewDataBinding mViewDataBinding;
    public BaseViewModel baseViewModel;

    private boolean isFitWindow = false;

    private boolean isStatusBarLightMode = true;


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

    }

    @Override
    protected void initPage() {
        if(isFitWindow){
            StatusBarUtils.fitStatusBarHight(this);
        }
        super.initPage();
    }

    @Override
    protected TitleBar initTitleBar() {
        return super.initTitleBar();
    }

    @Override
    protected void initViews() {
        baseViewModel.init();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
    }

    @Override
    protected void onLifecycleCreate() {

    }

    @Override
    protected void onLifecycleStart() {

    }

    @Override
    protected void onLifecycleResume() {

    }

    @Override
    protected void onLifecyclePuase() {

    }

    @Override
    protected void onLifecycleStop() {

    }

    @Override
    protected void onLifecycleDestroy() {

    }

    @Override
    protected void onLifecycleAny() {

    }

    public void setFitWindow(boolean fitWindow) {
        isFitWindow = fitWindow;
    }

    public void setStatusBarLightMode(boolean statusBarLightMode) {
        isStatusBarLightMode = statusBarLightMode;
    }

    private void setStatusBarMode() {
        if(isStatusBarLightMode){
            StatusBarUtils.setStatusBarLightMode(getActivity());
        }else{
            StatusBarUtils.setStatusBarDarkMode(getActivity());
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onShow();
        }
    }

    /**
     * fragment显示再布局上
     */
    protected void onShow(){
        setStatusBarMode();
    }

}
