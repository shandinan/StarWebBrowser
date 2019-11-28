package com.star.starwebbrowser.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.*;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.widget.Button;
import android.widget.Toast;
import com.star.library.jsbridge.BridgeHandler;
import com.star.library.jsbridge.BridgeWebView;
import com.star.library.jsbridge.CallBackFunction;
import com.star.library.jsbridge.DefaultHandler;
import com.google.gson.Gson;
import com.star.starwebbrowser.R;
import com.star.starwebbrowser.event.AbstractDelegation;
import com.star.starwebbrowser.event.EventHandler;
import com.star.starwebbrowser.event.MainHandler;
import com.star.starwebbrowser.save.SPUtils;
import com.star.starwebbrowser.service.HttpServer;
import fi.iki.elonen.util.ServerRunner;

import java.io.IOException;

public class MainActivity extends SuperActivity implements View.OnClickListener {
    private final String TAG = "MainActivity";
    BridgeWebView webView;
    HttpServer httpServer;//http服务
    Handler mainHandler; //主handle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        //支持javascript
        webView.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webView.getSettings().setSupportZoom(true);
       // 设置出现缩放工具
        webView.getSettings().setBuiltInZoomControls(true);
         //扩大比例的缩放
        webView.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        //按比例缩放
       // webView.setInitialScale(190);
       // webView.loadUrl("file:///android_asset/start.html");
        webView.loadUrl("http://50.79.98.190:9001/");

        /* * ** 注册供 JS调用的 ScanQR 打开二维码扫描界面** **/
        webView.registerHandler("InitShowMsg", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //data js请求java参数
                //拼接返回值
                String strResJson="{\"title\":\"请先配置基础数据\",\"photo\":\"staticfile/images/photo.png\",\"gh\":\"123456\",\"winnum\":\"1\",\"queue\":\"1888\"}";
                String tmp_title = SPUtils.readString(MainActivity.this,"html_title"); //标题头
                if(tmp_title==null || "".equals(tmp_title)){ //如果为空或者null
                    //提示先配置
                }else {
                    String temp_photo =SPUtils.readString(MainActivity.this,"html_photo").equals("")?"staticfile/images/photo.png":SPUtils.readString(MainActivity.this,"html_photo");
                    String temp_gh =SPUtils.readString(MainActivity.this,"html_gh").equals("")?"123456":SPUtils.readString(MainActivity.this,"html_gh");
                    String temp_winnum =SPUtils.readString(MainActivity.this,"html_winnum").equals("")?"1":SPUtils.readString(MainActivity.this,"html_winnum");
                    strResJson = String.format("{\"title\":\"%s\",\"photo\":\"%s\",\"gh\":\"%s\",\"winnum\":\"%s\",\"queue\":\"%s\"}",tmp_title,temp_photo,
                            temp_gh,temp_winnum,"1888");
                }
                function.onCallBack(strResJson); //响应JS请求
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
        httpServer = new HttpServer(webView);
        // httpServer = new HttpServer();
        try {
            httpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.mainHandler = new myHandler(Looper.getMainLooper());
        MainHandler.Init(mainHandler);

    }

    /**
     * Handler 类
     */
    private class myHandler extends Handler {
        public myHandler(Looper looper) {
            super();
        }
        public void handleMessage(Message message) {
            MainHandler localMainMessage = (MainHandler) message.obj;
            switch (localMainMessage.msgType) {
                case MODIFY_TITLE: //修改标题
                    //保存信息到本地
                    SPUtils.saveString(MainActivity.this, "html_title", localMainMessage.Info);
                    // 调用js方法修改显示
                    webView.callHandler("modify_title", localMainMessage.Info, new CallBackFunction() {
                        @Override
                        public void onCallBack(String data) {
                            //   Toast.makeText(MainActivity.this, data, Toast.LENGTH_LONG).show();
                        }
                    });
                    break;
                case MODIFY_PHOTO: //修改头像
                    //保存信息到本地
                    SPUtils.saveString(MainActivity.this, "html_photo", localMainMessage.Info);
                    // 调用js方法修改显示
                    webView.callHandler("modify_photo", localMainMessage.Info, new CallBackFunction() {
                        @Override
                        public void onCallBack(String data) {
                            //   Toast.makeText(MainActivity.this, data, Toast.LENGTH_LONG).show();
                        }
                    });
                    break;
                case MODIFY_GH: //工号
                    //保存信息到本地
                    SPUtils.saveString(MainActivity.this, "html_gh", localMainMessage.Info);
                    // 调用js方法修改显示
                    webView.callHandler("modify_gh", localMainMessage.Info, new CallBackFunction() {
                        @Override
                        public void onCallBack(String data) {
                            //   Toast.makeText(MainActivity.this, data, Toast.LENGTH_LONG).show();
                        }
                    });
                    break;
                case MODIFY_QUEUE://排队号
                    //保存信息到本地
                    SPUtils.saveString(MainActivity.this, "html_queue", localMainMessage.Info);
                    // 调用js方法修改显示
                    webView.callHandler("modify_queue", localMainMessage.Info, new CallBackFunction() {
                        @Override
                        public void onCallBack(String data) {
                            //   Toast.makeText(MainActivity.this, data, Toast.LENGTH_LONG).show();
                        }
                    });
                    break;
                case MODIFY_WIN_NUM://窗口号
                    //保存信息到本地
                    SPUtils.saveString(MainActivity.this, "html_winnum", localMainMessage.Info);
                    // 调用js方法修改显示
                    webView.callHandler("modify_winnum", localMainMessage.Info, new CallBackFunction() {
                        @Override
                        public void onCallBack(String data) {
                            //   Toast.makeText(MainActivity.this, data, Toast.LENGTH_LONG).show();
                        }
                    });
                    break;
                default:
                    break;
            }
        }

    }


}
