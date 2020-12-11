package com.example.myapplication.mvvm.view.fragment.waimai;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.base.utils.UIUtils;
import com.example.myapplication.R;
import com.example.myapplication.adapter.SelectedPositionRecylerViewAdapter;
import com.example.myapplication.bean.ui.IconStrData;
import com.example.myapplication.databinding.FragmentWaimaiTypeBinding;
import com.example.myapplication.mvvm.view.fragment.BaseFragment;
import com.example.myapplication.mvvm.vm.BaseViewModel;
import com.example.myapplication.mvvm.vm.waimai.WaiMaiTypeViewModel;
import com.example.myapplication.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.widget.flowlayout.FlowTagLayout;

@Page(name = "外卖子类型页",anim = CoreAnim.slide)
public class WaimaiTypeFragment extends BaseFragment {

    public static final String BUNDLE_FOOD_TYPE_STR_KEY = "my_food_type";
    private static String mFoodType;

    private WaiMaiTypeViewModel mViewModel;
    private FragmentWaimaiTypeBinding mBinding;

    SelectedPositionRecylerViewAdapter<IconStrData> adapter;

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new WaiMaiTypeViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentWaimaiTypeBinding) mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_waimai_type;
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        mFoodType = getArguments().getString(BUNDLE_FOOD_TYPE_STR_KEY);

        setFitStatusBarHeight(true);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_DARK);
        setStatusBarShowByType(SHOW_STATUS_BAR);
    }

    @Override
    protected void onFragmentShow() {
        super.onFragmentShow();
    }

    @Override
    protected void initViews() {
        super.initViews();

        initSubtypeRecycler();
        initFlowLayout();
        initShopContent();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        adapter.setmSelectedListener((holder, item) -> {
            refreshSortType(item.getIconType());
            refreshShopContent();
        });
    }

    private void initSubtypeRecycler(){
        mBinding.recyclerFoodSubtype.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        adapter = getSubtypeRecyclerAdapter();

        mBinding.recyclerFoodSubtype.setAdapter(adapter);
        mBinding.recyclerFoodSubtype.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent
                    , @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = (int)(40* UIUtils.getInstance(getContext()).getHorValue());
            }
        });

    }

    private SelectedPositionRecylerViewAdapter<IconStrData> getSubtypeRecyclerAdapter() {
        return new SelectedPositionRecylerViewAdapter<IconStrData>(mViewModel.getSubtypeTitles()){
            @Override
            public int getLayoutId() {
                return R.layout.item_waimai_food_subtype;
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, boolean selected, IconStrData item) {
                holder.setImageResource(R.id.iv_subtype_icon,item.getResImgId());
                holder.setText(R.id.tv_subtype_name,item.getIconType());
                if(selected){
                    holder.setBackgroundRes(R.id.tv_subtype_name,R.drawable.sr_bg_full_corners_white);
                    holder.setTextColor(R.id.tv_subtype_name,getResources().getColor(R.color.colorTheme));
                }else{
                    holder.setBackgroundColor(R.id.tv_subtype_name,getResources().getColor(R.color.transparent));
                    holder.setTextColor(R.id.tv_subtype_name,getResources().getColor(R.color.white));
                }
            }
        };
    }

    private void refreshShopContent() {

    }


    private void refreshSortType(String typeName){

    }

    private void initFlowLayout() {
//        FlowTagAdapter tagAdapter = new FlowTagAdapter(getContext());
//        mMultiFlowTagLayout.setAdapter(tagAdapter);
//        mMultiFlowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
//        mMultiFlowTagLayout.setOnTagSelectListener((parent, position, selectedList) -> XToastUtils.toast(getSelectedText(parent, selectedList)));
//        tagAdapter.addTags(ResUtils.getStringArray(R.array.tags_values));
//        tagAdapter.setSelectedPositions(2, 3, 4);

//        mMultiFlowTagLayout.setItems("1111", "2222", "3333", "4444");
//        mMultiFlowTagLayout.setSelectedItems("3333", "4444");
    }

    private void initShopContent() {
        FragmentManager fm= getChildFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();

        ft.add(R.id.adaptive_size_view,mViewModel.getRecommendedFragment(),null);
        ft.commit();
    }

}
