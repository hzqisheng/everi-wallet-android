package com.qs.modulemain.ui.activity.manage

import android.content.Intent
import android.graphics.Color
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qs.modulemain.R
import com.qs.modulemain.bean.ChooseGetBean
import com.qs.modulemain.bean.DetailMetas
import com.qs.modulemain.bean.DomainDetailBean
import com.qs.modulemain.ui.activity.index.AddMetaActivity
import com.qs.modulemain.ui.activity.index.NFTsCreateActivity
import com.qs.modulemain.ui.adapter.DomainDetailAdapter
import com.qs.modulemain.ui.adapter.MetaDataAdapter
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_domain_detail.*

class DomainDetailActivity : SimpleActivity() {

    companion object {
        var mDomainDetail: DomainDetailBean = DomainDetailBean()
    }

    private var mDomainName = ""

    private lateinit var nMetaAdapter: MetaDataAdapter
    private lateinit var mIssueAdapter: DomainDetailAdapter
    private lateinit var mTransferAdapter: DomainDetailAdapter
    private lateinit var nManageAdapter: DomainDetailAdapter

    override val layoutId: Int
        get() = R.layout.activity_domain_detail

    override fun initData() {
        mDomainName = intent.getStringExtra("mDomainName")
        tvTitle?.text = mDomainName
        tvRight?.apply {
            text = getString(R.string.authority_setting)
            setTextColor(Color.WHITE)
            setOnClickListener {
                val intent = Intent(this@DomainDetailActivity, NFTsCreateActivity::class.java)
                intent.putExtra("domainDetailUpdate", true)
                intent.putExtra("domainName", mDomainName)
                startActivityForResult(intent, 101)
            }
        }

        iv_add_data.setOnClickListener {
            var intent = Intent(this@DomainDetailActivity, AddMetaActivity::class.java)
            intent.putExtra("DomainDetailAdd", true)
            intent.putExtra("domainName", mDomainName)
            startActivityForResult(intent, 103)
        }

        mIssueAdapter = DomainDetailAdapter(mDomainDetail.issue.authorizers)
        rv_issue.adapter = mIssueAdapter
        mTransferAdapter = DomainDetailAdapter(mDomainDetail.transfer.authorizers)
        rv_transfer.adapter = mTransferAdapter
        nManageAdapter = DomainDetailAdapter(mDomainDetail.manage.authorizers)
        rv_manage.adapter = nManageAdapter
        nMetaAdapter = MetaDataAdapter(mDomainDetail.metas)
        rv_list.adapter = nMetaAdapter

        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when (it.type) {
                        RxBusCenter.DOMAIN_DETAIL -> {
                            loadDomainDetailSuccess(it.msg)
                        }
                        RxBusCenter.REQUEST_ERROR -> dismissLoading()
                    }
                })

        mWebView.evaluateJavascript(WebViewApi.getDomainDetail(mDomainName), null)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            mWebView.evaluateJavascript(WebViewApi.getDomainDetail(mDomainName)) {
                showLoading()
            }
        }
        if (requestCode == 103) {
            if (resultCode > 0 && data != null) {
                val metaBean = data.getSerializableExtra("result") as ChooseGetBean.MetasBean
                mDomainDetail.metas.add(DetailMetas(metaBean.key, metaBean.value, metaBean.creator))
                nMetaAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun loadDomainDetailSuccess(msg: String) {
        dismissLoading()
        mDomainDetail = Gson().fromJson<DomainDetailBean>(msg, object : TypeToken<DomainDetailBean>() {}.type)
        tv_name.text = mDomainDetail.name
        tv_time.text = mDomainDetail.create_time
        tv_domain_issue.text = "阈值：${mDomainDetail.issue.threshold}"
        tv_domain_transfer.text = "阈值：${mDomainDetail.transfer.threshold}"
        tv_domain_manage.text = "阈值：${mDomainDetail.manage.threshold}"
        mIssueAdapter.setNewData(mDomainDetail.issue.authorizers)
        mTransferAdapter.setNewData(mDomainDetail.transfer.authorizers)
        nManageAdapter.setNewData(mDomainDetail.manage.authorizers)
        nMetaAdapter.setNewData(mDomainDetail.metas)
    }
}
