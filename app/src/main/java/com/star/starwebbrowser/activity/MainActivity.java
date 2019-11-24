package com.star.starwebbrowser.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.widget.Button;
import com.star.library.jsbridge.BridgeHandler;
import com.star.library.jsbridge.BridgeWebView;
import com.star.library.jsbridge.CallBackFunction;
import com.star.library.jsbridge.DefaultHandler;
import com.google.gson.Gson;
import com.star.starwebbrowser.R;
import com.star.starwebbrowser.save.SPUtils;
import com.star.starwebbrowser.service.HttpServer;
import fi.iki.elonen.util.ServerRunner;

public class MainActivity extends SuperActivity implements View.OnClickListener {
    private final String TAG = "MainActivity";
    BridgeWebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (BridgeWebView) findViewById(R.id.webView);
        /* ** 配置浏览器缓存*/
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        webView.getSettings().setAppCachePath(appCachePath);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.getSettings().setAppCacheEnabled(true);
        /* ** 配置浏览器缓存*/
        webView.setDefaultHandler(new DefaultHandler());
        webView.setWebChromeClient(new WebChromeClient() {

        });
        // webView.loadUrl("file:///android_asset/start.html");
        webView.loadUrl("file:///android_asset/start.html");
        // webView.loadUrl("http://122.193.27.194:2000/PDAInspection/AppH5/start.html");
        //webView.loadUrl("http://192.168.1.58:8017/start.html");
        /* * ** 注册供 JS调用的 ScanQR 打开二维码扫描界面** **/
        webView.registerHandler("ScanQR", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                function.onCallBack("Response_sdn"); //响应JS请求
            }
        });

        /**
         * js调用获取手机端VERSION版本号，用于软件更新
         */
        webView.registerHandler("GetVersion", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                function.onCallBack(MainActivity.this.app.VERSION);//拿到对应的app VERSION版本号
            }
        });

        webView.send("start");

        //启动http服务监听请求
        ServerRunner.run(HttpServer.class);
    }




}
