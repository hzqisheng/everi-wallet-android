package com.qs.modulemain.view

import com.smallcat.shenhai.mvpbase.base.BaseView

interface WalletView: BaseView{
    fun loadSuccess(data: Any)
}