package com.life.waimaishuo.mvvm.view.fragment.mine;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.SelectedPositionRecyclerViewAdapter;
import com.life.waimaishuo.bean.MemberPackage;
import com.life.waimaishuo.databinding.FragmentMineMemberCenterRenewBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mine.MineSuperMemberRenewViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.util.TextUtil;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.widget.statelayout.StatefulLayout;

@Page(name = "会员中心-续费", anim = CoreAnim.slide)
public class MineSuperMemberRenewFragment extends BaseFragment {

    private FragmentMineMemberCenterRenewBinding mBinding;
    private MineSuperMemberRenewViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new MineSuperMemberRenewViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentMineMemberCenterRenewBinding) mViewDataBinding;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_member_center_renew;
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

        initMemberPackage();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
    }

    private void initTitle(){
        TextUtil.setFakeBoldText(mBinding.layoutTitle.tvTitle,true);
        mBinding.layoutTitle.tvTitle.setText(R.string.members_center);
    }

    private void initMemberPackage(){
        mBinding.recyclerMembershipPackage.setLayoutManager(new GridLayoutManager(requireContext(),3, LinearLayoutManager.VERTICAL,false));
        mBinding.recyclerMembershipPackage.setAdapter(new SelectedPositionRecyclerViewAdapter<MemberPackage>(mViewModel.getMemberPackageData()){
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_recycler_mine_super_member_package;
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, boolean selected, MemberPackage item) {
                holder.setText(R.id.tv_package_value,TextUtil.getAbsoluteSpannable("￥" + item.getValue(),44,0,1));
                holder.setText(R.id.tv_package_name,item.getName());

                boolean needSetPreferential = true;
                if(item.getPreferential() == null || "".equals(item.getPreferential())){
                    needSetPreferential = false;
                    setViewVisibility(holder.getView(R.id.tv_package_preferential),false);
                }else{
                    holder.setText(R.id.tv_package_preferential,item.getPreferential());
                }

                if(selected){
                    if(needSetPreferential){
                        holder.getView(R.id.tv_package_preferential).setSelected(true);
                        holder.setTextColor(R.id.tv_package_preferential,getResources().getColor(R.color.text_normal));
                    }
                    holder.getView(R.id.cl_bg).setSelected(true);
                }else{
                    if(needSetPreferential){
                        holder.getView(R.id.tv_package_preferential).setSelected(false);
                        holder.setTextColor(R.id.tv_package_preferential,getResources().getColor(R.color.white));
                    }
                    holder.getView(R.id.cl_bg).setSelected(false);
                }
            }
        });
        mBinding.recyclerMembershipPackage.addItemDecoration(new RecyclerView.ItemDecoration() {
            int interval = (int) UIUtils.getInstance().scalePx(12);

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = interval;
            }
        });
    }
}
