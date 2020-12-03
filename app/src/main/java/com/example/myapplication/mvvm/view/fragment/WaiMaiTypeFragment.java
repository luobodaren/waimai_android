package com.example.myapplication.mvvm.view.fragment;

import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.adapter.CustomLinkagePrimaryAdapterConfig;
import com.example.myapplication.adapter.ElemeSecondaryAdapterConfig;
import com.example.myapplication.bean.ElemeGroupedItem;
import com.example.myapplication.databinding.FragmentWaimaiTypeBinding;
import com.example.myapplication.mvvm.vm.BaseViewModel;
import com.example.myapplication.mvvm.vm.waimai.WaiMaiTypeViewModel;
import com.kunminx.linkage.LinkageRecyclerView;
import com.kunminx.linkage.adapter.viewholder.LinkagePrimaryViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryViewHolder;
import com.kunminx.linkage.bean.BaseGroupedItem;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.utils.SnackbarUtils;

import java.util.List;

@Page(name = "全部分类", anim = CoreAnim.slide)
public class WaiMaiTypeFragment extends BaseFragment implements CustomLinkagePrimaryAdapterConfig.OnPrimaryItemClickListener, ElemeSecondaryAdapterConfig.OnSecondaryItemClickListener{

    private FragmentWaimaiTypeBinding binding;
    private WaiMaiTypeViewModel viewModel;

    @Override
    protected BaseViewModel setViewModel() {
        viewModel = new WaiMaiTypeViewModel();
        return viewModel;
    }

    @Override
    protected void bindViewModel() {
        binding = (FragmentWaimaiTypeBinding) mViewDataBinding;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setStatusBarLightMode(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_waimai_type;
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();
        initLinkageRecycler();
    }

    private void initLinkageRecycler(){
        LinkageRecyclerView<ElemeGroupedItem.ItemInfo> linkage = binding.linkageWaimaiType;
        linkage.init(viewModel.getElemeGroupItems(), new CustomLinkagePrimaryAdapterConfig(this), new ElemeSecondaryAdapterConfig(this));
    }

    @Override
    public void onPrimaryItemClick(LinkagePrimaryViewHolder holder, View view, String title) {
        SnackbarUtils.Short(view, title).show();
    }

    @Override
    public void onSecondaryItemClick(LinkageSecondaryViewHolder holder, ViewGroup view, BaseGroupedItem<ElemeGroupedItem.ItemInfo> item) {
        SnackbarUtils.Short(view, item.info.getTitle()).show();
    }

    @Override
    public void onGoodAdd(View view, BaseGroupedItem<ElemeGroupedItem.ItemInfo> item) {
        SnackbarUtils.Short(view, "添加：" + item.info.getTitle()).show();
    }

}
