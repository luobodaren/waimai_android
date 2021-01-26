package com.life.waimaishuo.mvvm.view.fragment.mine;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.life.waimaishuo.BR;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.RedPacket;
import com.life.waimaishuo.databinding.FragmentMineRedPacketBinding;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mine.MineRedPackageViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "红包", anim = CoreAnim.slide)
public class MineRedPackageFragment extends BaseFragment {

    private FragmentMineRedPacketBinding mBinding;
    private MineRedPackageViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new MineRedPackageViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentMineRedPacketBinding) mViewDataBinding;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_red_packet;
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

        initRedPackageRecycler();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        mBinding.layoutTitle.ivBack.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                popToBack();
            }
        });
    }

    private void initTitle() {
        mBinding.layoutTitle.tvTitle.setText(getPageName());
        mBinding.layoutTitle.tvRight.setText(R.string.record_of_use);
    }

    private void initRedPackageRecycler(){
        int[] mViewTypes = {1, 2};//1：正常店铺商品 2：失效的素有商品
        mBinding.recyclerRedPacket.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        MyBaseRecyclerAdapter<RedPacket> adapter = new MyBaseRecyclerAdapter<RedPacket>(R.layout.item_recycler_mine_red_packet,mViewModel.getRedPacketData(), com.life.waimaishuo.BR.item){
            ColorMatrix matrix = new ColorMatrix();
            ColorMatrixColorFilter filter = null;

            @Override
            protected void initView(BaseViewHolder helper, RedPacket item) {
                super.initView(helper, item);
                if(filter == null){
                    matrix.setSaturation(0);
                    filter = new ColorMatrixColorFilter(matrix);
                }

                if(helper.getItemViewType() == mViewTypes[0]){
                    ((ImageView)helper.getView(R.id.iv_red_package_img)).clearColorFilter();
                }else{
                    ((ImageView)helper.getView(R.id.iv_red_package_img)).setColorFilter(filter);
                }
            }

        };
        SparseIntArray layouts = new SparseIntArray();
        layouts.put(mViewTypes[0],R.layout.item_recycler_mine_red_packet);
        layouts.put(mViewTypes[1],R.layout.item_recycler_mine_red_packet_noneffective);
        adapter.setMultiTypeDelegate(new MultiTypeDelegate<RedPacket>(layouts) {
            @Override
            protected int getItemType(RedPacket item) {
                if(item.isEffective()){
                    return mViewTypes[0];
                }else{
                    return mViewTypes[1];
                }
            }
        });
        mBinding.recyclerRedPacket.setAdapter(adapter);
    }
}
