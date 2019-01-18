package com.smallcat.shenhai.mvpbase;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zxy.tiny.Tiny;

import org.litepal.LitePal;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author smallCut
 * @date 2018/11/7
 */
public class App extends MultiDexApplication {

    private static App mApplication;

    private List<Activity> mActivists = Collections.synchronizedList(new LinkedList<Activity>());

    public static App getInstance() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        registerActivityListener();
        //app字体大小不随系统变化
        Resources res = getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());

        LitePal.initialize(this);
        Tiny.getInstance().init(this);
        
        ARouter.openLog();   // 打印日志
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

    private void registerActivityListener() {
        registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                addActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                removeActivity(activity);
            }
        });
    }

    public void addActivity(Activity act) {
        if (act != null){
            mActivists.add(act);
        }
    }

    public void removeActivity(Activity act) {
        if (act != null){
            mActivists.remove(act);
        }
    }

    public void finishAllActivity() {
        if (mActivists != null) {
            for (Activity act : mActivists) {
                act.finish();
            }
        }
    }

    public void finishAllActivityExcept(Activity activity) {
        if (mActivists != null) {
            for (Activity act : mActivists) {
                if (act != activity) {
                    act.finish();
                }
            }
        }
    }

    public void finishActivity(Activity activity) {
        if (mActivists == null || mActivists.isEmpty()) {
            return;
        }
        if (activity == null) {
            return;
        }
        removeActivity(activity);
        activity.finish();
    }

    public void exitApp() {
        finishAllActivity();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

}