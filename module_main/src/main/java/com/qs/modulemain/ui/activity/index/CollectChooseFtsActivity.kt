package com.qs.modulemain.ui.activity.index

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qs.modulemain.R
import com.qs.modulemain.bean.ChooseGetBean
import com.qs.modulemain.presenter.ChooseFTsPresenter
import com.qs.modulemain.ui.adapter.CollChooseFTSAdapter
import com.qs.modulemain.view.ChooseFTsView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import kotlinx.android.synthetic.main.activity_collect_choose_fts.*

class CollectChooseFtsActivity : BaseActivity<ChooseFTsPresenter>(), ChooseFTsView {

    private lateinit var chooseAdapter: CollChooseFTSAdapter
    private lateinit var dataList: ArrayList<ChooseGetBean>
    private var mChoosePos = 0
    private var bean = ChooseGetBean()

    override fun initPresenter() {
        mPresenter = ChooseFTsPresenter(mContext)
    }

    override val layoutId: Int
        get() = R.layout.activity_collect_choose_fts

    override fun initData() {
        tvTitle?.text = getString(R.string.choose_fts)

        if (intent.hasExtra("data")) {
            bean = intent.getSerializableExtra("data") as ChooseGetBean
        }

        dataList = ArrayList()
        chooseAdapter = CollChooseFTSAdapter(dataList)
        rv_list.adapter = chooseAdapter

        chooseAdapter.setOnItemClickListener { _, _, position ->
            dataList[mChoosePos].isChoose = false
            chooseAdapter.notifyItemChanged(mChoosePos)
            dataList[position].isChoose = true
            chooseAdapter.notifyItemChanged(position)

            Handler().postDelayed({
                val intent = Intent(this@CollectChooseFtsActivity, FtsIssueEditActivity::class.java)
                val bundle = Bundle()
                bundle.putSerializable("data", dataList[position])
                intent.putExtras(bundle)
                setResult(101, intent)
                finish()
            }, 500)
        }
    }

    override fun onDataResult(result: String) {
        val chooseBean = Gson().fromJson<java.util.ArrayList<ChooseGetBean>>(result, object : TypeToken<java.util.ArrayList<ChooseGetBean>>() {}.type)
        for (i in chooseBean.indices) {
            if (chooseBean[i].sym == bean.sym) {
                mChoosePos = i
                chooseBean[i].isChoose = true
                break
            }
        }
        dataList.clear()
        dataList.addAll(chooseBean)
        chooseAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        mWebView.evaluateJavascript(WebViewApi.getEVTFungibleBalanceList(mContext.sharedPref.publicKey)) {}
    }

}
