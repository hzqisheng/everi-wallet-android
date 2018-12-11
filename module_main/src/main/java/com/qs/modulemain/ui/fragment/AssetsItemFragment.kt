package com.qs.modulemain.ui.fragment


import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qs.modulemain.R
import com.qs.modulemain.presenter.AssetsItemPresenter
import com.qs.modulemain.ui.adapter.AssetsItemAdapter
import com.qs.modulemain.ui.adapter.AssetsNFtsAdapter
import com.qs.modulemain.view.AssetsItemView
import com.smallcat.shenhai.mvpbase.base.BaseFragment
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import kotlinx.android.synthetic.main.fragment_assets_item.*
import java.util.*


class AssetsItemFragment : BaseFragment<AssetsItemPresenter>(), AssetsItemView {

    private var type = -1
    private var ftsList = ArrayList<String>()

    private lateinit var ftsAdapter: AssetsItemAdapter

    override fun initPresenter() {
        mPresenter = AssetsItemPresenter(mContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        type = 0
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && type != -1) {
            mWebView.evaluateJavascript(WebViewApi.getFungibleBalance(mContext.sharedPref.publicKey)) {}
        }
    }

    override val layoutId: Int = R.layout.fragment_assets_item

    override fun initData() {
        mWebView.evaluateJavascript(WebViewApi.getFungibleBalance(mContext.sharedPref.publicKey)) {}
        ftsAdapter = AssetsItemAdapter(ftsList)
        rv_list.adapter = ftsAdapter
    }

    override fun loadFTsSuccess(msg: String) {
        val list = Gson().fromJson<List<String>>(msg, object : TypeToken<ArrayList<String>>() {}.type)
        ftsList.clear()
        ftsList.addAll(list)
        ftsAdapter.setNewData(ftsList)
    }
}
