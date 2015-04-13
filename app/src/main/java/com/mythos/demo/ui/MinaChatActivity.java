/*
 * Copyright (c) 2014 Myth Technology, Inc. All rights reserved.
 */
package com.mythos.demo.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mythos.demo.R;
import com.mythos.demo.app.MyApplication;
import com.mythos.demo.common.config.HttpConfig;
import com.mythos.demo.common.constants.ActionConstants;
import com.mythos.demo.common.constants.DateConstants;
import com.mythos.demo.example.mina.MinaChatMessage;
import com.mythos.demo.example.mina.MinaChatViewAdapter;
import com.mythos.demo.example.mina.MinaClientService;

/**
 * Description		: 使用mina网络通信框架实现聊天功能
 * 
 * <br><br>Time		: 2015-2-3  上午1:33:28
 * 
 * @version 1.0
 * 
 * @since 1.0
 * 
 * @author YLM
 */
public class MinaChatActivity extends Activity implements OnClickListener{

	private MyApplication myApplication;
	
	private Button mBtnSend;
	private Button mBtnBack;
	private EditText mEditTextContent;
	private ListView mListView;
	private MinaChatViewAdapter mAdapter;
	private List<MinaChatMessage> mDataArrays = new ArrayList<MinaChatMessage>();
	
	Intent serviceIntent;
	
	private IntentFilter intentFilter = new IntentFilter(ActionConstants.ACTION_RECEIVE_MESSAGE);
    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			MinaChatMessage message = (MinaChatMessage) intent.getExtras().get(MinaClientService.INTENT_KEY_MESSAGE);
			mDataArrays.add(message);
			mAdapter.notifyDataSetChanged();
			
			mListView.setSelection(mListView.getCount() - 1);
		}
    	
    };
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mina_chat);
        //启动activity时不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
        myApplication = (MyApplication) getApplication();
        initView();
        initAdapter();
        
        serviceIntent = new Intent(this, MinaClientService.class);
        startService(serviceIntent);
        
    }
    
    private void initView()
    {
    	mListView = (ListView) findViewById(R.id.lv_messagelist);
    	mBtnSend = (Button) findViewById(R.id.btn_sendmessage);
    	mBtnSend.setOnClickListener(this);
    	mBtnBack = (Button) findViewById(R.id.btn_back);
    	mBtnBack.setOnClickListener(this);
    	mEditTextContent = (EditText) findViewById(R.id.et_editmessage);
    }
    
    
	
    private void initAdapter() {

    	mAdapter = new MinaChatViewAdapter(this, mDataArrays);
		mListView.setAdapter(mAdapter);
		
    }
    
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btn_sendmessage:
			send();
			break;
		case R.id.btn_back:
			finish();
			break;
		}
	}
	
	private void send() {
		String contString = mEditTextContent.getText().toString();
		if (contString.length() > 0)
		{
			MinaChatMessage message = new MinaChatMessage();
			message.setTime(getDate());
			message.setIsFromMe(MinaChatMessage.Me);
			message.setContent(contString);
			
			mDataArrays.add(message);
			mAdapter.notifyDataSetChanged();
			
			mEditTextContent.setText("");
			
			mListView.setSelection(mListView.getCount() - 1);
			new SendAsyncTask().execute(message);
		}
	}
	
	private String getDate() {
    	SimpleDateFormat sdf = new SimpleDateFormat(DateConstants.DATE_FORMAT_YYYYMMDDHHMM);
    	return sdf.format(new Date());
    }
    
    private class SendAsyncTask extends AsyncTask<MinaChatMessage, Void, Boolean>{

		@Override
		protected Boolean doInBackground(MinaChatMessage... message) {
			Gson gson = new Gson();
			String jsonMessage = gson.toJson(message[0]);
			if(myApplication.session != null && myApplication.session.write(jsonMessage).awaitUninterruptibly(HttpConfig.MINA_TIMEOUT)){
				return true;
			}
			return false;
			
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if(result){
				Toast.makeText(MinaChatActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(MinaChatActivity.this, "服务器错误，发送失败", Toast.LENGTH_LONG).show();
			}
		}
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
        registerReceiver(messageReceiver, intentFilter);
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	unregisterReceiver(messageReceiver);
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	stopService(serviceIntent);
    }
    
}
