package com.qs.modulemain.view

import com.smallcat.shenhai.mvpbase.base.BaseView

interface MyGroupView: BaseView{
    abstract fun loadGroupSuccess(msg: String)
}