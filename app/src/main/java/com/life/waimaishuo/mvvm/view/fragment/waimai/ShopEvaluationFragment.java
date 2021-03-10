package com.life.waimaishuo.mvvm.view.fragment.waimai;

import androidx.databinding.Observable;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.tag.CommentTypeTagAdapter;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.Comment;
import com.life.waimaishuo.bean.ui.ImageViewInfo;
import com.life.waimaishuo.databinding.FragmentWaimaiShopEvaluationBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.ShopEvaluationViewModel;
import com.life.waimaishuo.util.MyDataBindingUtil;
import com.life.waimaishuo.util.PreViewUtil;
import com.life.waimaishuo.util.Utils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.adapter.recyclerview.GridDividerItemDecoration;
import com.xuexiang.xui.widget.flowlayout.FlowTagLayout;

import java.util.ArrayList;
import java.util.List;

@Page(name = "外卖店铺评价", anim = CoreAnim.fade)
public class ShopEvaluationFragment extends BaseFragment {

    private ShopEvaluationViewModel mViewModel;
    private FragmentWaimaiShopEvaluationBinding mBinding;

    private int shopId;

    private MyBaseRecyclerAdapter<Comment> commentsRecyclerAdapter;  //评价RecyclerView 适配器

    boolean isShowStatEvaluation = true;    //是否显示综合评分区域

    @Override
    protected BaseViewModel setViewModel() {
        if (mViewModel == null) {
            mViewModel = new ShopEvaluationViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentWaimaiShopEvaluationBinding) mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_waimai_shop_evaluation;
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

        if(isShowStatEvaluation){
            setViewVisibility(mBinding.llStatEvaluation,true);
        }else {
            setViewVisibility(mBinding.llStatEvaluation,false);
        }

        initScoreView();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        addCallBack();
    }

    @Override
    protected void firstRequestData() {
        super.firstRequestData();
        if(isShowStatEvaluation){
            mViewModel.requestStatEvaluate(shopId); //请求综合评价
        }
        mViewModel.requestShopEvaluate(shopId, 1, 10, 1);   //默认选择有图
    }

    private void initScoreView() {
        mBinding.layoutFiveStar.hideFans();
        mBinding.layoutFiveStar.hideTitle();
        mBinding.layoutFiveStar.setScore(0);
    }

    private void addCallBack() {
        MyDataBindingUtil.addCallBack(this, mViewModel.refreshScoreStar, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (mViewModel.getShopStatEvaluate() != null) {
                    float score = Float.valueOf(mViewModel.getShopStatEvaluate().getAllGrade());    //取整 有余数则结果加一
                    int scoreInt = (int) score;
                    if (scoreInt < score) {
                        scoreInt += 1;
                    }
                    LogUtil.d("score:" + scoreInt);
                    mBinding.layoutFiveStar.setScore(scoreInt);
                }
            }
        });

        MyDataBindingUtil.addCallBack(this, mViewModel.onRequestShopEvaluation, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.post(() -> {
                    if(commentsRecyclerAdapter == null){
                        initFlowCommentTypeLayout();
                        initCommentRecycler();
                    }else{
                        commentsRecyclerAdapter.setNewData(mViewModel.getCommentsData());
                        commentsRecyclerAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void initFlowCommentTypeLayout() {
        FlowTagLayout flowTagLayout = mBinding.flowLayoutCommentsType;
        flowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        flowTagLayout.setOnTagSelectListener((parent, position, selectedList) ->
                mViewModel.requestShopEvaluate(shopId,1,10,position));

        CommentTypeTagAdapter tagAdapter = new CommentTypeTagAdapter(getContext());
        tagAdapter.setSelectedPositions(0);

        flowTagLayout.setAdapter(tagAdapter);
        tagAdapter.addTags(mViewModel.getCommentsType());
    }

    private void initCommentRecycler() {

        commentsRecyclerAdapter = new MyBaseRecyclerAdapter<Comment>(R.layout.item_recycler_waimai_shop_comment, mViewModel.getCommentsData(), com.life.waimaishuo.BR.item) {
            @Override
            protected void initView(ViewDataBinding viewDataBinding, BaseViewHolder helper, Comment item) {
//                ScoreView scoreView = helper.getView(R.id.score_view);
//                scoreView.hideTitle();
//                scoreView.hideFans();
//                scoreView.setScore(Integer.parseInt(item.getScore()));

                RecyclerView recyclerView = helper.getView(R.id.recycler_comment_picture);
                if (recyclerView.getAdapter() == null) {
                    List<ImageViewInfo> goodsPicture = new ArrayList<>();
                    for (String string:item.getEvaluatePhotoList()) {
                        goodsPicture.add(new ImageViewInfo(string));
                    }
                    MyBaseRecyclerAdapter adapter = new MyBaseRecyclerAdapter<String>(R.layout.item_recycler_shop_picture, goodsPicture, com.life.waimaishuo.BR.item);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 3, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setAdapter(adapter);
                    recyclerView.addItemDecoration(new GridDividerItemDecoration(requireContext(), 3,
                            (int) UIUtils.getInstance().scalePx(
                                    getResources().getDimensionPixelSize(R.dimen.shop_grid_recycler_item_padding))));
                    PreViewUtil.initRecyclerPictureClickListener(ShopEvaluationFragment.this, adapter, gridLayoutManager);    //添加图片点击监听 看大图
                }

                /*FlowTagLayout flowTagLayout = helper.getView(R.id.flow_layout_goods_list);
                if(flowTagLayout.getAdapter() == null){
                    CommentGoodsTagAdapter tagAdapter = new CommentGoodsTagAdapter(getContext());
                    flowTagLayout.setAdapter(tagAdapter);
                    flowTagLayout.setOnTagClickListener((parent, view, position) ->
                            Toast.makeText(getContext(), "点击了：" + parent.getAdapter().getItem(position),
                                    Toast.LENGTH_SHORT).show());
                    tagAdapter.addTags(item.setGoodsSpecsIds(););
                }*/

                LogUtil.d(helper.getAdapterPosition() + "");
                if (helper.getAdapterPosition() == mViewModel.getCommentsData().size() - 1) {   //最后一项添加占位view 为底下腾出空间
                    helper.setVisible(R.id.space, true);
                }

            }
        };
        mBinding.recyclerComments.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        mBinding.recyclerComments.setAdapter(commentsRecyclerAdapter);
        mBinding.recyclerComments.addItemDecoration(Utils.getDefaultItemDecoration(requireContext()));

    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public void setShowStatEvaluation(boolean showStatEvaluation) {
        isShowStatEvaluation = showStatEvaluation;
    }
}
