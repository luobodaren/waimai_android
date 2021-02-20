package com.life.waimaishuo.bean.api.respon;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WaiMaiAllType {

    @SerializedName(value = "typeName")
    String typeName;
    @SerializedName(value = "typeIcon")
    String typeIon;
    @SerializedName(value = "children")
    List<SubType> subTypeList;

    public WaiMaiAllType() {
    }

    public WaiMaiAllType(String typeName, String typeIon, List<SubType> subTypeList) {
        this.typeName = typeName;
        this.typeIon = typeIon;
        this.subTypeList = subTypeList;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeIon() {
        return typeIon;
    }

    public void setTypeIon(String typeIon) {
        this.typeIon = typeIon;
    }

    public List<SubType> getSubTypeList() {
        return subTypeList;
    }

    public void setSubTypeList(List<SubType> subTypeList) {
        this.subTypeList = subTypeList;
    }

    public static class SubType{
        @SerializedName(value = "typeName")
        String typeName;
        @SerializedName(value = "typeIcon")
        String typeIon;

        public SubType() {
        }

        public SubType(String typeName, String typeIon) {
            this.typeName = typeName;
            this.typeIon = typeIon;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getTypeIon() {
            return typeIon;
        }

        public void setTypeIon(String typeIon) {
            this.typeIon = typeIon;
        }
    }

}
