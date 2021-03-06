package com.qs.modulemain.ui.activity.index

import android.app.Dialog
import android.app.KeyguardManager
import android.content.ContentValues
import android.content.Intent
import android.hardware.Camera
import android.hardware.fingerprint.FingerprintManager
import android.media.FaceDetector
import android.os.Build
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.qs.modulemain.R
import com.qs.modulemain.ui.fragment.PasswordDialogFragment
import com.qs.modulemain.util.confirmPassword
import com.smallcat.shenhai.mvpbase.base.FingerSuccessCallback
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.bean.BaseData
import kotlinx.android.synthetic.main.activity_wallet_detail.*
import org.litepal.crud.DataSupport


class WalletDetailActivity : SimpleActivity() {

    private var mWalletName: BaseData? = null
    private var dialog: Dialog? = null

    override val layoutId: Int
        get() = R.layout.activity_wallet_detail

    override fun initData() {
        val id = intent.getIntExtra("data", 0)
        mWalletName = DataSupport.find(BaseData::class.java, id.toLong())

        val name = if (mWalletName?.name.isNullOrBlank()) {
            "everiToken-wallet"
        } else {
            mWalletName?.name.toString()
        }

        mWalletName?.apply {
            tvTitle?.text = name
            tv_name.text = name
            tv_sign.text = publicKey
            if (isFinger == 1) {
                iv_touch.setImageResource(R.drawable.ic_finger_open)
            } else {
                iv_touch.setImageResource(R.drawable.ic_finger_close)
            }
        }

        layout_wallet.setOnClickListener { showDialog(getString(R.string.wallet_name), mWalletName!!.name, 0) }
        tv_export.setOnClickListener { showFingerPrintDialog() }
        //tv_set_up.setOnClickListener { getString(R.string.no_function).toast() }
        iv_touch.setOnClickListener {
            if (mWalletName?.isFinger == 0) {
                openFinger()
            } else {
                closeFinger()
            }
        }
    }

    private fun openFinger() {
        //判断有没有指纹功能
        if (supportFingerprint()) {
            /**check password**/
            val fragment = PasswordDialogFragment()
            fragment.setCallback(object : FingerSuccessCallback() {
                override fun onCheckSuccess() {
                    if (mWalletName?.isSelect == 1) {
                        sharedPref.isFinger = 1
                    }
                    mWalletName?.isFinger = 1
                    val values = ContentValues()
                    values.put("isFinger", 1)
                    DataSupport.update(BaseData::class.java, values, mWalletName!!.id.toLong())
                    iv_touch.setImageResource(R.drawable.ic_finger_open)
                }
            })
            fragment.show(supportFragmentManager, "password")
        }
    }

    private fun closeFinger() {
        if (mWalletName?.isSelect == 1) {
            sharedPref.isFinger = 0
        }
        mWalletName?.isFinger = 0
        val values = ContentValues()
        values.put("isFinger", 0)
        DataSupport.update(BaseData::class.java, values, mWalletName!!.id.toLong())
        iv_touch.setImageResource(R.drawable.ic_finger_close)
    }

    private fun supportFingerprint(): Boolean {
        if (Build.VERSION.SDK_INT < 23) {
            Toast.makeText(this, "您的系统版本过低，不支持指纹功能", Toast.LENGTH_SHORT).show()
            return false
        } else {
            val keyguardManager = getSystemService(KeyguardManager::class.java)
            val fingerprintManager = getSystemService(FingerprintManager::class.java)
            if (!fingerprintManager.isHardwareDetected) {
                Toast.makeText(this, "您的手机不支持指纹功能", Toast.LENGTH_SHORT).show()
                return false
            } else if (!keyguardManager.isKeyguardSecure) {
                Toast.makeText(this, "您还未设置锁屏，请先设置锁屏并添加一个指纹", Toast.LENGTH_SHORT).show()
                return false
            } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                Toast.makeText(this, "您至少需要在系统设置中添加一个指纹", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }

    private fun showFingerPrintDialog() {
        confirmPassword(mWalletName!!.isFinger, supportFragmentManager, object : FingerSuccessCallback() {
            override fun onCheckSuccess() {
                Intent(mContext, ExportPrivateKeyActivity::class.java).apply {
                    putExtra("data", mWalletName)
                    startActivity(this)
                }
            }
        })
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

    override fun onDestroy() {
        super.onDestroy()
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
        }
    }

}
