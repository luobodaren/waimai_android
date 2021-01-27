package com.life.waimaishuo.mvvm.view.fragment.mine;

import com.life.waimaishuo.R;
import com.life.waimaishuo.databinding.FragmentMineCollectionBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.util.MyTabSegmentBoldTypeFaceProvider;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.views.MyTabSegmentTab;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.widget.tabbar.TabSegment;

@Page(name = "我的收藏", anim = CoreAnim.slide)
public class MineCollectionFragment extends BaseFragment {

    private FragmentMineCollectionBinding mBinding;

    @Override
    protected BaseViewModel setViewModel() {
        return null;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentMineCollectionBinding) mViewDataBinding;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_collection;
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

        initTabSegment();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
    }

    private int textSizeNormal = 34;
    private void initTabSegment(){
        String[] titles = {"店铺收藏", "商品收藏"};

        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());

        addTab(mBinding.tabSegment,adapter,titles);
        mBinding.tabSegment.setHasIndicator(false);
        mBinding.tabSegment.setMode(TabSegment.MODE_FIXED);
//        mBinding.tabSegment.setDefaultNormalColor(getResources().getColor(R.color.text_tip));
//        mBinding.tabSegment.setDefaultSelectedColor(getResources().getColor(R.color.text_normal));
        mBinding.tabSegment.setTabTextSize(textSizeNormal);
        mBinding.tabSegment.setupWithViewPager(mBinding.viewPager,false);
        mBinding.tabSegment.setTypefaceProvider(new MyTabSegmentBoldTypeFaceProvider());

        mBinding.viewPager.setOffscreenPageLimit(titles.length - 1);
        mBinding.viewPager.setAdapter(adapter);
    }

    private void addTab(TabSegment tabSegment, FragmentAdapter<BaseFragment> adapter,
                        String[] titles){
        for (String title : titles) {
            MyTabSegmentTab tab = new MyTabSegmentTab(title);
            if(title.equals("店铺收藏")){
                adapter.addFragment(new MineCollectionShopFragment(), title);
            }else if(title.equals("商品收藏")){
                adapter.addFragment(new MineCollectionGoodsFragment(), title);
            }
            tabSegment.addTab(tab);
        }
    }
}
