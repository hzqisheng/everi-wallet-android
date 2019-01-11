package com.qs.modulemain.presenter

import android.content.Context
import com.qs.modulemain.view.AddFTsView
import com.smallcat.shenhai.mvpbase.base.BasePresenter
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by hui on 2018/5/11.
 */
class AddFTsPresenter(private val mContext: Context) : BasePresenter<AddFTsView>() {


    init {
        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when(it.type){
                        RxBusCenter.ADD_FTS -> mView!!.onDataResult(it.msg)

                        RxBusCenter.NEED_PRIVATE_KEY -> mView!!.showPassWordDialog(it.msg)
                    }
                })

    }


    fun loadData() {
        /*addSubscribe(mApi.getUseInfo()
                .sanitizeJson()
                .subscribeWith(object : CommonSubscriber<UseInfoBean>(mView) {
                    override fun onNext(data: UseInfoBean) {
                        mView!!.loadSuccess(data)
                    }
                }))*/
    }


}