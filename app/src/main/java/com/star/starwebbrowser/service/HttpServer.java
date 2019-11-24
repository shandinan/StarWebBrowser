package com.star.starwebbrowser.service;

import fi.iki.elonen.NanoHTTPD;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpServer extends NanoHTTPD {
    private static final String TAG = "HttpServer";

    public static final String DEFAULT_SHOW_PAGE = "index.html";
    public static final int DEFAULT_PORT = 9511;//此参数随便定义，最好定义1024-65535；1-1024是系统常用端口,1024-65535是非系统端口

    public enum Status implements Response.IStatus {
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

    public HttpServer() {//初始化端口
        super(DEFAULT_PORT);
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Map<String, String> headers = session.getHeaders();
        //接收不到post参数的问题，              http://blog.csdn.net/obguy/article/details/53841559
        try {
            session.parseBody(new HashMap<String, String>());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ResponseException e) {
            e.printStackTrace();
        }
        Map<String, String> parms = session.getParms();
        try {
            //   LogUtil.d(TAG, uri);

//判断uri的合法性，自定义方法，这个是判断是否是接口的方法
            //  if (checkUri(uri)) {
            if (true) {
                // 针对的是接口的处理
                if (headers != null) {
                    //   LogUtil.d(TAG, headers.toString());
                }
                if (parms != null) {
                    //    LogUtil.d(TAG, parms.toString());
                }
                if (Method.OPTIONS.equals(session.getMethod())) {
                    //   LogUtil.d(TAG, "OPTIONS探测性请求");
                    //Response.newFixedLengthResponse
                    return addHeaderResponse(Response.Status.OK);
                }

                switch (uri) {
                    case "/test": {//接口2
                        //此方法包括了封装返回的接口请求数据和处理异常以及跨域
                        //  return getXXX(parms);
                    }
                    default: {
                        return addHeaderResponse(Status.REQUEST_ERROR_API);
                    }
                }
            } else {
                //针对的是静态资源的处理
                /*
                String filePath = getFilePath(uri); // 根据url获取文件路径
                if (filePath == null) {
                    LogUtil.d(TAG, "sd卡没有找到");
                    return super.serve(session);
                }
                File file = new File(filePath);
                if (file != null && file.exists()) {
                    //   LogUtil.d(TAG, "file path = " + file.getAbsolutePath());
//根据文件名返回mimeType： image/jpg, video/mp4, etc
                    String mimeType = getMimeType(filePath);
                    Response response = null;
                    InputStream is = new FileInputStream(file);
                    response = newFixedLengthResponse(Response.Status.OK, mimeType, is, is.available());
//下面是跨域的参数（因为一般要和h5联调，所以最好设置一下）
// X-PINGOTHER, Content-Type
                    response.addHeader("Access-Control-Allow-Headers", allowHeaders);
                    response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, HEAD");
                    response.addHeader("Access-Control-Allow-Credentials", "true");
                    response.addHeader("Access-Control-Allow-Origin", "*");
                    response.addHeader("Access-Control-Max-Age", "" + 42 * 60 * 60);
                    return response;
                } else {
                    // LogUtil.d(TAG, "file path = " + file.getAbsolutePath() + "的资源不存在");
                }
                */
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //自己封装的返回请求
        return addHeaderResponse(Status.REQUEST_ERROR);
    }

    /**
     * 自定义Response返回内容
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
