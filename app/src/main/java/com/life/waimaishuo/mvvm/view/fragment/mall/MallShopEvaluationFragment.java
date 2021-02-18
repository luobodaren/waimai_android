package com.life.waimaishuo.mvvm.view.fragment.mall;

import android.graphics.Rect;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.Comment;
import com.life.waimaishuo.databinding.FragmentBaseTagRecyclerBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseTabSegmentRecyclerFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mall.MallShopViewModel;
import com.life.waimaishuo.util.PreViewUtil;
import com.life.waimaishuo.views.MyTabSegmentTab;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.adapter.recyclerview.GridDividerItemDecoration;
import com.xuexiang.xui.widget.tabbar.TabSegment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

@Page(name = "商城-店铺评价")
public class MallShopEvaluationFragment extends BaseTabSegmentRecyclerFragment {

    @Override
    protected void initListeners() {
        super.initListeners();

        FragmentBaseTagRecyclerBinding mBinding = (FragmentBaseTagRecyclerBinding)mViewDataBinding;
        mBinding.tabSegment.setOnTabClickListener(new TabSegment.OnTabClickListener() {
            @Override
            public void onTabClick(int index) {
                Toast.makeText(requireContext(), mBinding.tabSegment.getTab(index).getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_recycler_mall_shop_comment;
    }

    @Override
    protected RecyclerView.LayoutManager getRecyclerLayoutManager() {
        return new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
    }

    @Override
    protected List getListData() {
        return ((MallShopViewModel)baseViewModel).getEvaluation();    // FIXME: 2020/12/29 修改获取数据的方式
    }

    @Override
    protected int getVariableId() {
        return com.life.waimaishuo.BR.item;
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new RecyclerView.ItemDecoration() {
            int interval = (int) UIUtils.getInstance().scalePx(getResources().getDimensionPixelOffset(R.dimen.interval_size_xs));
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = interval;
            }
        };
    }

    @Override
    protected void onRecyclerBindViewHolder(ViewDataBinding viewDataBinding, BaseViewHolder helper, Object item) {
        RecyclerView recyclerView = helper.getView(R.id.recycler_comment_picture);
        MyBaseRecyclerAdapter adapter = new MyBaseRecyclerAdapter<String>(R.layout.item_recycler_shop_picture,((Comment)item).getCommentPictureList(), com.life.waimaishuo.BR.item);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(),3,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new GridDividerItemDecoration(requireContext(), 3,
                (int)UIUtils.getInstance().scalePx(
                        getResources().getDimensionPixelSize(R.dimen.shop_grid_recycler_item_padding))));
        PreViewUtil.initRecyclerPictureClickListener(this,adapter,gridLayoutManager);    //添加图片点击监听 看大图
    }

    @Override
    protected BaseViewModel setViewModel() {
        return baseViewModel;
    }

    @Override
    public void initTabSegment() {
        int space = getResources().getDimensionPixelOffset(R.dimen.goods_comment_tabSegment_item_space);
        List<String> commentsType = ((MallShopViewModel)baseViewModel).getCommentsType();

        FragmentBaseTagRecyclerBinding mBinding = (FragmentBaseTagRecyclerBinding)mViewDataBinding;
        mBinding.tabSegment.setItemSpaceInScrollMode(space);
        mBinding.tabSegment.setTabTextSize(getResources().getDimensionPixelSize(R.dimen.goods_comment_tabSegment_item_text_size));
        addTab(mBinding.tabSegment, commentsType);

    }

    private void addTab(TabSegment tabSegment,
                        List<String> titles) {
        Iterator<String> stringIterator = titles.iterator();
        while (stringIterator.hasNext()) {
            String s = stringIterator.next();
            MyTabSegmentTab tab = new MyTabSegmentTab(s);
            tab.setTextSize(getResources().getDimensionPixelSize(R.dimen.waimai_shop_tabbar_item_text_size_selected));
            tabSegment.addTab(tab);
        }
        tabSegment.notifyDataChanged();
        TabSegment.TabAdapter adapter = invokeGetAdapted(tabSegment);
        if(adapter != null){
            int size = adapter.getViews().size();
            for (int i = 0; i < size; i++) {
                TextView textView = adapter.getViews().get(i).getTextView();
                textView.setBackgroundResource(R.drawable.sr_bg_10radius_white);
                textView.setPadding(24,16,24,16);
            }
        }else{
            LogUtil.e("反射getAdapter方法失败");
        }
    }

    /**
     * 获取并调用私有方法
     */
    private TabSegment.TabAdapter invokeGetAdapted(TabSegment tabSegment) {
        try {
            // 获取方法名为showName，参数为String类型的方法
            Class<TabSegment> cls = (Class<TabSegment>)tabSegment.getClass();
            Method method = cls.getDeclaredMethod("getAdapter", null);
            // 若调用私有方法，必须抑制java对权限的检查
            method.setAccessible(true);
            // 使用invoke调用方法，并且获取方法的返回值，需要传入一个方法所在类的对象，new Object[]
            // {"Kai"}是需要传入的参数，与上面的String.class相对应
            TabSegment.TabAdapter adapter = (TabSegment.TabAdapter) method.invoke(tabSegment, null);
            return adapter;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


}
