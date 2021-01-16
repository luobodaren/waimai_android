package com.life.waimaishuo.mvvm.view.fragment.mall;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.databinding.FragmentMallShopBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mall.MallShopViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.views.MyTabSegmentTab;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.widget.tabbar.TabSegment;

@Page(name = "商城店铺详情", anim = CoreAnim.slide)
public class MallShopFragment extends BaseFragment {

    private final static String KEY_SHOP_DATA = "shop";

    FragmentMallShopBinding mBinding;
    MallShopViewModel mViewModel;

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

        initTabSegment();

    }

    @Override
    protected void initListeners() {
        super.initListeners();

        mBinding.viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                refreshTabViewStyle(mBinding.tabSegment,mViewModel.getTabTitle(),position);
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

    private int textSizeSelected = 46;
    private int textSizeNormal = 32;
    private void initTabSegment() {
        String[] titles = mViewModel.getTabTitle();

        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());

        addTab(mBinding.tabSegment,adapter,titles);
        mBinding.tabSegment.setHasIndicator(false);
        mBinding.tabSegment.setMode(TabSegment.MODE_FIXED);
//        mBinding.tabSegment.setDefaultNormalColor(getResources().getColor(R.color.text_tip));
//        mBinding.tabSegment.setDefaultSelectedColor(getResources().getColor(R.color.text_normal));
        mBinding.tabSegment.setTabTextSize(textSizeNormal);
        mBinding.tabSegment.setupWithViewPager(mBinding.viewPager,false);

        mBinding.viewPager.setOffscreenPageLimit(titles.length - 1);
        mBinding.viewPager.setAdapter(adapter);
    }

    private void addTab(TabSegment tabSegment, FragmentAdapter<BaseFragment> adapter,
                        String[] titles){
        boolean isFirstItem = true;
        for (String title : titles) {
            MyTabSegmentTab tab = new MyTabSegmentTab(title);
            if(isFirstItem){
                tab.setTextSize(textSizeSelected);
                isFirstItem = false;
            }else{
                tab.setTextSize(textSizeNormal);
            }
            LogUtil.d(title + "的textSize:" + tab.getTextSize());
            adapter.addFragment(mViewModel.getTabFragment(title), title);
            tabSegment.addTab(tab);
        }
    }

    /**
     * 更新TabBar样式
     */
    private void refreshTabViewStyle(TabSegment tabSegment,String[] titles,int position) {
        tabSegment.reset();
        resetTab(tabSegment,titles,position);
        tabSegment.notifyDataChanged();
    }

    int textSizeSelectedScale = 0;
    int textSizeNormalScale = 0;
    /**
     * 仅更新 title 若需要改变个数 需要修改方法内逻辑
     * @param tabSegment
     * @param titles
     * @param selectedPosition
     */
    private void resetTab(TabSegment tabSegment,String[] titles,
                          int selectedPosition){
        int position = 0;
        if(textSizeSelectedScale == 0 || textSizeNormalScale == 0){
            textSizeSelectedScale = (int) UIUtils.getInstance().scalePx(textSizeSelected);
            textSizeNormalScale = (int) UIUtils.getInstance().scalePx(textSizeNormal);
        }
        for (String title : titles) {
            MyTabSegmentTab tab = new MyTabSegmentTab(title);
            tab.setTextSize(position == selectedPosition ? textSizeSelectedScale :textSizeNormalScale);
            tabSegment.addTab(tab);
            position++;
        }
    }

    public static void openPageWithShop(BaseFragment baseFragment, Shop shop){
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_SHOP_DATA,shop);
        baseFragment.openPage(MallShopFragment.class,bundle);
    }


}
