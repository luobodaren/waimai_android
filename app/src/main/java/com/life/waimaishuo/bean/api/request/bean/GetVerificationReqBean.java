package com.life.waimaishuo.bean.api.request.bean;

public class GetVerificationReqBean {

    /**
     * 1app登录
     * 2app忘记密码
     * 3app注册
     * 4系统后台忘记密码
     * 5门店登录
     * 6门店忘记密码
     * 7配送端后台忘记密码
     * 8配送端app忘记密码
     * 9配送端后台登录
     * 10配送端app登录
     * 11门店注册
     * 12系统后台登录
     * 13门店后台添加支付宝账户
     * 17配送端App提现账户验证码
     */
    int usr;

    String mobile;

    public GetVerificationReqBean(String mobile, int usr) {
        this.mobile = mobile;
        this.usr = usr;
    }
}
