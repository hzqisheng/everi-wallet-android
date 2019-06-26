package com.qs.modulemain.ui.activity.index

import android.content.Intent
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.qs.modulemain.R
import com.qs.modulemain.arouter.ARouterConfig
import com.qs.modulemain.presenter.WalletPresenter
import com.qs.modulemain.ui.adapter.WalletAdapter
import com.qs.modulemain.view.WalletView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.model.bean.BaseData
import kotlinx.android.synthetic.main.activity_wallet.*
import org.litepal.crud.DataSupport
import android.content.ContentValues
import com.smallcat.shenhai.mvpbase.extension.*
import com.smallcat.shenhai.mvpbase.utils.addClipboard


@Route(path = ARouterConfig.MAIN_WALLET)
class WalletActivity : BaseActivity<WalletPresenter>(), WalletView {

    private lateinit var adapterNow: WalletAdapter
    private var nowList = ArrayList<BaseData>()

    override fun initPresenter() {
        mPresenter = WalletPresenter(mContext)
    }

    override val layoutId: Int
        get() = R.layout.activity_wallet

    override fun initData() {
        val type = intent.getIntExtra("type", 0)
        tvTitle?.text = getString(R.string.wallet)

        nowList.addAll(DataSupport.where("isCreate = ?", "0").find(BaseData::class.java))
        adapterNow = WalletAdapter(nowList)

        val dataList: List<BaseData> = updateShow()

        iv_add_now.setOnClickListener {
            val intent = Intent(this@WalletActivity, EditIdWalletActivity::class.java)
            intent.putExtra("data", dataList[0])
            startActivity(intent)
        }
        iv_add_import.setOnClickListener { start(ImportWalletActivity::class.java) }
        iv_more.setOnClickListener {
            Intent(mContext, WalletDetailActivity::class.java).apply {
                putExtra("data", dataList[0].id)
                mContext.startActivity(this)
            }
        }

        public_key.setOnClickListener {
            addClipboard(mContext, dataList[0].publicKey)
            mContext.getString(R.string.copy_success).toast()
        }

        adapterNow.setOnItemClickListener { adapter, view, position ->
            if (type == 1) {

                val baseBean = nowList[position]

                val values = ContentValues()
                values.put("isSelect", 0)
                DataSupport.updateAll(BaseData::class.java, values, "isSelect = ?", "1")

                baseBean.isSelect = 1
                baseBean.save()
                var datas = DataSupport.findAll(BaseData::class.java)

                updateShow()

                sharedPref.publicKey = nowList[position].publicKey
                sharedPref.privateKey = nowList[position].privateKey
                sharedPref.mnemoinc = nowList[position].mnemoinc
                sharedPref.password = nowList[position].password
                sharedPref.name = nowList[position].name
                sharedPref.isFinger = nowList[position].isFinger
                mContext.getString(R.string.switch_success).toast()
                finish()
            }
        }
        rv_import.adapter = adapterNow
        rv_import.isNestedScrollingEnabled = false
    }

    override fun onResume() {
        super.onResume()
        updateShow()
    }

    private fun updateShow(): List<BaseData> {
        val dataList: List<BaseData> = DataSupport.where("isCreate == 1").find(BaseData::class.java)
        if (dataList.isNotEmpty()) {
            if (dataList[dataList.size - 1].name.isNotEmpty())
                tv_name.text = dataList[dataList.size - 1].name
            public_key.text = dataList[dataList.size - 1].publicKey
            tv_type.text = dataList[dataList.size - 1].type

            if (dataList[dataList.size - 1].isSelect == 1) {
                iv_bg.setImageResource(mContext.getResourceColor(R.color.transparent))
                iv_bg.setBackgroundResource(R.drawable.ic_wallet_yellow_bg)
                sharedPref.name = dataList[dataList.size - 1].name
            } else {
                iv_bg.setBackgroundColor(mContext.getResourceColor(R.color.transparent))
                iv_bg.setImageResource(R.drawable.shape_round_wallet_bg)
            }
            iv_bg.setOnClickListener {
                val baseBean = dataList[dataList.size - 1]

                val values = ContentValues()
                values.put("isSelect", 0)
                DataSupport.updateAll(BaseData::class.java, values, "isSelect = ?", "1")

                baseBean.isSelect = 1
                baseBean.save()

                updateShow()

                sharedPref.publicKey = baseBean.publicKey
                sharedPref.privateKey = baseBean.privateKey
                sharedPref.mnemoinc = baseBean.mnemoinc
                sharedPref.password = baseBean.password
                sharedPref.name = baseBean.name
                sharedPref.isFinger = baseBean.isFinger
                runOnUiThread {
                    getString(R.string.switch_success).toast()
                }
                finish()
            }
        }

        nowList.clear()
        nowList.addAll(DataSupport.where("isCreate = ?", "0").find(BaseData::class.java))
        adapterNow.notifyDataSetChanged()
        return dataList
    }

    override fun loadSuccess(data: Any) {

    }

}
