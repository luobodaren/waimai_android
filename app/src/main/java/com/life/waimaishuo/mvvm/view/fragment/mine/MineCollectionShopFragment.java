package com.life.waimaishuo.mvvm.view.fragment.mine;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.tag.CashBackTagAdapter;
import com.life.waimaishuo.adapter.tag.MallShopSignAndClassificationTagAdapter;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseRecyclerFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mine.MineCollectionShopViewModel;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.flowlayout.FlowTagLayout;
import com.xuexiang.xui.widget.layout.ExpandableLayout;

import java.util.List;

@Page(name = "店铺收藏")
public class MineCollectionShopFragment extends BaseRecyclerFragment {

    private MineCollectionShopViewModel mViewModel;

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_recycler_collection_shop;
    }

    @Override
    protected void initViews() {
        super.initViews();
        getRootView().findViewById(R.id.my_ll_content_view).setBackgroundColor(getResources().getColor(R.color.background));
    }

    @Override
    protected RecyclerView.LayoutManager getRecyclerLayoutManager() {
        return new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
    }

    @Override
    protected List getListData() {
        return mViewModel.getShopCollectionData();
    }

    @Override
    protected int getVariableId() {
        return com.life.waimaishuo.BR.item;
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new RecyclerView.ItemDecoration() {
            int interval = (int) UIUtils.getInstance().scalePx(24);
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = interval;
                if(parent.getChildAdapterPosition(view) == state.getItemCount() - 1){
                    outRect.bottom = interval;
                }
            }
        };
    }

    String[] tags = {"“味道不错，挺好吃的”","大写的好吃"};
    String[] tags2 = {"40减20","60减25","4元会员红包","40减20","40减20","40减20","40减20","40减20","40减20"};
    @Override
    protected void onRecyclerBindViewHolder(ViewDataBinding viewDataBinding, BaseViewHolder helper, Object item) {
        FlowTagLayout flowTagLayout = helper.getView(R.id.flowTagLayout_comment);
        FlowTagLayout flowTagLayout1 = helper.getView(R.id.flowTagLayout_preferential);

        initCommentFlowTag(flowTagLayout,tags);// FIXME: 2021/1/4 修改内容
        initPreferentialFlowTag(flowTagLayout1,tags2);

        helper.getView(R.id.iv_expandable).setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                ExpandableLayout expandableLayout = helper.getView(R.id.expandable_layout);
                if(expandableLayout.isExpanded()){
                    expandableLayout.collapse();
                }else{
                    expandableLayout.expand();
                }
            }
        });
    }

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new MineCollectionShopViewModel();
        }
        return mViewModel;
    }

    private void initCommentFlowTag(FlowTagLayout flowTagLayout,String[] tags){
        flowTagLayout.setAdapter(new MallShopSignAndClassificationTagAdapter(getContext()));
        flowTagLayout.addTags(tags);
    }

    private void initPreferentialFlowTag(FlowTagLayout flowTagLayout,String[] tags){
        flowTagLayout.setAdapter(new CashBackTagAdapter(getContext()));
        flowTagLayout.addTags(tags);
    }

}
