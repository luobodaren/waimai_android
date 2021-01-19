package com.life.waimaishuo.mvvm.view.fragment.mall;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.BR;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.ui.MallNewArrival;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mall.MallShopViewModel;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "新品", anim = CoreAnim.slide)
public class MallShopNewArrivalFragment extends BaseFragment {

    private RecyclerView recyclerView;

    @Override
    protected BaseViewModel setViewModel() {
        return baseViewModel;
    }

    @Override
    protected void bindViewModel() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_recycler;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();
        initRecycler();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

    }

    private void initRecycler(){
        int interval = (int) UIUtils.getInstance().scalePx(getResources().getDimensionPixelSize(R.dimen.interval_size_xs));

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if(parent.getChildAdapterPosition(view) != 0){
                    outRect.top = interval;
                }
            }
        });
        recyclerView.setAdapter(
                new MyBaseRecyclerAdapter<MallNewArrival>(R.layout.item_recycler_mall_shop_new_arrival,
                        ((MallShopViewModel)baseViewModel).getNewArrivalData(), com.life.waimaishuo.BR.item){
                    @Override
                    protected void initView(BaseViewHolder helper, MallNewArrival item) {
                        super.initView(helper, item);

                        RecyclerView recyclerView = helper.getView(R.id.recycler_new_arrival_goods);
                        recyclerView.setLayoutManager(
                                new GridLayoutManager(requireContext(),3,
                                        LinearLayoutManager.VERTICAL,false));
                        recyclerView.setAdapter(
                                new MyBaseRecyclerAdapter(R.layout.item_recycler_mall_shop_recommend_goods,
                                        item.getGoodsList(),BR.item));
                    }
                });
    }


}
