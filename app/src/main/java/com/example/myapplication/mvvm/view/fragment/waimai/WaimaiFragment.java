package com.example.myapplication.mvvm.view.fragment.waimai;


import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.base.utils.UIUtils;
import com.example.myapplication.R;
import com.example.myapplication.adapter.mine.FoodTypeRecyclerAdapter;
import com.example.myapplication.databinding.FragmentWaimaiBinding;
import com.example.myapplication.mvvm.view.fragment.BaseFragment;
import com.example.myapplication.mvvm.vm.BaseViewModel;
import com.example.myapplication.mvvm.vm.waimai.WaimaiViewModel;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "外卖", anim = CoreAnim.fade)
public class WaimaiFragment extends BaseFragment {

    WaimaiViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        mViewModel = new WaimaiViewModel();
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        ((FragmentWaimaiBinding)mViewDataBinding).setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_waimai;
    }

    @Override
    protected void initViews() {
        super.initViews();
        initSlideShow();
        initFoodTypeRecycler();
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    private void initFoodTypeRecycler(){
        FragmentWaimaiBinding fragmentWaimaiBinding = ((FragmentWaimaiBinding)mViewDataBinding);

        FoodTypeRecyclerAdapter myBaseRecyclerAdapter
                = new FoodTypeRecyclerAdapter(R.layout.item_waimai_recycler_food_type,mViewModel.getMyFoodDataList()
                ,mViewModel.getFoodRecyclerViewModelList());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),5, LinearLayoutManager.VERTICAL,false);

        fragmentWaimaiBinding.recyclerFoodType.setAdapter(myBaseRecyclerAdapter);
        fragmentWaimaiBinding.recyclerFoodType.setLayoutManager(gridLayoutManager);
        fragmentWaimaiBinding.recyclerFoodType.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                if(position >= 5){
                    if(position < 10){
                        outRect.top = (int)(40* UIUtils.getInstance(getContext()).getHorValue());
                    }else{
                        outRect.top = (int)(32* UIUtils.getInstance(getContext()).getHorValue());
                    }
                }
            }
        });
    }

    private void initSlideShow(){
        FragmentWaimaiBinding fragmentWaimaiBinding = ((FragmentWaimaiBinding)mViewDataBinding);
        fragmentWaimaiBinding.sibSimpleUsage
                .setSource(mViewModel.getBannerItemList())
                .setOnItemClickListener((view,t,position)->{
                    Toast.makeText(getContext(),"点击了轮播图",Toast.LENGTH_SHORT).show();
                })
                .setIsOnePageLoop(false).startScroll();
    }

//    sib_simple_usage.setSource(mData)
//            .setOnItemClickListener((view, t, position) -> {
//    })
//            .setIsOnePageLoop(false).startScroll();
//
//        rib_simple_usage.setSource(mData).startScroll();

}
