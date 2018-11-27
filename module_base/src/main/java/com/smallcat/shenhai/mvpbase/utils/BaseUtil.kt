package com.smallcat.shenhai.mvpbase.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.Uri
import com.smallcat.shenhai.mvpbase.App

/**
 * @author smallCut
 * @date 2018/11/6
 */
/**
 * 检查网络是否可用
 *
 * @param context
 * @return
 */
fun isNetworkAvailable(context: Context): Boolean {

    val manager = context.applicationContext.getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val networkInfo = manager.activeNetworkInfo

    return !(networkInfo == null || !networkInfo.isAvailable)
}

/**
 * 获取版本信息
 */
fun getVersionName(context: Context): String? {
    val manager = context.packageManager
    var name: String? = null
    try {
        val info = manager.getPackageInfo(context.packageName, 0)
        name = info.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return name
}

/**
 * 打电话
 */
fun Call(phone: String, mContext: Context) {
    LogUtil.e(phone)
    val intent = Intent(Intent.ACTION_DIAL)
    val data = Uri.parse("tel:$phone")
    intent.data = data
    mContext.startActivity(intent)
}

/**
 * Adapt the screen for vertical slide.
 *
 * @param activity        The activity.
 * @param designWidthInPx The size of design diagram's width, in pixel.
 */
fun adaptScreen4VerticalSlide(activity: Activity,
                              designWidthInPx: Int) {
    adaptScreen(activity, designWidthInPx, true)
}

/**
 * Adapt the screen for horizontal slide.
 *
 * @param activity         The activity.
 * @param designHeightInPx The size of design diagram's height, in pixel.
 */
fun adaptScreen4HorizontalSlide(activity: Activity,
                                designHeightInPx: Int) {
    adaptScreen(activity, designHeightInPx, false)
}

/**
 * Reference from: https://mp.weixin.qq.com/s/d9QCoBP6kV9VSWvVldVVwA
 */
private fun adaptScreen(activity: Activity,
                        sizeInPx: Int,
                        isVerticalSlide: Boolean) {
    val systemDm = Resources.getSystem().displayMetrics
    val appDm = App.getInstance().resources.displayMetrics
    val activityDm = activity.resources.displayMetrics
    if (isVerticalSlide) {
        activityDm.density = activityDm.widthPixels / sizeInPx.toFloat()
    } else {
        activityDm.density = activityDm.heightPixels / sizeInPx.toFloat()
    }
    activityDm.scaledDensity = activityDm.density * (systemDm.scaledDensity / systemDm.density)
    activityDm.densityDpi = (160 * activityDm.density).toInt()

    appDm.density = activityDm.density
    appDm.scaledDensity = activityDm.scaledDensity
    appDm.densityDpi = activityDm.densityDpi

    ADAPT_SCREEN_ARGS.sizeInPx = sizeInPx
    ADAPT_SCREEN_ARGS.isVerticalSlide = isVerticalSlide
}

val ADAPT_SCREEN_ARGS = AdaptScreenArgs()

class AdaptScreenArgs {
    var sizeInPx: Int = 0
    var isVerticalSlide: Boolean = false
}

/**
 * Cancel adapt the screen.
 *
 * @param activity The activity.
 */
fun cancelAdaptScreen(activity: Activity) {
    val systemDm = Resources.getSystem().displayMetrics
    val appDm = App.getInstance().resources.displayMetrics
    val activityDm = activity.resources.displayMetrics
    activityDm.density = systemDm.density
    activityDm.scaledDensity = systemDm.scaledDensity
    activityDm.densityDpi = systemDm.densityDpi

    appDm.density = systemDm.density
    appDm.scaledDensity = systemDm.scaledDensity
    appDm.densityDpi = systemDm.densityDpi
}

/**
 * Return whether adapt screen.
 *
 * @return `true`: yes<br></br>`false`: no
 */
fun isAdaptScreen(): Boolean {
    val systemDm = Resources.getSystem().displayMetrics
    val appDm = App.getInstance().resources.displayMetrics
    return systemDm.density != appDm.density
}