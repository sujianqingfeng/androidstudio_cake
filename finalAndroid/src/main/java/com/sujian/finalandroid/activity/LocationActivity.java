package com.sujian.finalandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.sujian.finalandroid.base.BaseActivity;
import com.sujian.finalandroid.ui.TitleBuilder;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * @author 12111
 * 定位activity
 */
@ContentView(R.layout.activity_location)
public class LocationActivity extends BaseActivity {

    //地图显示控件
    @ViewInject(R.id.mv_bmapView)
    private MapView mv_bmapView;
    //百度地图的bitmap
    private BaiduMap baidumap;
    //显示位置信息的listview
    @ViewInject(R.id.lv_locationinfo)
    private ListView lv_locationinfo;
    // 定位相关
    private LocationClient mLocClient;
    //定位的监听器
    private MyLocationListenner myListener = new MyLocationListenner();
    // 是否首次定位
    boolean isFirstLoc = true;
    //中间的覆盖物
    private Marker mMarkerA;
    // 搜索模块，也可去掉地图模块独立使用
    private GeoCoder mSearch = null;
    //放置地址信息的集合
    private List<PoiInfo> poiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        super.initData();
        initTitle();
        initBaiduMap();
        initGeoCoder();
    }

    /**
     * 初始化搜索模块
     */
    private void initGeoCoder() {
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            //正向
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            //反向
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {

                    LogUtil.e("抱歉，未能找到结果");
                    return;
                }
                poiList = result.getPoiList();
                for (PoiInfo p : poiList) {
                    LogUtil.e("地址--" + p.address + "|城市--" + p.city + "|name--" + p.name + "|postCode" + p.postCode + "|phone" + p.phoneNum);
                }
                LocationAdapter locationAdapter = new LocationAdapter();
                lv_locationinfo.setAdapter(locationAdapter);
                lv_locationinfo.setItemChecked(0, true);
                listClickEnent();
            }
        });
    }

    /**
     * listview 条目的点击事件
     */
    private void listClickEnent() {
        lv_locationinfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setResult(RESULT_OK, getIntent().putExtra("address", poiList.get(position).address));
                LogUtil.e("选择的地址为：" + poiList.get(position).address);
                finish();
            }
        });
    }

    /**
     * 初始化百度地图
     */
    private void initBaiduMap() {
        //得到百度地图的map
        baidumap = mv_bmapView.getMap();
        //设置比例尺
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.zoomTo(18.0f);
        baidumap.animateMapStatus(mapStatusUpdate);
        // 开启定位图层
        baidumap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(3000);
        mLocClient.setLocOption(option);
        mLocClient.start();

        //设置地图状态发生变化的监听器  主要用来添加覆盖物
        baidumap.setOnMapStatusChangeListener(new CenterLocationListener());
    }


    /**
     * 初始化标题
     */
    private void initTitle() {
        /**
         * 1.设置左边的图片按钮显示，以及事件 2.设置中间TextView显示的文字 3.设置右边的图片按钮显示，并设置事件
         */
        new TitleBuilder(this).initTitle(this).setLeftImageRes(R.drawable.head_top_title_left_icon).setMiddleTitleText("定  位")
                .setRightImageRes(R.drawable.ic_launcher).setLeftTextOrImageListener(titleListener)
                .setRightTextOrImageListener(titleListener);

    }

    /**
     * 标题栏的监听
     */
    private View.OnClickListener titleListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.title_left_imageview:
                    finish();
                    break;

                case R.id.title_right_imageview:
                    Toast.makeText(getApplicationContext(), "右边被点击", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //关闭搜索
        mSearch.destroy();
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        baidumap.setMyLocationEnabled(false);
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mv_bmapView.onDestroy();
        mv_bmapView = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mv_bmapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mv_bmapView.onPause();
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mv_bmapView == null) {
                return;
            }

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            baidumap.setMyLocationData(locData);

            if (isFirstLoc) {
                LogUtil.e("得到我的位置信息--" + "经度--" + location.getLongitude() + "|纬度--" + location.getLatitude());

                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                baidumap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                // 反Geo搜索
                mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                        .location(new LatLng(location.getLatitude(), location.getLongitude())));
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    /**
     * 监听地图状态发生变化
     */
    private class CenterLocationListener implements BaiduMap.OnMapStatusChangeListener {
        @Override
        public void onMapStatusChangeStart(MapStatus mapStatus) {

        }

        @Override
        public void onMapStatusChange(MapStatus mapStatus) {

        }

        @Override
        public void onMapStatusChangeFinish(MapStatus mapStatus) {
            // 初始化全局 bitmap 信息，不用时及时 recycle
            BitmapDescriptor bdA = BitmapDescriptorFactory
                    .fromResource(R.drawable.icon_mark);
            LatLng target = mapStatus.target;
            // 反Geo搜索
            mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                    .location(target));

            MarkerOptions ooA = new MarkerOptions().position(target).icon(bdA)
                    .zIndex(9).draggable(true);

            //掉下动画
            ooA.animateType(MarkerOptions.MarkerAnimateType.drop);
            baidumap.clear();
            mMarkerA = (Marker) (baidumap.addOverlay(ooA));

        }
    }

    /**
     * listview 的适配器 显示地址信息
     */
    private class LocationAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return poiList.size();
        }

        @Override
        public Object getItem(int position) {
            return poiList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(mContext, R.layout.location_item, null);
                x.view().inject(viewHolder, convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tv_name.setText(poiList.get(position).name);
            viewHolder.tv_address.setText(poiList.get(position).address);
            return convertView;
        }


        class ViewHolder {
            @ViewInject(R.id.tv_location_name)
            private TextView tv_name;
            @ViewInject(R.id.tv_location_address)
            private TextView tv_address;
        }
    }
}
