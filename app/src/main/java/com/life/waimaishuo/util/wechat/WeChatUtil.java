package com.life.waimaishuo.util.wechat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

import com.life.base.utils.net.HttpUtils;
import com.life.waimaishuo.MyApplication;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelmsg.GetMessageFromWX;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WeChatUtil {
    private static WeChatUtil sInstance;

    // APP_ID 替换为你的应用从官方网站申请到的合法appID
    private static final String APP_ID = "wx0c317fe686662189";
    private static final String SECRET = "816026b0e0c153576d242e3c6718206e";

    private static final String GET_ACCESS_TOKEN_URL =
            "https://api.weixin.qq.com/sns/oauth2/access_token";

    // IWXAPI 是第三方app和微信通信的openApi接口
    private IWXAPI api;

    private WeChatUtil() { }

    public synchronized static WeChatUtil getInstance(){
        if(sInstance != null){
            sInstance = new WeChatUtil();
        }
        return sInstance;
    }

    /**
     * 使用应用ID向wx进行注册,使程序启动后微信终端能响应本程序
     * @param context
     */
    public void regToWx(Context context) {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(context, APP_ID, true);

        //1.直接将应用的appId注册到微信
//        api.registerApp(APP_ID);

        //2.动态监听微信启动广播进行注册到微信
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // 将该app注册到微信
                api.registerApp(APP_ID);
            }
        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));
    }

    /**
     * 授权登录
     */
    public void loginByWeChat(){
        if (!api.isWXAppInstalled()) {
            Toast.makeText(MyApplication.getMyApplication(), "您的设备未安装微信客户端", Toast.LENGTH_SHORT).show();
        } else {
            final SendAuth.Req req = new SendAuth.Req();
            // TODO: 2020/11/20 作用域添加
            req.scope = "snsapi_userinfo"; //应用授权作用域
            req.state = "wechat_sdk_demo_test"; // FIXME: 2020/11/20 参数可用于防止 csrf 攻击（跨站请求伪造攻击） 可设置为简单的随机数加 session 进行校验
            boolean result = api.sendReq(req);
            // TODO: 2020/11/20 处理登录结果
        }
    }

    /**
     * 发送请求到wx会话
     * @param text
     */
    public void sendReqToSession(String text){
        //初始化一个 WXTextObject 对象，填写分享的文本内容
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;

        //用 WXTextObject 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage(textObj);
        msg.description = text;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());  //transaction字段用与唯一标示一个请求
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;

        //调用api接口，发送数据到微信
        boolean result = api.sendReq(req);
        // TODO: 2020/11/20 添加发送数据回调结果处理
    }

    /**
     * 发送请求到wx朋友圈
     */
    public void sendReqToTimeline(String text){
        //微信4.2以上支持发送到朋友圈功能
        if (api.getWXAppSupportAPI() >= Build.TIMELINE_SUPPORTED_SDK_INT) {
            //初始化一个 WXTextObject 对象，填写分享的文本内容
            WXTextObject textObj = new WXTextObject();
            textObj.text = text;

            //用 WXTextObject 对象初始化一个 WXMediaMessage 对象
            WXMediaMessage msg = new WXMediaMessage(textObj);
            msg.description = text;

            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());  //transaction字段用与唯一标示一个请求
            req.message = msg;
            req.scene = SendMessageToWX.Req.WXSceneTimeline;

            //调用api接口，发送数据到微信
            boolean result = api.sendReq(req);
            // TODO: 2020/11/20 添加发送数据回调结果处理
        }else{
            // TODO: 2020/11/20  添加不支持发送朋友圈逻辑 
        }

    }

    /**
     * 发送响应到微信
     * @param text
     * @param bundle 为微信传递过来的Intent所带的内容，通过getExtras()方法获取
     */
    public void sendResp(String text, Bundle bundle){
        // 初始化一个 WXTextObject 对象
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;

        // 用 WXTextObject 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage(textObj);
        msg.description = text;

        // 构造一个Resp
        GetMessageFromWX.Resp resp = new GetMessageFromWX.Resp();
        // 将req的transaction设置到resp对象中，其中bundle为微信传递过来的Intent所带的内容，通过getExtras()方法获取
        resp.transaction = new GetMessageFromWX.Req(bundle).transaction;
        resp.message = msg;

        //调用api接口，发送数据到微信
        api. sendResp (resp) ;
    }


    public IWXAPI getApi() {
        return api;
    }

    public void getAccessToken(String code) {
        // TODO: 2020/11/23 微信授权登录 如何与服务器同步
        StringBuilder loginUrl = new StringBuilder();
        loginUrl.append("https://api.weixin.qq.com/sns/oauth2/access_token")
                .append("?appid=")
                .append(APP_ID)
                .append("&secret=")
                .append(SECRET)
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");
        HttpUtils.getHttpUtils().doGet(loginUrl.toString(), false, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String json) {

            }

            @Override
            public void onError(int errorType, Throwable error) {

            }
        });
    }
}
