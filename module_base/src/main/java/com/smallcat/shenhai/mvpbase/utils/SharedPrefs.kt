package com.smallcat.shenhai.mvpbase.utils

import android.content.Context
import android.content.SharedPreferences
import com.smallcat.shenhai.mvpbase.App

/**
 * @author hui
 * @date 2018/4/27
 */
open class SharedPref {

    private val prefs: SharedPreferences = App.getInstance().getSharedPreferences(SharedPref.PREFS_KEY, Context.MODE_PRIVATE)

    companion object {
        fun newInstance() = SharedPref()
        const val PREFS_KEY = "EVER"
        const val KEY_LANGUAGE = "LANGUAGE"
        const val KEY_CURRENCY = "CURRENCY"
        const val KEY_PUBLIC = "publicKey"
        const val KEY_PRIVATE = "privateKey"
        const val KEY_PWD = "password"
        const val KEY_MNEMONIC = "mnemonic"
        const val KEY_NAME = "name"
        const val KEY_FINGER = "finger"
        const val KEY_NODE = "NODE"
        const val KEY_TIP = "helpTipNext"
        const val CUSTOM_NODE = "customNode"
    }

    //0 chinese 1 english
    var languages: Int
        get() = prefs.getInt(KEY_LANGUAGE, -1)
        set(value) = prefs.edit().putInt(KEY_LANGUAGE, value).apply()

    //0 close 1 open
    var isFinger: Int
        get() = prefs.getInt(KEY_FINGER, 0)
        set(value) = prefs.edit().putInt(KEY_FINGER, value).apply()

    //0 cny 1 usd
    var currency: Int
        get() = prefs.getInt(KEY_CURRENCY, -1)
        set(value) = prefs.edit().putInt(KEY_CURRENCY, value).apply()

    var chooseNode: String
        get() = prefs.getString(KEY_NODE, "mainnet14.everitoken.io")!!
        set(value) = prefs.edit().putString(KEY_NODE, value).apply()

    var publicKey: String
        get() = prefs.getString(KEY_PUBLIC, "")!!
        set(value) = prefs.edit().putString(KEY_PUBLIC, value).apply()

    var privateKey: String
        get() = prefs.getString(KEY_PRIVATE, "")!!
        set(value) = prefs.edit().putString(KEY_PRIVATE, value).apply()

    var password: String
        get() = prefs.getString(KEY_PWD, "")!!
        set(value) = prefs.edit().putString(KEY_PWD, value).apply()

    var mnemoinc: String
        get() = prefs.getString(KEY_MNEMONIC, "")!!
        set(value) = prefs.edit().putString(KEY_MNEMONIC, value).apply()

    var name: String
        get() = prefs.getString(KEY_NAME, "")!!
        set(value) = prefs.edit().putString(KEY_NAME, value).apply()

    var helpTipNext: Boolean
        get() = prefs.getBoolean(KEY_TIP, false)!!
        set(value) = prefs.edit().putBoolean(KEY_TIP, value).apply()

    //保存我的代币数据，用于离线展示
    var myAssets: String
        get() = prefs.getString(privateKey, "")!!
        set(value) = prefs.edit().putString(privateKey, value).apply()

    //保存自定义节点地址，用#分隔开
    var customNode: String
        get() = prefs.getString(CUSTOM_NODE, "")!!
        set(value) = prefs.edit().putString(CUSTOM_NODE, value).apply()
}