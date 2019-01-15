package com.smallcat.shenhai.mvpbase.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.smallcat.shenhai.mvpbase.R
import com.smallcat.shenhai.mvpbase.extension.logE
import com.smallcat.shenhai.mvpbase.utils.getWebViewInstance
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import me.yokeyword.fragmentation.SupportFragment

/**
 * @author hui
 * @date 2018/4/27
 */
abstract class SimpleFragment : SupportFragment() {

    private var mCompositeDisposable: CompositeDisposable? = null
    protected lateinit var mView: View
    protected lateinit var mActivity: Activity
    protected lateinit var mContext: Context
    private var loadingDialog: Dialog? = null

    protected lateinit var mWebView: WebView

    protected abstract val layoutId: Int

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = (context as Activity?)!!
        mContext = context!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(layoutId, null)
        mWebView = getWebViewInstance()
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        initData()
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

    override fun onDestroyView() {
        super.onDestroyView()
        dismissLoading()
        unSubscribe()
    }

    protected fun dismissLoading() {
        if (loadingDialog != null)
            loadingDialog!!.dismiss()
    }


    private fun unSubscribe() {
        if (mCompositeDisposable != null)
            mCompositeDisposable!!.clear()
    }

    protected fun addSubscribe(disposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable!!.add(disposable)
    }

    protected abstract fun initData()
}