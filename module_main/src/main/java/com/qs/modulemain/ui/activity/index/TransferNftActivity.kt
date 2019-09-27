package com.qs.modulemain.ui.activity.index

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.toast
import kotlinx.android.synthetic.main.activity_transfer_nft.*
import kotlinx.android.synthetic.main.item_add_adress.view.*
import java.util.*

class TransferNftActivity : SimpleActivity() {

    private var tokenName: String = ""
    private var domainName: String = ""

    private var mAddressList: ArrayList<String> = java.util.ArrayList()
    private var mViewList: LinkedList<View> = java.util.LinkedList()

    override val layoutId: Int
        get() = R.layout.activity_transfer_nft

    override fun initData() {
        tvTitle?.text = getString(R.string.change)
        domainName = intent.getStringExtra("domain")
        tokenName = intent.getStringExtra("token")
        tv_name.text = tokenName
        //添加地址
        iv_add_address.setOnClickListener {
            addAddress(tv_address.text.toString())
        }
        //扫一扫
        iv_scan.setOnClickListener {
            val intent = Intent(this@TransferNftActivity, ScanActivity::class.java)
            intent.putExtra("ScanType", 1000)
            startActivityForResult(intent, 1)
        }
        tv_next.setOnClickListener {
            if (mAddressList.size == 0) {
                if (tv_address.text.toString().isNotEmpty()) {
                    addAddress(tv_address.text.toString())
                } else {
                    getString(R.string.please_input_address).toast()
                    return@setOnClickListener
                }
            }
            Intent(this@TransferNftActivity, TransferNftAckActivity::class.java).apply {
                putExtra("domain", domainName)
                putExtra("token", tokenName)
                putStringArrayListExtra("payees", mAddressList)
                startActivity(this)
            }
        }
    }

    private fun addAddress(address: String) {
        if (address.isEmpty()) {
            getString(R.string.address_invalid).toast()
            return
        }
        if (mAddressList.contains(address)) {
            getString(R.string.address_already_exist).toast()
            return
        }
        mAddressList.add(address)
        genAddressView(address)
    }

    /** 生成并添加一个View **/
    private fun genAddressView(address: String) {
        val view = LayoutInflater.from(this).inflate(R.layout.item_add_adress, null)
        view.tvAddress.text = address
        view.ivDeleted.setOnClickListener {
            addressContent.removeView(view)
            if (mAddressList.size > mViewList.indexOf(view))
                mAddressList.removeAt(mViewList.indexOf(view))
            mViewList.remove(view)
        }
        mViewList.add(view)
        addressContent.addView(view)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && data != null) {
            if (requestCode > 0) {
                val result = data.getStringExtra("result")
                mAddressList.add(result)
                genAddressView(result)
            }
        }
    }

}
