package com.life.waimaishuo.mvvm.vm.mine;

import androidx.databinding.ObservableField;

import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mine.MineFeedbackModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

public class MineFeedbackViewModel extends BaseViewModel {

    private MineFeedbackModel mModel;

    public ObservableField<String>  feedbackContentObservable = new ObservableField<>();
    public ObservableField<String>  connectObservable = new ObservableField<>();

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new MineFeedbackModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }
}
