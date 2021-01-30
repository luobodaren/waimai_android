package com.life.waimaishuo.mvvm.view.fragment.mine;

import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.RedPacket;
import com.life.waimaishuo.databinding.FragmentMineMemberCenterBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mine.MineSuperMemberCenterViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.util.TextUtil;
import com.life.waimaishuo.util.Utils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "会员中心-会员", anim = CoreAnim.slide)
public class MineSuperMemberCenterFragment extends BaseFragment {

    private FragmentMineMemberCenterBinding mBinding;
    private MineSuperMemberCenterViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new MineSuperMemberCenterViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentMineMemberCenterBinding) mViewDataBinding;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_member_center;
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

        initRedPacketRecycler();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
    }

    private void initTitle(){
        TextUtil.setFakeBoldText(mBinding.layoutTitle.tvTitle,true);
        mBinding.layoutTitle.tvTitle.setText(R.string.members_center);
    }

    private void initRedPacketRecycler(){
        MyBaseRecyclerAdapter<RedPacket> redPacketMyBaseRecyclerAdapter = new MyBaseRecyclerAdapter<RedPacket>(R.layout.item_recycler_mine_member_center_red_packet,mViewModel.getRedPacketData()){
            @Override
            protected void initView(BaseViewHolder helper, RedPacket item) {
                super.initView(helper, item);
                //添加drawable
                helper.setText(R.id.tv_value_of_red_packet, TextUtil.getAbsoluteSpannable("￥" + item.getPriceValue(),28,0,1));
                helper.setText(R.id.tv_name, item.getName());
                helper.setText(R.id.tv_exchange_cost,item.getIntroduce());
            }
        };
        TextView textView = new TextView(requireContext());
        textView.setText(R.string.exclusive_red_packet_exchange);
        textView.setTextColor(getResources().getColor(R.color.text_normal));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,34);
        textView.setPadding(24,0,0,0);
        redPacketMyBaseRecyclerAdapter.addHeaderView(textView);
        mBinding.recyclerRedPacket.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false));
        mBinding.recyclerRedPacket.setAdapter(redPacketMyBaseRecyclerAdapter);
        mBinding.recyclerRedPacket.addItemDecoration(new RecyclerView.ItemDecoration() {
            int interval = (int) UIUtils.getInstance().scalePx(32);

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                if(position != RecyclerView.NO_POSITION){
                    outRect.top = interval;
                    if(position == state.getItemCount()-1){
                        outRect.bottom = interval;
                    }
                }
            }
        });
    }
}
