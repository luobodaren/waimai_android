package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.databinding.Observable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.BaseBannerAdapter;
import com.life.waimaishuo.adapter.CashBackTagAdapter;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.databinding.FragmentShopDetailBinding;
import com.life.waimaishuo.enumtype.ShopTabTypeEnum;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.ShopDetailViewModel;
import com.life.waimaishuo.util.MyDataBindingUtil;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.views.MyTabSegmentTab;
import com.life.waimaishuo.views.widget.dialog.MyBottomDialog;
import com.life.waimaishuo.views.widget.pop.MyCheckRoundTextInfoPop;
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

    private MyCheckRoundTextInfoPop mAddMemberInfoPopWindow;
    private Runnable mCancelPopRunnable;


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
        mBinding.layoutMembers.setViewModel(mViewModel);
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
        addCallback();

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

    private void addCallback() {
        MyDataBindingUtil.addCallBack(this, mViewModel.onMembersCodeClick, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                showMembersQrCodeDialog();
            }
        });
    }

    private void initHeadDetail() {
        Shop shop = mViewModel.getShopDetail();

        mBinding.layoutShopDetails.tvShopName.setText(shop.getShop_name());
        mBinding.layoutShopDetails.layoutScoreAndFans.setScore((int) Float.parseFloat(shop.getFavorable_rate()));
        mBinding.layoutShopDetails.layoutScoreAndFans.
                setFansStr(getString(R.string.number_of_fans, shop.getNumber_of_fans()));
        mBinding.layoutShopDetails.tvShopDescribe1.
                setText("月售" + shop.getSale_count_per_month() + "+ 配送约60分钟");
        mBinding.layoutShopDetails.tvMorePreferential.
                setText(getString(R.string.more_preferential,4));
        mBinding.layoutShopDetails.tvShopAnnouncement.
                setText(getString(R.string.notice, shop.getNotice()));
        Glide.with(this).load(shop.getShop_head_portrait()).
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
        Glide.with(this).load(shop.getShop_head_portrait()).
                placeholder(R.drawable.ic_waimai_brand).centerCrop().
                into(mBinding.layoutMembers.ivShopIcon);

    }

    private void initBanner(){
        BaseBannerAdapter mAdapterHorizontal
                = new BaseBannerAdapter(mViewModel.getBannerSource(),R.layout.adapter_recycler_view_banner_image_item);
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

    /**
     * 显示会员二维码
     */
    private void showMembersQrCodeDialog() {
        Dialog dialog = new Dialog(requireContext(),R.style.mySimpleNoTitleDialog);

        View view = View.inflate(requireContext(),R.layout.layout_members_qr_code,null);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                getResources().getDimensionPixelSize(R.dimen.members_qr_code_dialog_width),
                getResources().getDimensionPixelSize(R.dimen.members_qr_code_dialog_height));
        view.setLayoutParams(layoutParams);

        view.findViewById(R.id.bt_members_center).setOnClickListener(new BaseActivity.OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                dialog.dismiss();
                showAddMemberInfo("加入会员成功",true);
            }
        });

        dialog.addContentView(view,layoutParams);
        dialog.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM | Gravity.CENTER;
        params.width = layoutParams.width;
        params.height = layoutParams.height;
        dialog.getWindow().setAttributes(params);
        dialog.show();

//        PopWindow popWindow = new PopWindow(getContext(),R.layout.layout_members_qr_code);
//        popWindow.findViewById(R.id.bt_members_center).setOnClickListener(new BaseActivity.OnMultiClickListener() {
//            @Override
//            public void onMultiClick(View view) {
//                showAddMemberInfo("加入会员成功",true);
//            }
//        });
//        popWindow.showAtLocation(this.getRootView(),Gravity.CENTER,0,0);
    }

    private void showAddMemberInfo(String info, boolean successful){
        // TODO: 2020/12/1 viewModel层读消息
        // TODO: 2020/12/1 改为在回调时调用
        if(mAddMemberInfoPopWindow == null){
            mAddMemberInfoPopWindow = new MyCheckRoundTextInfoPop(getContext(),info,successful);
            mAddMemberInfoPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    mHandler.removeCallbacks(mCancelPopRunnable);
                }
            });
        }

        if(mCancelPopRunnable == null){
            mCancelPopRunnable = new Runnable() {
                @Override
                public void run() {
                    mAddMemberInfoPopWindow.dismiss();
                }
            };
        }

        mAddMemberInfoPopWindow.showAtCenter(mRootView);
        mHandler.postDelayed(mCancelPopRunnable, Constant.POP_WINDOW_SHOW_TIME);
    }

    private void showSimpleBottomSheetList() {
        initPreferentialContentView();

        MyBottomDialog dialog = new MyBottomDialog(getContext());
        dialog.setContentView();
    }

    private void initPreferentialContentView() {
        View view = View.inflate(getContext(),R.layout.layout_dialog_shop_more_preferential,null);



        RecyclerView preferentialRecycler = view.findViewById(R.id.preferential_content_recyclerView);
        preferentialRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
//        preferentialRecycler.setHasFixedSize(true);
        preferentialRecycler.setAdapter();
    }

}
