package com.smallcat.shenhai.mvpbase.utils

import android.content.Context
import android.content.SharedPreferences
import com.smallcat.shenhai.mvpbase.App

/**
 * @author hui
 * @date 2018/4/27
 */
open class SharedPref {

    val KEY_TOKEN = "token"
    val KEY_FIRST= "isFirst"
    val KEY_APP_SECRET= "appSecret"
    val KEY_LANGUAGE = "LANGUAGE"

    private val prefs: SharedPreferences = App.getInstance().applicationContext.getSharedPreferences(SharedPref.PREFS_KEY, Context.MODE_PRIVATE)

    companion object {
        fun newInstance() = SharedPref()
        val PREFS_KEY = "ZH"
    }

    var token: String
        get() = prefs.getString(KEY_TOKEN, "null")!!
        set(token) = prefs.edit().putString(KEY_TOKEN, token).apply()

    var isFirst: Int
        get() = prefs.getInt(KEY_FIRST, 0)
        set(value) = prefs.edit().putInt(KEY_FIRST, value).apply()

    //0 chinese 1 english
    var languages: Int
        get() = prefs.getInt(KEY_LANGUAGE, 0)
        set(value) = prefs.edit().putInt(KEY_LANGUAGE, value).apply()

    var appSecret: String
        get() = prefs.getString(KEY_APP_SECRET, "null")!!
        set(value) = prefs.edit().putString(KEY_APP_SECRET, value).apply()


}