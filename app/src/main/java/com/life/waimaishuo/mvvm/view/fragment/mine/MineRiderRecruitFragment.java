package com.life.waimaishuo.mvvm.view.fragment.mine;

import android.view.View;

import com.life.waimaishuo.R;
import com.life.waimaishuo.databinding.FragmentRiderToRecruitBinding;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mine.MineRiderRecruitViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.util.TextUtil;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "骑手招募", anim = CoreAnim.slide)
public class MineRiderRecruitFragment extends BaseFragment {

    private FragmentRiderToRecruitBinding mBinding;
    private MineRiderRecruitViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new MineRiderRecruitViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentRiderToRecruitBinding) mViewDataBinding;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rider_to_recruit;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
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
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        /*mBinding.layoutTitle.tvRight.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                MineApplyRecordFragment.openPageWithPageType(MineRiderRecruitFragment.this,MineApplyRecordFragment.PAGE_TYPE_RIDER_TO_RECRUIT);
            }
        });*/
    }

    private void initTitle(){
        mBinding.layoutTitle.tvTitle.setText(getPageName());
        TextUtil.setFakeBoldText(mBinding.layoutTitle.tvTitle,true);

        //mBinding.layoutTitle.tvRight.setText(R.string.record_of_apply);
    }

}
