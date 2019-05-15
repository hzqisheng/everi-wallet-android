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
import com.qs.modulemain.bean.HelpCenterBean;

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

    public static List<HelpCenterBean> addFtsHelpData(Context mContext){
        List<HelpCenterBean> list = new ArrayList<>();
        list.add(new HelpCenterBean(mContext.getString(R.string.issue_fts), mContext.getString(R.string.addfts_help_1)));
        list.add(new HelpCenterBean(mContext.getString(R.string.coin_symbol), mContext.getString(R.string.addfts_help_2)));
        list.add(new HelpCenterBean(mContext.getString(R.string.asset_number), mContext.getString(R.string.addfts_help_3)));
        list.add(new HelpCenterBean(mContext.getString(R.string.total_supply), mContext.getString(R.string.addfts_help_4)));
        list.add(new HelpCenterBean(mContext.getString(R.string.decimals), mContext.getString(R.string.addfts_help_5)));
        list.add(new HelpCenterBean(mContext.getString(R.string.icon), mContext.getString(R.string.addfts_help_6)));
        return list;
    }

    public static List<HelpCenterBean> addNFtsHelpData(Context mContext){
        List<HelpCenterBean> list = new ArrayList<>();
        list.add(new HelpCenterBean(mContext.getString(R.string.domain), mContext.getString(R.string.add_nfts_help_1)));
        return list;
    }

    public static List<HelpCenterBean> addGroupHelpData(Context mContext){
        List<HelpCenterBean> list = new ArrayList<>();
        list.add(new HelpCenterBean(mContext.getString(R.string.group), mContext.getString(R.string.add_group_help)));
        return list;
    }

}
