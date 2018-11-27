package com.smallcat.shenhai.mvpbase.model.helper

import io.reactivex.Observable
import io.reactivex.processors.PublishProcessor
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/**
 * Created by hui on 2018/8/2.
 */
object RxBus {
    private val mBus: Subject<Any> = PublishSubject.create()

    fun <T> toObservable(cls: Class<T>): Observable<T> = mBus.ofType(cls)


    fun toObservable(): Observable<Any> = mBus


    fun post(obj: Any) {
        mBus.onNext(obj)
    }

    fun hasObservers(): Boolean {
        return mBus.hasObservers()
    }
}