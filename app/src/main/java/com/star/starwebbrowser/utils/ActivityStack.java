package com.star.starwebbrowser.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity堆栈控制工具类，用来存储当前未销毁的activity，用于程序完成退出
 * @author shandinan
 */
public class ActivityStack {

    /**activity列表对象*/
    private static List<Activity> activityList = new ArrayList<Activity>();

    /**
     * 删除堆栈中的activity
     * @param activity activity对象
     */
    public static void remove(Activity activity){
//        Log.i("ActivityStackControlUtil", "remove : "+activity.getLocalClassName());
        activityList.remove(activity);
    }

    /**
     * 向list中添加activity
     * @param activity activity对象
     */
    public static void add(Activity activity){
//        Log.i("ActivityStackControlUtil", "add : "+activity.getLocalClassName());
        activityList.add(activity);
    }

    /**
     * 结束程序
     */
    public static void finishProgram() {
        for (Activity activity : activityList) {
//            Log.i("ActivityStackControlUtil", "finishProgram : "+activity.getLocalClassName());
            if(activity != null){
                activity.finish();
            }
        }
        System.exit(0);
        //完全退出
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}

