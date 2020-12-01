package com.example.myapplication.mvvm.view.activity;

import com.example.myapplication.mvvm.view.fragment.MainFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void initActivityAttritube() {
        setTransluecnt(true);
        setFitWindow(false);
    }

    @Override
    protected void initView() {
        openPage(MainFragment.class);
    }

}
