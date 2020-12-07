package com.example.myapplication.mvvm.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.ViewDataBinding;

import com.example.base.utils.LogUtil;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivitySearchBinding;
import com.example.myapplication.mvvm.view.fragment.SearchHistoryFragment;
import com.example.myapplication.mvvm.vm.SearchViewModel;
import com.example.myapplication.util.DataBindingUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;

@Page(name = "搜索页",anim = CoreAnim.slide)
public class SearchActivity extends BaseActivity {

    private ViewDataBinding mViewDataBinding;
    private SearchViewModel mViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        changePage(SearchHistoryFragment.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //MVVM 绑定布局
        mViewDataBinding = DataBindingUtil.setContentView(this,getLayoutId());
        initDataBinding();
    }


    @Override
    protected void initActivityAttritube() {
        setTransluecnt(true);
    }

    private void initDataBinding(){
        mViewModel = new SearchViewModel();
        ((ActivitySearchBinding)mViewDataBinding).setViewModel(mViewModel);
        DataBindingUtils.addCallBack(this, mViewModel.cancelSearch, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                popPage();
            }
        });
    }

}
