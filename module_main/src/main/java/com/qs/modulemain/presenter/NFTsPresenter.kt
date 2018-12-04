package com.qs.modulemain.presenter

import android.content.Context
import com.qs.modulemain.view.AddFTsView
import com.qs.modulemain.view.ChooseFTsView
import com.qs.modulemain.view.NFTsView
import com.smallcat.shenhai.mvpbase.base.BasePresenter


/**
 * Created by hui on 2018/5/11.
 */
class NFTsPresenter(private val mContext: Context) : BasePresenter<NFTsView>() {

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