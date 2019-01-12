package com.qs.modulemain.view

import com.smallcat.shenhai.mvpbase.base.BaseView

interface AddFTsView : BaseView {
    fun onDataResult(msg: String)
    fun showPassWordDialog(msg: String)
    fun uploadSuccess(msg: String)

}