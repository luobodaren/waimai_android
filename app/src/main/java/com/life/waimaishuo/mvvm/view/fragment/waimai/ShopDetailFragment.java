package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.CashBackTagAdapter;
import com.life.waimaishuo.adapter.RecyclerViewBannerAdapter;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.databinding.FragmentShopDetailBinding;
import com.life.waimaishuo.enumtype.ShopTabTypeEnum;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.ShopDetailViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.views.MyTabSegmentTab;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.adapter.FragmentAdapter;
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
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_DARK);
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
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
    protected void initListeners() {
        super.initListeners();
        mBinding.stickyNavigationLayout.setOnScrollChangeListener(moveRatio -> {    // FIXME: 2020/12/10  没效果
            if(moveRatio == 1){
                setStatusBarShowByType(HIDE_STATUS_BAR);
            } else {
                setStatusBarShowByType(SHOW_STATUS_BAR);
            }
        });

        mRootView.findViewById(R.id.iv_back).setOnClickListener((v)-> popToBack());
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
        mBinding.contentLayout.setAdapter(mAdapterHorizontal);
    }

    private int space;
    /**
     * 初始化粘性导航栏
     */
    private void initNavigationTab() {
        space = getResources().getDimensionPixelOffset(R.dimen.waimai_shop_tabbar_item_space);
        List<ShopTabTypeEnum> shopTabTypes = mViewModel.getRecommendedTitle();

        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());

        mBinding.stickyView.setHasIndicator(true);
        mBinding.stickyView.setIndicatorDrawable(getResources().getDrawable(R.drawable.sr_widght_horizontal_bar));
        mBinding.stickyView.setIndicatorWidthAdjustContent(false);
        mBinding.stickyView.setMode(TabSegment.MODE_SCROLLABLE);
        mBinding.stickyView.setItemSpaceInScrollMode(space);
        mBinding.stickyView.setPadding(space, 0, space, 0);
        mBinding.stickyView.setDefaultNormalColor(getResources().getColor(R.color.text_tip));
        mBinding.stickyView.setDefaultSelectedColor(getResources().getColor(R.color.text_uncheck));
        mBinding.stickyView.setTabTextSize(getResources().getDimensionPixelSize(R.dimen.waimai_tabbar_item_text_size));
        addTab(mBinding.stickyView,adapter,shopTabTypes);
        mBinding.stickyView.setupWithViewPager(mBinding.adaptiveSizeView,true);

        mBinding.adaptiveSizeView.setOffscreenPageLimit(shopTabTypes.size() - 1);
        mBinding.adaptiveSizeView.setAdapter(adapter);
        mBinding.adaptiveSizeView.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                 //TODO: 2020/12/3 刷新内容

            }
        });

    }

    private void addTab(TabSegment tabSegment, FragmentAdapter<BaseFragment> adapter,
                        List<ShopTabTypeEnum> titles){
        Iterator<ShopTabTypeEnum> iterator = titles.iterator();
        while (iterator.hasNext()){
            ShopTabTypeEnum shopTabType = iterator.next();
            String title = shopTabType.getName();
            MyTabSegmentTab tab = new MyTabSegmentTab(title);
            tab.setTextSize(getResources().getDimensionPixelSize(R.dimen.waimai_shop_tabbar_item_text_size_selected));
            adapter.addFragment(mViewModel.getTabBarFragment(shopTabType), title);
            tabSegment.addTab(tab);
        }
    }
}
