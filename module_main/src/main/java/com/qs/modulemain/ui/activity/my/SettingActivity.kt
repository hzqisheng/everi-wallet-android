package com.qs.modulemain.ui.activity.my

import android.annotation.SuppressLint
import android.app.Dialog
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.start
import com.smallcat.shenhai.mvpbase.extension.toast
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.fragment_my.*

class SettingActivity : SimpleActivity() {

    private var dialog: Dialog? = null

    override val layoutId: Int
        get() = R.layout.activity_setting

    override fun initData() {
        tvTitle?.text = getString(R.string.system_setting)

        tv_choose_language.setOnClickListener { start(LanguagesActivity::class.java) }
        //tv_currency_setting.setOnClickListener { start(CurrencySettingActivity::class.java) }
        tv_node_setting.setOnClickListener { start(NodeSettingActivity::class.java) }
        ll_max_service_fee.setOnClickListener { showMaxServiceFeeDialog() }
        ll_max_pay.setOnClickListener { start(MaxPaymentActivity::class.java) }
    }

    @SuppressLint("SetTextI18n")
    private fun showMaxServiceFeeDialog(){
        if (dialog == null) {
            dialog = Dialog(mContext, R.style.CustomDialog)
        }
        val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_max_service_fee, null)
        val etNumber = view.findViewById<EditText>(R.id.et_number)
        val tvSure = view.findViewById<TextView>(R.id.tv_sure)
        val tvCancel = view.findViewById<TextView>(R.id.tv_cancel)
        tvSure.setOnClickListener {
            if (etNumber.text.isEmpty()){
                getString(R.string.the_service_fee_can_not_be_empty).toast()
            }else{
                dialog!!.dismiss()
                tv_max_service_fee.text = "${etNumber.text} EVT/PEVT"
            }
        }
        tvCancel.setOnClickListener{ dialog!!.dismiss() }
        dialog!!.setContentView(view)
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.setCancelable(true)
        dialog!!.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (dialog != null && dialog!!.isShowing){
            dialog!!.dismiss()
        }
    }
}
