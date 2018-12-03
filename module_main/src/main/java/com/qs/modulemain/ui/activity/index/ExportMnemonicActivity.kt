package com.qs.modulemain.ui.activity.index

import android.app.Dialog
import android.text.Html
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.start
import com.smallcat.shenhai.mvpbase.extension.toast
import kotlinx.android.synthetic.main.activity_export_mnemonic.*

class ExportMnemonicActivity : SimpleActivity() {

    private var dialog:Dialog? = null

    override val layoutId: Int
        get() = R.layout.activity_export_mnemonic

    override fun initData() {
        tvTitle?.text = getString(R.string.export_mnemonic_code)

        tv_sure.setOnClickListener { showDialog() }

        val str = getString(R.string.export_mnemonic)
        val s = "<font color=\"#3F7DEE\">${getString(R.string.import_note)}</font>$str"
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            tv_msg.text = Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY)
        } else {
            tv_msg.text = Html.fromHtml(s)
        }
    }

    private fun showDialog(){
        if (dialog == null) {
            dialog = Dialog(mContext, R.style.CustomDialog)
        }
        val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_max_service_fee, null)
        val etNumber = view.findViewById<EditText>(R.id.et_number)
        val tvSure = view.findViewById<TextView>(R.id.tv_sure)
        val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        val tvCancel = view.findViewById<TextView>(R.id.tv_cancel)
        etNumber.hint = getString(R.string.please_input_pwd)
        tvTitle.text = getString(R.string.please_input_pwd)
        tvSure.setOnClickListener {
            if (etNumber.text.isEmpty()){
                getString(R.string.please_input_pwd).toast()
            }else{
                dialog!!.dismiss()
                start(ExportMnemonic2Activity::class.java)
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
