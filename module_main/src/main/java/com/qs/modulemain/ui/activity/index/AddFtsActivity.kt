package com.qs.modulemain.ui.activity.index

import android.Manifest
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import com.google.gson.Gson
import com.qs.modulemain.R
import com.qs.modulemain.bean.AddFTSBean
import com.qs.modulemain.presenter.AddFTsPresenter
import com.qs.modulemain.util.confirmPassword
import com.qs.modulemain.view.AddFTsView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.base.FingerSuccessCallback
import com.smallcat.shenhai.mvpbase.extension.logE
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import com.smallcat.shenhai.mvpbase.utils.Glide4Engine
import com.smallcat.shenhai.mvpbase.utils.bitmapToBase64
import com.smallcat.shenhai.mvpbase.utils.lastPushTransaction
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zxy.tiny.Tiny
import kotlinx.android.synthetic.main.activity_add_fts.*
import android.text.method.ReplacementTransformationMethod
import android.widget.PopupWindow
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.qs.modulemain.ui.adapter.HelpAdapter
import com.qs.modulemain.util.DataUtils


class AddFtsActivity : BaseActivity<AddFTsPresenter>(), AddFTsView {

    private var REQUEST_CODE_CHOOSE = 0x11
    private var base64Image = ""

    override fun initPresenter() {
        mPresenter = AddFTsPresenter(mContext)
    }

    override val layoutId: Int = R.layout.activity_add_fts

    override fun initData() {
        tvTitle?.text = getString(R.string.CreateFts)
        ivRight?.apply {
            setBackgroundResource(R.drawable.ic_question)
            setOnClickListener { showHelpDialog() }
        }

        iv_img.setOnClickListener {
            val rxPermissions = RxPermissions(this)
            rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe { granted ->
                        if (granted) {
                            Matisse.from(this@AddFtsActivity)
                                    .choose(MimeType.ofImage())
                                    .countable(true)
                                    .maxSelectable(9)
                                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                    .thumbnailScale(0.85f)
                                    .maxSelectable(1)
                                    .imageEngine(Glide4Engine())
                                    .forResult(REQUEST_CODE_CHOOSE)
                        }
                    }
        }

        val stringArray: Array<String> = this.resources.getStringArray(R.array.add_power)
        val arrayAdapter = ArrayAdapter(this, R.layout.my_spinner, android.R.id.text1, stringArray)
        tv_permission.adapter = arrayAdapter
        tv_permission.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }

        }

        et_name.transformationMethod = replacementTransformationMethod

        tv_sure.setOnClickListener {

            if (et_name.text.isEmpty()) {
                getString(R.string.add_fts_symbol_not_empty).toast()
                return@setOnClickListener
            }

            if (et_full_name.text.isEmpty()) {
                getString(R.string.full_name_not_null).toast()
                return@setOnClickListener
            }

            if (et_code.text.isEmpty()) {
                getString(R.string.add_fts_symbol_id_not_empty).toast()
                return@setOnClickListener
            }

            if (et_decimals.text.isEmpty() || et_issue_number.text.isEmpty()) {
                getString(R.string.add_fts_count_not_null).toast()
                return@setOnClickListener
            }

            if (et_issue_number.text.length + et_decimals.text.toString().toInt() > 18) {
                getString(R.string.can_not_biger_18).toast()
                return@setOnClickListener
            }

            if (et_code.text.toString().toIntOrNull() == null) {
                getString(R.string.symbol_id_too_long).toast()
                return@setOnClickListener
            }

            if (base64Image == "") {
                getString(R.string.Upload_icon).toast()
                return@setOnClickListener
            }

            val addBean = AddFTSBean.create(et_name.text.toString(), et_full_name.text.toString(), et_code.text.toString(),
                    sharedPref.publicKey, et_issue_number.text.toString().toInt(), et_decimals.text.toString().toInt(),
                    tv_permission.selectedItemPosition, "")
            val addBeanJson = Gson().toJson(addBean)
            addBeanJson.logE()
            /** 创建 **/
            lastPushTransaction = RxBusCenter.ADD_FTS
            mWebView.evaluateJavascript(WebViewApi.pushTransaction("newfungible", addBeanJson), null)
        }

    }

    private val replacementTransformationMethod = object : ReplacementTransformationMethod() {
        override fun getOriginal(): CharArray {
            return charArrayOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z')
        }

        override fun getReplacement(): CharArray {
            return charArrayOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z')
        }
    }

    private fun showHelpDialog() {
        val contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_help, null, false)
        val recyclerView = contentView.findViewById<RecyclerView>(R.id.rv_list)
        val adapter = HelpAdapter(DataUtils.addFtsHelpData(mContext))
        recyclerView.adapter = adapter
        val popupWindow = PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true)
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popupWindow.isOutsideTouchable = true
        popupWindow.isTouchable = true
        popupWindow.animationStyle = R.style.ActionSheetDialogTop
        popupWindow.showAsDropDown(toolbar)
    }

    override fun onDataResult(msg: String) {
        msg.logE()
        lastPushTransaction = RxBusCenter.UPLOAD_IMG
        val map = HashMap<String, Any>()
        map["key"] = "symbol-icon"
        map["value"] = base64Image
        map["creator"] = "[A] ${sharedPref.publicKey}"
        val json = Gson().toJson(map)
        mWebView.evaluateJavascript(WebViewApi.pushTransaction("addmeta", json, "{}", ".fungible", et_code.text.toString().toInt()), null)
    }

    override fun uploadSuccess(msg: String) {
        getString(R.string.create_success).toast()
        finish()
    }

    /** 显示输入密码 **/
    override fun showPassWordDialog(msg: String) {
        if (lastPushTransaction == RxBusCenter.UPLOAD_IMG) {
            mWebView.evaluateJavascript(WebViewApi.needPrivateKeyResponse(sharedPref.privateKey), null)
        } else {
            confirmPassword(mContext.sharedPref.isFinger, supportFragmentManager, object : FingerSuccessCallback() {
                override fun onCheckSuccess() {
                    mWebView.evaluateJavascript(WebViewApi.needPrivateKeyResponse(sharedPref.privateKey), null)
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            val uriList: List<Uri> = Matisse.obtainResult(data)
            if (uriList.isEmpty()) {
                return
            }
            val options: BitmapFactory.Options = BitmapFactory.Options()
            var bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uriList[0]), null, options)

            val options1: Tiny.BitmapCompressOptions = Tiny.BitmapCompressOptions()
            options1.width = 50
            options1.height = 50
            Tiny.getInstance().source(uriList[0]).asBitmap().withOptions(options1).compress { isSuccess, bitmap ->

                if (isSuccess) {
                    iv_img.setImageBitmap(bitmap)
                    uriList[0].toString().logE()
                    val type: String = options.outMimeType
                    base64Image = "data:" + type + ";base64," + bitmapToBase64(bitmap)
                    base64Image = base64Image.replace("\n", "")
                    WebViewApi.EVTInit()
                }
            }
        }
    }

}
