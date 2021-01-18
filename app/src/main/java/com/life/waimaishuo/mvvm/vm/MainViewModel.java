package com.life.waimaishuo.mvvm.vm;

import com.life.waimaishuo.R;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.MainModel;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.view.fragment.mall.MallFragment;
import com.life.waimaishuo.mvvm.view.fragment.mine.MineFragment;
import com.life.waimaishuo.mvvm.view.fragment.order.OrderFragment;
import com.life.waimaishuo.mvvm.view.fragment.waimai.WaimaiFragment;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends BaseViewModel {

    List<String> pageList = new ArrayList<>();

    private MainModel mModel;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new MainModel();
        }
        return mModel;
    }

    @Override
    public void initData() {
        pageList.add("外卖");
        pageList.add("商城");
        pageList.add("订单");
        pageList.add("我的");
    }


    public String[] getTabData(){
        return pageList.toArray(new String[]{});
    }

    public List<String> getTabDataList(){
        return pageList;
    }

    public List<BaseFragment> getTabFragment() {
        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(new WaimaiFragment());
        fragmentList.add(new MallFragment());
        fragmentList.add(new OrderFragment());
        fragmentList.add(new MineFragment());

        return fragmentList;
    }

    public int[] getTabIcons() {
        return new int[]{R.drawable.ic_main_tabbar_waimai_unselect
                ,R.drawable.ic_main_tabbar_shop_unselect
                ,R.drawable.ic_main_tabbar_order_unselect
                ,R.drawable.ic_main_tabbar_mine_unselect};
    }

    public int[] getTabIconsSelected() {
        return new int[]{R.drawable.ic_main_tabbar_waimai_select
                ,R.drawable.ic_main_tabbar_shop_unselect
                ,R.drawable.ic_main_tabbar_order_unselect
                ,R.drawable.ic_main_tabbar_mine_unselect};
    }

}
