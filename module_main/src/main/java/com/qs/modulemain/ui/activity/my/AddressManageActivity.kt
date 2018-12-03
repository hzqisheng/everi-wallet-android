package com.qs.modulemain.ui.activity.my

import com.alibaba.android.arouter.facade.annotation.Route
import com.qs.modulemain.R
import com.qs.modulemain.arouter.ARouterConfig
import com.qs.modulemain.presenter.AddressManagePresenter
import com.qs.modulemain.view.AddressManageView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.getResourceColor
import com.smallcat.shenhai.mvpbase.extension.start
import kotlinx.android.synthetic.main.activity_address_manage.*

@Route(path = ARouterConfig.MY_ADDRESS)
class AddressManageActivity : BaseActivity<AddressManagePresenter>(),AddressManageView {

    override fun initPresenter() {
        mPresenter = AddressManagePresenter(mContext)
    }

    override val layoutId: Int
        get() = R.layout.activity_address_manage

    override fun initData() {
        tvTitle?.text = getString(R.string.address_manage)

        tvRight?.apply {
            text = getString(R.string.export)
            setTextColor(getResourceColor(R.color.color_e4))
            setOnClickListener{
                start(ExportAddressActivity::class.java)
            }
        }

        tv_add.setOnClickListener {
            start(AddAddressActivity::class.java)
        }
        tv_import.setOnClickListener {
            start(ImportAddressActivity::class.java)
        }

    }

    override fun loadSuccess(data: Any) {

    }
}
