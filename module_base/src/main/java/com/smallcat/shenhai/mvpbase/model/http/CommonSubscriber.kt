package com.smallcat.shenhai.mvpbase.model.http

import com.smallcat.shenhai.mvpbase.base.BaseView
import com.smallcat.shenhai.mvpbase.extension.logE
import io.reactivex.subscribers.ResourceSubscriber

/**
 * @author hui
 * @date 2018/4/27
 */
abstract class CommonSubscriber<T> protected constructor(private val mView : BaseView?) : ResourceSubscriber<T>() {

    override fun onComplete() {

    }

    override fun onError(t: Throwable) {
        if (mView == null) return
        if (t is ApiException) {
            mView.showErrorMsg(t.message!!)
        } else {
            t.message.toString().logE()
            mView.showErrorMsg("服务器开小差了")
        }
    }

    companion object {
        private val unknown_error = "未知错误"
    }

}