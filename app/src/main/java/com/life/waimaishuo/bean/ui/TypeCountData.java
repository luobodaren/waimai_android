package com.life.waimaishuo.bean.ui;

import androidx.annotation.NonNull;

public class TypeCountData {
    String type;
    String number;

    public TypeCountData(@NonNull String type, @NonNull String number) {
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

    public void setTypeCountRecyclerViewItemData(TypeCountData typeCountRecyclerViewItemData){
        this.type = typeCountRecyclerViewItemData.type;
        this.number = typeCountRecyclerViewItemData.number;
    }
}
