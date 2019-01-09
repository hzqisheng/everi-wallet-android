package com.qs.modulemain.ui.fragment

import android.annotation.TargetApi
import android.hardware.fingerprint.FingerprintManager
import android.os.Bundle
import android.os.CancellationSignal
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.qs.modulemain.R
import com.qs.modulemain.util.confirmPassword
import com.smallcat.shenhai.mvpbase.base.FingerSuccessCallback
import com.smallcat.shenhai.mvpbase.extension.toast
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

@TargetApi(23)
class FingerprintDialogFragment : DialogFragment() {

    private var fingerprintManager: FingerprintManager? = null

    private var mCancellationSignal: CancellationSignal? = null

    private var mCipher: Cipher? = null

    private var errorMsg: TextView? = null

    private var mCallback: FingerSuccessCallback? = null

    private val DEFAULT_KEY_NAME = "default_key"

    private var keyStore: KeyStore? = null

    /**
     * 标识是否是用户主动取消的认证。
     */
    private var isSelfCancelled: Boolean = false

    fun setCallback(callback: FingerSuccessCallback) {
        mCallback = callback
    }

    @TargetApi(23)
    private fun initKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore?.load(null)
            val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            val builder = KeyGenParameterSpec.Builder(DEFAULT_KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            keyGenerator.init(builder.build())
            keyGenerator.generateKey()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    @TargetApi(23)
    private fun initCipher() {
        try {
            val key = keyStore?.getKey(DEFAULT_KEY_NAME, null) as SecretKey
            val cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7)
            cipher.init(Cipher.ENCRYPT_MODE, key)
            mCipher = cipher
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initKey()
        initCipher()
        fingerprintManager = context!!.getSystemService(FingerprintManager::class.java)
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fingerprint_dialog, container, false)
        errorMsg = v.findViewById(R.id.error_msg)
        val cancel = v.findViewById<TextView>(R.id.cancel)
        val password = v.findViewById<TextView>(R.id.sure)
        cancel.setOnClickListener {
            dismiss()
            stopListening()
        }
        password.setOnClickListener {
            confirmPassword(0, fragmentManager!!, mCallback!!)
            dismiss()
            stopListening()
        }
        return v
    }

    override fun onResume() {
        super.onResume()
        // 开始指纹认证监听
        startListening(mCipher)
    }

    override fun onPause() {
        super.onPause()
        // 停止指纹认证监听
        stopListening()
    }

    private fun startListening(cipher: Cipher?) {
        isSelfCancelled = false
        mCancellationSignal = CancellationSignal()
        fingerprintManager!!.authenticate(FingerprintManager.CryptoObject(cipher!!), mCancellationSignal, 0, object : FingerprintManager.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                if (!isSelfCancelled) {
                    errorMsg!!.text = errString
                    if (errorCode == FingerprintManager.FINGERPRINT_ERROR_LOCKOUT) {
                        errString.toString().toast()
                    }
                }
            }

            override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence) {
                errorMsg!!.text = helpString
            }

            override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult) {
                mCallback?.onCheckSuccess()
                dismiss()
            }

            override fun onAuthenticationFailed() {
                errorMsg!!.text = getString(R.string.Fingerprint_authentication_failed_please_try_again)
            }
        }, null)
    }

    private fun stopListening() {
        if (mCancellationSignal != null) {
            mCancellationSignal!!.cancel()
            mCancellationSignal = null
            isSelfCancelled = true
        }
    }

}
