/*
 * Copyright (c) 2014 Myth Technology, Inc. All rights reserved.
 */
package com.mythos.demo.example.pagecache;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Description		: 
 * 
 * 
 * <br><br>Time		: 2015年2月3日  上午8:36:42
 * 
 * @version 1.0
 * 
 * @since 1.0
 * 
 * @author HONG
 */
public class HttpUtils {

	/**
	 * 通过post加载图片
	 * 
	 * @param imagePath
	 *            加载图片的URL
	 * @return
	 */
	public static byte[] loadImageFromPost(String imagePath) {
		HttpClient httpClient = new DefaultHttpClient();
		//HttpPost httpPost = new HttpPost(imagePath);
		HttpGet get = new HttpGet(imagePath);
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(get);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toByteArray(httpResponse.getEntity());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return null;
	}
}
