package com.life.waimaishuo.mvvm.view.fragment.common;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.LogUtil;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.SelectedPositionRecyclerViewAdapter;
import com.life.waimaishuo.bean.event.MessageEvent;
import com.life.waimaishuo.constant.MessageCodeConstant;
import com.life.waimaishuo.databinding.FragmentMainBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.MainViewModel;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.adapter.FragmentAdapter;

import java.lang.ref.WeakReference;
import java.util.List;

@Page(name = "主页",anim = CoreAnim.fade)
public class MainFragment extends BaseFragment {

    public final static String KEY_PAGE_POSITION_STRING = "key_page_position";
    private FragmentMainBinding mBinding;

    private MainViewModel mViewModel;

    private SelectedPositionRecyclerViewAdapter<String> mRecyclerItemSelectedAdapter;
    private WeakReference<LottieAnimationView> lavReference;    //底部TabBar选中的LottieAnimationView的弱引用

    private String[] animationAssetNames = {"tab_waimai_dynamic_effect.json",
            "tab_mall_dynamic_effect.json",
            "tab_order_dynamic_effect.json",
            "tab_mine_dynamic_effect.json"};

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new MainViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentMainBinding)mViewDataBinding;
        mBinding.setViewModel((MainViewModel) baseViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setRegisterEventBus(true);
    }

    @Override
    protected void initViews() {
        super.initViews();

//        initTab();
        initTabRecycler();
        initViewPager();
        //initTabAndViewPager();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        mRecyclerItemSelectedAdapter.setSelectedListener((holder, item, isCancel) -> {
            if(isCancel){

            }else{
                if(mRecyclerItemSelectedAdapter.getData().contains(item)){
                    int position = mRecyclerItemSelectedAdapter.getData().indexOf(item);
                    mBinding.viewPager.setCurrentItem(position);
                }
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
    public void onFragmentDataReset(Bundle bundle) {
        super.onFragmentDataReset(bundle);
        int position = bundle.getInt(KEY_PAGE_POSITION_STRING, -1);
        switch (position){
            case 0:
                mBinding.viewPager.setCurrentItem(0);
                break;
            case 1:
                mBinding.viewPager.setCurrentItem(1);
                break;
            case 2:
                mBinding.viewPager.setCurrentItem(2);
                break;
            case 3:
                mBinding.viewPager.setCurrentItem(3);
                break;
            default:
                LogUtil.e("页面返回值错误 position=" + position);
                break;
        }
    }

    @Override
    protected void onLifecycleStop() {
        super.onLifecycleStop();
        cancelTabViewAnimation();
    }

    @Override
    public void MessageEvent(MessageEvent messageEvent) {
        LogUtil.d(messageEvent.toString());
        super.MessageEvent(messageEvent);
        switch (messageEvent.getCode()){
            case MessageCodeConstant.MAIN_TAB_BAR_PAGE_CHANGE_WAIMAI:
                mBinding.viewPager.setCurrentItem(0);
                break;
            case MessageCodeConstant.MAIN_TAB_BAR_PAGE_CHANGE_MALL:
                mBinding.viewPager.setCurrentItem(1);
                break;
            case MessageCodeConstant.MAIN_TAB_BAR_PAGE_CHANGE_ORDER:
                mBinding.viewPager.setCurrentItem(2);
                break;
            case MessageCodeConstant.MAIN_TAB_BAR_PAGE_CHANGE_MINE:
                mBinding.viewPager.setCurrentItem(3);
                break;
        }
    }

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

    private void initViewPager(){
        List<BaseFragment> tabFragment = mViewModel.getTabFragment();
        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());
        String[] tabData = mViewModel.getTabData();
        int tabSize = tabData.length;
        for(int i = 0;i < tabSize;i++){
            String tabStr = tabData[i];
            adapter.addFragment(tabFragment.get(i),tabStr);
        }
        mBinding.viewPager.setOffscreenPageLimit(3);
        mBinding.viewPager.setAdapter(adapter);
        mBinding.viewPager.setCurrentItem(0,false);
    }

    private void cancelTabViewAnimation(){
        if(lavReference != null){
            LottieAnimationView lottieAnimationView = lavReference.get();
            if(lottieAnimationView != null){
                lottieAnimationView.cancelAnimation();
            }
            lavReference.clear();
        }
    }

    /*private void initTabAndViewPager(){
        List<BaseFragment> tabFragment = ((MainViewModel)baseViewModel).getAllPreciousTabFragment();
        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());

        mBinding.tabSegment.setupWithViewPager(mBinding.viewPager,false);
        mBinding.tabSegment.setHasIndicator(false);
        mBinding.tabSegment.setDefaultTabIconPosition(TabSegment.ICON_POSITION_TOP);
        mBinding.tabSegment.setMode(TabSegment.MODE_FIXED);
        mBinding.tabSegment.setDefaultNormalColor(getResources().getColor(R.color.view_uncheck));
        mBinding.tabSegment.setDefaultSelectedColor(getResources().getColor(R.color.view_check));

        int[] tabIconId = mViewModel.getTabIcons();
        int[] tabIconIdSelected = mViewModel.getTabIconsSelected();

        String[] tabData = mViewModel.getTabData();
        int tabSize = tabData.length;
        for(int i = 0;i < tabSize;i++){
            String tabStr = tabData[i];
            adapter.addFragment(tabFragment.get(i),tabStr);
        }
        mBinding.viewPager.setOffscreenPageLimit(3);
        mBinding.viewPager.setAdapter(adapter);
        mBinding.viewPager.setCurrentItem(0,false);
    }*/

}
