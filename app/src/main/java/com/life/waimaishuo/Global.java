package com.life.waimaishuo;

import com.life.base.utils.LogUtil;
import com.life.waimaishuo.bean.User;
import com.life.waimaishuo.constant.MMKVConstant;
import com.tencent.mmkv.MMKV;

public class Global {

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
    public static String LocationDistrict = "";
    //建筑名称（AOI NAME
    public static String AoiName = "";
    //具体位置
    public static String address = "";
    */

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
    //建筑名称（AOI NAME
    public static String AoiName = "深圳讯美大厦";
    //具体位置
    public static String Address = "广东省深圳市南山区科智东路79号靠近科技园·金融基地";



    private static User user;    //用户信息

    private static String machine_number = "";   //手机本机号码

    /*public static boolean isLogin = false;  //是否登录了（可从mmkv获取到token，则表示登录了，否则为不可登录
    public static String token = "";*/

    //测试用----------------
    private static boolean isLogin = true;  //是否登录了
    private static String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMzcxNTcxNDA5OSIsInVzZXJuYW1lIjoiMTM3MTU3MTQwOTkiLCJpYXQiOjE2MTU0NDM0OTV9.lt_jn4NhpQFr_h3a89tGGKxB_rinerBP2L9tcycwUHU";

    public static boolean isLogin() {
        return isLogin;
    }

    public static void setIsLogin(boolean isLogin) {
        Global.isLogin = isLogin;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        isLogin = true;
        Global.token = token;

        MMKV mmkv = MMKV.mmkvWithID(MMKVConstant.MMKV_ID_USER);
        mmkv.encode("token", token);
    }

    public static User getUser() {
        return user;
    }

    public static void setUserInfoAndSave(User user) {
        Global.user = user;
        //保存到MMKV文件中
        MMKV mmkv = MMKV.mmkvWithID(MMKVConstant.MMKV_ID_USER);
        mmkv.encode("user", user);
    }

    public static String getMachine_number() {
        return machine_number;
    }

    public static void setMachine_number(String machine_number) {
        Global.machine_number = machine_number;

        MMKV mmkv = MMKV.mmkvWithID(MMKVConstant.MMKV_ID_USER);
        mmkv.encode("machine_number", machine_number);
    }

    public static void resetUserLonAndLat() {
        userLonAndLat = longitude + "," + latitude;
    }

    /**
     * 从MMKV文件中获取数据
     */
    public static void readFormMMKV() {
        MMKV mmkv = MMKV.mmkvWithID(MMKVConstant.MMKV_ID_USER);
        user = mmkv.decodeParcelable("user", User.class, null);
        // TODO: 2021/3/13  为了测试方便 去掉此处的token获取
        //token = mmkv.decodeString("token", "");
        machine_number = mmkv.decodeString("machine_number", "");
        LogUtil.d("token:" + token + " user:" + (user == null ? "null" : user.toString()));
    }
}
