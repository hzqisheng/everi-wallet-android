package com.qs.modelthird;

import com.smallcat.shenhai.mvpbase.App;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

/**
 * Created by hui on 2018/8/9.
 */
public class MyApp extends App {

    @Override
    public void onCreate() {
        super.onCreate();
        UMConfigure.init(this, "5aa9e2ebf29d98623e0000fa", "umeng", UMConfigure.DEVICE_TYPE_PHONE,"");
        PlatformConfig.setWeixin("wxa663b68625d5f4aa", "7c1470df2960a9269f6b7ce1970cd0b1");
        PlatformConfig.setQQZone("1106700131", "GkcI2eXbY8GsB1SO");
        PlatformConfig.setSinaWeibo("3549905875","59ae56149c0244a00b2f0b3ff23d6616","http://www.weibo.com");
       /* UMConfigure.setLogEnabled(true);
        enableStrictMode();*/
    }

}
