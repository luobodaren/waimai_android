package com.example.myapplication.mvvm.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.myapplication.mvvm.vm.BaseViewModel;
import com.xuexiang.xpage.utils.TitleBar;

public abstract class BaseFragment extends com.example.base.fragment.BaseFragment {

    public ViewDataBinding mViewDataBinding;
    public BaseViewModel baseViewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

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
        super.initArgs();
    }

    @Override
    protected void initPage() {
        super.initPage();
    }

    @Override
    protected TitleBar initTitleBar() {
        return super.initTitleBar();
    }


    @Override
    protected  int getLayoutId() {
        return 0;
    }

    @Override
    protected void initViews() {
        baseViewModel.initData();
    }

    @Override
    protected void initListeners() {

    }

    protected abstract BaseViewModel setViewModel();

    protected abstract void bindViewModel();

}
