/*
 * Copyright (c) 2014 Myth Technology, Inc. All rights reserved.
 */
package com.mythos.demo.example.pagecache;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Description :
 * 
 * 
 * <br>
 * <br>
 * Time : 2015年2月3日 下午5:22:06
 * 
 * @version 1.0
 * 
 * @since 1.0
 * 
 * @author HONG
 */
public class PageCacheDatabaseHelper extends SQLiteOpenHelper {

	public static final String DATABASE_TABLE = "pagecache";
	private static final String CREATE_TABLE_SQL = "create table "
			+ DATABASE_TABLE
			+ "(_id integer primary "
			+ "key autoincrement , user_photo text, username text, create_date text, content text)";

	public PageCacheDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 第一次使用数据库时自动建表
		db.execSQL(CREATE_TABLE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
