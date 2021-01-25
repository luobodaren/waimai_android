package com.life.waimaishuo.mvvm.vm.mine;

import com.life.waimaishuo.bean.ui.PersonalInfo;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mine.MinePersonalCenterModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class MinePersonalCenterViewModel extends BaseViewModel {

    private MinePersonalCenterModel mModel;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new MinePersonalCenterModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }


    public List<PersonalInfo> getPersonalInfo() {
        List<PersonalInfo> list = new ArrayList<>();
        list.add(new PersonalInfo("头像","","","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640",true,false));
        list.add(new PersonalInfo("用户昵称","","一点点","",true,false));
        list.add(new PersonalInfo("手机号","","134****1234","",true,false));
        list.add(new PersonalInfo("个性签名","","","",true,false));
        list.add(new PersonalInfo("超级会员","","立即续费","",true,true));
        return list;
    }

    public List<PersonalInfo> getAccountBindingInfo(){  //注意 右侧文字未绑定时 应该高亮
        List<PersonalInfo> list = new ArrayList<>();
        list.add(new PersonalInfo("手机","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640","134****7777","",true,false));
        list.add(new PersonalInfo("支付宝","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640","未绑定","",true,true));
        list.add(new PersonalInfo("微信","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640","已绑定","",true,false));
        list.add(new PersonalInfo("QQ","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640","未绑定","",true,true));
        return list;
    }

}
