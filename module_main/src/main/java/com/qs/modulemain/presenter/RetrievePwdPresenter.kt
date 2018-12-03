package com.qs.modulemain.presenter

import android.content.Context
import com.qs.modulemain.view.ChangePwdView
import com.qs.modulemain.view.RetrievePwdView
import com.smallcat.shenhai.mvpbase.base.BasePresenter


/**
 * Created by hui on 2018/5/11.
 */
class RetrievePwdPresenter(private val mContext: Context) : BasePresenter<RetrievePwdView>() {

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