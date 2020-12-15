package com.life.waimaishuo.mvvm.view.activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.ViewDataBinding;
import com.life.waimaishuo.databinding.ActivitySearchBinding;
import com.life.waimaishuo.R;
import com.life.waimaishuo.mvvm.view.fragment.SearchHistoryFragment;
import com.life.waimaishuo.mvvm.vm.SearchViewModel;
import com.life.waimaishuo.util.MyDataBindingUtil;
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
        setFitStatusBarHeight(true);
    }

    private void initDataBinding(){
        mViewModel = new SearchViewModel();
        ((ActivitySearchBinding)mViewDataBinding).setViewModel(mViewModel);
        MyDataBindingUtil.addCallBack(this, mViewModel.cancelSearch, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                popPage();
            }
        });
    }

}
