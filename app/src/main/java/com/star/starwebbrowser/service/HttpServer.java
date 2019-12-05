package com.star.starwebbrowser.service;

import android.widget.Toast;
import com.star.library.jsbridge.BridgeWebView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.star.library.jsbridge.CallBackFunction;
import com.star.starwebbrowser.activity.MainActivity;
import com.star.starwebbrowser.event.MainHandler;
import fi.iki.elonen.NanoHTTPD;


public class HttpServer extends NanoHTTPD {
    private static final String TAG = "HttpServer";

    public static final String DEFAULT_SHOW_PAGE = "index.html";
    public static final int DEFAULT_PORT = 8888;//此参数随便定义，最好定义1024-65535；1-1024是系统常用端口,1024-65535是非系统端口
    BridgeWebView webView;//全局webView
    //NanoFileUpload uploader;

    public enum Status implements NanoHTTPD.Response.IStatus {
        REQUEST_ERROR(500, "请求失败"),
        REQUEST_ERROR_API(501, "无效的请求接口"),
        REQUEST_ERROR_CMD(502, "无效命令");
        private final int requestStatus;
        private final String description;

        Status(int requestStatus, String description) {
            this.requestStatus = requestStatus;
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public int getRequestStatus() {
            return requestStatus;
        }
    }

    public HttpServer(BridgeWebView _webView) {//初始化端口
        super(DEFAULT_PORT);
        webView = _webView;
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Map<String, String> files = new HashMap<String, String>();

            Map<String, String> headers = session.getHeaders();
            String post_param = "";
      //  if (Method.POST.equals(session.getMethod())) {
            //接收不到post参数的问题，              http://blog.csdn.net/obguy/article/details/53841559
            try {
                // session.parseBody(new HashMap<String, String>());
                session.parseBody(files);
               // String body = session.getQueryParameterString();
                //post_param = body;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ResponseException e) {
                e.printStackTrace();
            }
       // }
        Map<String, String> parms = session.getParms();
        try {
            switch (uri) {
                case "/test": //测试
                    break;
                case "/title": //标题
                    MainHandler.SendMessage(MainHandler.MESSTYPE.MODIFY_TITLE, parms.get("title"));
                    break;
                case "/photo": //照片
                    MainHandler.SendMessage(MainHandler.MESSTYPE.MODIFY_PHOTO,parms.get("photo"));
                    break;
                case "/gh": //工号
                    MainHandler.SendMessage(MainHandler.MESSTYPE.MODIFY_GH,parms.get("gh"));
                    break;
                case "/winnum": //窗口号
                    MainHandler.SendMessage(MainHandler.MESSTYPE.MODIFY_WIN_NUM,parms.get("winnum"));
                    break;
                case "/queue": //排队号
                    MainHandler.SendMessage(MainHandler.MESSTYPE.MODIFY_QUEUE,parms.get("queue"));
                    break;
                case "/sdn_customize": //自定义函数
                    MainHandler.SendMessage(MainHandler.MESSTYPE.SDN_CUSTOMIZE,parms.get("sdn_customize"));
                    break;
                case"/url": //更换webview 的URL地址
                    MainHandler.SendMessage(MainHandler.MESSTYPE.MODIFY_URL,parms.get("url"));
                    break;
                case"/url_save":
                    MainHandler.SendMessage(MainHandler.MESSTYPE.MODIFY_URL,parms.get("url_save"));
                    break;
                case "/update":
                    MainHandler.SendMessage(MainHandler.MESSTYPE.MODIFY_URL,post_param);
                    break;
                case "/add":
                    MainHandler.SendMessage(MainHandler.MESSTYPE.MODIFY_URL,post_param);
                    break;

                default: {
                    return addHeaderResponse(Status.REQUEST_ERROR_API);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newFixedLengthResponse("ok");
        //自己封装的返回请求
        //return addHeaderResponse(Status.REQUEST_ERROR);
    }

    /**
     * 自定义Response返回内容
     *
     * @param status
     * @return
     */
    private Response addHeaderResponse(Response.IStatus status) {
        Response response = null;
        response = newFixedLengthResponse(status, "application/json", "msg");
        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Content-Type");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, HEAD");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Max-Age", "" + 42 * 60 * 60);
        return response;
    }

}
