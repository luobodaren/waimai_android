package com.life.waimaishuo.mvvm.vm;

import androidx.databinding.ObservableInt;

import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.LoginModel;

public class LoginViewModel extends BaseViewModel {

    private LoginModel mModel;

    public ObservableInt loginObservable = new ObservableInt();
    public ObservableInt requestVerificationObservable = new ObservableInt();

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new LoginModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    public void loginWithVerification(String phoneNumber, String verification) {
        mModel.loginByPhone(phoneNumber,verification,new BaseModel.NotifyChangeRequestCallBack(loginObservable));
    }

    public void requestVerification(String currentRequestVerificationPhone) {
        mModel.requestVerification(currentRequestVerificationPhone,new BaseModel.NotifyChangeRequestCallBack(requestVerificationObservable));
    }
}
