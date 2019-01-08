package com.qs.modulemain.ui.activity.index

import android.app.Dialog
import android.content.Intent
import android.text.InputType
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.start
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.bean.BaseData
import kotlinx.android.synthetic.main.activity_wallet_detail.*

class WalletDetailActivity : SimpleActivity() {

    private var mWalletName: BaseData? = null
    private var dialog: Dialog? = null
    private var pwdDialog: Dialog? = null

    override val layoutId: Int
        get() = R.layout.activity_wallet_detail

    override fun initData() {
        mWalletName = intent.getSerializableExtra("data") as BaseData?

        var name = ""
        if (mWalletName?.name.isNullOrBlank()) {
            name = "EVT-wallet"
        } else {
            name = mWalletName?.name.toString()
        }

        tvTitle?.text = name
        tv_name.text = name
        tv_sign.text = mWalletName?.publicKey
        layout_wallet.setOnClickListener { showDialog(getString(R.string.wallet_name), name, 0) }
        tv_export.setOnClickListener {

            showSetUpDialog()
        }
        tv_set_up.setOnClickListener { showSetUpDialog() }
    }

    private fun showDialog(title: String, hint: String, type: Int) {
        if (dialog == null) {
            dialog = Dialog(mContext, R.style.CustomDialog)
        }
        val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_max_service_fee, null)
        val etNumber = view.findViewById<EditText>(R.id.et_number)
        val tvSure = view.findViewById<TextView>(R.id.tv_sure)
        val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        val tvCancel = view.findViewById<TextView>(R.id.tv_cancel)
        etNumber.inputType = EditorInfo.TYPE_CLASS_TEXT
        etNumber.hint = hint
        tvTitle.text = title
        tvSure.setOnClickListener {
            if (etNumber.text.isEmpty()) {
                getString(R.string.can_not_be_empty).toast()
            } else {
                dialog!!.dismiss()
                when (type) {
                    0 -> {
                        mWalletName!!.name = etNumber.text.toString()
                        mWalletName!!.update(mWalletName!!.id.toLong())
                        tv_name.text = mWalletName!!.name
                    }
                }

            }
        }
        tvCancel.setOnClickListener { dialog!!.dismiss() }
        dialog!!.setContentView(view)
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.setCancelable(true)
        dialog!!.show()
    }

    private fun showSetUpDialog() {
        if (pwdDialog == null) {
            pwdDialog = Dialog(mContext, R.style.CustomDialog)
        }
        val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_set_up_sign, null)
        val etNumber = view.findViewById<EditText>(R.id.et_number)
        val tvSure = view.findViewById<TextView>(R.id.tv_sure)
        val cbCheck = view.findViewById<CheckBox>(R.id.cb_check)
        val tvCancel = view.findViewById<TextView>(R.id.tv_cancel)
        tvSure.setOnClickListener {

            if (!mContext.sharedPref.password.equals(etNumber.text.toString())) {
                getString(R.string.password_error).toast()

                return@setOnClickListener
            }

            var intent = Intent(this, ExportPrivateKeyActivity::class.java).apply {
                putExtra("data", mWalletName)
                startActivity(this)
            }

            pwdDialog!!.dismiss()

        }
        tvCancel.setOnClickListener { pwdDialog!!.dismiss() }
        pwdDialog!!.setContentView(view)
        pwdDialog!!.setCanceledOnTouchOutside(false)
        pwdDialog!!.setCancelable(true)
        pwdDialog!!.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
        }
        if (pwdDialog != null && pwdDialog!!.isShowing) {
            pwdDialog!!.dismiss()
        }
    }

}
