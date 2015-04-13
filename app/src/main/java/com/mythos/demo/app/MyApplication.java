package com.mythos.demo.app;

import org.apache.mina.core.session.IoSession;

import cn.jpush.android.api.JPushInterface;

import android.app.Application;


public class MyApplication extends Application {

	public IoSession session;
	
	@Override
	public void onCreate() {
		super.onCreate();

        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
	}
	
}
