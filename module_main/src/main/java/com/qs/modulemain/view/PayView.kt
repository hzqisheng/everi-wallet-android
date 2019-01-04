package com.qs.modulemain.view

import com.smallcat.shenhai.mvpbase.base.BaseView

interface PayView: BaseView{
    fun loadSuccess(data: Any)
    abstract fun onDataResult(msg: String)
}