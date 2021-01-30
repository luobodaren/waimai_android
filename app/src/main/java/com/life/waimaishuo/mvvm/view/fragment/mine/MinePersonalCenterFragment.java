package com.life.waimaishuo.mvvm.view.fragment.mine;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.ui.PersonalInfo;
import com.life.waimaishuo.databinding.FragmentMinePersonalCenterBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mine.MinePersonalCenterViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

import java.util.List;

@Page(name = "个人中心", anim = CoreAnim.slide)
public class MinePersonalCenterFragment extends BaseFragment {

    private FragmentMinePersonalCenterBinding mBinding;
    private MinePersonalCenterViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new MinePersonalCenterViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentMinePersonalCenterBinding) mViewDataBinding;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_personal_center;
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

        initInfoRecycler();
        initAccountBindingRecycler();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        ((MyBaseRecyclerAdapter)mBinding.recyclerPersonalInfo.getAdapter()).setOnItemClickListener((adapter, view, position) -> {
            if(position == 0){  //头像
                openPage(MineChangeHeadPortraitFragment.class);
            }else if(position == 1){    //用户昵称
                Bundle bundle = new Bundle();
                bundle.putInt(MineChangePersonInfoFragment.PAGE_TYPE_KEY,MineChangePersonInfoFragment.PAGE_TYPE_CHANGE_NAME);
                openPage(MineChangePersonInfoFragment.class,bundle);
            }else if(position == 2){    //手机号
                Bundle bundle = new Bundle();
                bundle.putInt(MineChangePersonInfoFragment.PAGE_TYPE_KEY,MineChangePersonInfoFragment.PAGE_TYPE_CHANGE_PHONE);
                openPage(MineChangePersonInfoFragment.class,bundle);
            }else if(position == 3){    //个性签名
                Bundle bundle = new Bundle();
                bundle.putInt(MineChangePersonInfoFragment.PAGE_TYPE_KEY,MineChangePersonInfoFragment.PAGE_TYPE_CHANGE_SIGNATURE);
                openPage(MineChangePersonInfoFragment.class,bundle);
            }else if(position == 4){    //超级会员
                // TODO: 2021/1/30 判断会员状态
                openPage(MineSuperMemberRenewFragment.class,null);
            }
        });
    }

    private void initTitle(){
        mBinding.layoutTitle.tvTitle.setText(getPageName());
    }

    private void initInfoRecycler(){
        mBinding.recyclerPersonalInfo.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        mBinding.recyclerPersonalInfo.setAdapter(getRecyclerAdapter(mViewModel.getPersonalInfo()));
    }

    private void initAccountBindingRecycler(){
        mBinding.recyclerAccountBinding.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        mBinding.recyclerAccountBinding.setAdapter(getRecyclerAdapter(mViewModel.getAccountBindingInfo()));
    }

    private MyBaseRecyclerAdapter<PersonalInfo> getRecyclerAdapter(List<PersonalInfo> list){
        return new MyBaseRecyclerAdapter<PersonalInfo>(R.layout.item_recycler_personal_info_text,list, com.life.waimaishuo.BR.item){
            @Override
            protected void initView(BaseViewHolder helper, PersonalInfo item) {
                super.initView(helper, item);
                if(item.getLeftIvUrl() == null || "".equals(item.getLeftIvUrl())){
                    setViewVisibility(helper.getView(R.id.iv_left),false);
                }else{
                    setViewVisibility(helper.getView(R.id.iv_left),true);
                }

                if(item.isShowRightArrow()){
                    setViewVisibility(helper.getView(R.id.iv_right_arrow),true);
                }else{
                    setViewVisibility(helper.getView(R.id.iv_right_arrow),false);
                }

                if(item.isHighlightRT()){
                    helper.setTextColor(R.id.tv_right,helper.itemView.getContext().getResources().getColor(R.color.colorTheme));
                }else{
                    helper.setTextColor(R.id.tv_right,helper.itemView.getContext().getResources().getColor(R.color.text_normal));
                }

                if(item.getRightIvUrl() == null || "".equals(item.getRightIvUrl())){
                    setViewVisibility(helper.getView(R.id.iv_right),false);
                }else{
                    setViewVisibility(helper.getView(R.id.iv_right),true);
                }
            }
        };
    }

}
