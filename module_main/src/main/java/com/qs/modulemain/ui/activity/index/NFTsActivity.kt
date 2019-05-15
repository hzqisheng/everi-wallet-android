package com.qs.modulemain.ui.activity.index

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qs.modulemain.R
import com.qs.modulemain.bean.DomainBean
import com.qs.modulemain.presenter.NFTsPresenter
import com.qs.modulemain.ui.activity.manage.DomainDetailActivity
import com.qs.modulemain.ui.adapter.MyDomainAdapter
import com.qs.modulemain.view.NFTsView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.start
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import com.smallcat.shenhai.mvpbase.utils.lastMY_NFTS
import kotlinx.android.synthetic.main.activity_nfts.*
import java.util.ArrayList

/** 我的通证 **/
class NFTsActivity : BaseActivity<NFTsPresenter>(), NFTsView {


    private var nFTsList = ArrayList<DomainBean>()

    private lateinit var nFTsAdapter: MyDomainAdapter

    override fun initPresenter() {
        mPresenter = NFTsPresenter(mContext)
    }

    override val layoutId: Int = R.layout.activity_nfts

    override fun initData() {
        tvTitle?.text = getString(R.string.my_domains)
        tv_add.setOnClickListener { start(NFTsCreateActivity::class.java) }

        nFTsAdapter = MyDomainAdapter(nFTsList)
        rv_list.adapter = nFTsAdapter

//        nFTsAdapter.setOnItemChildClickListener({adapter, view, position ->
//            start(NFTsIssueActivity::class.java)
//        })
        swipe_refresh.setOnRefreshListener {
            lastMY_NFTS = RxBusCenter.MY_NFTS_ACTIVITY
            mWebView.evaluateJavascript(WebViewApi.getEVTDomainsList(sharedPref.publicKey), null)
        }

        nFTsAdapter.onItemClickListener = object : AdapterView.OnItemClickListener, BaseQuickAdapter.OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                val intent = Intent(this@NFTsActivity, DomainDetailActivity::class.java)
                intent.putExtra("mDomainName", nFTsList[position].name)
                startActivity(intent)
            }

            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }
        }
        nFTsAdapter.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.tv_issue) {
                val intent = Intent(this@NFTsActivity, NFTsIssueActivity::class.java)
                val bundle = Bundle()
                bundle.putSerializable("domain", nFTsList[position].name)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        lastMY_NFTS = RxBusCenter.MY_NFTS_ACTIVITY
        mWebView.evaluateJavascript(WebViewApi.getEVTDomainsList(sharedPref.publicKey), null)
    }

    override fun loadNFTsSuccess(msg: String) {
        swipe_refresh.isRefreshing = false
        val list = Gson().fromJson<List<DomainBean>>(msg, object : TypeToken<ArrayList<DomainBean>>() {}.type)
        nFTsList.clear()
        nFTsList.addAll(list)
        nFTsAdapter.setNewData(nFTsList)
    }


}
