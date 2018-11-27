package com.smallcat.shenhai.mvpbase.extension

import android.content.Context
import android.support.v4.content.ContextCompat
import com.smallcat.shenhai.mvpbase.utils.SharedPref

/**
 * @author hui
 * @date 2018/4/27
 */
fun Context.getSharedPrefs() = getSharedPreferences(SharedPref.PREFS_KEY, Context.MODE_PRIVATE)

val Context.sharedPref: SharedPref get() = SharedPref.newInstance()

fun Context.getResourceColor(color:Int) = ContextCompat.getColor(this, color)