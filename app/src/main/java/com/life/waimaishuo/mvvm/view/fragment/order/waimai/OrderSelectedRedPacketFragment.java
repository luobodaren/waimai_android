package com.life.waimaishuo.mvvm.view.fragment.order.waimai;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.BR;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.SelectedPositionRecyclerViewAdapter;
import com.life.waimaishuo.bean.RedPacket;
import com.life.waimaishuo.databinding.FragmentChoseRedPacketBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.OrderSelectedRedPacketViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "选择红包", anim = CoreAnim.slide)
public class OrderSelectedRedPacketFragment extends BaseFragment {

    FragmentChoseRedPacketBinding mBinding;
    OrderSelectedRedPacketViewModel mViewModel;

    public static final String RESULT_KEY_RED_PACKET_ID = "red_packet_id";

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new OrderSelectedRedPacketViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentChoseRedPacketBinding)mViewDataBinding;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chose_red_packet;
    }

    @Override
    protected TitleBar initTitleBar() {
        TitleBar titleBar = super.initTitleBar();
        titleBar.setHeight((int) UIUtils.getInstance().scalePx(getResources().getDimensionPixelSize(R.dimen.titlebar_height)));

        int titleBarDrawableSizes = (int) UIUtils.getInstance().scalePx(R.dimen.titlebar_drawable_size);
        Drawable leftDrawable = getResources().getDrawable(R.drawable.ic_arrow_left_black);
        leftDrawable.setBounds(0,0,titleBarDrawableSizes,titleBarDrawableSizes);
        titleBar.setLeftImageDrawable(leftDrawable);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popToBackWithResult();
            }
        });
        titleBar.setCenterTextBold(true);
        ImageView imageView = (ImageView) titleBar.addAction(new TitleBar.ImageAction(R.drawable.ic_menu) {
            @Override
            public void performAction(View view) {
                Toast.makeText(requireContext(), "点击了菜单", Toast.LENGTH_SHORT).show();
            }
        });
        return titleBar;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setFitStatusBarHeight(true);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_LIGHT);
    }

    @Override
    protected void initViews() {
        super.initViews();

        initRecyclerView();
    }

    SelectedPositionRecyclerViewAdapter<RedPacket> adapter;
    private void initRecyclerView() {
        adapter = new SelectedPositionRecyclerViewAdapter<RedPacket>(mViewModel.getRedPacketData()) {
            int viewType_usable = 1;
            int viewType_disable = 2;

            @Override
            public int getLayoutId(int viewType) {
                if(viewType == viewType_usable){
                    return R.layout.item_recycler_red_packet;
                }else{
                    return R.layout.item_recycler_red_packet_disable;
                }
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, boolean selected, RedPacket item) {
                ViewDataBinding binding = DataBindingUtil.bind(holder.itemView);
                if(binding != null){
                    binding.setVariable(BR.item,item);
                    ImageView ivCheckBottom = holder.getView(R.id.iv_check_bottom);
                    if(item.isGet()){
                        if(ivCheckBottom.getVisibility() == View.GONE){
                            ivCheckBottom.setVisibility(View.VISIBLE);
                        }
                        if(selected){
                            ivCheckBottom.setImageResource(R.drawable.ic_check_round_fill_red);
                        }else{
                            ivCheckBottom.setImageDrawable(null);
                        }
                    }else{
                        if(ivCheckBottom.getVisibility() == View.VISIBLE){
                            ivCheckBottom.setVisibility(View.GONE);
                        }
                    }
                }else{
                    LogUtil.e("绑定布局失败");
                }
            }

            @Override
            public int getItemViewType(int position) {
                if(getData().get(position).isGet()){
                    return viewType_usable;
                }else{
                    return viewType_disable;
                }
            }
        };
        adapter.setSelectedListener(new SelectedPositionRecyclerViewAdapter.OnSelectedListener<RedPacket>() {
            @Override
            public void onSelectedClick(BaseViewHolder holder, RedPacket item) {
                if(item.isGet()){
                    popToBackWithResult();
                }else{
                    LogUtil.d("点击了没有获得的会员卡");
                }
            }
        });
        mBinding.recyclerRedPacket.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        mBinding.recyclerRedPacket.setAdapter(adapter);
        mBinding.recyclerRedPacket.addItemDecoration(new RecyclerView.ItemDecoration() {
            int space = (int) UIUtils.getInstance().scalePx(
                    getResources().getDimensionPixelSize(R.dimen.interval_size_xs));
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = space;
            }
        });
    }

    private RedPacket getSelectedPacket() {
        return adapter.getData().get(adapter.getSelectedPosition());
    }

    private void popToBackWithResult(){
        Intent intent = new Intent();
        intent.putExtra(RESULT_KEY_RED_PACKET_ID,getSelectedPacket());
        setFragmentResult(RESULT_CODE_SUCCESS,intent);
        popToBack();
    }

}
