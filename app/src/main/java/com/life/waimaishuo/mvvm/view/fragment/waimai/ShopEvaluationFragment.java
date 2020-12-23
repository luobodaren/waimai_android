package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.graphics.Canvas;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.BR;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.CommentGoodsTagAdapter;
import com.life.waimaishuo.adapter.CommentTypeTagAdapter;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.Comment;
import com.life.waimaishuo.databinding.FragmentShopEvaluationBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.ShopEvaluationViewModel;
import com.life.waimaishuo.util.MyDataBindingUtil;
import com.life.waimaishuo.views.ScoreView;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.adapter.recyclerview.GridDividerItemDecoration;
import com.xuexiang.xui.widget.flowlayout.FlowTagLayout;

@Page(name = "评价", anim = CoreAnim.fade)
public class ShopEvaluationFragment extends BaseFragment {

    ShopEvaluationViewModel mViewModel;
    FragmentShopEvaluationBinding mBinding;

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new ShopEvaluationViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentShopEvaluationBinding)mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shop_evaluation;
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

        initScoreView();
        initFlowCommentTypeLayout();
        initCommentRecycler();

        //网络请求数据
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        addCallBack();
    }

    private void initScoreView() {
        mBinding.layoutFiveStar.hideFans();
        mBinding.layoutFiveStar.hideTitle();
        mBinding.layoutFiveStar.setScore(5);
    }

    private void addCallBack(){
        MyDataBindingUtil.addCallBack(this, mViewModel.refreshScoreStar, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                // TODO: 2020/12/23 刷新评分星星
            }
        });
    }

    private void initFlowCommentTypeLayout() {
        FlowTagLayout flowTagLayout = mBinding.flowLayoutCommentsType;
        CommentTypeTagAdapter tagAdapter = new CommentTypeTagAdapter(getContext());
        tagAdapter.addTags(mViewModel.getCommentsType());

        flowTagLayout.setAdapter(tagAdapter);
        flowTagLayout.setOnTagClickListener((parent, view, position) ->
                Toast.makeText(getContext(), "点击了：" + parent.getAdapter().getItem(position),
                        Toast.LENGTH_SHORT).show());

    }

    private void initCommentRecycler() {
        mBinding.recyclerComments.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        mBinding.recyclerComments.setAdapter(new MyBaseRecyclerAdapter<Comment>(R.layout.item_recycler_shop_comment,mViewModel.getCommentsData(), com.life.waimaishuo.BR.item){
            @Override
            protected void initView(BaseViewHolder helper, Comment item) {
                super.initView(helper, item);
                ScoreView scoreView = helper.getView(R.id.score_view);
                scoreView.hideTitle();
                scoreView.hideFans();
                scoreView.setScore(Integer.parseInt(item.getScore()));

                RecyclerView recyclerView = helper.getView(R.id.recycler_comment_picture);
                recyclerView.setLayoutManager(new GridLayoutManager(requireContext(),3));
                recyclerView.setAdapter(new MyBaseRecyclerAdapter<String>(R.layout.item_recycler_shop_commen_picture,item.getCommentPictureList(), com.life.waimaishuo.BR.item));
                recyclerView.addItemDecoration(new GridDividerItemDecoration(getContext(), 3,
                        (int)UIUtils.getInstance(requireContext()).scalePx(16)));

                FlowTagLayout flowTagLayout = helper.getView(R.id.flow_layout_goods_list);
                CommentGoodsTagAdapter tagAdapter = new CommentGoodsTagAdapter(getContext());
                tagAdapter.addTags(item.getGoodsList());

                flowTagLayout.setAdapter(tagAdapter);
                flowTagLayout.setOnTagClickListener((parent, view, position) ->
                        Toast.makeText(getContext(), "点击了：" + parent.getAdapter().getItem(position),
                                        Toast.LENGTH_SHORT).show());

            }
        });

    }

}
