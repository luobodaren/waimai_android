package com.life.waimaishuo.mvvm.view.fragment.mall;

import android.graphics.Rect;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.ui.ImageUrlNameData;
import com.life.waimaishuo.databinding.FragmentMallBinding;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mall.MallViewModel;
import com.life.waimaishuo.util.MyDataBindingUtil;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.adapter.FragmentAdapter;

@Page(name = "商城", anim = CoreAnim.slide)
public class MallFragment extends BaseFragment {

    FragmentMallBinding mBinding;
    MallViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new MallViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentMallBinding)mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mall;
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

        initTitle();
        initGoodsTypeTab();

        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());
        adapter.addFragment(mViewModel.getViewPagerFragment("全部"), "全部");
        mBinding.viewPager.setAdapter(adapter);
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        MyDataBindingUtil.addCallBack(this, mViewModel.goToLocation, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {

            }
        });

        mBinding.layoutItemTabAll.cl.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                openPage(MallAllTypeFragment.class);
            }
        });
        mBinding.viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                // TODO: 2020/12/3 刷新内容

            }
        });
    }

    private void initTitle(){

    }

//    private int textSizeNormal;
    private void initGoodsTypeTab() {
//        int space = (int) UIUtils.getInstance().scalePx(32);
        mBinding.layoutItemTabAll.tvType.setText(R.string.all);
        mBinding.layoutItemTabAll.iv.setImageResource(R.drawable.ic_all_function);

        mBinding.recyclerTab.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false));
        mBinding.recyclerTab.setAdapter(
                new MyBaseRecyclerAdapter<ImageUrlNameData>(R.layout.item_recycler_mall_tab_goods_type,
                        mViewModel.getTypeTabData(), com.life.waimaishuo.BR.item){
                    @Override
                    protected void initView(ViewDataBinding viewDataBinding, BaseViewHolder helper, ImageUrlNameData item) {
                        Glide.with(helper.itemView.getContext())
                                .load(item.getImgUrl())
                                .centerCrop()
                                .placeholder(R.drawable.ic_waimai_brand)
                                .into((ImageView)helper.getView(R.id.iv));

                        helper.setText(R.id.tv_type,item.getName());

                        helper.getView(R.id.cl).setOnClickListener(new BaseActivity.OnSingleClickListener() {
                            @Override
                            public void onSingleClick(View view) {
                                MallGoodsTypeFragment.openPageWithGoodsType(
                                        MallFragment.this,item.getName());
                            }
                        });
                    }
                });
        mBinding.recyclerTab.addItemDecoration(new RecyclerView.ItemDecoration() {
            int interval = (int) UIUtils.getInstance().scalePx(getResources().getDimensionPixelOffset(R.dimen.interval_size_xs));
            int interval_32 = (int) UIUtils.getInstance().scalePx(32);

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                if(position == 0){
                    outRect.left = interval;
                }else{
                    outRect.left = interval_32;
                    if(position == state.getItemCount() - 1){
                        outRect.right = interval_32;
                    }
                }
            }
        });


        /*textSizeNormal = 24;
        String[] titles = mViewModel.getGoodsTypeStrings();

        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());

        addTab(mBinding.tabSegmentGoodsType,adapter,titles);
        mBinding.tabSegmentGoodsType.setDefaultTabIconPosition(TabSegment.ICON_POSITION_TOP);
        mBinding.tabSegmentGoodsType.setHasIndicator(false);
        mBinding.tabSegmentGoodsType.setMode(TabSegment.MODE_SCROLLABLE);
        mBinding.tabSegmentGoodsType.setItemSpaceInScrollMode(space);
        mBinding.tabSegmentGoodsType.setDefaultNormalColor(getResources().getColor(R.color.text_normal));
        mBinding.tabSegmentGoodsType.setDefaultSelectedColor(getResources().getColor(R.color.text_normal));
        mBinding.tabSegmentGoodsType.setTabTextSize(textSizeNormal);
        mBinding.tabSegmentGoodsType.setupWithViewPager(mBinding.viewPager,false);

        mBinding.viewPager.setOffscreenPageLimit(titles.length - 1);
        mBinding.viewPager.setAdapter(adapter);*/
    }

    /*private void addTab(TabSegment tabSegment, FragmentAdapter<BaseFragment> adapter,
                        String[] titles){
        Drawable drawable;
        Context context = requireContext();
        for (String title : titles) {
            drawable = mViewModel.getTitleDrawable(context,title);
            MyTabSegmentTab tab = new MyTabSegmentTab(drawable,drawable,title,false);
            tab.setTextSize(textSizeNormal);
            adapter.addFragment(mViewModel.getViewPagerFragment(title), title);
            tabSegment.addTab(tab);
        }
    }*/

}
