package com.life.waimaishuo.bean.event;

import androidx.annotation.NonNull;

public class MessageEvent {

    private int code;
    private String message;

    public MessageEvent(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @NonNull
    @Override
    public String toString() {
        return "code:" + code + " message:" + message;
    }
}
