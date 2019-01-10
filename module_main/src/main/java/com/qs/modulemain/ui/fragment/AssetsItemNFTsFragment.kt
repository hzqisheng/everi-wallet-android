package com.qs.modulemain.ui.fragment


import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qs.modulemain.R
import com.qs.modulemain.bean.ChooseGetBean
import com.qs.modulemain.presenter.AssetsItemNFTsPresenter
import com.qs.modulemain.ui.adapter.AssetsNFtsAdapter
import com.qs.modulemain.view.AssetsItemNFTsView
import com.smallcat.shenhai.mvpbase.base.BaseFragment
import com.smallcat.shenhai.mvpbase.extension.logE
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import com.smallcat.shenhai.mvpbase.utils.lastMY_NFTS
import kotlinx.android.synthetic.main.fragment_assets_item.*
import java.lang.Exception


class AssetsItemNFTsFragment : BaseFragment<AssetsItemNFTsPresenter>(), AssetsItemNFTsView {

    private var nFTsList = ArrayList<ChooseGetBean>()

    private var nFTsAdapter: AssetsNFtsAdapter ? = null

    override fun initPresenter() {
        mPresenter = AssetsItemNFTsPresenter(mContext)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lastMY_NFTS = RxBusCenter.MY_NFTS
            mWebView.evaluateJavascript(WebViewApi.getOwnedTokens(mContext.sharedPref.publicKey), null)
        }
    }

    override val layoutId: Int = R.layout.fragment_assets_item

    override fun initData() {
        lastMY_NFTS = RxBusCenter.MY_NFTS
        nFTsAdapter = AssetsNFtsAdapter(nFTsList)
        rv_list.adapter = nFTsAdapter
    }

    override fun loadNFTsSuccess(msg: String) {
        var chooseBean = ArrayList<ChooseGetBean>()
        try {
            chooseBean = Gson().fromJson<java.util.ArrayList<ChooseGetBean>>(msg, object : TypeToken<java.util.ArrayList<ChooseGetBean>>() {}.type)
        } catch (e: Exception) {
            "数据为空".logE()
        }

        nFTsList.clear()
        nFTsList.addAll(chooseBean)
        if(nFTsAdapter != null)
        nFTsAdapter.setNewData(nFTsList)
    }
}
