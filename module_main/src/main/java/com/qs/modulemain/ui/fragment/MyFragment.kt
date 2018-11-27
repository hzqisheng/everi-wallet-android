package com.qs.modulemain.ui.fragment


import com.qs.modulemain.R
import com.qs.modulemain.presenter.MyPresenter
import com.qs.modulemain.view.MyView
import com.smallcat.shenhai.mvpbase.base.BaseFragment


class MyFragment : BaseFragment<MyPresenter>(), MyView {

    override fun initPresenter() {
        mPresenter = MyPresenter(mContext)
    }

    override val layoutId: Int
        get() = R.layout.fragment_my

    override fun initData() {
    }

    override fun loadSuccess(data: Any) {
    }

    override fun loginOut() {
    }

}
