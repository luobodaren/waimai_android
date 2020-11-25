package com.example.myapplication.mvvm.model.mine;

import androidx.annotation.NonNull;

public class TopDataRecyclerModel {

    public static class Data{
        String type;
        String number;

        public Data(@NonNull String type, @NonNull String number) {
            this.type = type;
            this.number = number;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }
}
