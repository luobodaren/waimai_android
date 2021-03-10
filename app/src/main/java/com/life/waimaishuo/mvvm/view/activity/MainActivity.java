package com.life.waimaishuo.mvvm.view.activity;

import com.life.waimaishuo.mvvm.view.fragment.common.MainFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void initActivityAttribute() {
        setTransluecnt(true);
        setFitStatusBarHeight(false);
    }

    @Override
    protected void initView() {
        openPage(MainFragment.class);
    }

}
