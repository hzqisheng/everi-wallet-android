package com.qs.modulemain.ui.activity.index

import android.view.ViewGroup
import android.widget.LinearLayout
import com.qs.modulemain.R
import com.qs.modulemain.presenter.AddFTsPresenter
import com.qs.modulemain.ui.widget.ProgressWebView
import com.qs.modulemain.view.AddFTsView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.toast
import kotlinx.android.synthetic.main.activity_add_fts.*

class AddFtsActivity : BaseActivity<AddFTsPresenter>(), AddFTsView{

    override fun initPresenter() {
        mPresenter = AddFTsPresenter(mContext)
    }

    override val layoutId: Int = R.layout.activity_add_fts

    override fun initData() {
        tvTitle?.text = getString(R.string.CreateFts)

        val mWebView = ProgressWebView(applicationContext)
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        mWebView?.layoutParams = params
        ll_layout.addView(mWebView)
        mWebView.loadUrl("file:///android_asset/dist/index.html")
        iv_img.setOnClickListener { view ->
            mWebView.evaluateJavascript("javascript:common()") { it.toast() }
        }

        tv_sure.setOnClickListener {view ->
            mWebView.evaluateJavascript("javascript:randomPrivateKey()") { it.toast() }
        }


    }

}
