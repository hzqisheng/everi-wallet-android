package com.qs.modulemain.ui.activity.index

import android.app.Dialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import com.google.gson.Gson
import com.qs.modulemain.R
import com.qs.modulemain.bean.AddFTSBean
import com.qs.modulemain.presenter.AddFTsPresenter
import com.qs.modulemain.view.AddFTsView
import com.smallcat.shenhai.mvpbase.base.BaseActivity
import com.smallcat.shenhai.mvpbase.extension.getResourceString
import com.smallcat.shenhai.mvpbase.extension.logE
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import kotlinx.android.synthetic.main.activity_add_fts.*
import com.zhihu.matisse.engine.impl.GlideEngine
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.support.annotation.RequiresApi
import android.text.TextUtils
import com.qs.modulemain.ui.activity.MainActivity
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import com.smallcat.shenhai.mvpbase.utils.Glide4Engine
import com.smallcat.shenhai.mvpbase.utils.bitmapToBase64
import com.smallcat.shenhai.mvpbase.utils.lastPushTransaction
import com.smallcat.shenhai.mvpbase.utils.sizeCompres
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zxy.tiny.Tiny
import com.zxy.tiny.callback.BitmapCallback
import java.util.jar.Manifest


class AddFtsActivity : BaseActivity<AddFTsPresenter>(), AddFTsView {

    /** 密码框 **/
    private var pwdDialog: Dialog? = null
    private var REQUEST_CODE_CHOOSE =0x11;
    private var base64Image:String ? = null;

    override fun initPresenter() {
        mPresenter = AddFTsPresenter(mContext)
    }

    override val layoutId: Int = R.layout.activity_add_fts

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initData() {
        tvTitle?.text = getString(R.string.CreateFts)

        iv_img.setOnClickListener { view ->

            if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == -1) {
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_APN_SETTINGS), 10)
                return@setOnClickListener
            }
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


        var stringArray: Array<String> = this.resources.getStringArray(R.array.add_power)
        var arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, stringArray)
        tv_permission.setAdapter(arrayAdapter);
        tv_permission.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }

        }

        tv_sure.setOnClickListener { view ->

            if(et_name.text.isEmpty()){
                getResourceString(R.string.add_fts_symbol_not_empty)
                return@setOnClickListener
            }

            if(et_code.text.isEmpty()){
                getResourceString(R.string.add_fts_symbol_id_not_empty)
                return@setOnClickListener
            }

            if(et_decimals.text.isEmpty()){
                getResourceString(R.string.add_fts_count_not_null)
                return@setOnClickListener
            }


            var addBean = AddFTSBean.create(et_name.text.toString(),et_full_name.text.toString(), et_code.text.toString(), sharedPref.publicKey, et_decimals.text.toString().toInt(),et_decimals.text.toString().toInt(), tv_permission.selectedItemPosition,base64Image);
            var addBeanJson = Gson().toJson(addBean)
            addBeanJson.logE()
            /** 创建 **/
            lastPushTransaction = RxBusCenter.ADD_FTS
            mWebView.evaluateJavascript(WebViewApi.pushTransaction("newfungible",addBeanJson)){}
        }


    }

    override fun onDataResult(msg: String) {
        msg.logE()
        if(!msg.isEmpty()){
            finish();
        }
    }

    /** 显示输入密码 **/
    override fun showPassWordDailog(msg: String) {
        showSetUpDialog()
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
            if(sharedPref.password.equals(etNumber.text.toString())){
                mWebView.evaluateJavascript(WebViewApi.needPrivateKeyResponse(sharedPref.privateKey)){}
            }else{
                getResourceString(R.string.password_error).toast()
            }
            pwdDialog!!.dismiss()
        }
        tvCancel.setOnClickListener{ pwdDialog!!.dismiss() }
        pwdDialog!!.setContentView(view)
        pwdDialog!!.setCanceledOnTouchOutside(false)
        pwdDialog!!.setCancelable(true)
        pwdDialog!!.show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            var uriList:List<Uri>  = Matisse.obtainResult(data)
            if(uriList == null || uriList.size ==0){
                return
            }
            var options: BitmapFactory.Options =  BitmapFactory.Options()
            var bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uriList[0]),null,options)

            var  options_1:Tiny.BitmapCompressOptions = Tiny.BitmapCompressOptions()
            options_1.width = 50
            options_1.height = 50
            Tiny.getInstance().source(uriList[0]).asBitmap().withOptions(options_1).compress { isSuccess, bitmap ->

                if(isSuccess){
                    iv_img.setImageBitmap(bitmap)
                    uriList[0].toString().logE()
                    var type:String = options.outMimeType;
                    base64Image = "data:"+type+";base64,"+bitmapToBase64(bitmap)
                }

            }


        }
    }



}
