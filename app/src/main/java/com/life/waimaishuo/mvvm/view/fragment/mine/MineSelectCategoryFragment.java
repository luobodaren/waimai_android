package com.life.waimaishuo.mvvm.view.fragment.mine;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kunminx.linkage.LinkageRecyclerView;
import com.kunminx.linkage.adapter.viewholder.LinkagePrimaryViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryHeaderViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryViewHolder;
import com.kunminx.linkage.bean.BaseGroupedItem;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.config.CustomLinkagePrimaryGoodsTypeAdapterConfig;
import com.life.waimaishuo.adapter.config.CustomLinkageSecondaryGoodsTypeAdapterConfig;
import com.life.waimaishuo.bean.ui.LinkageGoodsTypeGroupedItemInfo;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.databinding.FragmentMineSelectCategoryBinding;
import com.life.waimaishuo.listener.OnPrimaryItemClickListener;
import com.life.waimaishuo.listener.OnSecondaryItemClickListener;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mine.MineSelectCategoryViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.views.MyLinkageRecyclerView;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

import java.lang.ref.WeakReference;

@Page(name = "选择品类", anim = CoreAnim.slide)
public class MineSelectCategoryFragment extends BaseFragment implements
        OnPrimaryItemClickListener,
        OnSecondaryItemClickListener {

    public static final String RESULT_KEY_CATEGORY_TYPE = "result_key_category_type";

    private FragmentMineSelectCategoryBinding mBinding;
    private MineSelectCategoryViewModel mViewModel;

    private MyLinkageRecyclerView<LinkageGoodsTypeGroupedItemInfo> linkage;
    private CustomLinkagePrimaryGoodsTypeAdapterConfig primaryConfig;
    private CustomLinkageSecondaryGoodsTypeAdapterConfig secondaryConfig;
    private String selected_type;

    @Override
    protected BaseViewModel setViewModel() {
        if (mViewModel == null) {
            mViewModel = new MineSelectCategoryViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentMineSelectCategoryBinding) mViewDataBinding;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_select_category;
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

        initTypeTv();
        initLinkageRecycler();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        mBinding.tvMall.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                if (mViewModel.currentSelectedType != 1) {
                    mViewModel.currentSelectedType = 1;
                    refreshView();
                }
            }
        });
        mBinding.tvWaimai.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                if (mViewModel.currentSelectedType != 2) {
                    mViewModel.currentSelectedType = 2;
                    refreshView();
                }
            }
        });

        mBinding.btChosen.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(RESULT_KEY_CATEGORY_TYPE,selected_type);
                setFragmentResult(Constant.RESULT_CODE_SUCCESS,resultIntent);
                popToBack();
            }
        });

    }

    @Override
    public void onPrimaryItemClick(LinkagePrimaryViewHolder holder, View view, String title) {

    }

    @Override
    public void onPrimaryItemChange(int position) {

    }

    @Override
    public void onSecondaryItemClick(LinkageSecondaryViewHolder holder, ViewGroup view, BaseGroupedItem<LinkageGoodsTypeGroupedItemInfo> item) {
        selected_type = item.info.getTitle();
    }

    @Override
    public void onSecondaryHeadClick(LinkageSecondaryHeaderViewHolder holder, BaseGroupedItem<LinkageGoodsTypeGroupedItemInfo> item) {

    }

    private void initTitle() {
        mBinding.layoutTitle.tvTitle.setText(getPageName());
    }

    private void initTypeTv() {
        mViewModel.currentSelectedType = 1;
        resetTypeSelectView();
    }

    private void initLinkageRecycler() {
        linkage = mBinding.linkageAllType;
        primaryConfig = new CustomLinkagePrimaryGoodsTypeAdapterConfig<>(this, linkage);
        secondaryConfig = new CustomLinkageSecondaryGoodsTypeAdapterConfig(this) {
            private WeakReference<View> selectView;

            @Override
            public int getGridLayoutId() {
                return super.getGridLayoutId(); // FIXME: 2021/1/26 生成可选择的drawable layout
            }

            @Override
            public int getHeaderLayoutId() {
                return R.layout.adapter_secondary_header_goods_type_mall;
            }

            @Override
            public void onBindHeaderViewHolder(LinkageSecondaryHeaderViewHolder holder, BaseGroupedItem<LinkageGoodsTypeGroupedItemInfo> item) {
                ((TextView) holder.getView(R.id.secondary_header)).setText(item.header);
            }

            @Override
            public void onBindViewHolder(LinkageSecondaryViewHolder holder, BaseGroupedItem<LinkageGoodsTypeGroupedItemInfo> item) {
                ((TextView) holder.getView(R.id.iv_goods_name)).setText(item.info.getTitle());
                Glide.with(holder.itemView.getContext()).load(item.info.getImgUrl()).into((ImageView) holder.getView(R.id.iv_goods_img));

                View imgView = holder.getView(R.id.iv_goods_img);
                ViewGroup viewGroup = holder.getView(R.id.iv_goods_item);

                if(imgView != null){
                    imgView.setOnClickListener(v -> {
                        if(selectView != null){
                            selectView.get().setSelected(false);
                            selectView.clear();
                        }
                        v.setSelected(true);
                        selectView = new WeakReference<>(v);

                        onSecondaryItemClick(holder, viewGroup, item);
                    });
                }
                viewGroup.setOnClickListener(v -> {
                    if(selectView != null){
                        selectView.get().setSelected(false);
                        selectView.clear();
                    }
                    if(imgView != null){
                        imgView.setSelected(true);
                        selectView = new WeakReference<>(imgView);
                    }

                    onSecondaryItemClick(holder, viewGroup, item);

                });

            }
        };
        linkage.init(mViewModel.getLinkageGroupItems(), primaryConfig, secondaryConfig);
        linkage.setGridMode(true);
    }

    /**
     * 刷新界面显示 即数据
     */
    private void refreshView(){
        resetTypeSelectView();
        refreshLinkageRecycler();
    }

    /**
     * 重置类型选择view的状态
     */
    private void resetTypeSelectView(){
        if(mViewModel.currentSelectedType == 1){
            mBinding.tvWaimai.setBackgroundResource(R.drawable.sr_stroke_1px_4dp_gray);
            mBinding.tvWaimai.setTextColor(getResources().getColor(R.color.text_normal));
            mBinding.tvMall.setBackgroundResource(R.drawable.sr_bg_4dp_theme);
            mBinding.tvMall.setTextColor(getResources().getColor(R.color.white));
        }else{
            mBinding.tvWaimai.setBackgroundResource(R.drawable.sr_stroke_1px_4dp_gray);
            mBinding.tvWaimai.setTextColor(getResources().getColor(R.color.text_normal));
            mBinding.tvMall.setBackgroundResource(R.drawable.sr_bg_4dp_theme);
            mBinding.tvMall.setTextColor(getResources().getColor(R.color.white));
        }
    }

    /**
     * 刷新双链表
     */
    private void refreshLinkageRecycler() {
        mViewModel.refreshLinkageData();
        linkage.getPrimaryAdapter().notifyDataSetChanged();
        linkage.getSecondaryAdapter().notifyDataSetChanged();
    }

    public static void openMyPage(BaseFragment baseFragment, int requestCode){
        baseFragment.openPageForResult(MineSelectCategoryFragment.class,null,requestCode);
    }

}
