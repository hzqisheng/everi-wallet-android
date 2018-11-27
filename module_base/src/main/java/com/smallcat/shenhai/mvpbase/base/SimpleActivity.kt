package com.smallcat.shenhai.mvpbase.base

import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.content.ComponentCallbacks
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import com.smallcat.shenhai.mvpbase.R
import com.smallcat.shenhai.mvpbase.utils.adaptScreen4VerticalSlide
import me.yokeyword.fragmentation.SupportActivity

/**
 * @author hui
 * @date 2018/4/27
 */
abstract class SimpleActivity : SupportActivity() {

    protected lateinit var mContext: Context
    private var loadingDialog: Dialog? = null
    private var sNonCompatDensity: Float = 0F
    private var sNonCompatScaledDensity: Float = 0F

    protected abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //固定竖屏
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        //状态栏适配
        fitSystem()
        //UI适配方案
        adaptScreen4VerticalSlide(this, 360)
        setContentView(layoutId)
        mContext = this
        onViewCreated()
        initData()
    }

    protected open fun onViewCreated() {}

    protected open fun fitSystem() {
    }

    protected abstract fun initData()

    protected fun startActivityFinish(cls: Class<*>) {
        startActivity(getIntent(cls))
        finish()
    }

    protected fun showLoading() {
        if (loadingDialog == null) {
            loadingDialog = Dialog(mContext, R.style.CustomDialog)
        }
        val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_loading, null)
        loadingDialog!!.setContentView(view)
        loadingDialog!!.setCanceledOnTouchOutside(true)
        loadingDialog!!.setCancelable(true)
        loadingDialog!!.show()
    }

    protected fun dismissLoading() {
        if (loadingDialog != null)
            loadingDialog!!.dismiss()
    }

    protected fun getIntent(cls: Class<*>): Intent {
        return Intent(mContext, cls)
    }

    /**
     * 简书最新适配方案，根据360dp宽度适配所有机型
     * @author hui
     * @date 2018/7/24
     */
    private fun setCustomDensity(activity: Activity, application: Application) {
        val appDisplayMetrics = application.resources.displayMetrics
        if (sNonCompatDensity == 0f) {
            sNonCompatDensity = appDisplayMetrics.density
            sNonCompatScaledDensity = appDisplayMetrics.scaledDensity
            application.registerComponentCallbacks(object : ComponentCallbacks {
                override fun onConfigurationChanged(newConfig: Configuration?) {
                    if (newConfig != null && newConfig!!.fontScale > 0) {
                        sNonCompatScaledDensity = application.resources.displayMetrics.scaledDensity
                    }
                }

                override fun onLowMemory() {

                }
            })
        }
        val targetDensity = (appDisplayMetrics.widthPixels / 360).toFloat()
        val targetScaleDensity = targetDensity * (sNonCompatScaledDensity / sNonCompatDensity)
        val targetDensityDpi = (160 * targetDensity).toInt()
        appDisplayMetrics.density = targetDensity
        appDisplayMetrics.scaledDensity = targetScaleDensity
        appDisplayMetrics.densityDpi = targetDensityDpi

        val activityDisplayMetrics = activity.resources.displayMetrics
        activityDisplayMetrics.density = targetDensity
        activityDisplayMetrics.scaledDensity = targetScaleDensity
        activityDisplayMetrics.densityDpi = targetDensityDpi
    }
}
