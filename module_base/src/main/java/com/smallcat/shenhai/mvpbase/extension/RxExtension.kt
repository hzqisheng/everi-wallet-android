package com.smallcat.shenhai.mvpbase.extension

import com.smallcat.shenhai.mvpbase.model.http.ApiException
import com.smallcat.shenhai.mvpbase.model.http.HttpResponse
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
 * 统一返回结果处理
 * @param
 * @return
 */
fun <T> Flowable<HttpResponse<T>>.sanitizeJson(): Flowable<T> = this
        .subscribeOn(Schedulers.io())
        .flatMap { response ->
            if (response.code == 0 && response.data!= null) {
                createData(response.data!!)
            }else if(response.code == 2){
                Flowable.error(ApiException("登录过期", response.code))
            }else {
                Flowable.error(ApiException(response.message, response.code))
            }
        }
        .observeOn(AndroidSchedulers.mainThread())

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