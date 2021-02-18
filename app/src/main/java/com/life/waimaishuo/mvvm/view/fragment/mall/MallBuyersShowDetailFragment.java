package com.life.waimaishuo.mvvm.view.fragment.mall;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.BuyersShow;
import com.life.waimaishuo.bean.Comment;
import com.life.waimaishuo.databinding.FragmentBuyersShowDetailsBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mall.MallBuyersShowDetailViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "精选买家秀", anim = CoreAnim.slide)
public class MallBuyersShowDetailFragment extends BaseFragment {

    private final static String KEY_BUYER_DATA = "key_buyer_data";

    private FragmentBuyersShowDetailsBinding mBinding;
    private MallBuyersShowDetailViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new MallBuyersShowDetailViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentBuyersShowDetailsBinding)mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_buyers_show_details;
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

        initTitle();
        initUserInfo();
        initBanner();
        initShopInfo();
        initCommentRecycler();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
    }

    private void initTitle(){
        mBinding.layoutTitle.tvTitle.setText(getPageName());
        setViewVisibility(mBinding.layoutTitle.ivMenu,false);
    }

    private void initBanner(){
        mBinding.bannerLayout.setItemLayoutId(R.layout.adapter_banner_image_item_buyers_show);
        mBinding.bannerLayout.setSource(mViewModel.getBannerItemList())
                .setOnItemClickListener((view, t, position) -> {
                })
                .setIsOnePageLoop(false).startScroll();
    }

    private void initUserInfo(){
        Glide.with(requireContext())
                .load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201901%2F17%2F20190117092809_ffwKZ.thumb.700_0.jpeg&refer=http%3A%2F%2Fb-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1613617512&t=2698bd76db54daeb2d7649fce96a9c4e")
                .centerCrop()
                .placeholder(R.drawable.ic_waimai_brand)
                .into(mBinding.ivUserImg);
        mBinding.tvUserName.setText("匿名用户");
        mBinding.tvCreateTime.setText("2020-05-20");
    }

    private void initShopInfo(){
        Glide.with(requireContext())
                .load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201901%2F17%2F20190117092809_ffwKZ.thumb.700_0.jpeg&refer=http%3A%2F%2Fb-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1613617512&t=2698bd76db54daeb2d7649fce96a9c4e")
                .centerCrop()
                .placeholder(R.drawable.ic_waimai_brand)
                .into(mBinding.ivShopIcon);
        mBinding.tvShopName.setText("匿名用户");
        mBinding.tvGoodsIntroduce.setText("小米南瓜粥非常棒，很好喝，下次还来点。小米南瓜粥非常棒，很好喝，下次还来点。小米南瓜粥非常棒，很好喝，下次还来点。小米南瓜粥非常棒，很好喝，下次还来点。");
    }

    private void initCommentRecycler(){
        mBinding.recyclerComment.setLayoutManager(
                new LinearLayoutManager(
                        requireContext(),LinearLayoutManager.VERTICAL,false));
        MyBaseRecyclerAdapter adapter = new MyBaseRecyclerAdapter<Comment>(R.layout.item_recycler_mall_buyers_show_comment,mViewModel.getCommentData(), com.life.waimaishuo.BR.item){

            boolean hasInitDrawable = false;
            Drawable likeClickedDrawable;
            Drawable likeUnCheckDrawable;

            @Override
            protected void initView(ViewDataBinding viewDataBinding, BaseViewHolder helper, Comment item) {
                if(!hasInitDrawable){
                    int size = (int) UIUtils.getInstance().scalePx(40);
                    likeClickedDrawable = requireContext().getResources().getDrawable(R.drawable.ic_like);
                    likeClickedDrawable.setBounds(0,0,size,size);
                    likeUnCheckDrawable = requireContext().getResources().getDrawable(R.drawable.ic_like_uncheck);
                    likeUnCheckDrawable.setBounds(0,0,size,size);
                }

                helper.setText(R.id.tv_like,item.getCountOfLike());
                if(item.isClickedLike()){
                    helper.setTextColor(R.id.tv_like,getResources().getColor(R.color.colorTheme));
                    ((TextView)helper.getView(R.id.tv_like)).setCompoundDrawables(likeClickedDrawable,null,null,null);
                }else{
                    helper.setTextColor(R.id.tv_like,getResources().getColor(R.color.text_tip));
                    ((TextView)helper.getView(R.id.tv_like)).setCompoundDrawables(likeUnCheckDrawable,null,null,null);
                }
            }
        };
        /*adapter.setEnableLoadMore(true);
        adapter.setLoadMoreView(new LoadMoreView() {
            @Override
            public int getLayoutId() {
                return 0;
            }

            @Override
            protected int getLoadingViewId() {
                return 0;
            }

            @Override
            protected int getLoadFailViewId() {
                return 0;
            }

            @Override
            protected int getLoadEndViewId() {
                return 0;
            }
        });*/
        mBinding.recyclerComment.setAdapter(adapter);
    }

    public static void openPageWhitData(BaseFragment baseFragment, BuyersShow buyersShow){
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_BUYER_DATA,buyersShow);
        baseFragment.openPage(MallBuyersShowDetailFragment.class,bundle);
    }
}
