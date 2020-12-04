package com.example.myapplication.mvvm.view.fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public ViewDataBinding mViewDataBinding;
    public BaseViewModel baseViewModel;

    //用于UI更新
    protected Handler mHandler;

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
        initMyHandle();
    }

    @Override
    protected void initPage() {
        if(isFitWindow){
            StatusBarUtils.fitStatusBarHight(this);
        }
        super.initPage();
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
                LogUtil.d(event.name());
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

    public void setFitWindow(boolean fitWindow) {
        isFitWindow = fitWindow;
    }

    public void setStatusBarLightMode(boolean statusBarLightMode) {
        isStatusBarLightMode = statusBarLightMode;
        setStatusBarMode();
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
            onFragmentShow();
        }
    }

    /**
     * fragment显示的监听
     */
    protected void onFragmentShow(){
        setStatusBarMode();
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
