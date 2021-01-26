package com.life.waimaishuo.mvvm.view.fragment.mine;

import android.view.View;

import com.life.waimaishuo.R;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "平台介绍", anim = CoreAnim.slide)
public class MinePlatformIntroduceFragment extends BaseFragment {

    @Override
    protected BaseViewModel setViewModel() {
        return null;
    }

    @Override
    protected void bindViewModel() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_platform_introduce;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();

        // TODO: 2021/1/26 加载平台介绍图片

    }

    @Override
    protected void initListeners() {
        super.initListeners();

        findViewById(R.id.bt_immediately_open_shop).setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                openPage(MineCreateShopFragment.class);
            }
        });
    }

}
