package com.life.waimaishuo.mvvm.view.fragment.mine;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.SparseIntArray;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.RedPacket;
import com.life.waimaishuo.databinding.FragmentMineRedPacketBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mine.MineRedPacketViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "红包", anim = CoreAnim.slide)
public class MineRedPacketFragment extends BaseFragment {

    private FragmentMineRedPacketBinding mBinding;
    private MineRedPacketViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new MineRedPacketViewModel();
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
    }

    private void initTitle() {
        mBinding.layoutTitle.tvTitle.setText(getPageName());
        mBinding.layoutTitle.tvRight.setText(R.string.record_of_use);
    }

    private void initRedPackageRecycler(){
        int[] viewTypes = {1, 2};//1：正常店铺商品 2：失效的素有商品
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

                if(helper.getItemViewType() == viewTypes[0]){
                    ((ImageView)helper.getView(R.id.iv_red_packet_img)).clearColorFilter();
                }else{
                    ((ImageView)helper.getView(R.id.iv_red_packet_img)).setColorFilter(filter);
                }
            }

        };
        SparseIntArray layouts = new SparseIntArray();
        layouts.put(viewTypes[0],R.layout.item_recycler_mine_red_packet);
        layouts.put(viewTypes[1],R.layout.item_recycler_mine_red_packet_noneffective);
        adapter.setMultiTypeDelegate(new MultiTypeDelegate<RedPacket>(layouts) {
            @Override
            protected int getItemType(RedPacket item) {
                if(item.isEffective()){
                    return viewTypes[0];
                }else{
                    return viewTypes[1];
                }
            }
        });
        mBinding.recyclerRedPacket.setAdapter(adapter);
    }
}
