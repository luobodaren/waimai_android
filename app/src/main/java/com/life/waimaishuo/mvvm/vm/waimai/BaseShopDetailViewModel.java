package com.life.waimaishuo.mvvm.vm.waimai;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableInt;

import com.life.waimaishuo.mvvm.vm.BaseViewModel;

public abstract class BaseShopDetailViewModel extends BaseViewModel {

    public BaseObservable onShowShoppingCart = new ObservableInt();

    public BaseObservable goToSettleAccounts = new ObservableInt();

    /**
     * 展示购物车
     *
     * @param view
     */
    public void onShowShoppingCart(View view) {
        onShowShoppingCart.notifyChange();
    }

    /**
     * 去结算
     *
     * @param view
     */
    public void goToSettleAccounts(View view) {
        goToSettleAccounts.notifyChange();
    }

}
