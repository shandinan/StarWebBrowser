package com.star.starwebbrowser.event;

import android.os.Handler;
import android.os.Message;

public class MainHandler {

    static Handler mainHandler = null;
    public String Info;
    public MESSTYPE msgType;

    MainHandler(MESSTYPE paramMESSTYPE, String paramString) {
        this.msgType = paramMESSTYPE;
        this.Info = paramString;
    }

    public static void Init(Handler paramHandler) {
        mainHandler = paramHandler;
    }

    public static void SendMessage(MESSTYPE messtype, String paramString) {
        MainHandler localMainMessage = new MainHandler(messtype, paramString);
        Message localMessage = mainHandler.obtainMessage(1, 1, 1, localMainMessage);
        mainHandler.sendMessage(localMessage);
    }

    public static enum MESSTYPE {
        MODIFY_TITLE, MODIFY_PHOTO, MODIFY_QUEUE, MODIFY_GH, MODIFY_WIN_NUM,MODIFY_URL,MODIFY_URL_SAVE,SDN_CUSTOMIZE, SDN_UPDATE,SDN_ADD,SDN_STOP
    }
}
