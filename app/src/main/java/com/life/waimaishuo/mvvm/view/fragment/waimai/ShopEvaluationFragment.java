package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.graphics.Rect;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.tagAdapter.CommentGoodsTagAdapter;
import com.life.waimaishuo.adapter.tagAdapter.CommentTypeTagAdapter;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.Comment;
import com.life.waimaishuo.databinding.FragmentWaimaiShopEvaluationBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.ShopEvaluationViewModel;
import com.life.waimaishuo.util.MyDataBindingUtil;
import com.life.waimaishuo.util.PreViewUtil;
import com.life.waimaishuo.views.widget.ScoreView;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.adapter.recyclerview.GridDividerItemDecoration;
import com.xuexiang.xui.widget.flowlayout.FlowTagLayout;

@Page(name = "外卖店铺评价", anim = CoreAnim.fade)
public class ShopEvaluationFragment extends BaseFragment {

    ShopEvaluationViewModel mViewModel;
    FragmentWaimaiShopEvaluationBinding mBinding;

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new ShopEvaluationViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentWaimaiShopEvaluationBinding)mViewDataBinding;
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

        flowTagLayout.setAdapter(tagAdapter);
        flowTagLayout.setOnTagClickListener((parent, view, position) ->
                Toast.makeText(getContext(), "点击了：" + parent.getAdapter().getItem(position),
                        Toast.LENGTH_SHORT).show());

        tagAdapter.addTags(mViewModel.getCommentsType());

    }

    private void initCommentRecycler() {
        mBinding.recyclerComments.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        mBinding.recyclerComments.setAdapter(new MyBaseRecyclerAdapter<Comment>(R.layout.item_recycler_waimai_shop_comment,mViewModel.getCommentsData(), com.life.waimaishuo.BR.item){
            @Override
            protected void initView(BaseViewHolder helper, Comment item) {
                super.initView(helper, item);
//                ScoreView scoreView = helper.getView(R.id.score_view);
//                scoreView.hideTitle();
//                scoreView.hideFans();
//                scoreView.setScore(Integer.parseInt(item.getScore()));

                RecyclerView recyclerView = helper.getView(R.id.recycler_comment_picture);
                MyBaseRecyclerAdapter adapter = new MyBaseRecyclerAdapter<String>(R.layout.item_recycler_shop_picture,item.getCommentPictureList(), com.life.waimaishuo.BR.item);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(),3,LinearLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setAdapter(adapter);
                recyclerView.addItemDecoration(new GridDividerItemDecoration(requireContext(), 3,
                        (int)UIUtils.getInstance().scalePx(
                                getResources().getDimensionPixelSize(R.dimen.shop_grid_recycler_item_padding))));
                PreViewUtil.initRecyclerPictureClickListener(ShopEvaluationFragment.this,adapter,gridLayoutManager);    //添加图片点击监听 看大图


                FlowTagLayout flowTagLayout = helper.getView(R.id.flow_layout_goods_list);
                CommentGoodsTagAdapter tagAdapter = new CommentGoodsTagAdapter(getContext());

                flowTagLayout.setAdapter(tagAdapter);
                flowTagLayout.setOnTagClickListener((parent, view, position) ->
                        Toast.makeText(getContext(), "点击了：" + parent.getAdapter().getItem(position),
                                        Toast.LENGTH_SHORT).show());
                tagAdapter.addTags(item.getGoodsList());

                LogUtil.d(helper.getAdapterPosition() + "");
                if(helper.getAdapterPosition() == mViewModel.getCommentsData().size()-1){   //最后一项添加占位view 为底下腾出空间
                    helper.setVisible(R.id.space,true);
                }

            }
        });
        mBinding.recyclerComments.addItemDecoration(new RecyclerView.ItemDecoration() {
            int space = (int) UIUtils.getInstance().scalePx(getResources().getDimensionPixelSize(R.dimen.interval_size_xs));
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = space;
                if(parent.getChildAdapterPosition(view) == state.getItemCount()-1){   //最后一个
                    outRect.bottom = space;
                }
            }
        });

    }

}
