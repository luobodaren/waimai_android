package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.app.Dialog;
import android.graphics.Rect;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.BR;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.BaseBannerAdapter;
import com.life.waimaishuo.adapter.CashBackTagAdapter;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.MerchantsService;
import com.life.waimaishuo.bean.PreferentialActivity;
import com.life.waimaishuo.bean.RedPacket;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.databinding.FragmentShopDetailBinding;
import com.life.waimaishuo.databinding.LayoutDialogShopMorePreferentialBinding;
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
        mBinding.layoutShopDetails.setViewModel(mViewModel);
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

        initNavigationTab();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        addCallback();

        mBinding.stickyNavigationLayout.setOnScrollChangeListener(moveRatio -> {
            if(moveRatio == 1){
                setStatusBarShowByType(HIDE_STATUS_BAR);
            } else {
                setStatusBarShowByType(SHOW_STATUS_BAR);
            }
        });
        mBinding.adaptiveSizeView.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                //TODO: 2020/12/3 viewpager切换
                if(position == 0){
                    mBinding.layoutShoppingCart.llShoppingCart.setVisibility(View.VISIBLE);
                }else{
                    mBinding.layoutShoppingCart.llShoppingCart.setVisibility(View.INVISIBLE);
                }
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
        MyDataBindingUtil.addCallBack(this, mViewModel.onMorePreferentialClick, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                showBottomPreferentialDialog();
            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.onCancelDialogClick, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                closePreferentialDialog();
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
                setText(getString(R.string.notice_content, shop.getNotice()));
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
        mBinding.layoutMembers.tvShopMembersCardName.setText(shop.getMemberCard().getName());
        mBinding.layoutMembers.tvShopMembersCardDescribe.setText(shop.getMemberCard().getDescribe());
        Glide.with(this).load(shop.getShop_head_portrait()).
                placeholder(R.drawable.ic_waimai_brand).centerCrop().
                into(mBinding.layoutMembers.ivShopIcon);

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
    private Dialog mMembersQeCardDialog;
    /**
     * 显示会员二维码
     */
    private void showMembersQrCodeDialog() {
        if(mMembersQeCardDialog == null){
            mMembersQeCardDialog = new Dialog(requireContext(),R.style.mySimpleNoTitleDialog);
            View view = View.inflate(requireContext(),R.layout.layout_members_qr_code,null);
            view.findViewById(R.id.bt_members_center).setOnClickListener(new BaseActivity.OnMultiClickListener() {
                @Override
                public void onMultiClick(View view) {
                    mMembersQeCardDialog.dismiss();
                    showAddMemberInfo("加入会员成功",true);
                }
            });
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            mMembersQeCardDialog.setContentView(view,layoutParams);

            mMembersQeCardDialog.setCanceledOnTouchOutside(true);
            WindowManager.LayoutParams params = mMembersQeCardDialog.getWindow().getAttributes();
            params.gravity = Gravity.CENTER;
            params.width = (int)UIUtils.getInstance(requireContext()).scalePx(
                    getResources().getDimensionPixelSize(R.dimen.members_qr_code_dialog_width));
            params.height = (int)UIUtils.getInstance(requireContext()).scalePx(
                    getResources().getDimensionPixelSize(R.dimen.members_qr_code_dialog_height));
            mMembersQeCardDialog.getWindow().setAttributes(params);
        }
        mMembersQeCardDialog.show();
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

    private MyBottomDialog mPreferentialDialog;
    private void showBottomPreferentialDialog() {
        if(mPreferentialDialog == null){
            View contentView = initPreferentialContentView();
            mPreferentialDialog = new MyBottomDialog(requireContext(),R.style.mySimpleNoTitleDialog);
            mPreferentialDialog.setContentView(contentView,
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        mPreferentialDialog.show();
    }

    private void closePreferentialDialog() {
        mPreferentialDialog.dismiss();
    }
    private View initPreferentialContentView() {
        View view = View.inflate(getContext(),R.layout.layout_dialog_shop_more_preferential,null);
        LayoutDialogShopMorePreferentialBinding binding = LayoutDialogShopMorePreferentialBinding.bind(view);
        binding.setViewModel(mViewModel);

        binding.tvShopName.setText(mViewModel.getShopDetail().getShop_name());

        binding.redPacketRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        binding.redPacketRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            int interval = (int)UIUtils.getInstance(getContext()).scalePx(getResources().getDimensionPixelSize(R.dimen.interval_size_xs));
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = interval;
            }
        });
        binding.redPacketRecyclerView.setAdapter(new MyBaseRecyclerAdapter<RedPacket>(
                R.layout.item_red_packet,mViewModel.getPreferentialData().getRedPacketList(), BR.item){
            String receiveStr = "";
            String receivedStr = "";
            @Override
            protected void initView(BaseViewHolder helper, RedPacket item) {
                super.initView(helper, item);
                if(receiveStr.equals("")){
                    receiveStr = getResources().getString(R.string.receive);
                }
                if(receivedStr.equals("")){
                    receivedStr = getResources().getString(R.string.received);
                }

                SpannableStringBuilder builder = new SpannableStringBuilder("￥" + item.getPriceValue());
                builder.setSpan(new AbsoluteSizeSpan((int)UIUtils.getInstance(requireContext()).scalePx(28)),
                        0,
                        1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ((TextView)helper.getView(R.id.tv_price)).setText(builder);

                Button receiveBt = helper.getView(R.id.bt_receive);
                if(item.isGet()){
                    receiveBt.setBackground(getResources().getDrawable(R.drawable.sr_stroke_1px_full_raduis_red));
                    receiveBt.setText(receivedStr);
                    receiveBt.setTextColor(getResources().getColor(R.color.colorTheme));
                }else{
                    receiveBt.setBackground(getResources().getDrawable(R.drawable.sr_bg_full_corners_theme));
                    receiveBt.setText(receiveStr);
                    receiveBt.setTextColor(getResources().getColor(R.color.white));
                }
            }
        });

        binding.preferentialRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        binding.preferentialRecyclerView.addItemDecoration(getItemDecoration());
        binding.preferentialRecyclerView.setAdapter(new MyBaseRecyclerAdapter<PreferentialActivity>(
                R.layout.item_recycler_preferential_activity,mViewModel.getPreferentialData().getPreferentialActivityList(), BR.item){
            @Override
            protected void initView(BaseViewHolder helper, PreferentialActivity item) {
                super.initView(helper, item);
                helper.setText(R.id.tv_tag,item.getName());
            }
        });

        binding.serviceRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        binding.serviceRecyclerView.addItemDecoration(getItemDecoration());
        binding.serviceRecyclerView.setAdapter(new MyBaseRecyclerAdapter<MerchantsService>(
                R.layout.item_recycler_merchants_service,mViewModel.getPreferentialData().getMerchantsServiceList(), BR.item){
            @Override
            protected void initView(BaseViewHolder helper, MerchantsService item) {
                super.initView(helper, item);
                helper.setText(R.id.tv_tag,item.getName());
            }
        });

        binding.tvNoticeContent.setText(getResources().getString(R.string.notice_content,mViewModel.getPreferentialData().getNotice()));

        return view;

    }

    private RecyclerView.ItemDecoration getItemDecoration(){
        return new RecyclerView.ItemDecoration() {
            int interval = (int)UIUtils.getInstance(getContext()).scalePx(getResources().getDimensionPixelSize(R.dimen.preferential_item_space));
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if(parent.getChildAdapterPosition(view) != 0){
                    outRect.top = interval;
                }
            }
        };
    }

}
