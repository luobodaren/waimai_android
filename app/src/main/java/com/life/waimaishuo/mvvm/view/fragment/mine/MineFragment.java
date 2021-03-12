package com.life.waimaishuo.mvvm.view.fragment.mine;

import android.view.View;
import android.widget.TextView;

import androidx.databinding.Observable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.Global;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.event.MessageEvent;
import com.life.waimaishuo.bean.ui.IconStrData;
import com.life.waimaishuo.bean.ui.TypeCountData;
import com.life.waimaishuo.constant.MessageCodeConstant;
import com.life.waimaishuo.databinding.FragmentMineBinding;
import com.life.waimaishuo.listener.AppBarStateChangeListener;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mine.MineViewModel;
import com.life.waimaishuo.util.MyDataBindingUtil;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.util.Utils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

import java.util.ArrayList;
import java.util.List;

@Page(name = "我的", anim = CoreAnim.fade)
public class MineFragment extends BaseFragment {

    private FragmentMineBinding mBinding;
    private MineViewModel mViewModel;

    private MyBaseRecyclerAdapter<TypeCountData> topRecyclerAdapter;
    private MyBaseRecyclerAdapter<IconStrData> recommendRecyclerAdapter;

    private AppBarStateChangeListener appBarStateChangeListener;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initArgs() {
        super.initArgs();

        setRegisterEventBus(true);
        setFitStatusBarHeight(true);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_LIGHT);
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected BaseViewModel setViewModel() {
        mViewModel = new MineViewModel();
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentMineBinding) mViewDataBinding;
        mBinding.setViewModel(mViewModel);
        if(Global.getUser() != null){
            mBinding.setUser(Global.getUser());
        }
    }

    @Override
    protected void initViews() {
        super.initViews();

        if(Global.isLogin()){   //直接展示
            //若已登录通过用户信息展示界面，并再请求一次数据进行展示

        }else{
            //展示空数据
        }

        initAppBarLayout();
        initSuperMember();

        Glide.with(requireContext())
                .load("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1004682078,2886860504&fm=26&gp=0.jpg")
                .centerCrop()
                .placeholder(R.drawable.ic_waimai_brand)
                .into(mBinding.ivRecommendedImg);

        // TODO: 2020/11/26 改为使用流布局实现
        initTopRecycler();
        initLogisticsRecycler();
        initMoreRecommendedRecycler();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        addCallBack();

        mBinding.xuiLayout.setOnClickListener(new BaseActivity.OnSingleClickListener(500) {
            @Override
            public void onSingleClick(View view) {
                if(appBarStateChangeListener.getmCurrentState() == AppBarStateChangeListener.State.EXPANDED){
                    mBinding.appbarLayout.setExpanded(false,true);
                }else if(appBarStateChangeListener.getmCurrentState() == AppBarStateChangeListener.State.COLLAPSED){
                    mBinding.appbarLayout.setExpanded(true,true);
                }
            }
        });

        mBinding.layoutSuperMember.btGoToMemberCenter.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                if(Global.getUser() == null){
                    //跳转登录界面

                }else{
                    if(Global.getUser().getIsVip() == 0){
                        openPage(MineSuperMemberCenterFragment.class);  //非会员   开通
                    }else{
                        openPage(MineSuperMemberRenewFragment.class);   //会员    会员中心
                    }
                }
            }
        });

        mBinding.appbarLayout.addOnOffsetChangedListener(appBarStateChangeListener);

        mBinding.clPersonalInfo.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                openPage(MinePersonalCenterFragment.class);
            }
        });

        mBinding.ivSetting.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                openPage(MineSettingFragment.class);
            }
        });

        topRecyclerAdapter.setOnItemClickListener((adapter, view, position) -> {
            switch (position) { //注意与R.array.mine_top_data_list_str匹配
                case 3:
                    openPage(MineRedPacketFragment.class);
                    break;
            }
        });
        recommendRecyclerAdapter.setOnItemClickListener((adapter1, view, position) -> {
            LogUtil.d("更多推荐点击 position=" + position);
            switch (position) { //注意与R.array.mine_recommends_str匹配
                case 0:
                    MineAddressManagerFragment.openPage(MineFragment.this,null);
                    break;
                case 2:
                    openPage(MineCollectionFragment.class);
                    break;
                case 3:
                    openPage(MineMerchantsTenantsFragment.class);
                    break;
                case 4:
                    MineApplyRecordFragment.openPageWithPageType(MineFragment.this,MineApplyRecordFragment.PAGE_TYPE_BUSINESS_COOPERATION);
                    break;
                case 6:
                    MineApplyRecordFragment.openPageWithPageType(MineFragment.this,MineApplyRecordFragment.PAGE_TYPE_RIDER_TO_RECRUIT);
                    break;
            }
        });
    }

    @Override
    protected void firstRequestData() {
        super.firstRequestData();
        if(Global.isLogin()){
            //若已登录 重新获取用户信息 刷新界面
            mViewModel.requestUserInfo();
        }
    }

    @Override
    public void MessageEvent(MessageEvent messageEvent) {
        super.MessageEvent(messageEvent);
        switch (messageEvent.getCode()){
            case MessageCodeConstant.LOGIN_AND_GET_USERINFO_SUCCESS:    //登录成功
                updateUIWithUserInfo();
                break;
        }
    }

    private void addCallBack(){
        MyDataBindingUtil.addCallBack(this, mViewModel.onRequestUserInfo, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.post(() -> updateUIWithUserInfo());
            }
        });
    }

    private void initAppBarLayout() {
        appBarStateChangeListener = new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) { }
        };
        mBinding.appbarLayout.setExpanded(false, false);

    }

    private float mShadowAlpha = 0.25f;
    private int mShadowElevationDp = 14;
    private void initSuperMember(){
        // TODO: 2021/1/30 根据会员身份调整界面
        mBinding.xuiLayout.setRadiusAndShadow(UIUtils.getInstance().dpToPx(12),
                UIUtils.getInstance().dpToPx(mShadowElevationDp),mShadowAlpha);
    }

    private void initTopRecycler() {
       topRecyclerAdapter = new MyBaseRecyclerAdapter<TypeCountData>(
               R.layout.item_vertical_data_show, mViewModel.getTopDataList(requireContext(),null), com.life.waimaishuo.BR.item) {};
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4, LinearLayoutManager.VERTICAL, false);

        mBinding.recyclerTopData.setAdapter(topRecyclerAdapter);
        mBinding.recyclerTopData.setLayoutManager(gridLayoutManager);
    }

    private void initLogisticsRecycler() {
        MyBaseRecyclerAdapter<IconStrData> myBaseRecyclerAdapter =
                new MyBaseRecyclerAdapter<>(R.layout.item_mine_recycler_good_logistics, getGoodsLogisticsData()
                        , com.life.waimaishuo.BR.item);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 5, LinearLayoutManager.VERTICAL, false);

        mBinding.recyclerGoodsLogistics.setAdapter(myBaseRecyclerAdapter);
        mBinding.recyclerGoodsLogistics.setLayoutManager(gridLayoutManager);
    }

    private void initMoreRecommendedRecycler() {
        MyBaseRecyclerAdapter<IconStrData> adapter = getFunctionRecyclerAdapter();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                getContext(),
                4,
                LinearLayoutManager.VERTICAL,
                false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0)
                    return 4;
                return 1;
            }
        });

        View view = View.inflate(getContext(), R.layout.head_mine_recommend_recycler, null);
        ((TextView) view.findViewById(R.id.tv_head)).setText(R.string.more_recommended);
        adapter.addHeaderView(view);

        mBinding.recyclerMoreRecommended.setAdapter(adapter);
        mBinding.recyclerMoreRecommended.setLayoutManager(gridLayoutManager);
    }

    private MyBaseRecyclerAdapter<IconStrData> getFunctionRecyclerAdapter() {
        recommendRecyclerAdapter = new MyBaseRecyclerAdapter<>
                (R.layout.item_mine_recycler_more_recommended, getMoreRecommendData(), com.life.waimaishuo.BR.item);
        return recommendRecyclerAdapter;
    }

    private List<IconStrData> getGoodsLogisticsData() {
        List<IconStrData> goodsLogisticsData = new ArrayList<>();
        String[] orderStateStr = getResources().getStringArray(R.array.mine_order_state_list_str);
        int[] orderStateIcon = Utils.getDrawableResIdFormArray(requireContext(),R.array.mine_order_state_list_icon);
        for (int i = 0; i < orderStateStr.length; i++) {
            goodsLogisticsData.add(new IconStrData(orderStateIcon[i],orderStateStr[i]));
        }
        return goodsLogisticsData;
    }

    private List<IconStrData> getMoreRecommendData() {
        List<IconStrData> mMoreRecommendData = new ArrayList<>();
        String[] iconTypes = getResources().getStringArray(R.array.mine_recommends_str);
        int[] resImgId = Utils.getDrawableResIdFormArray(requireContext(),R.array.mine_recommends_icon);
        for (int i = 0; i < iconTypes.length; i++) {
            mMoreRecommendData.add(new IconStrData(resImgId[i],iconTypes[i]));
        }
        return mMoreRecommendData;
    }

    private void updateUIWithUserInfo(){
        mBinding.setUser(Global.getUser());

        if(Global.getUser().getIsVip() == 0){   //非会员
            mBinding.layoutSuperMember.btGoToMemberCenter.setText(R.string.open);
        }else { //会员
            mBinding.layoutSuperMember.btGoToMemberCenter.setText(R.string.into);
        }

        mViewModel.getTopDataList(requireContext(), topRecyclerAdapter.getData());
        topRecyclerAdapter.notifyDataSetChanged();

    }

}
