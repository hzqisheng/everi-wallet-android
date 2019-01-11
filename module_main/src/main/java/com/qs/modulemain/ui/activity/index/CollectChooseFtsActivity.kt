package com.qs.modulemain.ui.activity.index

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qs.modulemain.R
import com.qs.modulemain.bean.ChooseGetBean
import com.qs.modulemain.presenter.ChooseFTsPresenter
import com.qs.modulemain.ui.adapter.ChooseFTSAdapter
import com.qs.modulemain.ui.adapter.CollChooseFTSAdapter
import com.qs.modulemain.view.ChooseFTsView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import kotlinx.android.synthetic.main.activity_collect_choose_fts.*

class CollectChooseFtsActivity : BaseActivity<ChooseFTsPresenter>(), ChooseFTsView {

    private lateinit var chooseAdapter: CollChooseFTSAdapter;
    private lateinit var dataList:ArrayList<ChooseGetBean>
    override fun initPresenter() {
        mPresenter = ChooseFTsPresenter(mContext)
    }


    override val layoutId: Int
        get() = R.layout.activity_collect_choose_fts


    override fun initData() {
        tvTitle?.text = getString(R.string.choose_fts)
        dataList = ArrayList()
        chooseAdapter = CollChooseFTSAdapter(dataList)
        rv_list.adapter = chooseAdapter

        chooseAdapter.onItemClickListener = object : AdapterView.OnItemClickListener, BaseQuickAdapter.OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {

                var intent  = Intent(this@CollectChooseFtsActivity,FtsIssueEditActivity::class.java)
                var bundle = Bundle()
                bundle.putSerializable("data",dataList[position])
                intent.putExtras(bundle)
                setResult(101,intent)
                finish()
            }

            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }
        }
    }

    override fun onDataResult(result: String) {
        val chooseBean = Gson().fromJson<java.util.ArrayList<ChooseGetBean>>(result,object : TypeToken<java.util.ArrayList<ChooseGetBean>>() {}.type)
        dataList.clear()
        dataList.addAll(chooseBean)
        chooseAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        mWebView.evaluateJavascript(WebViewApi.getEVTFungibleBalanceList(mContext.sharedPref.publicKey)){}
    }


}
