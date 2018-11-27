package com.smallcat.shenhai.mvpbase.utils

import android.content.SharedPreferences
import com.smallcat.shenhai.mvpbase.App
import com.smallcat.shenhai.mvpbase.extension.getSharedPrefs

/**
 * @author hui
 * @date 2018/4/27
 */
open class SharedPref {
    val KEY_TOKEN = "token"
    val KEY_FIRST= "isFirst"
    val KEY_APP_SECRET= "appSecret"
    val STUDENT_ID = "studentId"
    var TEACHER_ID = "teacherId"
    var KEY_PHONE = "phone"
    var TEACHER_TYPE = "type"
    var STUDEN_LIST = "studentList"
    var SET_NUMBER = "setNumber"
    protected val prefs: SharedPreferences = App.getInstance().getSharedPrefs()


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

    var appSecret: String
        get() = prefs.getString(KEY_APP_SECRET, "null")!!
        set(value) = prefs.edit().putString(KEY_APP_SECRET, value).apply()

    var phone: String
        get() = prefs.getString(KEY_PHONE, "")!!
        set(value) = prefs.edit().putString(KEY_PHONE, value).apply()

    /**
     * 教师类型
     */
    var type: Int
        get() = prefs.getInt(TEACHER_TYPE, 0)
        set(value) = prefs.edit().putInt(TEACHER_TYPE, value).apply()

    var studentList: String
        get() = prefs.getString(STUDEN_LIST, "")!!
        set(value) = prefs.edit().putString(STUDEN_LIST, value).apply()

    var setNumber: Int
        get() = prefs.getInt(SET_NUMBER, 1)
        set(value) = prefs.edit().putInt(SET_NUMBER, value).apply()
}