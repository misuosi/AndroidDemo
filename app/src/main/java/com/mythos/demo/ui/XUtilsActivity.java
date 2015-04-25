package com.mythos.demo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.mythos.demo.R;

import java.io.File;

public class XUtilsActivity extends Activity {

    private TextView showPageCode;

    private ImageView showimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xutils);

        showPageCode = (TextView) this.findViewById(R.id.tv_showpagecode);
        showimage = (ImageView) this.findViewById(R.id.tv_showimage);

        HttpUtils httpGet = new HttpUtils();
        httpGet.send(HttpRequest.HttpMethod.GET, "http://www.baidu.com", new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> objectResponseInfo) {
                showPageCode.setText(objectResponseInfo.result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                showPageCode.setText("出错了");
            }
        });

        showPageCode.setOnClickListener(new View.OnClickListener() {
            boolean flag = true;
            HttpHandler handler;
            @Override
            public void onClick(View view) {
                HttpUtils http = new HttpUtils();
                if (flag) {
                    handler = http.download("http://bm.gduf.edu.cn/bm2/siteserver/cms/background_utils.aspx?type=Redirect&download=True&publishmentSystemID=406&nodeID=413&contentID=1843",
                            "/sdcard/23101945105.doc",
                            true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
                            false, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
                            new RequestCallBack<File>() {

                                @Override
                                public void onStart() {
                                    showPageCode.setText("conn...");
                                }

                                @Override
                                public void onLoading(long total, long current, boolean isUploading) {
                                    showPageCode.setText(current + "/" + total);
                                }

                                @Override
                                public void onSuccess(ResponseInfo<File> responseInfo) {
                                    showPageCode.setText("downloaded:" + responseInfo.result.getPath());
                                    if (new File("/sdcard/23101945105.doc").delete()) {
                                        showPageCode.setText(showPageCode.getText() + ",deleted 1");
                                    }
                                    if (new File(responseInfo.result.getPath()).delete()) {
                                        showPageCode.setText(showPageCode.getText() + ",deleted 2");
                                    }
                                }


                                @Override
                                public void onFailure(HttpException error, String msg) {
                                    showPageCode.setText(msg);
                                }
                            });
                    flag = false;
                } else if (handler != null) {
                    //调用pause()方法暂停下载
                    handler.pause();
                    flag = true;
                }

            }
        });

        // 加载网络图片
        BitmapUtils bitmapUtils = new BitmapUtils(this);
        bitmapUtils.display(showimage, "http://bbs.lidroid.com/static/image/common/logo.png");

    }

}
