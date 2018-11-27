package com.qs.modulemain.view

import com.smallcat.shenhai.mvpbase.base.BaseView

interface MyView: BaseView{
    fun loadSuccess(data: Any)
    fun loginOut()
}