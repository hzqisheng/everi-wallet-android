package com.qs.modulemain.ui.activity.index

import android.app.Dialog
import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.start
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.bean.BaseData
import com.smallcat.shenhai.mvpbase.utils.addClipboard
import kotlinx.android.synthetic.main.activity_export_mnemonic.*

class ExportMnemonicActivity : SimpleActivity() {
    /** 当前传递过来的钱包 **/
    private lateinit var mWalletBean: BaseData
    private var dialog:Dialog? = null
    private var isCreate :Boolean = false

    override val layoutId: Int
        get() = R.layout.activity_export_mnemonic

    override fun initData() {
        mWalletBean = intent.getSerializableExtra("data") as BaseData

        if (intent.hasExtra("isCreate")){
            isCreate = intent.getBooleanExtra("isCreate",false);
        }

        tvTitle?.text = getString(R.string.export_mnemonic_code)
        tv_sure.setOnClickListener {
            if(isCreate ){
                var intent = Intent(this,ExportMnemonic2Activity::class.java).apply{
                    putExtra("data",mWalletBean.mnemoinc)
                    startActivity(this)
                    finish()
                }
            }else {

                showDialog()
            }

        }

        tv_exit.setOnClickListener {
            finish()
        }

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
        val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_set_up_sign, null)
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
                if(mWalletBean.password.equals(etNumber.text.toString())){
                    var intent = Intent(this,ExportMnemonic2Activity::class.java).apply{
                        putExtra("data",mWalletBean.mnemoinc)
                        startActivity(this)
                    }
                    finish()
                }else{
                    getString(R.string.password_error).toast()

                }
                dialog!!.dismiss()

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
