package com.qs.modulemain.view

import com.smallcat.shenhai.mvpbase.base.BaseView

interface AddFTsView: BaseView{
    abstract fun onDataResult(msg: String)
    abstract fun showPassWordDailog(msg: String)
}