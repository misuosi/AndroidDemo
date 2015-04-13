/*
 * Copyright (c) 2014 Myth Technology, Inc. All rights reserved.
 */
package com.mythos.demo.ui;

import java.lang.ref.SoftReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mythos.demo.R;
import com.mythos.demo.example.pagecache.BitmapUtils;
import com.mythos.demo.example.pagecache.HttpUtils;
import com.mythos.demo.example.pagecache.Images;
import com.mythos.demo.example.pagecache.PageCacheDatabaseHelper;
import com.mythos.demo.ui.view.ScroListView;
import com.mythos.demo.ui.view.ScroListView.IXListViewListener;

/**
 * Description :
 * 
 * 
 * <br>
 * <br>
 * Time : 2015年2月2日 下午11:00:22
 * 
 * @version 1.0
 * 
 * @since 1.0
 * 
 * @author HONG
 */
public class PageCacheActivity extends Activity implements IXListViewListener {

	/** 内存缓存 */
	private LruCache<String, Bitmap> bitmapLruCache;

	/** SQLiteOpenHelper */
	private PageCacheDatabaseHelper pageCacheDatabaseHelper;

	private ScroListView scroListView;
	private ImageAdapter imageAdapter;
	private List<Map<String, Object>> listMap;
	private Handler mHandler;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_page_cache);

		// 获得虚拟机的做大内存空间、以KB单位
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		// 取1/8的内存作为缓存大小
		final int maxCache = maxMemory / 8;
		//
		bitmapLruCache = new LruCache<String, Bitmap>(maxCache) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				// 获得缓存图片的大小，以KB单位
				return bitmap.getByteCount() / 1024;
			}
		};

		pageCacheDatabaseHelper = new PageCacheDatabaseHelper(this,
				"pageCacheDB.db3", null, 1);

		scroListView = (ScroListView) this.findViewById(R.id.listview_cache);
		listMap = new ArrayList<Map<String, Object>>();
		// 初始化数据
		initData();

		scroListView.setPullLoadEnable(true);
		imageAdapter = new ImageAdapter();
		scroListView.setAdapter(imageAdapter);
		imageAdapter.notifyDataSetChanged();
		scroListView.setXListViewListener(this);

		mHandler = new Handler();
	}

	/**
	 * Description : 自定义Adapter
	 * 
	 * 
	 * <br>
	 * <br>
	 * Time : 2015年2月3日 下午5:08:11
	 * 
	 * @version 1.0
	 * 
	 * @since 1.0
	 * 
	 * @author HONG
	 */
	class ImageAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return listMap.size();
		}

		@Override
		public Object getItem(int position) {
			return listMap.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LinearLayout view = null;
			if (convertView == null) {
				view = (LinearLayout) LayoutInflater.from(
						PageCacheActivity.this).inflate(
						R.layout.item_page_cache, null);
			} else {
				view = (LinearLayout) convertView;
			}

			ImageView imageView = (ImageView) view
					.findViewById(R.id.user_photo);
			TextView userNameTv = (TextView) view
					.findViewById(R.id.user_name_tv);
			TextView createDateTv = (TextView) view
					.findViewById(R.id.create_date_tv);
			TextView contentTv = (TextView) view.findViewById(R.id.content_tv);

			loadBitmap(String.valueOf(listMap.get(position).get("userPhoto")),
					imageView);
			userNameTv.setText(String.valueOf(listMap.get(position).get(
					"userName")));
			createDateTv.setText(String.valueOf(listMap.get(position).get(
					"createDate")));
			contentTv.setText(String.valueOf(listMap.get(position).get(
					"content")));

			return view;
		}

	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		Cursor cursor = pageCacheDatabaseHelper.getReadableDatabase().query(
				PageCacheDatabaseHelper.DATABASE_TABLE,
				new String[] { "user_photo", "username", "create_date",
						"content" }, null, null, null, null, null);
		if (cursor != null && cursor.getCount() != 0) {
			// cursor.moveToFirst();
			int userphoto = cursor.getColumnIndexOrThrow("user_photo");
			int username = cursor.getColumnIndexOrThrow("username");
			int createdate = cursor.getColumnIndexOrThrow("create_date");
			int content = cursor.getColumnIndexOrThrow("content");
			while (cursor.moveToNext()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userPhoto", cursor.getString(userphoto));
				map.put("userName", cursor.getString(username));
				map.put("createDate", cursor.getString(createdate));
				map.put("content", cursor.getString(content));
				listMap.add(map);
			}
		} else {
			//onRefresh();
			for (int i = 0; i < Images.image360.length; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userPhoto", Images.image360[i]);
				map.put("userName", "AAA" + i);
				map.put("createDate", "2015-2-4 0:53");
				map.put("content", "我是个图片缓存demo，写了很久，出了很多错误，写的不好，将就将就！");
				listMap.add(map);
			}
			addDataToSQLite(listMap);
		}
		// 关闭cursor
		cursor.close();
	}

	/**
	 * @param map
	 */
	public void addDataToSQLite(List<Map<String, Object>> map) {
		SQLiteDatabase db = pageCacheDatabaseHelper.getWritableDatabase();
		db.delete(PageCacheDatabaseHelper.DATABASE_TABLE, null, null);
		int sumRow = map.size() <= 25 ? map.size() : 25;
		for (int i = 0; i < sumRow; i++) {
			ContentValues newValues = new ContentValues();
			newValues.put("user_photo", (String) map.get(i).get("userPhoto"));
			newValues.put("username", (String) map.get(i).get("userName"));
			newValues.put("create_date", (String) map.get(i).get("createDate"));
			newValues.put("content", (String) map.get(i).get("content"));
			db.insert(PageCacheDatabaseHelper.DATABASE_TABLE, null, newValues);
		}
	}

	/**
	 * 添加图片到缓存中
	 * 
	 * @param key
	 *            图片的key
	 * @param bitmap
	 *            图片的value
	 */
	@SuppressLint("NewApi")
	public void addBitmapToMemCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemCache(key) == null) {
			bitmapLruCache.put(key, bitmap);
		}
	}

	/**
	 * 通过key值来获得内存缓存中的图片
	 * 
	 * @param key
	 * @return
	 */
	@SuppressLint("NewApi")
	public Bitmap getBitmapFromMemCache(String key) {
		return bitmapLruCache.get(key);
	}

	class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {

		/** 定义一个弱引用 */
		private final SoftReference<ImageView> SoftReference;
		/** 定义一个字符串 */
		private String data = "";

		public BitmapWorkerTask(ImageView imageView) {
			SoftReference = new SoftReference<ImageView>(imageView);
		}

		@Override
		protected Bitmap doInBackground(String... datas) {
			data = datas[0];
			byte[] imageByte = HttpUtils.loadImageFromPost(data);
			Bitmap bitmap = BitmapUtils.decodeBitmapFromByte(imageByte, 40, 40);
			// 添加图片到缓存
			addBitmapToMemCache(data, bitmap);
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			if (isCancelled()) {
				bitmap = null;
			}

			if (SoftReference != null && bitmap != null) {
				final ImageView imageView = SoftReference.get();
				final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
				if (this == bitmapWorkerTask && imageView != null) {
					imageView.setImageBitmap(bitmap);
				}
			}
		}

	}

	/**
	 * 下载图片
	 * 
	 * @param imagePath
	 * @param imageView
	 */
	public void loadBitmap(String imagePath, ImageView imageView) {
		// 检查缓存
		final Bitmap bitmap = getBitmapFromMemCache(imagePath);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		} else {
			Bitmap placeBitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.emoji_0);

			if (cancelPotentialWork(imagePath, imageView)) {
				final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
				final AsyncDrawable asyncDrawable = new AsyncDrawable(
						getResources(), placeBitmap, task);
				imageView.setImageDrawable(asyncDrawable);
				task.execute(imagePath);
			}
		}
	}

	static class AsyncDrawable extends BitmapDrawable {
		private final SoftReference<BitmapWorkerTask> bitmapWorkerTaskReference;

		public AsyncDrawable(Resources res, Bitmap bitmap,
				BitmapWorkerTask bitmapWorkerTask) {
			super(res, bitmap);
			bitmapWorkerTaskReference = new SoftReference<BitmapWorkerTask>(
					bitmapWorkerTask);
		}

		public BitmapWorkerTask getBitmapWorkerTask() {
			return bitmapWorkerTaskReference.get();
		}
	}

	public static boolean cancelPotentialWork(String imagePath,
			ImageView imageView) {
		final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

		if (bitmapWorkerTask != null) {
			final String bitmapData = bitmapWorkerTask.data;
			// If bitmapData is not yet set or it differs from the new data
			if (bitmapData != null || bitmapData != imagePath) {
				// Cancel previous task
				bitmapWorkerTask.cancel(true);
			} else {
				// The same work is already in progress
				return false;
			}
		}
		// No task associated with the ImageView, or an existing task was
		// cancelled
		return true;
	}

	private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
		if (imageView != null) {
			final Drawable drawable = imageView.getDrawable();
			if (drawable instanceof AsyncDrawable) {
				final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
				asyncDrawable.getBitmapWorkerTask();
			}
		}
		return null;
	}

	private void onLoad() {
		scroListView.stopRefresh();
		scroListView.stopLoadMore();
		scroListView.setRefreshTime(new SimpleDateFormat("yyyy-MM-dd hh:mm")
				.format(new Date()));
	}

	// 刷新
	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				listMap.clear();
				for (int i = 0; i < Images.image360.length; i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("userPhoto", Images.image360[i]);
					map.put("userName", "AAA" + i);
					map.put("createDate", "2015-2-6 0:53");
					map.put("content", "我是个图片缓存demo，写了很久，出了很多错误，写的不好，将就将就！....");
					listMap.add(map);
				}
				imageAdapter = new ImageAdapter();
				scroListView.setAdapter(imageAdapter);
				onLoad();
			}
		}, 2000);
		
		addDataToSQLite(listMap);
	}

	// 加载更多
	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 5; i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("userPhoto", Images.image360[i]);
					map.put("userName", "BBB" + i);
					map.put("createDate", "2015-2-7 0:53");
					map.put("content",
							"BBBBBBBBB我是个图片缓存demo，写了很久，出了很多错误，写的不好，将就将就！....");
					listMap.add(map);
				}
				imageAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}

	protected void onDestroy() {
		super.onDestroy();
		if (pageCacheDatabaseHelper != null) {
			pageCacheDatabaseHelper.close();
		}
	};
}
