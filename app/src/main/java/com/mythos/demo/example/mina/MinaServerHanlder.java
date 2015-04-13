/*
 * Copyright (c) 2014 Myth Technology, Inc. All rights reserved.
 */
package com.mythos.demo.example.mina;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.google.gson.Gson;
import com.mythos.demo.common.constants.DateConstants;


/**
 * Description		: 服务器IoHandler，本类写在服务器端
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
public class MinaServerHanlder extends IoHandlerAdapter {
	private int count = 0;
	private IoAcceptor acceptor = null;
	private Map<Long, IoSession> sessionMap = null;
	
	public MinaServerHanlder(IoAcceptor acceptor) {
		if(acceptor == null) {
			this.acceptor = new NioSocketAcceptor();
	        DefaultIoFilterChainBuilder chain = this.acceptor.getFilterChain();
	        chain.addLast("textFilter", new ProtocolCodecFilter(new TextLineCodecFactory()));
	        this.acceptor.setHandler(this);
		} else {
			this.acceptor = acceptor;
		}
		sessionMap = this.acceptor.getManagedSessions();
	}
	
    // 当一个新客户端连接后触发此方法.
    @Override
    public void sessionCreated(IoSession session) {
        System.out.println("新客户端连接");
    }
 
    // 当一个客户端进入时 
    @Override
    public void sessionOpened(IoSession session) throws Exception {
        count++;
        System.out.println("第" + count + "个 client 登陆！");
        System.out.println("IP地址"  + session.getRemoteAddress());
        System.out.println("sessionID："  + session.getId());
               
 
    }
 
    // 当客户端发送的消息到达时:
    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {
        //我们己设定了服务器解析消息的规则是一行一行字符串读取,这里就可转为String:
        String s = (String) message;
        System.out.println("收到客户机发来的消息: " + s);
        //测试将消息回送给客户端 
        Gson gson = new Gson();
        MinaChatMessage msg = new MinaChatMessage();
        msg.setTime(getDate());
        msg.setContent(gson.fromJson(s, MinaChatMessage.class).getContent());
        msg.setIsFromMe(MinaChatMessage.OTHER);
        String sendMessage = gson.toJson(msg);
        Set<Long> keySet = sessionMap.keySet();
        long id = session.getId();
        for(Long key : keySet){
        	if(key != id){
            	IoSession otherSession = sessionMap.get(key);
        		otherSession.write(sendMessage);
        	}
        }
 
        //传输对象
	    /*Map<String, Object> msg = (Map<String, Object>) message;
	    System.out.println(msg.get("message"));
	    msg.put("reply", "hi");
        session.write(msg);*/
 
    }
 
    // 当信息已经传送给客户端后触发此方法.
    @Override
    public void messageSent(IoSession session, Object message) {
        System.out.println("信息已经发送给客户端"+ session.getId());
 
    }
 
    // 当一个客户端关闭时
    @Override
    public void sessionClosed(IoSession session) {
        System.out.println("一个客户端断开连接,sessionID:"+ session.getId());
    }
 
    // 当连接空闲时触发此方法.
    @Override
    public void sessionIdle(IoSession session, IdleStatus status) {
        System.out.println("连接空闲");
    }
 
    // 当接口中其他方法抛出异常未被捕获时触发此方法
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        System.out.println("其他方法抛出异常");
    }
    
    private String getDate() {
    	SimpleDateFormat sdf = new SimpleDateFormat(DateConstants.DATE_FORMAT_YYYYMMDDHHMM);
    	return sdf.format(new Date());
    }
}
