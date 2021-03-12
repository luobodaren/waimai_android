package com.life.waimaishuo.mvvm.view.fragment.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.LogUtil;
import com.life.waimaishuo.Global;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.adapter.SelectedPositionRecyclerViewAdapter;
import com.life.waimaishuo.bean.event.MessageEvent;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.constant.MessageCodeConstant;
import com.life.waimaishuo.databinding.FragmentMineLocalAddressBinding;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.utils.TitleBar;

import java.util.ArrayList;
import java.util.List;

@Page(name = "定位地址")
public class MineLocalAddressFragment extends BaseFragment implements PoiSearch.OnPoiSearchListener{

    private final static String RESULT_KEY = "result_key";

    private FragmentMineLocalAddressBinding mBinding;

    private AMap aMap;  //地图的控制器类
    private float defaultZoomToLevel = 15;

    private SelectedPositionRecyclerViewAdapter<PoiItem> nearbyAddressAdapter;
    private SelectedPositionRecyclerViewAdapter<PoiItem> strQueryAdapter;

    private PoiSearch.OnPoiSearchListener onStrSearchPoiListener;

    private PoiItem selectedPoi;


    @Override
    protected BaseViewModel setViewModel() {
        return null;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentMineLocalAddressBinding)mViewDataBinding;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_local_address;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mBinding.map.onCreate(savedInstanceState);
        return view;
    }

    @Override
    protected void onLifecycleResume() {
        super.onLifecycleResume();
        mBinding.map.onResume();


    }

    @Override
    protected void onLifecyclePause() {
        super.onLifecyclePause();
        mBinding.map.onPause();
    }

    @Override
    protected void onLifecycleDestroy() {
        super.onLifecycleDestroy();
        mBinding.map.onDestroy();
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mBinding.map.onSaveInstanceState(outState);
    }

    @Override
    protected void initArgs() {
        super.initArgs();

        setRegisterEventBus(true);
        setFitStatusBarHeight(true);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_LIGHT);
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void firstRequestData() {
        super.firstRequestData();

        query("",Global.latitude, Global.longitude,null);
    }

    @Override
    protected void initViews() {
        super.initViews();

        initTitle();

        //初始化地图控制器对象
        aMap = mBinding.map.getMap();
        aMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Global.latitude,Global.longitude)));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(defaultZoomToLevel)); //设置缩放层级

        //添加覆盖物

        //实现定位蓝点
//        MyLocationStyle myLocationStyle;
//        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
//        myLocationStyle.showMyLocation(true);//设置是否显示定位小蓝点，用于满足只想使用定位，不想使用定位小蓝点的场景，设置false以后图面上不再有定位蓝点的概念，但是会持续回调位置信息。
        //myLocationStyle.myLocationIcon(myLocationIcon);//设置定位蓝点的icon图标方法，需要用到BitmapDescriptor类对象作为参数

        //myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
        //myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE) ;//定位一次，且将视角移动到地图中心点。
        //myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW) ;//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
        //myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);//连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
        //myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
        // 以下三种模式从5.1.0版本开始提供
        //myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
        //myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
        //myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。

