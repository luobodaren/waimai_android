package com.life.waimaishuo.bean.event;

import androidx.annotation.NonNull;

public class MessageEvent {

    private int code;
    private Object message;

    public MessageEvent(int code, Object message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    @NonNull
    @Override
    public String toString() {
        return "code:" + code + " message:" + message;
    }
}
