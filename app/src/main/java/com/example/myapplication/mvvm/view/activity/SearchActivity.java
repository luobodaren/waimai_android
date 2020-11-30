package com.example.myapplication.mvvm.view.activity;

import androidx.databinding.Observable;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivitySearchBinding;
import com.example.myapplication.mvvm.vm.BaseViewModel;
import com.example.myapplication.mvvm.vm.SearchActivityViewModel;
import com.example.myapplication.util.DataBindingUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;

@Page(name = "搜索页",anim = CoreAnim.fade)
public class SearchActivity extends BaseActivity {

    SearchActivityViewModel mViewModel;

    @Override
    protected int initLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected BaseViewModel setViewModel() {
        mViewModel = new SearchActivityViewModel();
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        ((ActivitySearchBinding)mViewDataBinding).setViewModel(mViewModel);
    }

    @Override
    protected void initView() {

        DataBindingUtils.addCallBack(this, mViewModel.cancelSearch, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                popPage();
            }
        });

    }

    @Override
    protected void initActivityAttritube() {

    }



}
