package com.qs.modulemain.ui.activity.my

import com.qs.modulemain.R
import com.qs.modulemain.bean.AddressBean
import com.qs.modulemain.presenter.ImportAddressPresenter
import com.qs.modulemain.view.ImportAddressView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.getResourceString
import com.smallcat.shenhai.mvpbase.extension.toast
import kotlinx.android.synthetic.main.activity_import_address.*
import java.util.ArrayList

class ImportAddressActivity : BaseActivity<ImportAddressPresenter>(), ImportAddressView {

    override fun initPresenter() {
        mPresenter = ImportAddressPresenter(mContext)
    }

    override val layoutId: Int
        get() = R.layout.activity_import_address

    override fun initData() {
        tvTitle?.text = getString(R.string.import_address)

        tv_save.setOnClickListener {

            var text = et_address.text.toString()
            var result: List<String> = text.split("\n")
            for (item in result) {
                var value: List<String> = item.split(",")
                if (value.size < 6) {
                    "Format error! please Check".toast()
                    return@setOnClickListener
                }
                var addressBean = AddressBean(value[0], value[1], value[2], value[3], value[4], value[5])
                addressBean.save()
            }
            finish()
        }


    }


}
