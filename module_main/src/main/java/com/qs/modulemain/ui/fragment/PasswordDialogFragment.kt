package com.qs.modulemain.ui.fragment

import android.annotation.TargetApi
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.qs.modulemain.R
import com.smallcat.shenhai.mvpbase.base.FingerSuccessCallback
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.toast

@TargetApi(23)
class PasswordDialogFragment : DialogFragment() {

    private lateinit var mCallback: FingerSuccessCallback
    private var mPassword: String = ""

    fun setCallback(callback: FingerSuccessCallback) {
        mCallback = callback
    }

    fun setPwd(password: String) {
        mPassword = password
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.dialog_set_up_sign, container, false)
        val etNumber = v.findViewById<EditText>(R.id.et_number)
        val tvSure = v.findViewById<TextView>(R.id.tv_sure)
        val cbCheck = v.findViewById<CheckBox>(R.id.cb_check)
        val tvCancel = v.findViewById<TextView>(R.id.tv_cancel)
        tvSure.setOnClickListener {
            if (mPassword == ""){
                mPassword = context!!.sharedPref.password
            }
            if(mPassword == etNumber.text.toString()){
                mCallback.onCheckSuccess()
                dismiss()
            }else{
                getString(R.string.password_error).toast()
            }
        }
        tvCancel.setOnClickListener { dismiss() }
        return v
    }
}
