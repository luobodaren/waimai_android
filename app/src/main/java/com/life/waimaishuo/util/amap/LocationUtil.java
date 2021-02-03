package com.life.waimaishuo.util.amap;

import androidx.annotation.Nullable;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.life.base.utils.LogUtil;
import com.life.waimaishuo.MyApplication;

public class LocationUtil {

    private static AMapLocationClient mLocationClient;

    //设置是否允许模拟位置,默认为true，允许模拟位置
    //mLocationOption.setMockEnable(true);

    //关闭缓存机制（默认开启）
    //mLocationOption.setLocationCacheEnable(false);

    //-------------监听回调数据解析---------------//
    //-------------成功
    //amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
    //amapLocation.getLatitude();//获取纬度
    //amapLocation.getLongitude();//获取经度
    //amapLocation.getAccuracy();//获取精度信息
    //amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
    //amapLocation.getCountry();//国家信息
    //amapLocation.getProvince();//省信息
    //amapLocation.getCity();//城市信息
    //amapLocation.getDistrict();//城区信息
    //amapLocation.getStreet();//街道信息
    //amapLocation.getStreetNum();//街道门牌号信息
    //amapLocation.getCityCode();//城市编码
    //amapLocation.getAdCode();//地区编码
    //amapLocation.getAoiName();//获取当前定位点的AOI信息
    //amapLocation.getBuildingId();//获取当前室内定位的建筑物Id
    //amapLocation.getFloor();//获取当前室内定位的楼层
    //amapLocation.getGpsAccuracyStatus();//获取GPS的当前状态
    //获取定位时间
    //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //Date date = new Date(amapLocation.getTime());
    //df.format(date);
    //-----------------失败
    // 可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
    //    Log.e("AmapError","location Error, ErrCode:"
    //        + amapLocation.getErrorCode() + ", errInfo:"
    //        + amapLocation.getErrorInfo());
    //    }

    /**
     * 初始化
     */
    private static void initLocationClientState() {
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(MyApplication.getMyApplication());
        }
    }

    /**
     * 默认定位 可设置定位场景
     *
     * @param aMapLocationListener
     * @param scene
     */
    private static void location(AMapLocationListener aMapLocationListener, @Nullable AMapLocationClientOption.AMapLocationPurpose scene, AMapLocationClientOption.AMapLocationMode aMapLocationMode) {
        initLocationClientState();
        //设置定位回调监听
        mLocationClient.setLocationListener(aMapLocationListener);

        //初始化AMapLocationClientOption对象
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        if (scene != null) {
            mLocationOption.setLocationPurpose(scene);
        }
        //定位模式（高精度、低功耗、仅设备）
        mLocationOption.setLocationMode(aMapLocationMode);

        startLocation(mLocationOption);
    }

    /**
     * 一次定位
     *
     * @param aMapLocationListener
     */
    public static void onceLocation(AMapLocationListener aMapLocationListener, @Nullable AMapLocationClientOption.AMapLocationMode aMapLocationMode) {
        initLocationClientState();

        mLocationClient.setLocationListener(aMapLocationListener);

        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        mLocationOption.setOnceLocation(true);
        mLocationOption.setLocationMode(aMapLocationMode);

        startLocation(mLocationOption);
    }

    /**
     * 连续定位
     *
     * @param aMapLocationListener
     * @param interval             定位间隔，单位毫秒
     */
    public static void intervalLocation(AMapLocationListener aMapLocationListener, long interval, AMapLocationClientOption.AMapLocationMode aMapLocationMode) {
        initLocationClientState();

        mLocationClient.setLocationListener(aMapLocationListener);

        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        mLocationOption.setInterval(interval);
        mLocationOption.setLocationMode(aMapLocationMode);

        startLocation(mLocationOption);
    }

    /**
     * 获取最近3s内精度最高的一次定位结果
     *
     * @param aMapLocationListener
     */
    public static void onceLatestLocation(AMapLocationListener aMapLocationListener, AMapLocationClientOption.AMapLocationMode aMapLocationMode) {
        initLocationClientState();

        mLocationClient.setLocationListener(aMapLocationListener);

        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        mLocationOption.setOnceLocationLatest(true);
        mLocationOption.setLocationMode(aMapLocationMode);

        startLocation(mLocationOption);
    }

    /**
     * 执行定位
     *
     * @param mLocationOption
     */
    private static void startLocation(AMapLocationClientOption mLocationOption) {
        LogUtil.d("开始定位");
        initLocationClientState();

        mLocationClient.setLocationOption(mLocationOption);
        //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
        mLocationClient.stopLocation();
        mLocationClient.startLocation();
    }

    /**
     * 停止定位（注意不会自动销毁定位服务）
     *
     * @param isDestroy
     */
    public static void stopLocation(boolean isDestroy) {
        LogUtil.d("停止定位");
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            if (isDestroy) {
                LogUtil.d("销毁定位服务");
                mLocationClient.onDestroy();
                mLocationClient = null;
            }
        }
    }


}
