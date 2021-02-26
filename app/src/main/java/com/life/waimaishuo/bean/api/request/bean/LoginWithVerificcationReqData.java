package com.life.waimaishuo.bean.api.request.bean;

public class LoginWithVerificcationReqData {

    private String authCode;
    private String phoneNumber;

    public LoginWithVerificcationReqData(String authCode, String phoneNumber) {
        this.authCode = authCode;
        this.phoneNumber = phoneNumber;
    }
}
