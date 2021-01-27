package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.graphics.Rect;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.BaseBannerAdapter;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.ui.IconStrData;
import com.life.waimaishuo.databinding.FragmentWaimaiBrandStoryBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.BrandStoryViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "品牌故事", anim = CoreAnim.slide)
public class ShopBrandStoryFragment extends BaseFragment {

    private FragmentWaimaiBrandStoryBinding mBinding;
    private BrandStoryViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new BrandStoryViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentWaimaiBrandStoryBinding)mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_waimai_brand_story;
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
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

        initTitleBarView();

        initBanner();
        initRecycler();
    }

    private void initTitleBarView() {
        mBinding.layoutTitle.tvTitle.setText(getPageName());
    }

    private void initBanner(){
        BaseBannerAdapter mAdapterHorizontal
                = new BaseBannerAdapter(mViewModel.getBrandStoryPictures(),R.layout.adapter_banner_image_item_brand_story);
        mAdapterHorizontal.setOnBannerItemClickListener(position ->
                Toast.makeText(getContext(),"点击了轮播图：" + position,Toast.LENGTH_SHORT).show());
        mBinding.bannerLayout.setAdapter(mAdapterHorizontal);
    }

    private void initRecycler() {
        mBinding.recyclerBrandImg.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false));
        mBinding.recyclerBrandImg.setAdapter(new MyBaseRecyclerAdapter<IconStrData>(R.layout.item_recycler_brand_story_introduce,mViewModel.getBrandStoryPictures(), com.life.waimaishuo.BR.item));   // FIXME: 2020/12/24 需要添加bean类引用到布局文件中,动态设置内容
        mBinding.recyclerBrandImg.addItemDecoration(new RecyclerView.ItemDecoration() {
            int paddingStartAndEnd = (int) UIUtils.getInstance().scalePx(
                    getResources().getDimensionPixelSize(R.dimen.interval_size_xs));
            int padding = (int) UIUtils.getInstance().scalePx(
                    getResources().getDimensionPixelSize(R.dimen.shop_grid_recycler_item_padding));
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                if(position != 0){
                    outRect.left = padding;
                    if(position == state.getItemCount()-1){   //最后一个
                        outRect.right = paddingStartAndEnd;
                    }
                }else{
                    outRect.left = paddingStartAndEnd;
                }
            }
        });
    }
}
