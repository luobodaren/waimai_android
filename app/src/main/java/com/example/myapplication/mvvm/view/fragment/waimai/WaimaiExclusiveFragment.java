package com.example.myapplication.mvvm.view.fragment.waimai;

import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myapplication.R;
import com.example.myapplication.adapter.MyBaseRecyclerAdapter;
import com.example.myapplication.bean.Goods;
import com.example.myapplication.databinding.FragmentExclusiveBinding;
import com.example.myapplication.mvvm.view.fragment.BaseFragment;
import com.example.myapplication.mvvm.vm.BaseViewModel;
import com.example.myapplication.mvvm.vm.waimai.WaimaiExclusiveViewModel;
import com.example.myapplication.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.widget.imageview.RadiusImageView;

@Page(name = "专属早餐",anim = CoreAnim.slide)
public class WaimaiExclusiveFragment extends BaseFragment {

    WaimaiExclusiveViewModel viewModel;

    @Override
    protected BaseViewModel setViewModel() {
        viewModel = new WaimaiExclusiveViewModel();
        return viewModel;
    }

    @Override
    protected void bindViewModel() {
        ((FragmentExclusiveBinding)mViewDataBinding).setViewModel(viewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_exclusive;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setmStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_DARK);
        setFitStatusBarHeight(true);
    }

    @Override
    protected TitleBar initTitleBar() {
        TitleBar titleBar = super.initTitleBar();

        titleBar.setLeftImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_left_black));

        titleBar.setBackgroundColor(getResources().getColor(R.color.white));
        titleBar.getCenterText().setTextSize(TypedValue.COMPLEX_UNIT_PX,36);
        titleBar.setCenterTextBold(true);
        titleBar.setTitleColor(getResources().getColor(R.color.white));
        titleBar.addAction(new TitleBar.ImageAction(R.drawable.ic_share) {
            @Override
            public void performAction(View view) {
                Toast.makeText(getContext(),"分享···",Toast.LENGTH_SHORT).show();
            }
        });
        return titleBar;
    }

    @Override
    protected void initViews() {
        super.initViews();
        initExclusiveRecycler();
    }

    @Override
    protected void onLifecycleResume() {
        super.onLifecycleResume();
        setStatusBarMode();
    }

    private void initExclusiveRecycler() {
        RecyclerView recyclerView = ((FragmentExclusiveBinding)mViewDataBinding).recyclerExclusive;
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
                2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new MyBaseRecyclerAdapter<Goods>(R.layout.item_exclusive_goods_info,viewModel.getmBreakFastList()) {
            @Override
            protected void initView(BaseViewHolder helper, Goods item) {
                helper.setText(R.id.tv_goods_name,item.getName());
                RadiusImageView radiusImageView;
                radiusImageView.
                RequestOptions options = new RequestOptions().error(R.drawable.ic_waimai_brand).bitmapTransform(new RoundedCorners(30));
                Glide.with(WaimaiExclusiveFragment.this)
                        .load(item.getGoodsImgUrl())
                        .centerCrop()
                        .placeholder(R.drawable.ic_waimai_brand)
                        .op
                        .into((ImageView) helper.getView(R.id.iv_goods_img));
            }
        });
    }
}
