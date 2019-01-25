package com.qs.modulemain.ui.activity.my

import com.qs.modulemain.R
import com.qs.modulemain.bean.AddressBean
import com.qs.modulemain.presenter.ImportAddressPresenter
import com.qs.modulemain.view.ImportAddressView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.logE
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_import_address.*

class ImportAddressActivity : BaseActivity<ImportAddressPresenter>(), ImportAddressView {

    private var pos = 0
    private var maxPos = 0
    private var addressData: List<String>? = null

    override fun initPresenter() {
        mPresenter = ImportAddressPresenter(mContext)
    }

    override val layoutId: Int
        get() = R.layout.activity_import_address

    override fun initData() {
        tvTitle?.text = getString(R.string.import_address)

        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when (it.type) {
                        RxBusCenter.CHECK_ADDRESS -> {
                            checkSuccess(it.msg, pos)
                            pos++
                        }
                    }
                })

        tv_save.setOnClickListener {

            if (et_address.text.toString().isEmpty()) {
                getString(R.string.please_input_address).toast()
                return@setOnClickListener
            }

            val text = et_address.text.toString().trim()
            val result: List<String> = text.split("\n")
            addressData = result
            maxPos = result.size - 1
            for (i in result.indices) {
                val item = result[i]
                if (item == "") {
                    maxPos--
                    continue
                }
                val value: List<String> = item.split(",")
                if (value.size < 6) {
                    getString(R.string.Format_error_please_Check).toast()
                    return@setOnClickListener
                }
                mWebView.evaluateJavascript(WebViewApi.isValidPublicKey(value[1]), null)
            }
        }
    }

    private fun checkSuccess(string: String, position: Int) {
        if (string == "false") {
            getString(R.string.address_error).toast()
        } else {
            if (position == maxPos) {
                if (addressData == null) {
                    return
                }
                for (item in addressData!!) {
                    if (item == "") {
                        continue
                    }
                    val value: List<String> = item.split(",")
                    val addressBean = AddressBean(value[0], value[1], value[2], value[3], value[4], value[5])
                    addressBean.save()
                }
                getString(R.string.import_address_success).toast()
                finish()
            }
        }
    }

}
