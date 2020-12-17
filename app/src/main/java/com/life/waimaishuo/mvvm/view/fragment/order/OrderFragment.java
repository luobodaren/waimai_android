package com.life.waimaishuo.mvvm.view.fragment.order;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.life.base.utils.UIUtils;
import com.life.waimaishuo.MyApplication;
import com.life.waimaishuo.R;
import com.life.waimaishuo.databinding.FragmentOrderBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.order.OrderViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.views.MyTabSegmentTab;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.widget.tabbar.TabSegment;

import java.util.List;

import com.life.waimaishuo.mvvm.view.activity.BaseActivity;

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
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_LIGHT);
    }

    @Override
    protected TitleBar initTitleBar() {
        TitleBar titleBar = super.initTitleBar();
        titleBar.setLeftImageDrawable(null);
        ImageView imageView = (ImageView) titleBar.addAction(new TitleBar.ImageAction(R.drawable.ic_search_black){
            @Override
            public void performAction(View view) {
                ((BaseActivity)getActivity())
                        .showToast("点击了图标");
            }
        });
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;   // FIXME: 2020/12/15 改为动态设置大小
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        imageView.setLayoutParams(layoutParams);

        titleBar.setBackgroundColor(Color.WHITE);
        titleBar.setTitleColor(getContext().getResources().getColor(R.color.text_normal));
        titleBar.setHeight((int)UIUtils.getInstance(getContext()).scalePx(getContext().getResources()
                .getDimensionPixelSize(R.dimen.titlebar_height)));
        titleBar.setTitleSize((int)UIUtils.getInstance(getContext()).scalePx(getContext().getResources()
                .getDimensionPixelOffset(R.dimen.titlebar_text_size)));
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
