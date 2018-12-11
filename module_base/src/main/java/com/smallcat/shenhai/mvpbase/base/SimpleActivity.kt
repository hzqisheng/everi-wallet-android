package com.smallcat.shenhai.mvpbase.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import com.smallcat.shenhai.mvpbase.R
import com.smallcat.shenhai.mvpbase.utils.LocalManageUtil
import com.smallcat.shenhai.mvpbase.utils.adaptScreen4VerticalSlide
import com.smallcat.shenhai.mvpbase.utils.getWebViewInstance
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

    private var ivFinish:ImageView? = null
    protected var tvTitle: TextView? = null

    protected abstract val layoutId: Int
    protected var tvRight: TextView? = null
    protected var ivRight: ImageView? = null

    protected lateinit var mWebView: WebView

    @SuppressLint("JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //固定竖屏
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        //状态栏适配
        fitSystem()
        //UI适配方案
        adaptScreen4VerticalSlide(this, 360)
        LocalManageUtil.setLocal(this)
        setContentView(layoutId)
        mContext = this
        mWebView = getWebViewInstance()
        onViewCreated()
        initToolbar()
        initData()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocalManageUtil.setLocal(newBase))
    }

    protected open fun onViewCreated() {}

    protected open fun fitSystem() {
    }

    private fun initToolbar(){
        ivFinish = findViewById(R.id.iv_back)
        ivFinish?.setOnClickListener { finish() }
        tvTitle = findViewById(R.id.tv_title)
        tvRight = findViewById(R.id.tv_right)
        ivRight = findViewById(R.id.iv_icon)
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


}
