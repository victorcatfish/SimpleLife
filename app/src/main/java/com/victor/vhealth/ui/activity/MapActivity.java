package com.victor.vhealth.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.base.ContentBaseFragment;
import com.victor.vhealth.factory.CustomFragmentFactory;
import com.victor.vhealth.ui.fragment.detail.CompanyDetailFragment;
import com.victor.vhealth.ui.fragment.detail.HospitalDetailFragment;
import com.victor.vhealth.ui.fragment.detail.PharmacyDetailFragment;

public class MapActivity extends AppCompatActivity {

    public static final String MAP_CLASSIFY_KEY = "map_classify_key";
    public static final String Y_COORDINATE = "y_coordinate";
    public static final String X_COORDINATE = "x_coordinate";
    public static final String HOSPITAL_NAME = "hospital_name";

    @ViewInject(R.id.tool_bar)
    private Toolbar mToolbar;

    SupportMapFragment mMapFragment;
    private ContentBaseFragment mFragment;
    private FragmentManager mFm;
    private OverlayOptions mOptions;
    private String mHospitalName;
    private LatLng mBaiduLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ViewUtils.inject(this);
        mFm = getSupportFragmentManager();

        initFragment();
        initMap();
        initToolbar();
    }

    private void initMap() {
        Intent intent = getIntent();
        mHospitalName = intent.getStringExtra(HOSPITAL_NAME);
        MapStatus.Builder builder = new MapStatus.Builder();
        if (intent.hasExtra(X_COORDINATE) && intent.hasExtra(Y_COORDINATE)) {
            // 当用intent参数时，设置中心点为指定点
            Bundle b = intent.getExtras();
            // 坐标转换
            LatLng location = new LatLng(b.getFloat(Y_COORDINATE), b.getFloat(X_COORDINATE));
            CoordinateConverter converter  = new CoordinateConverter();
            converter.from(CoordinateConverter.CoordType.COMMON);

            converter.coord(location);
            mBaiduLocation = converter.convert();

            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
            mOptions = new MarkerOptions().position(mBaiduLocation).icon(bitmap);
            builder.target(mBaiduLocation);
        }
        builder.overlook(-20).zoom(15);
        BaiduMapOptions bo = new BaiduMapOptions().mapStatus(builder.build())
                .compassEnabled(false).zoomControlsEnabled(false);
        mMapFragment = SupportMapFragment.newInstance(bo);
        mFm.beginTransaction().add(R.id.fl_map, mMapFragment, "map_fragment").commit();
    }

    private void initFragment() {
        Intent intent = getIntent();
        int id = intent.getIntExtra(ContentBaseFragment.DATA_ID, -1);
        String classifyKey = intent.getStringExtra(MAP_CLASSIFY_KEY);
        mFragment = CustomFragmentFactory.createDetailFragment(classifyKey, id);

        FragmentTransaction ft = mFm.beginTransaction();
        ft.replace(R.id.fl_content_with_map, mFragment);
        ft.commitAllowingStateLoss();
    }


    /**初始化详情页的toolbar*/
    private void initToolbar() {
        if (mFragment instanceof HospitalDetailFragment) {
            mToolbar.setTitle("医院信息");
        } else if (mFragment instanceof PharmacyDetailFragment) {
            mToolbar.setTitle("药店信息");
        } else if (mFragment instanceof CompanyDetailFragment) {
            mToolbar.setTitle("药企信息");
        }
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BaiduMap baiduMap = mMapFragment.getBaiduMap();
        if (mOptions != null) {
            baiduMap.addOverlay(mOptions);
        }

        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                MapView mapView = mMapFragment.getMapView();
                showPopupWindow(mapView);
                return false;
            }
        });

    }

    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.pop_window, null);
        TextView tvName = (TextView) contentView.findViewById(R.id.tv_hospital_name);
        Button btnNavi = (Button) contentView.findViewById(R.id.btn_navi);
        tvName.setText(mHospitalName);
        // 设置按钮的点击事件
        btnNavi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startBaiduMapApp();
            }
        });

        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.selectmenu_bg_downward));

        // 设置好参数之后再show
        popupWindow.showAsDropDown(view, 0, - view.getHeight() / 2);
    }

    private void startBaiduMapApp() {

        Intent intent = new Intent();
        intent.setData(Uri.parse("baidumap://map/marker?" +
                "location=" + mBaiduLocation.latitude + "," + mBaiduLocation.longitude +
                "&title=" + mHospitalName +"&traffic=on"));
        startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
