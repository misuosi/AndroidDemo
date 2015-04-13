/*
 * Copyright (c) 2014 Myth Technology, Inc. All rights reserved.
 */
package com.mythos.demo.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerDragListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Gradient;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.HeatMap;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.mythos.demo.R;

/**
 * Description		: 百度地图demo
 * 
 * <br><br>Time		: 2015-2-2 下午8:12:25
 * 
 * @version 1.0
 * 
 * @since 1.0
 * 
 * @author YLM
 */
public class BaiduMapActivity extends Activity {

	private static final String Tag = "BaiduMapActivity";
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private PoiSearch mPoiSearch;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//在使用SDK各组件之前初始化context信息，传入ApplicationContext  
        //注意该方法要再setContentView方法之前实现  
        SDKInitializer.initialize(getApplicationContext()); 
		setContentView(R.layout.activity_baidu_map);
		
		//获取地图控件引用  
        mMapView = (MapView) findViewById(R.id.abm_view_baidu_map);
        mBaiduMap = mMapView.getMap();  
        //普通地图  (默认)
        //mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);  
        //卫星地图  
        //mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);

        //开启交通图   
        //mBaiduMap.setTrafficEnabled(true);
        
        //开启交通图   
        //mBaiduMap.setBaiduHeatMapEnabled(true);
        
        /* 开发者可根据自己实际的业务需求，利用标注覆盖物，在地图指定的位置上添加标注信息 */
        setOverlay();
        
        /* 针对已经添加在地图上的标注，可设置进行手势拖拽 */
        setDragOverlay();
        
        /* 地图SDK提供多种结合图形覆盖物，利用这些图形，
         * 可帮助您构建更加丰富多彩的地图应用。目前提供的几何图形有：
         * 点（Dot）、折线（Polyline）、弧线（Arc）、圆（Circle）、多边形（Polygon）  */
        setGraphOverlay();
        
        /* 文字，在地图中也是一种覆盖物，开发者可利用相关的接口，快速实现在地图上书写文字的需求。 */
        setTextOverlay();
	
        /* 弹出窗覆盖物的实现方式如下，开发者可利用此接口，构建具有更强交互性的地图页面。 */
        setInfoWindowOverlay();
        
        /* 地形图图层（GroundOverlay），又可叫做图片图层，
         * 即开发者可在地图的指定位置上添加图片。该图片可随地图的平移、
         * 缩放、旋转等操作做相应的变换。该图层是一种特殊的Overlay， 
         * 它位于底图和底图标注层之间（即该图层不会遮挡地图标注信息）。 */
        setGroundOverlay();

		/* 热力图是用不同颜色的区块叠加在地图上描述人群分布、
		 * 密度和变化趋势的一个产品，百度地图SDK将绘制热力图
		 * 的能力为广大开发者开放，帮助开发者利用自有数据，构建
		 * 属于自己的热力图，提供丰富的展示效果。 */
		setHeatMap();
		
