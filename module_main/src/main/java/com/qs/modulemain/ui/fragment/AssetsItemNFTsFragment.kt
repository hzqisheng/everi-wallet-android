package com.qs.modulemain.ui.fragment


import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qs.modulemain.R
import com.qs.modulemain.presenter.AssetsItemNFTsPresenter
import com.qs.modulemain.ui.adapter.AssetsNFtsAdapter
import com.qs.modulemain.view.AssetsItemNFTsView
import com.smallcat.shenhai.mvpbase.base.BaseFragment
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.bean.NFtsBean
import kotlinx.android.synthetic.main.fragment_assets_item.*
import java.util.*


class AssetsItemNFTsFragment : BaseFragment<AssetsItemNFTsPresenter>(), AssetsItemNFTsView {

    private var type = -1
    private var nFTsList = ArrayList<NFtsBean>()

    private lateinit var nFTsAdapter: AssetsNFtsAdapter

    override fun initPresenter() {
        mPresenter = AssetsItemNFTsPresenter(mContext)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && type != -1) {
            mWebView.evaluateJavascript(WebViewApi.getOwnedTokens(mContext.sharedPref.publicKey)) {}
        }
    }

    override val layoutId: Int = R.layout.fragment_assets_item

    override fun initData() {
        type = 0
        mWebView.evaluateJavascript(WebViewApi.getOwnedTokens(mContext.sharedPref.publicKey)) {}
        nFTsAdapter = AssetsNFtsAdapter(nFTsList)
        rv_list.adapter = nFTsAdapter

    }

    override fun loadNFTsSuccess(msg: String) {
        val list = Gson().fromJson<List<NFtsBean>>(msg, object : TypeToken<ArrayList<NFtsBean>>() {}.type)
        nFTsList.clear()
        nFTsList.addAll(list)
        nFTsAdapter.setNewData(nFTsList)
    }

}
