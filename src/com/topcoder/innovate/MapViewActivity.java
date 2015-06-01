package com.topcoder.innovate;

import java.util.Iterator;
import java.util.List;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.topcoder.innovate.model.PosMsg;
import com.topcoder.innovate.model.Speaker;
import com.topcoder.innovate.util.DataRetriever;
import com.topcoder.innovate.util.PositionRetriever;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class MapViewActivity extends Activity {

	private MapView mapView;
	private BaiduMap mBaiduMap;
	private List<PosMsg> listPosMsg;
	private BitmapDescriptor bitmap;
	private final double moveLongitude = 238.811123;
	private final double moveLagitude = 2.131202;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_mapviewactivity);

		mapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mapView.getMap();
		//构建Marker图标  
	    bitmap = BitmapDescriptorFactory.fromResource(R.drawable.u);
		


		//Log.i("ning", "go to set center");

		LatLng centerPos = new LatLng(37.783753+moveLagitude, -122.401192+moveLongitude);
		MapStatus mMapStatus = new MapStatus.Builder()
	        .target(centerPos)
	        .zoom(16)
	        .build();
		 
		 //Log.i("ning", "go to set center to map");
		 MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
	        //改变地图状态
	     mBaiduMap.setMapStatus(mMapStatusUpdate);
	     
	     
	     mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker arg0) {
				// TODO Auto-generated method stub
				String name = arg0.getExtraInfo().getString("name");
				String address = arg0.getExtraInfo().getString("address");
				displayToast(name + " " + address);
				return false;
			}
		});

	     
	     String url = getResources().getString(R.string.web_position);
	     listPosMsg = new PositionRetriever().retrieveAllPos(this);
	     
	     for (Iterator<PosMsg> it = listPosMsg.iterator(); it.hasNext(); ){
	    	 PosMsg tmp = it.next();
	    	 LatLng pos = new LatLng(tmp.getLatitude()+moveLagitude,
	    			 tmp.getLongitude()+moveLongitude);
	    	 Bundle extraMsg = new Bundle();
		     extraMsg.putString("name", tmp.getName());
		     extraMsg.putString("address", tmp.getAddress());
		     //构建MarkerOption，用于在地图上添加Marker  
		     OverlayOptions option = new MarkerOptions()  
		         .position(pos)  
		         .icon(bitmap)
		         .extraInfo(extraMsg);  
		     
		     //在地图上添加Marker，并显示  
		     mBaiduMap.addOverlay(option);
	     }
	     

	}

	protected void displayToast(String string) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
	}

}
