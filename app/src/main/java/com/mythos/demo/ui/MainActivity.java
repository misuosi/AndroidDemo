/*
 * Copyright (c) 2014 Myth Technology, Inc. All rights reserved.
 */
package com.mythos.demo.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import cn.jpush.android.api.JPushInterface;

import com.mythos.demo.R;

/**
 * Description		: Activity主类
 * 
 * <br><br>Time		: 2015-2-2 下午8:12:25
 * 
 * @version 1.0
 * 
 * @since 1.0
 * 
 * @author YLM
 */
public class MainActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ListView demoList = (ListView) findViewById(android.R.id.list);
		
		
		List<String> list = new ArrayList<String>();
		list.add("百度地图");
		list.add("页面缓存");
		list.add("mina聊天");
		list.add("JPush");
		list.add("xUtils");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
		this.setListAdapter(adapter);
		
		demoList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch(position){
				case 0:
					Intent intent = new Intent(MainActivity.this, BaiduMapActivity.class);
					startActivity(intent);
					break;
				case 1:
					Intent intent1 = new Intent(MainActivity.this, PageCacheActivity.class);
					startActivity(intent1);
					break;
				case 2:
					Intent intent2 = new Intent(MainActivity.this, MinaChatActivity.class);
					startActivity(intent2);
					break;
				case 3:
					Intent intent3 = new Intent(MainActivity.this, JPushActivity.class);
					startActivity(intent3);
					break;
				case 4:
					Intent intent4 = new Intent(MainActivity.this, XUtilsActivity.class);
					startActivity(intent4);
					break;
				}
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		JPushInterface.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(this);
	}
}
