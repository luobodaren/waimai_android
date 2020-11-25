package com.example.myapplication.mvvm.view.activity.wxapi;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.base.utils.LogUtil;
import com.example.myapplication.util.wechat.WeChatUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    String state;
    String scope;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WeChatUtil.getInstance().getApi().handleIntent(getIntent(),this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        SendAuth.Req req = (SendAuth.Req) baseReq;
        this.state = req.state;
        this.scope = req.scope;
    }

    @Override
    public void onResp(BaseResp baseResp) {
        //登录回调
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                SendAuth.Resp resp = (SendAuth.Resp) baseResp;
                String code = resp.code;
                checkRespState(resp.state);
                //获取accesstoken
                WeChatUtil.getInstance().getAccessToken(code);
                LogUtil.d("fantasychongwxlogin"+code.toString()+ "");
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
                finish();
                break;
            default:
                finish();
                break;
        }

    }

    private void checkRespState(String state) {
        LogUtil.d("checkRespState state=" + state  + " this.state=" + this.state);
    }
}
