package com.life.waimaishuo.mvvm.view.fragment.mall;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.databinding.Observable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.appbar.AppBarLayout;
import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.SelectedPositionRecyclerViewAdapter;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.databinding.FragmentMallShopBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mall.MallShopViewModel;
import com.life.waimaishuo.util.MyDataBindingUtil;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.adapter.FragmentAdapter;

import java.lang.ref.WeakReference;
import java.util.List;

@Page(name = "商城店铺详情", anim = CoreAnim.slide)
public class MallShopFragment extends BaseFragment {

    private final static String KEY_SHOP_DATA = "shop";

    private FragmentMallShopBinding mBinding;
    private MallShopViewModel mViewModel;

    private SelectedPositionRecyclerViewAdapter<String> mRecyclerItemSelectedAdapter;

    private String[] animationAssetNames = {"tab_mine_dynamic_effect.json",
            "tab_mall_dynamic_effect.json",
            "tab_order_dynamic_effect.json",
            "tab_waimai_dynamic_effect.json"};

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new MallShopViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentMallShopBinding) mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mall_shop;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setFitStatusBarHeight(true);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_DARK);

        Bundle bundle = getArguments();
        if(bundle == null){
            throw new Error(Constant.ERROR_MSG_NO_BUNDLE);
        }

    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();

        initAppBarLayoutToolbar();
        initShopInfoCl();

        initTabRecycler();
        initViewPager();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        MyDataBindingUtil.addCallBack(this, mViewModel.onShopInfoClick, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                MallShopImpressionFragment.openPageWithShopData(MallShopFragment.this,new Shop());
            }
        });

        mBinding.appbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            int folding = 0;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {    // FIXME: 2020/12/25 存在问题：由于布局使用fitSystemWindow 布局会预留出状态栏空间，但activity又是沉浸式，所以会存在空白
                if (folding == 0) {
                    folding = mBinding.flFolding.getMeasuredHeight()
                            - ((ViewGroup.MarginLayoutParams) mBinding.appbarLayoutToolbar.getLayoutParams()).topMargin
                            - mBinding.appbarLayoutToolbar.getMeasuredHeight();    //可折叠的距离
                }
                float gradient = (float) -verticalOffset / (float) folding;//渐变值
                if ((-verticalOffset) < (folding / 2)) {            //折叠未超过一半
                    setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_DARK);//状态栏白色字体
                    changeStatusBarMode();
                    setTitleBarFoldingStyle(false);
                    /*mBinding.layoutTitleShopDetail.etSearch.setFocusable(false);    // FIXME: 2020/12/25 无效
                    mBinding.layoutTitleShopDetail.etSearch.setClickable(false);*/
                } else {                                          //折叠超过一半
                    setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_LIGHT);//状态栏黑色字体
                    changeStatusBarMode();
                    setTitleBarFoldingStyle(true);
                    /*mBinding.layoutTitle.etSearch.setFocusable(true);
                    mBinding.layoutTitleShopDetail.etSearch.setClickable(true);*/
                }
                LogUtil.d("渐变值：" + gradient);
                /*mBinding.layoutTitleShopDetail.etSearch.setAlpha(gradient);*/

                if ((-verticalOffset) == folding) {//完全折叠
                    LogUtil.d("完全折叠");
                }
            }
        });

        mRecyclerItemSelectedAdapter.setSelectedListener((holder, item) -> {
            if(mRecyclerItemSelectedAdapter.getData().contains(item)){
                int position = mRecyclerItemSelectedAdapter.getData().indexOf(item);
                mBinding.viewPager.setCurrentItem(position);
            }
        });

        mBinding.viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                LogUtil.d("viewPager onPageSelected " + position);
                mRecyclerItemSelectedAdapter.setSelectedPosition(position);
            }
        });
    }

    @Override
    protected void onLifecycleStop() {
        super.onLifecycleStop();
        cancelTabViewAnimation();
    }

    private void setTitleBarFoldingStyle(boolean isFolding) {
        initTitleDrawable();
        if(isFolding){
            mBinding.layoutTitle.ivBack.setImageDrawable(drawableBackDark);
            mBinding.layoutTitle.ivShoppingCart.setImageDrawable(drawableShoppingCartDark);
            mBinding.layoutTitle.ivShare.setImageDrawable(drawableShareDark);
            mBinding.layoutTitle.ivMenu.setImageDrawable(drawableMenuDark);
        }else{
            mBinding.layoutTitle.ivBack.setImageDrawable(drawableBack);
            mBinding.layoutTitle.ivShoppingCart.setImageDrawable(drawableShoppingCart);
            mBinding.layoutTitle.ivShare.setImageDrawable(drawableShare);
            mBinding.layoutTitle.ivMenu.setImageDrawable(drawableMenu);
        }
    }

    private Drawable drawableBack;
    private Drawable drawableBackDark;

    private Drawable drawableShoppingCart;
    private Drawable drawableShareDark;

    private Drawable drawableShare;
    private Drawable drawableShoppingCartDark;

    private Drawable drawableMenu;
    private Drawable drawableMenuDark;
    private boolean isInitTitleDrawable = false;

    private void initTitleDrawable() {
        if (!isInitTitleDrawable) {
            isInitTitleDrawable = true;
            drawableBack = getResources().getDrawable(R.drawable.ic_arrow_left_white);
            drawableBackDark = getResources().getDrawable(R.drawable.ic_arrow_left_black);
            drawableShoppingCart = getResources().getDrawable(R.drawable.ic_shopping_cart_white);
            drawableShoppingCartDark = getResources().getDrawable(R.drawable.ic_shopping_cart_dark);
            drawableShare = getResources().getDrawable(R.drawable.ic_share);
            drawableShareDark = getResources().getDrawable(R.drawable.ic_share_black);
            drawableMenu = getResources().getDrawable(R.drawable.ic_menu_white);
            drawableMenuDark = getResources().getDrawable(R.drawable.ic_menu_normal);
        }
    }

    private void initAppBarLayoutToolbar() {
        ((ViewGroup.MarginLayoutParams) mBinding.appbarLayoutToolbar.getLayoutParams()).setMargins(
                0, (int) (UIUtils.getInstance().getSystemBarHeight() / UIUtils.getInstance().getHorValue()), 0, 0);
    }

    private void initShopInfoCl(){
        int paddingTop = (int) UIUtils.getInstance().getSystemBarHeight();
        paddingTop += UIUtils.getInstance().scalePx(getResources().getDimensionPixelSize(R.dimen.titlebar_height));
        mBinding.clShopInfo.setPadding(0,paddingTop,0,0);
    }

    WeakReference<LottieAnimationView> lavReference;
    private void initTabRecycler(){
        mRecyclerItemSelectedAdapter = new SelectedPositionRecyclerViewAdapter<String>(mViewModel.getTabDataList()) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.layout_main_tab;
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, boolean selected, String item) {
                int position = holder.getAdapterPosition();

                holder.setText(R.id.tv_tab,mViewModel.getTabDataList().get(position));

                String animationAssetName = animationAssetNames[position];
                LottieAnimationView lottieAnimationView = holder.getView(R.id.animation_view);
                if(lottieAnimationView.getTag(R.id.tag_animation_init) == null){
                    lottieAnimationView.setAnimation(animationAssetName);
                    lottieAnimationView.setTag(R.id.tag_animation_init,"1");
                }
                lottieAnimationView.setProgress(0);
                lottieAnimationView.cancelAnimation();
                if(selected){
                    if(lavReference != null)
                        lavReference.clear();
                    lavReference = new WeakReference<>(lottieAnimationView);
                    lottieAnimationView.playAnimation();
                    holder.setTextColor(R.id.tv_tab,getResources().getColor(R.color.colorTheme));
                }else{
                    holder.setTextColor(R.id.tv_tab,getResources().getColor(R.color.text_uncheck));
                }
            }
        };

        mBinding.recyclerTab.setLayoutManager(
                new GridLayoutManager(requireContext(),4, LinearLayoutManager.VERTICAL,false));
        mBinding.recyclerTab.setAdapter(mRecyclerItemSelectedAdapter);
    }

    private void cancelTabViewAnimation(){
        LottieAnimationView lottieAnimationView = lavReference.get();
        lottieAnimationView.cancelAnimation();
        lavReference.clear();
    }

    private void initViewPager(){
        List<BaseFragment> tabFragment = mViewModel.getTabFragment();
        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());
        List<String> tabData = mViewModel.getTabDataList();
        int tabSize = tabData.size();
        for(int i = 0;i < tabSize;i++){
            String tabStr = tabData.get(i);
            adapter.addFragment(tabFragment.get(i),tabStr);
        }
        mBinding.viewPager.setOffscreenPageLimit(3);
        mBinding.viewPager.setAdapter(adapter);
        mBinding.viewPager.setCurrentItem(0,false);
    }

    public static void openPageWithShop(BaseFragment baseFragment, Shop shop){
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_SHOP_DATA,shop);
        baseFragment.openPage(MallShopFragment.class,bundle);
    }

}
