package com.life.waimaishuo.mvvm.view.fragment.mall;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.ui.MallShopGoodGoods;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mall.MallShopViewModel;
import com.life.waimaishuo.util.TextUtil;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "新品", anim = CoreAnim.slide)
public class MallShopGoodGoodsFragment extends BaseFragment {

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
                new MyBaseRecyclerAdapter<MallShopGoodGoods>(R.layout.item_recycler_mall_shop_good_goods,
                        ((MallShopViewModel)baseViewModel).getGoodGoodsData()){
                    @Override
                    protected void initView(ViewDataBinding viewDataBinding, BaseViewHolder helper, MallShopGoodGoods item) {
                        Glide.with(requireContext())
                                .load(item.getGoodsImglist().get(0))    //取第一张图片
                                .centerCrop()
                                .placeholder(R.drawable.ic_waimai_brand)
                                .into((ImageView) helper.getView(R.id.iv_goods_img));

                        helper.setText(R.id.tv_goods_name,item.getGoodsName());

                        initGoodsSign(helper.getView(R.id.tv_interested_count), item.getInterestedCount());

                        RecyclerView recyclerView = helper.getView(R.id.recycler_goods_img);
                        if(recyclerView.getAdapter() == null){
                            recyclerView.setLayoutManager(
                                    new GridLayoutManager(requireContext(),3,
                                            LinearLayoutManager.VERTICAL,false));
                            recyclerView.setAdapter(
                                    new MyBaseRecyclerAdapter<String>(R.layout.item_recycler_mall_shop_good_goods_chiren,
                                            item.getGoodsImglist()){
                                        @Override
                                        protected void initView(ViewDataBinding viewDataBinding, BaseViewHolder helper, String item) {
                                            Glide.with(requireContext())
                                                    .load(item)
                                                    .placeholder(R.drawable.ic_waimai_brand)
                                                    .into((ImageView)helper.getView(R.id.iv));
                                        }
                                    });
                        }
                    }

                    Drawable likeDrawable;
                    // FIXME: 2021/1/16 实现
                    private void initGoodsSign(TextView textView, String count){
                        if(likeDrawable == null){
                            likeDrawable = getResources().getDrawable(R.drawable.ic_interested);
                            int size = (int) UIUtils.getInstance().scalePx(30);
                            likeDrawable.setBounds(0,0,size,size);
                        }
                        textView.setCompoundDrawables(likeDrawable,null,null,null);
                        textView.setText(
                                TextUtil.getColorSpannable(
                                        getString(R.string.guest_officer_interested, count),
                                        getResources().getColor(R.color.colorTheme),0,count.length()));
                    }
                });
    }


}
