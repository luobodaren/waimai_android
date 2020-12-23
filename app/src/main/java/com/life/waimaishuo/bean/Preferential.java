package com.life.waimaishuo.bean;

import java.util.List;

public class Preferential {

    private List<RedPacket> mRedPacketList;
    private List<PreferentialActivity> mPreferentialActivityList;
    private List<MerchantsService> mMerchantsServiceList;
    private String notice;

    public Preferential() {
    }

    public List<RedPacket> getRedPacketList() {
        return mRedPacketList;
    }

    public void setRedPacketList(List<RedPacket> mRedPacketList) {
        this.mRedPacketList = mRedPacketList;
    }

    public List<PreferentialActivity> getPreferentialActivityList() {
        return mPreferentialActivityList;
    }

    public void setPreferentialActivityList(List<PreferentialActivity> mPreferentialActivityList) {
        this.mPreferentialActivityList = mPreferentialActivityList;
    }

    public List<MerchantsService> getMerchantsServiceList() {
        return mMerchantsServiceList;
    }

    public void setMerchantsServiceList(List<MerchantsService> mMerchantsServiceList) {
        this.mMerchantsServiceList = mMerchantsServiceList;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }
}
