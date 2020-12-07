package com.example.myapplication.mvvm.view.fragment.order;

import android.graphics.Color;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.base.utils.UIUtils;
import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentOrderBinding;
import com.example.myapplication.mvvm.view.fragment.BaseFragment;
import com.example.myapplication.mvvm.vm.BaseViewModel;
import com.example.myapplication.mvvm.vm.order.OrderViewModel;
import com.example.myapplication.util.StatusBarUtils;
import com.example.myapplication.views.widget.MyTabSegmentTab;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.widget.tabbar.TabSegment;

import java.util.List;

@Page(name = "订单",anim = CoreAnim.fade)
public class OrderFragment extends BaseFragment {

    OrderViewModel myViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        myViewModel = new OrderViewModel();
        return myViewModel;
    }

    @Override
    protected void bindViewModel() {
        ((FragmentOrderBinding)mViewDataBinding).setViewModel(myViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initArgs() {
        setFitStatusBarHeight(true);
        setmStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_LIGHT);
    }

    @Override
    protected TitleBar initTitleBar() {
        TitleBar titleBar = super.initTitleBar();
        titleBar.setLeftImageDrawable(null);
        titleBar.addAction(new TitleBar.ImageAction(R.drawable.ic_search_black){
            @Override
            public void performAction(View view) {
                ((com.example.myapplication.mvvm.view.activity.BaseActivity)getActivity())
                        .showToast("点击了图标");
            }
        });
        titleBar.setBackgroundColor(Color.WHITE);
        titleBar.setTitleColor(getContext().getResources().getColor(R.color.text_normal));
        titleBar.setHeight((int)(getContext().getResources()
                .getDimensionPixelOffset(
                        R.dimen.titlebar_height)* UIUtils.getInstance(getContext()).getHorValue()));
        titleBar.setTitleSize((int)(getContext().getResources()
                .getDimensionPixelOffset(
                        R.dimen.titlebar_text_size)));
        return titleBar;
    }

    @Override
    protected void initViews() {
        super.initViews();

        initTabAndViewPager();
    }

    private void initTabAndViewPager(){
        FragmentOrderBinding mBinding = (FragmentOrderBinding)mViewDataBinding;

        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());

        mBinding.tabSegment.setupWithViewPager(mBinding.viewPager,false);
        mBinding.tabSegment.setHasIndicator(false);
        mBinding.tabSegment.setMode(TabSegment.MODE_FIXED);
        mBinding.tabSegment.setBackgroundColor(getContext().getResources().getColor(R.color.white));
        String[] tabData = myViewModel.getTabDatas();
        List<BaseFragment> tabFragment = myViewModel.getTabFragment();

        int tabSize = tabData.length;
        for(int i = 0;i < tabSize;i++){
            String tabStr = tabData[i];
            MyTabSegmentTab tab;
            if(i == 0){
                tab = new MyTabSegmentTab(
                        ContextCompat.getDrawable(MyApplication.getMyApplication(),R.drawable.ic_arrow_down_black),
                        null,
                        tabStr,
                        true
                );
                tab.setIconPosition(TabSegment.ICON_POSITION_RIGHT);
                tab.setTextSize(getResources().getDimensionPixelSize(R.dimen.order_tab_text_size_first));
            }else{
                tab = new MyTabSegmentTab(tabStr);
                tab.setTextSize(getResources().getDimensionPixelSize(R.dimen.order_tab_text_size));
            }
            mBinding.tabSegment.addTab(tab);
            adapter.addFragment(tabFragment.get(i),tabStr);
        }

        mBinding.viewPager.setOffscreenPageLimit(3); // FIXME: 2020/11/27 ???
        mBinding.viewPager.setAdapter(adapter);
        mBinding.viewPager.setCurrentItem(0,false);
    }


}
