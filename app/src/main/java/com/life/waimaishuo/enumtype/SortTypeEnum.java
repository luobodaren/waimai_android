package com.life.waimaishuo.enumtype;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public enum SortTypeEnum {

    SCORE("综合排序",1),
    STARTING_SHIPPING_PRICE_LOWEST("起送价最低",2),
    DELIVERY_FASTEST("配送最快",3),
    DELIVERY_PRICE_LOWEST("配送费最低",4),
    PER_CAPITA_PRICE_LOWEST("人均从低到高",5),
    PER_CAPITA_PRICE_HIGHEST("人均从高到低",6),
    DISTANCE("距离",7),
    SALES("销量",8);

    private String type;
    private int code;


    SortTypeEnum(String type, int code) {
        this.type = type;
        this.code = code;
    }

    public static SortTypeEnum get(@NonNull String type){
        if(type.equals(SCORE.type)){
            return SCORE;
        }else if(type.equals(STARTING_SHIPPING_PRICE_LOWEST.type)){
            return STARTING_SHIPPING_PRICE_LOWEST;
        }else if(type.equals(DELIVERY_FASTEST.type)){
            return DELIVERY_FASTEST;
        }else if(type.equals(DELIVERY_PRICE_LOWEST.type)){
            return DELIVERY_PRICE_LOWEST;
        }else if(type.equals(PER_CAPITA_PRICE_LOWEST.type)){
            return PER_CAPITA_PRICE_LOWEST;
        }else if(type.equals(PER_CAPITA_PRICE_HIGHEST.type)){
            return PER_CAPITA_PRICE_HIGHEST;
        }else if(type.equals(DISTANCE.type)){
            return DISTANCE;
        }else if(type.equals(SALES.type)){
            return SALES;
        }
        return null;
    }

    public static SortTypeEnum get(int code){
        if(code == SCORE.code){
            return SCORE;
        }else if(code == STARTING_SHIPPING_PRICE_LOWEST.code){
            return STARTING_SHIPPING_PRICE_LOWEST;
        }else if(code == DELIVERY_FASTEST.code){
            return DELIVERY_FASTEST;
        }else if(code == DELIVERY_PRICE_LOWEST.code){
            return DELIVERY_PRICE_LOWEST;
        }else if(code == PER_CAPITA_PRICE_LOWEST.code){
            return PER_CAPITA_PRICE_LOWEST;
        }else if(code == PER_CAPITA_PRICE_HIGHEST.code){
            return PER_CAPITA_PRICE_HIGHEST;
        }else if(code == DISTANCE.code){
            return DISTANCE;
        }else if(code == SALES.code){
            return SALES;
        }
        return null;
    }

    public static List<String> getKeyList(){
        List<String> keyList = new ArrayList<>();
        keyList.add(SCORE.type);
        keyList.add(STARTING_SHIPPING_PRICE_LOWEST.type);
        keyList.add(DELIVERY_FASTEST.type);
        keyList.add(DELIVERY_PRICE_LOWEST.type);
        keyList.add(PER_CAPITA_PRICE_LOWEST.type);
        keyList.add(PER_CAPITA_PRICE_HIGHEST.type);
        keyList.add(DISTANCE.type);
        keyList.add(SALES.type);
        return keyList;
    }

    public String getType() {
        return type;
    }

    public int getCode() {
        return code;
    }
}
