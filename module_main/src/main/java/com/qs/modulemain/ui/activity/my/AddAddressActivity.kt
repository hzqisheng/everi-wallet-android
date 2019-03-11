package com.qs.modulemain.ui.activity.my

import android.content.Intent
import com.qs.modulemain.R
import com.qs.modulemain.bean.AddressBean
import com.qs.modulemain.presenter.AddAddressPresenter
import com.qs.modulemain.ui.activity.index.ScanActivity
import com.qs.modulemain.view.AddAddressView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_add_address.*

class AddAddressActivity : BaseActivity<AddAddressPresenter>(), AddAddressView {

    override fun initPresenter() {
        mPresenter = AddAddressPresenter(mContext)
    }

    override val layoutId: Int
        get() = R.layout.activity_add_address

    override fun initData() {
        tvTitle?.text = getString(R.string.add_address)

        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    when (it.type) {
                        RxBusCenter.CHECK_ADDRESS -> checkSuccess(it.msg)
                    }
                })

        ivScan.setOnClickListener {
            val intent = Intent(this@AddAddressActivity, ScanActivity::class.java)
            intent.putExtra("ScanType", 1000)
            startActivityForResult(intent, 1)
        }


        tv_save.setOnClickListener {

            if (tv_address.text.toString().isEmpty()) {
                "Address is not null !".toast()
                return@setOnClickListener
            }

            mWebView.evaluateJavascript(WebViewApi.isValidPublicKey(tv_address.text.toString()), null)

        }
    }

    private fun checkSuccess(string: String) {
        if (string == "true") {
            val address = tv_address.text.toString()

            val groupName = et_group_name.text.toString()

            val name = et_name.text.toString()

            val phoneNum = et_phone.text.toString()

            val memo = et_notes.text.toString()

            val addressBean = AddressBean("EVT", address, groupName, name, phoneNum, memo)

            if (addressBean.save()) {
                "Save Success".toast()
                finish()
            }
        } else {
            getString(R.string.address_error).toast()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == ScanActivity.resultCode) {
            val result = data!!.getStringExtra("result")
            tv_address.setText(result.toString())
        }
    }


}
