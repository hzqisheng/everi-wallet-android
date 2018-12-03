package com.qs.modulemain.ui.activity.my

import android.app.Dialog
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.toast
import kotlinx.android.synthetic.main.activity_max_payment.*

class MaxPaymentActivity : SimpleActivity() {

    private var dialog:Dialog? = null

    override val layoutId: Int
        get() = R.layout.activity_max_payment

    override fun initData() {
        tvTitle?.text = getString(R.string.max_payment)
        ll_everi.setOnClickListener{
            showMaxPaymentDialog(max_evt)
        }
        ll_eos.setOnClickListener{
            showMaxPaymentDialog(max_eos)
        }
        ll_eth.setOnClickListener{
            showMaxPaymentDialog(max_eth)
        }
    }

    private fun showMaxPaymentDialog(textView: TextView){
        if (dialog == null) {
            dialog = Dialog(mContext, R.style.CustomDialog)
        }
        val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_max_service_fee, null)
        val etNumber = view.findViewById<EditText>(R.id.et_number)
        val tvSure = view.findViewById<TextView>(R.id.tv_sure)
        val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        val tvCancel = view.findViewById<TextView>(R.id.tv_cancel)
        tvTitle.text = getString(R.string.max_payment)
        tvSure.setOnClickListener {
            if (etNumber.text.isEmpty()){
                getString(R.string.can_not_be_empty).toast()
            }else{
                dialog!!.dismiss()
                textView.text = etNumber.text
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
