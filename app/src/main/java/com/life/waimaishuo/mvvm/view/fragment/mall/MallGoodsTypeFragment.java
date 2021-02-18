package com.life.waimaishuo.mvvm.view.fragment.mall;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.ui.ImageUrlNameData;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.databinding.FragmentMallGoodsTypeBinding;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mall.MallGoodsTypeViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.adapter.FragmentAdapter;

@Page(name = "商城-商品类型页", anim = CoreAnim.slide)
public class MallGoodsTypeFragment extends BaseFragment {

    private final static String KEY_PAGE_GOODS_TYPE = "key_page_goods_type";

    private FragmentMallGoodsTypeBinding mBinding;
    private MallGoodsTypeViewModel mViewModel;

    private String pageGoodsType = "";

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new MallGoodsTypeViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentMallGoodsTypeBinding)mViewDataBinding;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mall_goods_type;
    }

    @Override
    protected void initArgs() {
        super.initArgs();

        Bundle bundle = getArguments();
        if(bundle != null){
            pageGoodsType = bundle.getString(KEY_PAGE_GOODS_TYPE);
        }else{
            throw new Error(Constant.ERROR_MSG_NO_BUNDLE);
        }

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

        initTitle();
        initGoodsTypeTab();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
    }

    @Override
    protected void onLifecycleResume() {
        changeStatusBarMode();
    }

    private void initTitle(){
        mBinding.layoutTitle.tvTitle.setText(pageGoodsType);
    }

    private void initGoodsTypeTab() {
        mBinding.recyclerTab.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false));
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
                                // TODO: 2021/1/23 刷新商品数据
                            }
                        });
                    }
                });
        mBinding.recyclerTab.addItemDecoration(new RecyclerView.ItemDecoration() {
            int interval = (int) UIUtils.getInstance().scalePx(
                    getResources().getDimensionPixelOffset(R.dimen.interval_size_xs));
            int interval_32 = (int) UIUtils.getInstance().scalePx(32);

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, 
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
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

        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());
        adapter.addFragment(new MallRecyclerRecommendFragment(), "全部");
        mBinding.viewPager.setAdapter(adapter);
    }

    public static void openPageWithGoodsType(BaseFragment baseFragment, String goodsType){
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PAGE_GOODS_TYPE,goodsType);
        baseFragment.openPage(MallGoodsTypeFragment.class,bundle);
    }
}
