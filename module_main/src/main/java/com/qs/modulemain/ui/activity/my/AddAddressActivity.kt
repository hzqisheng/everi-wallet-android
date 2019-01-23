package com.qs.modulemain.ui.activity.my

import android.content.Intent
import com.qs.modulemain.R
import com.qs.modulemain.bean.AddressBean
import com.qs.modulemain.presenter.AddAddressPresenter
import com.qs.modulemain.ui.activity.index.ScanActivity
import com.qs.modulemain.view.AddAddressView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.toast
import kotlinx.android.synthetic.main.activity_add_address.*

class AddAddressActivity : BaseActivity<AddAddressPresenter>(), AddAddressView {

    override fun initPresenter() {
        mPresenter = AddAddressPresenter(mContext)
    }

    override val layoutId: Int
        get() = R.layout.activity_add_address

    override fun initData() {
        tvTitle?.text = getString(R.string.add_address)

        ivScan.setOnClickListener {
            val intent = Intent(this@AddAddressActivity, ScanActivity::class.java)
            intent.putExtra("ScanType",1000)
            startActivityForResult(intent,1)
        }


        tv_save.setOnClickListener {
            //类型 evt 0
            var type = "EVT"

            if(et_address.text.toString().isEmpty()){
                "Address is not null !".toast()
                return@setOnClickListener
            }

            var address = et_address.text.toString()

            var groupName = et_group_name.text.toString()

            var name = et_name.text.toString()

            var phoneNum = et_phone.text.toString()

            var memo = et_notes.text.toString()

            var addressBean = AddressBean(type,address,groupName,name,phoneNum,memo)

            if(addressBean.save()){
                "Save Sucess".toast()
                finish()
            }


        }
    }

    override fun loadSuccess(data: Any) {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == ScanActivity.resultCode){
            var result = data!!.getStringExtra("result")
            et_address.setText(result.toString())
        }
    }


}
