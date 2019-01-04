package com.smallcat.shenhai.mvpbase.utils

import android.content.Context
import android.content.SharedPreferences
import com.smallcat.shenhai.mvpbase.App

/**
 * @author hui
 * @date 2018/4/27
 */
open class SharedPref {

    val KEY_LANGUAGE = "LANGUAGE"
    val KEY_CURRENCY = "CURRENCY"
    val KEY_PUBLIC = "publicKey"
    val KEY_PRIVATE = "privateKey"
    val KEY_PWD = "password"
    val KEY_MNEMOINC = "mnemoinc"
    val KEY_NAME = "name"
    private val prefs: SharedPreferences = App.getInstance().applicationContext.getSharedPreferences(SharedPref.PREFS_KEY, Context.MODE_PRIVATE)

    companion object {
        fun newInstance() = SharedPref()
        val PREFS_KEY = "EVER"
    }

    //0 chinese 1 english
    var languages: Int
        get() = prefs.getInt(KEY_LANGUAGE, 0)
        set(value) = prefs.edit().putInt(KEY_LANGUAGE, value).apply()

    //0 cny 1 usd
    var currency: Int
        get() = prefs.getInt(KEY_CURRENCY, 0)
        set(value) = prefs.edit().putInt(KEY_CURRENCY, value).apply()


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
        get() = prefs.getString(KEY_MNEMOINC, "")!!
        set(value) = prefs.edit().putString(KEY_MNEMOINC, value).apply()

    var name:String
        get() = prefs.getString(KEY_NAME, "")!!
        set(value) = prefs.edit().putString(KEY_NAME, value).apply()


}