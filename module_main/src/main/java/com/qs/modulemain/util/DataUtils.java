package com.qs.modulemain.util;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qs.modulemain.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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

    public static View getEmptyView(Context mContext, String s){
        View view = LayoutInflater.from(mContext).inflate(R.layout.empty_view, null);
        TextView textView = view.findViewById(R.id.tv_text);
        textView.setText(s);
        return view;
    }

    public static void test(String s){
        List<String> list = new Gson().fromJson(s, new TypeToken<ArrayList<String>>() {}.getType());
    }
}
