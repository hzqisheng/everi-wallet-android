package com.qs.modulemain.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.LinearLayout;

import com.smallcat.shenhai.mvpbase.utils.SharedPref;

import java.lang.reflect.Field;
import java.util.Locale;

public class DataUtils {

    public static void setUpIndicatorWidth(TabLayout tabLayout, Context mContext) {
        Class<?> tabLayoutClass = tabLayout.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayoutClass.getDeclaredField("mTabStrip");
            tabStrip.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        LinearLayout layout = null;
        try {
            if (tabStrip != null) {
                layout = (LinearLayout) tabStrip.get(tabLayout);
            }
            for (int i = 0; i < layout.getChildCount(); i++) {
                View child = layout.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                params.setMarginStart(UtilsKt.dip2px(mContext, 16));
                params.setMarginEnd(UtilsKt.dip2px(mContext, 16));
                child.setLayoutParams(params);
                child.invalidate();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
