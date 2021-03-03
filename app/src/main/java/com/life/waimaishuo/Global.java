package com.life.waimaishuo;

import com.life.base.utils.LogUtil;

public class Global {

    public static String machine_number = "";   //手机本机号码

    /*public static boolean isLogin = false;  //是否登录了
    public static String token = "";*/

    //测试用----------------
    public static boolean isLogin = true;  //是否登录了
    public static String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMzcxNTcxNDA5OSIsInVzZXJuYW1lIjoiMTM3MTU3MTQwOTkiLCJpYXQiOjE2MTQ1NjU4Nzd9.8MqozOria4U82eP1L_sJRAxzkJnMhRNicf6Sx5wR_IA";

    public static String user_login_phone = "";   //用户登录手机号
    public static void setPhoneLoginSuccessData(String phone, String token){
        isLogin = true;
        Global.token = token;
        user_login_phone = phone;

        //保存到MMKV文件中
        saveToMMKV();
    }

    /*//纬度
    public static double latitude = 0L;
    //经度
    public static double longitude = 0L;
    //经纬度拼接
    public static String userLonAndLat = "";
    //定位的省
    public static String LocationProvince = "";
    //定位的城市
    public static String LocationCity = "";
    //定位的城区
    public static String LocationDistrict = "";*/

    /* 测试用 */
    //纬度
    public static double latitude = 22.544688;
    //经度
    public static double longitude = 113.947271;
    //
    public static String userLonAndLat = longitude + "," + latitude;
    //定位的省
    public static String LocationProvince = "广东省";
    //定位的城市
    public static String LocationCity = "深圳市";
    //定位的城区
    public static String LocationDistrict = "科技园";

    public static void resetUserLonAndLat(){
        userLonAndLat = longitude + "," + latitude;
    }

    public static void saveToMMKV(){
        LogUtil.d("phone:" + user_login_phone + "  token:" + token);
    }
}
