package com.life.waimaishuo.bean.api.request.bean;

public class LoginWithVerificcationReqBean {

    private String authCode;
    private String phoneNumber;

    public LoginWithVerificcationReqBean(String authCode, String phoneNumber) {
        this.authCode = authCode;
        this.phoneNumber = phoneNumber;
    }
}
