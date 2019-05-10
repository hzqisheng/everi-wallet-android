package com.qs.modulemain.presenter

import android.content.Context
import com.qs.modulemain.view.AddFTsView
import com.qs.modulemain.view.ChooseFTsView
import com.qs.modulemain.view.MyGroupView
import com.qs.modulemain.view.NFTsView
import com.smallcat.shenhai.mvpbase.base.BasePresenter
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by hui on 2018/5/11.
 */
class MyGroupPresenter(private val mContext: Context) : BasePresenter<MyGroupView>() {

    init {
        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when(it.type){
                        RxBusCenter.MY_GROUP -> mView!!.loadGroupSuccess(it.msg)
                    }
                })
    }

    fun loadData(){
        /*addSubscribe(mApi.getUseInfo()
                .sanitizeJson()
                .subscribeWith(object : CommonSubscriber<UseInfoBean>(mView) {
                    override fun onNext(data: UseInfoBean) {
                        mView!!.loadSuccess(data)
                    }
                }))*/
    }


}