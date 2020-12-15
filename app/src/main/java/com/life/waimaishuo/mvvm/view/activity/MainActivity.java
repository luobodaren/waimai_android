package com.life.waimaishuo.mvvm.view.activity;

import com.life.waimaishuo.mvvm.view.fragment.MainFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void initActivityAttritube() {
        setTransluecnt(true);
        setFitStatusBarHeight(false);
    }

    @Override
    protected void initView() {
        openPage(MainFragment.class);
    }

}
