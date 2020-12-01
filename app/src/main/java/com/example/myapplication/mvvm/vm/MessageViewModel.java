package com.example.myapplication.mvvm.vm;

import com.example.myapplication.bean.Message;
import com.example.myapplication.mvvm.model.BaseModel;

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
        messageList.add(new Message("七匹狼","您收到了本店送出的十件时尚时装，请输入收获地址接受！！！！！","2020/12/20","5","https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2424071932,228392684&fm=15&gp=0.jpg"));
        messageList.add(new Message("七匹狼","您收到了本店送出的十件时尚时装，请输入收获地址接受！！！！！","2020/12/20","5","https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2424071932,228392684&fm=15&gp=0.jpg"));
        messageList.add(new Message("七匹狼","您收到了本店送出的十件时尚时装，请输入收获地址接受！！！！！","2020/12/20","5","https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2424071932,228392684&fm=15&gp=0.jpg"));
        messageList.add(new Message("七匹狼","您收到了本店送出的十件时尚时装，请输入收获地址接受！！！！！","2020/12/20","5","https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2424071932,228392684&fm=15&gp=0.jpg"));
        messageList.add(new Message("七匹狼","您收到了本店送出的十件时尚时装，请输入收获地址接受！！！！！","2020/12/20","5","https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2424071932,228392684&fm=15&gp=0.jpg"));
        messageList.add(new Message("七匹狼","您收到了本店送出的十件时尚时装，请输入收获地址接受！！！！！","2020/12/20","5","https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2424071932,228392684&fm=15&gp=0.jpg"));
    }

    public List<Message> getMessageData() {
        return messageList;
    }
}
