package com.qs.modulemain.view

import com.smallcat.shenhai.mvpbase.base.BaseView

interface NFTsView: BaseView{
    abstract fun loadNFTsSuccess(msg: String)
}