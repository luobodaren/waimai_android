package com.life.waimaishuo.mvvm.vm;

import com.life.waimaishuo.bean.Message;
import com.life.waimaishuo.mvvm.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

public class MessageViewModel extends BaseViewModel {

    List<Message> messageList = new ArrayList<>();

    @Override
    public BaseModel getModel() {
        return null;
    }

    @Override
    public void initData() {
        messageList.add(new Message("七匹狼","您收到了本店送出的十件时尚时装，请输入收获地址接受！！！！！","2020/12/20","5","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640"));
        messageList.add(new Message("七匹狼","您收到了本店送出的十件时尚时装，请输入收获地址接受！！！！！","2020/12/20","5","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640"));
        messageList.add(new Message("七匹狼","您收到了本店送出的十件时尚时装，请输入收获地址接受！！！！！","2020/12/20","5","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640"));
        messageList.add(new Message("七匹狼","您收到了本店送出的十件时尚时装，请输入收获地址接受！！！！！","2020/12/20","5","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640"));
        messageList.add(new Message("七匹狼","您收到了本店送出的十件时尚时装，请输入收获地址接受！！！！！","2020/12/20","5","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640"));
        messageList.add(new Message("七匹狼","您收到了本店送出的十件时尚时装，请输入收获地址接受！！！！！","2020/12/20","5","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640"));
    }

    public List<Message> getMessageData() {
        return messageList;
    }
}
