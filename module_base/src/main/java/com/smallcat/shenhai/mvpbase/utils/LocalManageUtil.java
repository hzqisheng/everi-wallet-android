package com.smallcat.shenhai.mvpbase.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;

import com.smallcat.shenhai.mvpbase.extension.ContextExtensionKt;

import java.util.Locale;

public class LocalManageUtil {

    /**
     * 获取选择的语言设置
     *
     * @param context
     * @return
     */
    public static Locale getSetLanguageLocale(Context context) {

        switch (ContextExtensionKt.getSharedPref(context).getLanguages()) {
            case 0:
                return Locale.CHINA;
            case 1:
                return Locale.ENGLISH;
            default:
                return Locale.ENGLISH;
        }
    }

    /**
     * 获取系统的locale
     *
     * @return Locale对象
     */
    public static Locale getSystemLocale(Context context) {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        return locale;
    }

    public static Context setLocal(Context context) {
        return updateResources(context, getSetLanguageLocale(context));
    }

    private static Context updateResources(Context context, Locale locale) {
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.setLocale(locale);
        context = context.createConfigurationContext(config);
        return context;
    }
}
