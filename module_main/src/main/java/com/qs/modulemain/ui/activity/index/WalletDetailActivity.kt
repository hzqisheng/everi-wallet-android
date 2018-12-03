package com.qs.modulemain.ui.activity.index

import android.app.Dialog
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.start
import com.smallcat.shenhai.mvpbase.extension.toast
import kotlinx.android.synthetic.main.activity_wallet_detail.*

class WalletDetailActivity : SimpleActivity() {

    private var mWalletName = ""
    private var dialog:Dialog? = null
    private var pwdDialog:Dialog? = null

    override val layoutId: Int
        get() = R.layout.activity_wallet_detail

    override fun initData() {
        mWalletName = intent.getStringExtra("name")
        tvTitle?.text = mWalletName
        tv_name.text = mWalletName
        layout_wallet.setOnClickListener { showDialog(getString(R.string.wallet_name), mWalletName, 0) }
        tv_export.setOnClickListener { start(ExportPrivateKeyActivity::class.java)}
        tv_set_up.setOnClickListener { showSetUpDialog() }
    }

    private fun showDialog(title: String, hint:String , type:Int){
        if (dialog == null) {
            dialog = Dialog(mContext, R.style.CustomDialog)
        }
        val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_max_service_fee, null)
        val etNumber = view.findViewById<EditText>(R.id.et_number)
        val tvSure = view.findViewById<TextView>(R.id.tv_sure)
        val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        val tvCancel = view.findViewById<TextView>(R.id.tv_cancel)
        etNumber.hint = hint
        tvTitle.text = title
        tvSure.setOnClickListener {
            if (etNumber.text.isEmpty()){
                getString(R.string.can_not_be_empty).toast()
            }else{
                dialog!!.dismiss()
                when(type){
                    0 -> {
                        mWalletName = etNumber.text.toString()
                        tv_name.text = mWalletName
                    }
                }

            }
        }
        tvCancel.setOnClickListener{ dialog!!.dismiss() }
        dialog!!.setContentView(view)
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.setCancelable(true)
        dialog!!.show()
    }

    private fun showSetUpDialog(){
        if (pwdDialog == null) {
            pwdDialog = Dialog(mContext, R.style.CustomDialog)
        }
        val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_set_up_sign, null)
        val etNumber = view.findViewById<EditText>(R.id.et_number)
        val tvSure = view.findViewById<TextView>(R.id.tv_sure)
        val cbCheck = view.findViewById<CheckBox>(R.id.cb_check)
        val tvCancel = view.findViewById<TextView>(R.id.tv_cancel)
        tvSure.setOnClickListener {
        }
        tvCancel.setOnClickListener{ pwdDialog!!.dismiss() }
        pwdDialog!!.setContentView(view)
        pwdDialog!!.setCanceledOnTouchOutside(false)
        pwdDialog!!.setCancelable(true)
        pwdDialog!!.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (dialog != null && dialog!!.isShowing){
            dialog!!.dismiss()
        }
        if (pwdDialog != null && pwdDialog!!.isShowing){
            pwdDialog!!.dismiss()
        }
    }

}
