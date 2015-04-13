/*
 * Copyright (c) 2014 Myth Technology, Inc. All rights reserved.
 */
package com.mythos.demo.example.mina;

import java.io.Serializable;

/**
 * Description		: 消息实体类
 * 
 * 
 * <br><br>Time		: 2015-2-7  下午7:31:16
 * 
 * @version 1.0
 * 
 * @since 1.0
 * 
 * @author YLM
 */
public class MinaChatMessage implements Serializable {
	
	public static final int Me = 1;
	public static final int OTHER = 0;
	private String content;
	private String time;
	private int isFromMe;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getIsFromMe() {
		return isFromMe;
	}
	public void setIsFromMe(int isFromMe) {
		this.isFromMe = isFromMe;
	}
}