		/* POI（Point of Interest），中文可以翻译为“兴趣点”。
		 * 在地理信息系统中，一个POI可以是一栋房子、一个商铺、一个邮筒、一个公交站等。
		 * 百度地图SDK提供三种类型的POI检索：周边检索、区域检索和城市内检索。
		 */
		setPoisearch();
	}
	
	/**
	 * POI（Point of Interest），中文可以翻译为“兴趣点”。
	 * 在地理信息系统中，一个POI可以是一栋房子、一个商铺、一个邮筒、一个公交站等。
	 * 百度地图SDK提供三种类型的POI检索：周边检索、区域检索和城市内检索。
	 */
	private void setPoisearch(){
		//第一步，创建POI检索实例
		mPoiSearch = PoiSearch.newInstance();
		//第二步，创建POI检索监听者；
		OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener(){  
		    public void onGetPoiResult(PoiResult result){  
		    	//获取POI检索结果  
		    	List<PoiInfo> pois = result.getAllPoi();
		    	for(PoiInfo poi : pois){
		    		Log.i(Tag, poi.address);
		    	}
		    }  
		    public void onGetPoiDetailResult(PoiDetailResult result){  
		    	//获取Place详情页检索结果  
		    }  
		};
		//第三步，设置POI检索监听者；
		mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
		//第四步，发起检索请求；
		mPoiSearch.searchInCity((new PoiCitySearchOption())  
		    .city("广州")  
		    .keyword("美食")  
		    .pageNum(10));
		//第五步，释放POI检索实例；
		//mPoiSearch.destroy();
	}

	/**
	 * 热力图是用不同颜色的区块叠加在地图上描述人群分布、密度和变化
	 * 趋势的一个产品，百度地图SDK将绘制热力图的能力为广大开发者
	 * 开放，帮助开发者利用自有数据，构建属于自己的热力图，提供丰富的展示效果。
	 */
	private void setHeatMap(){
		//第一步，设置颜色变化：
		//设置渐变颜色值
		int[] DEFAULT_GRADIENT_COLORS = {Color.rgb(102, 225,  0), Color.rgb(255, 0, 0) };
		//设置渐变颜色起始值
		float[] DEFAULT_GRADIENT_START_POINTS = { 0.2f, 1f };
		//构造颜色渐变对象
		Gradient gradient = new Gradient(DEFAULT_GRADIENT_COLORS, DEFAULT_GRADIENT_START_POINTS);
		//第二步，准备数据：
		//以下数据为随机生成地理位置点，开发者根据自己的实际业务，传入自有位置数据即可
		List<LatLng> randomList = new ArrayList<LatLng>();
		Random r = new Random();
		for (int i = 0; i < 500; i++) {
		    // 116.220000,39.780000 116.570000,40.150000
		    int rlat = r.nextInt(370000);
		    int rlng = r.nextInt(370000);
		    int lat = 39780000 + rlat;
		    int lng = 116220000 + rlng;
		    LatLng ll = new LatLng(lat / 1E6, lng / 1E6);
		    randomList.add(ll);
		}
		//第三步，添加、显示热力图：
		//在大量热力图数据情况下，build过程相对较慢，建议放在新建线程实现
		HeatMap heatmap = new HeatMap.Builder()
		    .data(randomList)
		    .gradient(gradient)
		    .build();
		//在地图上添加热力图
		mBaiduMap.addHeatMap(heatmap);
		//第四步，删除热力图：
		//heatmap.removeHeatMap();
	}

	/**
	 * 地形图图层（GroundOverlay），又可叫做图片图层，
	 * 即开发者可在地图的指定位置上添加图片。该图片可随地图的平移、
	 * 缩放、旋转等操作做相应的变换。该图层是一种特殊的Overlay， 
	 * 它位于底图和底图标注层之间（即该图层不会遮挡地图标注信息）。
	 */
	private void setGroundOverlay(){
		//定义Ground的显示地理范围  
		LatLng southwest = new LatLng(39.92235, 116.380338);  
		LatLng northeast = new LatLng(39.947246, 116.414977);  
		LatLngBounds bounds = new LatLngBounds.Builder()  
		    .include(northeast)  
		    .include(southwest)  
		    .build();  
		//定义Ground显示的图片  
		BitmapDescriptor bdGround = BitmapDescriptorFactory  
		    .fromResource(R.drawable.cancel_n);  
		//定义Ground覆盖物选项  
		OverlayOptions ooGround = new GroundOverlayOptions()  
		    .positionFromBounds(bounds)  
		    .image(bdGround)  
		    .transparency(0.8f);  
		//在地图中添加Ground覆盖物  
		mBaiduMap.addOverlay(ooGround);
		
	}

	/**
	 * 弹出窗覆盖物的实现方式如下，开发者可利用此接口，构建具有更强交互性的地图页面。
	 */
	private void setInfoWindowOverlay(){
		//创建InfoWindow展示的view  
		Button button = new Button(getApplicationContext());  
		button.setBackgroundResource(R.drawable.ing);  
		//定义用于显示该InfoWindow的坐标点  
		LatLng pt = new LatLng(39.86923, 116.397428);  
		//创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量 
		InfoWindow mInfoWindow = new InfoWindow(button, pt, -30);  
		//显示InfoWindow  
		mBaiduMap.showInfoWindow(mInfoWindow);
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "米所思", Toast.LENGTH_SHORT).show();
			}
		});
	}

	/**
	 * 文字，在地图中也是一种覆盖物，开发者可利用相关的接口，快速实现在地图上书写文字的需求。
	 */
	private void setTextOverlay(){
		//定义文字所显示的坐标点  
		LatLng llText = new LatLng(39.86923, 116.397428);  
		//构建文字Option对象，用于在地图上添加文字  
		OverlayOptions textOption = new TextOptions()  
		    .bgColor(0xAAFFFF00)  
		    .fontSize(24)  
		    .fontColor(0xFFFF00FF)  
		    .text("米所思")  
		    .rotate(-30)  
		    .position(llText);  
		//在地图上添加该文字对象并显示  
		mBaiduMap.addOverlay(textOption);
	}
	
	/**
	 * 地图SDK提供多种结合图形覆盖物，利用这些图形，
	 * 可帮助您构建更加丰富多彩的地图应用。目前提供的几何图形有：
	 * 点（Dot）、折线（Polyline）、弧线（Arc）、圆（Circle）、多边形（Polygon）
	 */
	private void setGraphOverlay(){
		//定义多边形的五个顶点  
		LatLng pt1 = new LatLng(39.93923, 116.357428);  
		LatLng pt2 = new LatLng(39.91923, 116.327428);  
		LatLng pt3 = new LatLng(39.89923, 116.347428);  
		LatLng pt4 = new LatLng(39.89923, 116.367428);  
		LatLng pt5 = new LatLng(39.91923, 116.387428);  
		List<LatLng> pts = new ArrayList<LatLng>();  
		pts.add(pt1);  
		pts.add(pt2);  
		pts.add(pt3);  
		pts.add(pt4);  
		pts.add(pt5);  
		//构建用户绘制多边形的Option对象  
		OverlayOptions polygonOption = new PolygonOptions()  
		    .points(pts)  
		    .stroke(new Stroke(5, 0xAA00FF00))  
		    .fillColor(0xAAFFFF00);  
		//在地图上添加多边形Option，用于显示  
		mBaiduMap.addOverlay(polygonOption);
	}
	
	/**
	 * 针对已经添加在地图上的标注，可设置进行手势拖拽
	 */
	private void setDragOverlay(){
		//第一步，设置可拖拽：
		//定义Maker坐标点  
		LatLng point = new LatLng(39.963175, 116.410244);  
		//构建Marker图标  
		BitmapDescriptor bitmap = BitmapDescriptorFactory  
		    .fromResource(R.drawable.girl);  
		OverlayOptions options = new MarkerOptions()
		    .position(point)  //设置marker的位置
		    .icon(bitmap)  //设置marker图标
		    .zIndex(9)  //设置marker所在层级
		    .draggable(true);  //设置手势拖拽
		//将marker添加到地图上
		Marker marker = (Marker) (mBaiduMap.addOverlay(options));
		//第二步，设置监听方法：
		//调用BaiduMap对象的setOnMarkerDragListener方法设置marker拖拽的监听
		mBaiduMap.setOnMarkerDragListener(new OnMarkerDragListener() {
		    public void onMarkerDrag(Marker marker) {
		        //拖拽中
		    }
		    public void onMarkerDragEnd(Marker marker) {
		        //拖拽结束
		    }
		    public void onMarkerDragStart(Marker marker) {
		        //开始拖拽
		    }
		});
		//针对已添加在地图上的标注覆盖物，可利用如下方法进行修改和删除操作：
		//marker.remove();   //调用Marker对象的remove方法实现指定marker的删除
	}
	
	/**
	 * 开发者可根据自己实际的业务需求，利用标注覆盖物，在地图指定的位置上添加标注信息
	 */
	private void setOverlay(){
		//定义Maker坐标点  
		LatLng point = new LatLng(39.963175, 116.400244);  
		//构建Marker图标  
		BitmapDescriptor bitmap = BitmapDescriptorFactory  
		    .fromResource(R.drawable.boy);  
		//构建MarkerOption，用于在地图上添加Marker  
		OverlayOptions option = new MarkerOptions()  
		    .position(point)  
		    .icon(bitmap);  
		//在地图上添加Marker，并显示  
		mBaiduMap.addOverlay(option);
	}
	
	@Override  
    protected void onDestroy() {  
        super.onDestroy();  
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理  
        mMapView.onDestroy();  
    } 
	
    @Override  
    protected void onResume() {  
        super.onResume();  
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
        mMapView.onResume();  
    }

    @Override  
    protected void onPause() {  
        super.onPause();  
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
        mMapView.onPause();  
    }  

}
