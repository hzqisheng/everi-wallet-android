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
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.start
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.bean.BaseData
import kotlinx.android.synthetic.main.activity_wallet.*
import kotlinx.android.synthetic.main.fragment_assets.*
import org.litepal.crud.DataSupport
import android.content.ContentValues
import android.media.Image
import android.widget.ImageView
import com.smallcat.shenhai.mvpbase.extension.getResourceColor


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

        var dataList: List<BaseData> = updateShow()

        iv_add_now.setOnClickListener {
            var intent = Intent(this@WalletActivity,EditIdWalletActivity::class.java)
            intent.putExtra("data",dataList[0])
            startActivity(intent)
        }
        iv_add_import.setOnClickListener { start(ImportWalletActivity::class.java) }

        iv_more.setOnClickListener {
            Intent(mContext, WalletDetailActivity::class.java).apply {
                putExtra("data", dataList[0])
                mContext.startActivity(this)
            }
        }

        adapterNow.setOnItemClickListener { adapter, view, position ->
            if (type == 1) {

                var baseBean = nowList[position]


                val values = ContentValues()
                values.put("isSelect", 0);
                DataSupport.updateAll(BaseData::class.java,values,"isSelect = ?", "1")

                baseBean.isSelect = 1
                baseBean.save()

               var datas =  DataSupport.findAll(BaseData::class.java)

                updateShow()

                sharedPref.publicKey = nowList[position].publicKey
                sharedPref.privateKey = nowList[position].privateKey
                sharedPref.mnemoinc = nowList[position].mnemoinc
                sharedPref.password = nowList[position].password
                sharedPref.name = nowList[position].name
                "Switch Success!".toast()
                finish()
            }
        }
        rv_import.adapter = adapterNow
        rv_import.isNestedScrollingEnabled = false
    }

    private fun updateShow(): List<BaseData> {
        var dataList: List<BaseData> = DataSupport.where("isCreate == 1").find(BaseData::class.java);
        if (dataList.size > 0) {
            if(dataList[dataList.size - 1].name!=null && !dataList[dataList.size - 1].name.isEmpty())
            tv_name.text = dataList[dataList.size - 1].name
            public_key.text = dataList[dataList.size - 1].publicKey
            tv_type.text = dataList[dataList.size - 1].type

            if (dataList[dataList.size - 1].isSelect == 1) {
                findViewById<View>(R.id.iv_bg).background = getDrawable(R.drawable.ic_wallet_yellow_bg)
            } else {
                //R.drawable.shape_round_wallet_bg
                findViewById<View>(R.id.iv_bg).setBackgroundColor(getResourceColor(R.color.transparent))
                findViewById<ImageView>(R.id.iv_bg).setImageDrawable(getDrawable(R.drawable.shape_round_wallet_bg))
            }
            findViewById<View>(R.id.iv_bg).setOnClickListener {
                var baseBean = dataList[dataList.size - 1]


                val values = ContentValues()
                values.put("isSelect", 0);
                DataSupport.updateAll(BaseData::class.java,values,"isSelect = ?", "1")

                baseBean.isSelect = 1
                baseBean.save()

                updateShow()

                sharedPref.publicKey = baseBean.publicKey
                sharedPref.privateKey = baseBean.privateKey
                sharedPref.mnemoinc = baseBean.mnemoinc
                sharedPref.password = baseBean.password
                sharedPref.name = baseBean.name
                "Switch Success!".toast()
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
