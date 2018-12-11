package com.smallcat.shenhai.mvpbase.base

import android.app.Dialog
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import android.view.LayoutInflater
import android.webkit.WebView
import com.smallcat.shenhai.mvpbase.R
import com.smallcat.shenhai.mvpbase.extension.logE
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author hui
 * @date 2018/4/27
 */
abstract class BasePresenter<T : BaseView> : IPresenter<T> {

    private var loadingDialog: Dialog? = null
    private var mCompositeDisposable: CompositeDisposable? = null
    protected var mView: T? = null

    protected lateinit var mWebView: WebView

    private fun unSubscribe() {
        if (mCompositeDisposable != null)
            mCompositeDisposable!!.clear()
    }

    override fun attachView(view: T) {
        mView = view
    }

    override fun onCreate() {
    }

    override fun onStop() {
        mView = null
        unSubscribe()
        dismissLoading()
    }


    protected fun addSubscribe(disposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable!!.add(disposable)
    }

  /*  protected fun <T> addSubscribe(observable: Flowable<HttpResponse<T>>, subscriber: CommonSubscriber<T>) {
        addSubscribe(observable
                .sanitizeJson()
                .subscribeWith(subscriber)
        )
    }*/

    protected fun showLoading(mContext: Context) {
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
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog!!.dismiss()
        }
    }
}