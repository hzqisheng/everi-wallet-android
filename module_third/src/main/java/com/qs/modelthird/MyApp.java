package com.qs.modelthird;

import com.smallcat.shenhai.mvpbase.App;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

/**
 * Created by hui on 2018/8/9.
 */
public class MyApp extends App {

    private static MyApp mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
       /* UMConfigure.setLogEnabled(true);
        enableStrictMode();*/
    }

}
