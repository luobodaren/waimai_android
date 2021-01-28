package com.life.waimaishuo.mvvm.view.fragment.mine;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.life.waimaishuo.R;
import com.life.waimaishuo.databinding.FragmentChangePersonalInfoBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mine.MineChangePersonInfoViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "修改个人信息")
public class MineChangePersonInfoFragment extends BaseFragment {

    public final static String PAGE_TYPE_KEY = "page_type_key";
    public final static int PAGE_TYPE_CHANGE_NAME = 0;
    public final static int PAGE_TYPE_CHANGE_PHONE = 1;
    public final static int PAGE_TYPE_CHANGE_SIGNATURE = 2;
    private final String[] titles = {"修改昵称","修改手机号","设置签名"};

    private FragmentChangePersonalInfoBinding mBinding;
    private MineChangePersonInfoViewModel mViewModel;

    private int myPageType;

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new MineChangePersonInfoViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentChangePersonalInfoBinding) mViewDataBinding;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_change_personal_info;
    }

    @Override
    protected void initArgs() {
        super.initArgs();

        Bundle bundle = getArguments();
        myPageType = bundle.getInt(PAGE_TYPE_KEY);

        setFitStatusBarHeight(true);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_LIGHT);
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();

        initTitle();
        initContentFrameLayout();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
    }

    private void initTitle(){
        mBinding.layoutTitle.tvTitle.setText(titles[myPageType]);
    }

    /**
     * 根据页面类型 显示不同界面
     */
    private void initContentFrameLayout(){
        Fragment fragment = null;
        if(myPageType == PAGE_TYPE_CHANGE_NAME){
            fragment = new Fragment(R.layout.fragment_change_nickname);
        }else if(myPageType == PAGE_TYPE_CHANGE_PHONE){
            fragment = new Fragment(R.layout.fragment_change_phone);
        }else if(myPageType == PAGE_TYPE_CHANGE_SIGNATURE){
            fragment = new Fragment(R.layout.fragment_change_signature);
        }
        if(fragment != null){
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.replace(R.id.frame_layout,fragment).commit();
        }
    }
}
