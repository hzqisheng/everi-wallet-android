package com.qs.modulemain.presenter

import android.content.Context
import com.google.zxing.oned.ITFReader
import com.qs.modulemain.view.MainView
import com.smallcat.shenhai.mvpbase.base.BasePresenter
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by hui on 2018/5/11.
 */
class MainPresenter(private val mContext: Context) : BasePresenter<MainView>() {

    init {
        registerEvent()
    }


    private fun registerEvent(){
        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when(it.type){
                        RxBusCenter.CHECK_VERSION -> mView!!.checkSuccess(it.msg)
                    }
                })
    }

}