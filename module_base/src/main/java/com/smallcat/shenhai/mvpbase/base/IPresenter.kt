package com.smallcat.shenhai.mvpbase.base

/**
 * @author hui
 * @date 2018/4/27
 */
interface IPresenter<in T : BaseView>{

    fun onCreate()

    fun onStop()

    fun attachView(view: T)

}