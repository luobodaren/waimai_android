package com.example.myapplication.mvvm.model.mine;

import androidx.annotation.NonNull;

import com.example.myapplication.mvvm.model.BaseModel;

public class GoodLogisticsRecyclerModel extends BaseModel {


    public static class Data {
        int resImgId;
        String iconType;

        public Data(@NonNull int resImgId, @NonNull String iconType) {
            this.resImgId = resImgId;
            this.iconType = iconType;
        }

        public int getResImgId() {
            return resImgId;
        }

        public void setResImgId(int resImgId) {
            this.resImgId = resImgId;
        }

        public String getIconType() {
            return iconType;
        }

        public void setIconType(String iconType) {
            this.iconType = iconType;
        }
    }

}
