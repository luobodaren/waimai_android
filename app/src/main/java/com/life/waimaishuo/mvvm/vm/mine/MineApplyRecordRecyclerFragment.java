package com.life.waimaishuo.mvvm.vm.mine;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.waimaishuo.R;
import com.life.waimaishuo.mvvm.view.fragment.BaseRecyclerFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.util.Utils;
import com.xuexiang.xpage.annotation.Page;

import java.util.List;

@Page(name = "申请记录—记录列表")
public class MineApplyRecordRecyclerFragment extends BaseRecyclerFragment {

    private MineApplyRecordViewModel mViewModel;

    private int mRecordType; //商务合作、店铺开店、骑手招募
    private String mListState; //全部、审核中、已完成

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_recycler_apply_record;
    }

    @Override
    protected RecyclerView.LayoutManager getRecyclerLayoutManager() {
        return new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
    }

    @Override
    protected List getListData() {
        return mViewModel.getRecordList(mRecordType, mListState);
    }

    @Override
    protected int getVariableId() {
        return com.life.waimaishuo.BR.item;
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
        return Utils.getDefaultItemDecoration(requireContext());
    }

    @Override
    protected void onRecyclerBindViewHolder(BaseViewHolder helper, Object item) {

    }

    @Override
    protected BaseViewModel setViewModel() {
        mViewModel = (MineApplyRecordViewModel) baseViewModel;
        return baseViewModel;
    }

    public int getRecordType() {
        return mRecordType;
    }

    public void setRecordType(int recordType) {
        this.mRecordType = recordType;
    }

    public String getListState() {
        return mListState;
    }

    public void setListState(String listState) {
        this.mListState = listState;
    }
}
