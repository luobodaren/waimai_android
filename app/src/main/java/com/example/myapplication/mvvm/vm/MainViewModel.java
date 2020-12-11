package com.example.myapplication.mvvm.vm;

import com.example.myapplication.R;
import com.example.myapplication.mvvm.model.BaseModel;
import com.example.myapplication.mvvm.model.MainModel;
import com.example.myapplication.mvvm.view.fragment.BaseFragment;
import com.example.myapplication.mvvm.view.fragment.mine.MineFragment;
import com.example.myapplication.mvvm.view.fragment.order.OrderFragment;
import com.example.myapplication.mvvm.view.fragment.waimai.WaimaiFragment;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends BaseViewModel {

    String[] pages = {"外卖","商场","订单","我的"};

    @Override
    public BaseModel getModel() {
        return new MainModel();
    }

    @Override
    public void initData() {

    }


    public String[] getTabDatas(){
        return pages;
    }

    public List<BaseFragment> getTabFragment() {
        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(new WaimaiFragment());
        fragmentList.add(new WaimaiFragment());
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
