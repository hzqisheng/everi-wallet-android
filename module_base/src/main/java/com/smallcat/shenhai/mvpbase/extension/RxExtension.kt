package com.smallcat.shenhai.mvpbase.extension

import com.smallcat.shenhai.mvpbase.model.http.ApiException
import com.smallcat.shenhai.mvpbase.utils.LogUtil
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by hui on 2018/4/27.
 */
fun <T> transformScheduler(): FlowableTransformer<T, T> {
    return FlowableTransformer { upstream ->
        upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}


/**
 * 生成新的对象
 * @param
 * @return
 */
fun <T> createData(data: T?): Flowable<T> {
    return Flowable.create({ e ->
        try {
            if (data != null) {
                LogUtil.e("api", "subscribe: " + data.toString())
                e.onNext(data)
            }
            e.onComplete()
        } catch (exception: Exception) {
            e.onError(exception)
        }
    }, BackpressureStrategy.BUFFER)
}