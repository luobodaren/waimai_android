package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.databinding.Observable;

import com.bumptech.glide.Glide;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryHeaderViewHolder;
import com.life.base.utils.LogUtil;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.config.CustomLinkagePrimaryGoodsTypeAdapterConfig;
import com.life.waimaishuo.adapter.config.CustomLinkageSecondaryGoodsTypeAdapterConfig;
import com.life.waimaishuo.bean.ui.LinkageGoodsTypeGroupedItemInfo;
import com.life.waimaishuo.databinding.FragmentWaimaiMallAllTypeBinding;
import com.life.waimaishuo.listener.OnPrimaryItemClickListener;
import com.life.waimaishuo.listener.OnSecondaryItemClickListener;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.WaiMaiAllTypeViewModel;
import com.life.waimaishuo.util.MyDataBindingUtil;
import com.life.waimaishuo.util.StatusBarUtils;
import com.kunminx.linkage.LinkageRecyclerView;
import com.kunminx.linkage.adapter.viewholder.LinkagePrimaryViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryViewHolder;
import com.kunminx.linkage.bean.BaseGroupedItem;
import com.life.waimaishuo.views.MyLinkageRecyclerView;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.widget.imageview.RadiusImageView;

@Page(name = "全部分类-外卖", anim = CoreAnim.slide)
public class WaimaiAllTypeFragment extends BaseFragment implements
        OnPrimaryItemClickListener,
        OnSecondaryItemClickListener {

    private FragmentWaimaiMallAllTypeBinding mBinding;
    private WaiMaiAllTypeViewModel mViewModel;

    //双列表联动Recycler右侧顶部ImageView
    private RadiusImageView rightTopCustomImgView;

    @Override
    protected BaseViewModel setViewModel() {
        mViewModel = new WaiMaiAllTypeViewModel();
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentWaimaiMallAllTypeBinding) mViewDataBinding;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setFitStatusBarHeight(true);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_LIGHT);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_waimai_mall_all_type;
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();
        findViewById(R.id.iv_left_action).setOnClickListener(v -> {
            popToBack();
        });
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        addCallBack();
    }

    @Override
    protected void onLifecycleResume() {
        super.onLifecycleResume();
        changeStatusBarMode();
    }

    @Override
    protected void firstRequestData() {
        super.firstRequestData();
        LogUtil.d("全部分类 firstRequestData");
        mViewModel.requestLinkageGroupItems();
    }

    @Override
    public void onPrimaryItemClick(LinkagePrimaryViewHolder holder, View view, String title) {
//        SnackbarUtils.Short(view, title).show();
        /*for(BaseGroupedItem<LinkageGoodsTypeGroupedItemInfo> baseGroupedItem : mViewModel.getLinkageGroupItems()){
            if(baseGroupedItem.isHeader){
                if(baseGroupedItem.header.equals(title)){
                    Glide.with(this)
                            .load(baseGroupedItem.info.getImgUrl())
                            .placeholder(R.drawable.ic_waimai_brand)
                            .centerCrop()
                            .into(rightTopCustomImgView);
                    break;
                }
            }
        }*/
    }

    @Override
    public void onPrimaryItemChange(int position) {
        int index = 0;
        for(BaseGroupedItem<LinkageGoodsTypeGroupedItemInfo> baseGroupedItem : mViewModel.getLinkageGroupItems()){
            LogUtil.d("onPrimaryItemChange isheader:" + baseGroupedItem.isHeader + " header;" + baseGroupedItem.header);
            if(baseGroupedItem.isHeader){
                if(index == position){
                    Glide.with(this)
                            .load(baseGroupedItem.info.getImgUrl())
                            .placeholder(R.drawable.ic_waimai_brand)
                            .centerCrop()
                            .into(rightTopCustomImgView);
                    break;
                }else{
                    index++;
                }
            }
        }
    }

    @Override
    public void onSecondaryItemClick(LinkageSecondaryViewHolder holder, ViewGroup view,
                                     BaseGroupedItem<LinkageGoodsTypeGroupedItemInfo> item) {
        WaimaiTypeFragment.openPage(WaimaiAllTypeFragment.this, item.info.getGroup(),item.info.getTitle());
    }

    @Override
    public void onSecondaryHeadClick(LinkageSecondaryHeaderViewHolder holder,
                                     BaseGroupedItem<LinkageGoodsTypeGroupedItemInfo> item) {
        if(item.isHeader){
            WaimaiTypeFragment.openPage(WaimaiAllTypeFragment.this, item.header,"");
        }
    }

    private void addCallBack(){
        MyDataBindingUtil.addCallBack(this, mViewModel.getAllTypeObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.post(() -> initLinkageRecycler());
            }
        });
    }

    /**
     * 没有数据时，不可调用初始化 否则会报错
     */
    private void initLinkageRecycler(){
        if(mViewModel.getLinkageGroupItems().size() <= 0){  //构造空数据
            mViewModel.getLinkageGroupItems().add(new BaseGroupedItem<LinkageGoodsTypeGroupedItemInfo>(true,"没有数据") {});
        }
        MyLinkageRecyclerView<LinkageGoodsTypeGroupedItemInfo> linkage = mBinding.linkageAllType;
        linkage.setScrollSmoothly(false);
        FrameLayout rightTopCustomView = linkage.findViewById(R.id.right_top_custom);
        rightTopCustomView.setVisibility(View.VISIBLE);
        initRightTopCustomView(rightTopCustomView);
        linkage.init(mViewModel.getLinkageGroupItems(),
                new CustomLinkagePrimaryGoodsTypeAdapterConfig<>(this,linkage),
                new CustomLinkageSecondaryGoodsTypeAdapterConfig(this));
        linkage.setGridMode(true);
    }

    private void initRightTopCustomView(FrameLayout frameLayout) {
        rightTopCustomImgView = new RadiusImageView(getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
        rightTopCustomImgView.setLayoutParams(layoutParams);
        rightTopCustomImgView.setBackground(getResources().getDrawable(R.drawable.sr_bg_8dp_white));
        rightTopCustomImgView.setCornerRadius(24);
        frameLayout.addView(rightTopCustomImgView);

        if(mViewModel.getLinkageGroupItems().size() > 0){
            Glide.with(this)
                    .load(mViewModel.getLinkageGroupItems().get(0).info.getImgUrl())
                    .placeholder(R.drawable.ic_waimai_brand)
                    .centerCrop()
                    .into(rightTopCustomImgView);
        }
    }

}
