package com.qs.modulemain.view

import com.smallcat.shenhai.mvpbase.base.BaseView

interface AboutUsView: BaseView{
    abstract fun checkSuccess(msg: String)
}