package com.codey.ugly.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Mr.Codey on 2016/4/8.
 * sp工具类
 */
public class SharedPreferencesUtil
{
    private static final String File_Name="sp_ugly";

    /**
     * 存储数据到sp
     * @param context
     * @param key
     * @param value
     */
    public static void savaData(Context context,String key,Object value)
    {
        //获取存入数据的type
        String type=value.getClass().getSimpleName();

        SharedPreferences preferences=context.getSharedPreferences(File_Name,Context.MODE_PRIVATE);

        SharedPreferences.Editor editor=preferences.edit();

        if ("Integer".equals(type))
        {
            editor.putInt(key,(Integer)value);
        }
        if ("String".equals(type))
        {
            editor.putString(key, (String) value);
        }
        if ("Boolean".equals(type))
        {
            editor.putBoolean(key, (Boolean) value);
        }
        if ("Float".equals(type))
        {
            editor.putFloat(key,(Float)value);
        }
        if ("Long".equals(type))
        {
            editor.putLong(key,(Long)value);
        }
        editor.commit();
    }

    public static Object getData(Context context,String key,Object defValue)
    {
        //获取存入数据的type
        String type=defValue.getClass().getSimpleName();

        SharedPreferences preferences=context.getSharedPreferences(File_Name,Context.MODE_PRIVATE);

        if ("Integer".equals(type))
        {
            return  preferences.getInt(key,(Integer)defValue);
        }
        if ("String".equals(type))
        {
          return preferences.getString(key, (String)defValue);
        }
        if ("Boolean".equals(type))
        {
            return preferences.getBoolean(key,(Boolean)defValue);
        }
        if ("Float".equals(type))
        {
            return preferences.getFloat(key,(Float)defValue);
        }
        if ("Long".equals(type))
        {
            return preferences.getLong(key,(Long)defValue);
        }
        return null;
    }
}
