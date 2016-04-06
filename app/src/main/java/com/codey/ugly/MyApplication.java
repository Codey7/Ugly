package com.codey.ugly;

import android.app.Application;

import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.core.sdkmanager.LocationSDKManager;
import com.umeng.community.location.DefaultLocationImpl;

/**
 * Created by Mr.Codey on 2016/4/3.
 */
public class MyApplication extends Application
{

    @Override
    public void onCreate()
    {
        super.onCreate();
        // 确保不要重复注入同一类型的对象,建议在Application类的onCreate中执行该代码。
        LocationSDKManager.getInstance().addAndUse(new DefaultLocationImpl()) ;
        //umeng

    }
}
