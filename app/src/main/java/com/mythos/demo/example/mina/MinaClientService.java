/*
 * Copyright (c) 2014 Myth Technology, Inc. All rights reserved.
 */
package com.mythos.demo.example.mina;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mythos.demo.app.MyApplication;
import com.mythos.demo.common.config.HttpConfig;
import com.mythos.demo.common.constants.ActionConstants;

/**
 * Description		: 客户端IoHandler
 * 
 * 
 * <br><br>Time		: 2015-2-6  下午7:48:21
 * 
 * @version 1.0
 * 
 * @since 1.0
 * 
 * @author YLM
 */
public class MinaClientService extends Service {

	public static final String INTENT_KEY_MESSAGE = "INTENT_KEY_MESSAGE";
	
	private MyApplication myApplication;
	private IoConnector connector;
	private Handler handler = new Handler();
	
	@Override
	public void onCreate() {
		super.onCreate();
		myApplication = (MyApplication) getApplication();
		new Thread(){
        	public void run(){
                initMina();
        	}
        }.start();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(myApplication.session != null){
			//关闭session连接
			myApplication.session.close(true);
		}
		if(connector != null) {
	        //释放资源
	        connector.dispose();
		}
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	private void initMina() {
    	// 创建一个基于NIO的tcp/ip连接
        connector = new NioSocketConnector();
		// 创建接收数据的过滤器
        DefaultIoFilterChainBuilder chain = connector.getFilterChain();
        /*---------接收字符串---------*/
        //设置单行字符串的过滤器读取数据
        chain.addLast("textFilter", new ProtocolCodecFilter(new TextLineCodecFactory()));
        /*---------接收对象---------*/
        //设置以对象为单位的过滤器读取数据
        //ProtocolCodecFilter filter = new ProtocolCodecFilter(new ObjectSerializationCodecFactory());
        // 设定服务器端的消息处理器:一个SamplMinaServerHandler对象,
        //chain.addLast("objectFilter",filter);
 
        //允许读取服务器端传送过来的数据
        //SocketSessionConfig cfg = connector.getSessionConfig();  
        //cfg.setUseReadOperation(true); 
        
        // 设定服务器端的消息处理器:一个 SamplMinaServerHandler 对象,
        connector.setHandler(new MinaClientHanlder());
        // 设置超时时间.
        connector.setConnectTimeoutMillis(HttpConfig.MINA_TIMEOUT);
        //connector.setConnectTimeoutCheckInterval(HttpConfig.MINA_TIMEOUT);
        try{
        	// 连接到服务器:
            ConnectFuture cf = connector.connect(new InetSocketAddress(HttpConfig.MINA_IP,
            		HttpConfig.MINA_PORT));
            // 等待服务器响应，等待是否连接成功，相当于是转异步执行为同步执行。
            cf.awaitUninterruptibly();
            // 连接成功后获取会话对象。如果没有上面的等待，由于connect()方法是异步的，session可能会无法获取。  
            myApplication.session = cf.getSession();
        	//myApplication.session.getCloseFuture().awaitUninterruptibly();
        } catch(Exception e){
        	handler.post(new Runnable(){

				@Override
				public void run() {
					Toast.makeText(MinaClientService.this, "连接服务器失败", Toast.LENGTH_LONG).show();
				}
        		
        	});
        }
        
    }
	
	private class MinaClientHanlder extends IoHandlerAdapter {
		
	    // 当一个客端端连结进入时 @Override
	    public void sessionOpened(IoSession session) throws Exception {
	        System.out.println("sessionOpened:我登陆了！服务器地址： : "
	                + session.getRemoteAddress());
	    }
	 
	    // 当客户端发送的消息到达时:
	    @Override
	    public void messageReceived(IoSession session, Object message)
	            throws Exception {
	    	String s = (String) message;
	    	System.out.println("messageReceived"+s);
	    	Gson gson = new Gson();
	    	MinaChatMessage msg = gson.fromJson(s, MinaChatMessage.class);
	    	
	    	Intent intent = new Intent(ActionConstants.ACTION_RECEIVE_MESSAGE);
	    	intent.putExtra(INTENT_KEY_MESSAGE, msg);
	    	sendBroadcast(intent);
	    	
	    	/*Map<String, Object> map = (Map<String, Object>)message;
	        System.out.println(map.get("reply"));*/
	 
	    }
	 
	    // 当信息已经传送给服务器后触发此方法.
	    @Override
	    public void messageSent(IoSession session, Object message) {
	        System.out.println("messageSent:信息已经发送给服务器");
	 
	    }
	 
	    // 当一个客户端关闭时
	    @Override
	    public void sessionClosed(IoSession session) {
	        System.out.println("sessionClosed:本客户端断开连接");
	    }
	 
	    // 当连接空闲时触发此方法.
	    @Override
	    public void sessionIdle(IoSession session, IdleStatus status) {
	        System.out.println("sessionIdle:连接空闲");
	    }
	 
	    // 当接口中其他方法抛出异常未被捕获时触发此方法
	    @Override
	    public void exceptionCaught(IoSession session, Throwable cause) {
	        System.out.println("exceptionCaught:其他方法抛出异常");
	    }
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}