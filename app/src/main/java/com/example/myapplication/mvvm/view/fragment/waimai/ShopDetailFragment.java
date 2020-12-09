package com.example.myapplication.mvvm.view.fragment.waimai;

import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.adapter.CashBackTagAdapter;
import com.example.myapplication.adapter.RecyclerViewBannerAdapter;
import com.example.myapplication.bean.Shop;
import com.example.myapplication.databinding.FragmentShopDetailBinding;
import com.example.myapplication.enumtype.ShopTabType;
import com.example.myapplication.mvvm.view.fragment.BaseFragment;
import com.example.myapplication.mvvm.vm.BaseViewModel;
import com.example.myapplication.mvvm.vm.waimai.ShopDetailViewModel;
import com.example.myapplication.util.StatusBarUtils;
import com.example.myapplication.views.MyTabSegmentTab;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.widget.banner.recycler.BannerLayout;
import com.xuexiang.xui.widget.tabbar.TabSegment;

import java.util.Iterator;
import java.util.List;

@Page(name = "店铺详情页", anim = CoreAnim.slide)
public class ShopDetailFragment extends BaseFragment {

    private FragmentShopDetailBinding mBinding;
    private ShopDetailViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new ShopDetailViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        ((FragmentShopDetailBinding)mViewDataBinding).setViewModel(mViewModel);
        mBinding = (FragmentShopDetailBinding)mViewDataBinding;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shop_detail;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setFitStatusBarHeight(true);
        setmStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_DARK);
    }

    @Override
    protected void initViews() {
        super.initViews();

        initHeadDetail();
        initMemberCard();

        initBanner();
        initNavigationTab();
    }


    @Override
    protected void onLifecycleResume() {
        super.onLifecycleResume();
        changeStatusBarMode();
    }

    private void initHeadDetail() {
        Shop shop = mViewModel.getShopDetail();

        mBinding.layoutShopDetails.tvShopName.setText(shop.getShopName());
        mBinding.layoutShopDetails.layoutScoreAndFans.setScore(shop.getScore());
        mBinding.layoutShopDetails.layoutScoreAndFans.
                setFansStr(getString(R.string.number_of_fans,shop.getNumber_of_fans()));
        mBinding.layoutShopDetails.tvShopDescribe1.
                setText("月售" + shop.getSale_count_per_month() + "+ 配送约60分钟");
        mBinding.layoutShopDetails.tvMorePreferential.
                setText(getString(R.string.more_preferential,4));
        mBinding.layoutShopDetails.tvShopAnnouncement.
                setText(getString(R.string.announcement,shop.getAnnouncement()));
        Glide.with(this).load(shop.getShopIcon()).
                placeholder(R.drawable.ic_waimai_brand).centerCrop().
                into(mBinding.layoutShopDetails.ivShopIcon);
        initCashBackFlowTagLayout();
    }

    private void initCashBackFlowTagLayout() {
        CashBackTagAdapter tagAdapter = new CashBackTagAdapter(getContext());
        mBinding.layoutShopDetails.flowlayoutCashBack.setAdapter(tagAdapter);
        mBinding.layoutShopDetails.flowlayoutCashBack.
                setOnTagClickListener((parent, view, position) ->
                        Toast.makeText(getContext(),
                                "点击了：" + parent.getAdapter().getItem(position),
                                Toast.LENGTH_SHORT).show());

        tagAdapter.addTags(mViewModel.getCashBackData());
    }

    private void initMemberCard() {
        Shop shop = mViewModel.getShopDetail();
        mBinding.layoutMembers.btSignIn.setOnClickListener((v) ->{
            Toast.makeText(ShopDetailFragment.this.getContext(), "点击了入会", Toast.LENGTH_SHORT).show();
        });
        mBinding.layoutMembers.tvShopMembersCardName.setText(shop.getMemberCard().getName());
        mBinding.layoutMembers.tvShopMembersCardDescribe.setText(shop.getMemberCard().getDescribe());
        Glide.with(this).load(shop.getShopIcon()).
                placeholder(R.drawable.ic_waimai_brand).centerCrop().
                into(mBinding.layoutMembers.ivShopIcon);

    }

    private void initBanner(){
        RecyclerViewBannerAdapter mAdapterHorizontal
                = new RecyclerViewBannerAdapter(mViewModel.getBannerSource());
        mAdapterHorizontal.setOnBannerItemClickListener(position ->
                Toast.makeText(getContext(),"点击了轮播图：" + position,Toast.LENGTH_SHORT).show());
        mBinding.blHorizontal.setAdapter(mAdapterHorizontal);
    }

    private int space;
    /**
     * 初始化粘性导航栏
     */
    private void initNavigationTab() {
        space = getResources().getDimensionPixelOffset(R.dimen.waimai_shop_tabbar_item_space);
        List<ShopTabType> shopTabTypes = mViewModel.getRecommendedTitle();

        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());

        mBinding.tabLayout.setHasIndicator(true);
        mBinding.tabLayout.setIndicatorDrawable(getResources().getDrawable(R.drawable.sr_widght_horizontal_bar));
        mBinding.tabLayout.setMode(TabSegment.MODE_SCROLLABLE);
        mBinding.tabLayout.setItemSpaceInScrollMode(space);
        mBinding.tabLayout.setPadding(space, 0, space, 0);
        mBinding.tabLayout.setDefaultNormalColor(getResources().getColor(R.color.text_tip));
        mBinding.tabLayout.setDefaultSelectedColor(getResources().getColor(R.color.text_uncheck));
        addTab(mBinding.tabLayout,adapter,shopTabTypes);
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager,true);

        mBinding.viewPager.setOffscreenPageLimit(shopTabTypes.size() - 1);
        mBinding.viewPager.setAdapter(adapter);
        mBinding.viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                 //TODO: 2020/12/3 刷新内容

            }
        });

//        mBinding.stickyNavigationLayout.setOnScrollChangeListener(moveRatio -> {
//            if(moveRatio == 1){
//                if(isShowStatusBar()){
//                    setStatusBarShowByType(HIDE_STATUS_BAR);
//                }
//            } else {
//                if(isHideStatusBar()){
//                    setStatusBarShowByType(SHOW_STATUS_BAR);
//                }
//            }
//        });
    }

    private void addTab(TabSegment tabSegment, FragmentAdapter<BaseFragment> adapter,
                        List<ShopTabType> titles){
        Iterator<ShopTabType> iterator = titles.iterator();
        while (iterator.hasNext()){
            ShopTabType shopTabType = iterator.next();
            String title = shopTabType.getName();
            MyTabSegmentTab tab = new MyTabSegmentTab(title);
            tab.setTextSize(getResources().getDimensionPixelSize(R.dimen.waimai_shop_tabbar_item_space));
            adapter.addFragment(mViewModel.getTabBarFragment(shopTabType), title);
            tabSegment.addTab(tab);
        }
    }
}