//        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//        aMap.getUiSettings().setMyLocationButtonEnabled(false);//设置默认定位按钮是否显示，非必需设置。
//        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        initNearbyAddressRecycler();
        initStrQueryResultRecycler();

    }

    @Override
    protected void initListeners() {
        super.initListeners();

        onStrSearchPoiListener = new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {
                if(i == 1000){
                    if(poiResult.getPois().size() > 0){
                        selectedPoi = poiResult.getPois().get(0);
                    }
                    strQueryAdapter.setData(poiResult.getPois());
                }else{  //失败清空数据
                    strQueryAdapter.getData().clear();
                }
                strQueryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) { }
        };

        mBinding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                String resultStr = s.toString();
                if(resultStr.length() > 0){
                    setViewVisibility(mBinding.recyclerSearchResult,true);
                    query(resultStr,Global.latitude,Global.longitude, onStrSearchPoiListener);
                }else{
                    setViewVisibility(mBinding.recyclerSearchResult,false);
                    selectedPoi = nearbyAddressAdapter.getData().get(nearbyAddressAdapter.getSelectedPosition());
                }
            }
        });

        /*aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                getAddressByLatlng(location.getLatitude(),location.getLongitude());
            }
        });*/

        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                aMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                aMap.moveCamera(CameraUpdateFactory.zoomTo(defaultZoomToLevel));
            }
        });
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                LogUtil.d("onCameraChangeFinish");
                //根据位置获取周围兴趣点信息
                query("",cameraPosition.target.longitude,cameraPosition.target.latitude,null);
            }
        });

        mBinding.layoutTitle.tvRight.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                onFinish();
            }
        });
    }

    @Override
    public void MessageEvent(MessageEvent messageEvent) {
        super.MessageEvent(messageEvent);
        switch (messageEvent.getCode()){
            case MessageCodeConstant.LOCAL_INFO_UPDATE:
                nearbyAddressAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if(i == 1000){
            List<PoiItem> poiItemList = poiResult.getPois();
            poiItemList.add(0,new PoiItem("",null,"",""));
            nearbyAddressAdapter.setData(poiItemList);
        }else{
            nearbyAddressAdapter.getData().clear();
        }
        nearbyAddressAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
        LogUtil.d(poiItem.getAdName());
        //移动到最近兴趣点上！
        //aMap.moveCamera(CameraUpdateFactory.newLatLng(poiResult.getPois().));
    }

    private void initTitle(){
        mBinding.layoutTitle.tvTitle.setText(getPageName());
        mBinding.layoutTitle.tvRight.setText(R.string.finish);
    }

    /**
     * 查询周边地址，你可以在每次定位中心点后开始查询
     * @param keyWord 关键词
     * @param i 经度
     * @param y 纬度
     */
    private void query(String keyWord ,double i, double y, PoiSearch.OnPoiSearchListener listener) {
        PoiSearch.Query query = new PoiSearch.Query(keyWord, "", Global.LocationCity);
        //keyWord表示搜索字符串，
        //第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码
        //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query.setPageSize(25);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);//设置查询页码
        PoiSearch poiSearch = new PoiSearch(requireContext(), query);
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(i, y), 800));//设置周边搜索的中心点以及半径
        if(listener != null){
            poiSearch.setOnPoiSearchListener(listener);
        }else{
            poiSearch.setOnPoiSearchListener(this);
        }
        poiSearch.searchPOIAsyn();
    }

    /**
     * 逆地理
     * @param cityName
     */
    private void getLatlon(String cityName){
        GeocodeSearch geocodeSearch=new GeocodeSearch(requireContext());
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                if (i==1000){
                    if (geocodeResult!=null && geocodeResult.getGeocodeAddressList()!=null &&
                            geocodeResult.getGeocodeAddressList().size()>0){

                        GeocodeAddress geocodeAddress = geocodeResult.getGeocodeAddressList().get(0);
                        double latitude = geocodeAddress.getLatLonPoint().getLatitude();//纬度
                        double longititude = geocodeAddress.getLatLonPoint().getLongitude();//经度
                        String adcode= geocodeAddress.getAdcode();//区域编码

                        LogUtil.e("地理编码:" + geocodeAddress.getAdcode()+"");
                        LogUtil.e("纬度latitude:" + latitude+"");
                        LogUtil.e("经度longititude:" + longititude+"");

                    }else {
                        Toast.makeText(requireContext(), "地址名出错", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        GeocodeQuery geocodeQuery=new GeocodeQuery(cityName.trim(),"29");
        geocodeSearch.getFromLocationNameAsyn(geocodeQuery);
    }

    private void initNearbyAddressRecycler(){
        nearbyAddressAdapter = new SelectedPositionRecyclerViewAdapter<PoiItem>(new ArrayList<PoiItem>()){
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_recycler_mine_nearby_address;
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, boolean selected, PoiItem item) {
                if(holder.getAdapterPosition() == 0){
                    holder.setText(R.id.tv_name,Global.AoiName);
                    holder.setText(R.id.tv_address,Global.Address);
                }else{
                    holder.setText(R.id.tv_name,item.getTitle());
                    holder.setText(R.id.tv_address,item.getSnippet());
                }
                if(selected){
                    holder.getView(R.id.select_sign).setSelected(true);
                }else{
                    holder.getView(R.id.select_sign).setSelected(false);
                }
            }
        };
        nearbyAddressAdapter.setSelectedListener(new SelectedPositionRecyclerViewAdapter.OnSelectedListener<PoiItem>() {
            @Override
            public void onSelectChangeClick(BaseViewHolder holder, PoiItem item, boolean isCancel) {
                selectedPoi = item;
            }
        });
        mBinding.recyclerNearbyAddress.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        mBinding.recyclerNearbyAddress.setAdapter(nearbyAddressAdapter);
    }

    private void initStrQueryResultRecycler(){
        strQueryAdapter = new SelectedPositionRecyclerViewAdapter<PoiItem>(new ArrayList<PoiItem>()){
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_recycler_mine_nearby_address;
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, boolean selected, PoiItem item) {
                holder.setText(R.id.tv_name,item.getTitle());
                holder.setText(R.id.tv_address,item.getSnippet());
                if(selected){
                    holder.getView(R.id.select_sign).setSelected(true);
                }else{
                    holder.getView(R.id.select_sign).setSelected(false);
                }
            }
        };
        strQueryAdapter.setSelectedListener(new SelectedPositionRecyclerViewAdapter.OnSelectedListener<PoiItem>() {
            @Override
            public void onSelectChangeClick(BaseViewHolder holder, PoiItem item, boolean isCancel) {
                selectedPoi = item;
            }
        });

        mBinding.recyclerSearchResult.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        mBinding.recyclerSearchResult.setAdapter(strQueryAdapter);
    }

    /**
     * 退出时调用 返回给上一个页面
     */
    private void onFinish(){
        Intent resultIntent = new Intent();
        resultIntent.putExtra(RESULT_KEY,selectedPoi);
        setFragmentResult(Constant.REQUEST_CODE_LOCAL_ADDRESS, resultIntent);
    }

}
