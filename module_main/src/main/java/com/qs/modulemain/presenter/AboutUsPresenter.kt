package com.qs.modulemain.presenter

import android.content.Context
import com.qs.modulemain.view.AboutUsView
import com.smallcat.shenhai.mvpbase.base.BasePresenter
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by hui on 2018/5/11.
 */
class AboutUsPresenter(private val mContext: Context) : BasePresenter<AboutUsView>() {

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