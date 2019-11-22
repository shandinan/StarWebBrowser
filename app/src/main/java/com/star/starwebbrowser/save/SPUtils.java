package com.star.starwebbrowser.save;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences类型的存储工具类
 * @author shandinan
 */
public class SPUtils {
    /**SharedPreferences名称*/
    private final static String NAME = "sdn";

    /**
     * 存储数据
     * @param context 上下文
     * @param name 存储名称
     * @param value 存储内容
     */
    public static void saveString(Context context, String name, String value){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putString(name, value).commit();
    }

    /**
     * 读取名称为name的存储数值
     * @param context 上下文对象
     * @param name 存储名称
     * @return 存储内容，如果没有则返回""
     */
    public static String readString(Context context,String name){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getString(name, "");
    }


    /**
     * 存储数据
     * @param context 上下文
     * @param name 存储名称
     * @param value 存储内容
     */
    public static void saveBoolean(Context context,String name,boolean value){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(name, value).commit();
    }

    /**
     * 读取名称为name的存储数值
     * @param context 上下文对象
     * @param name 存储名称
     * @return 存储内容，如果没有则返回0
     */
    public static boolean readBoolean(Context context,String name){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(name,true);
    }

    /**
     * 存储数据
     * @param context 上下文
     * @param name 存储名称
     * @param value 存储内容
     */
    public static void saveInt(Context context,String name,int value){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(name, value).commit();
    }

    /**
     * 读取名称为name的存储数值
     * @param context 上下文对象
     * @param name 存储名称
     * @return 存储内容，如果没有则返回0
     */
    public static int readInt(Context context,String name){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getInt(name,0);
    }
}


