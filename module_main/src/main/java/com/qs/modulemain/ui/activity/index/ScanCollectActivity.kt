package com.qs.modulemain.ui.activity.index

import android.content.Intent
import android.graphics.Bitmap
import com.qs.modulemain.R
import com.qs.modulemain.bean.ChooseGetBean
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.utils.Base64Utils
import kotlinx.android.synthetic.main.activity_scan_collect.*

class ScanCollectActivity : SimpleActivity() {

    private lateinit  var mUseFts:ChooseGetBean

    override val layoutId: Int
        get() = R.layout.activity_scan_collect

    override fun initData() {

        layout.setOnClickListener {
//            var intent = Intent(this,)
            var intent = Intent(this,CollectChooseFtsActivity::class.java)
            startActivityForResult(intent,101)
        }

        if(intent.hasExtra("data")){
            mUseFts = intent.getSerializableExtra("data") as ChooseGetBean

            textView6.text =mUseFts.name

            if(mUseFts!!.metas.size >0){
                if("symbol-icon".equals(mUseFts!!.metas[0].key)){
                    var bitmap = Base64Utils.base64ToBitmap(mUseFts!!.metas[0].value)
                    iv_img.setImageBitmap(bitmap)
                }
            }
         }



        tv_sure.setOnClickListener {

            if(mUseFts == null){
                getString(R.string.please_choose_currency_and_money).toast()
                return@setOnClickListener
            }

            var intent = Intent(this,ScanActivity::class.java)
            intent.putExtra("data",mUseFts)
            intent.putExtra("num",et_pwd.text.toString())
            intent.putExtra("ScanType",ScanActivity.RECE)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 101){
            if(resultCode == 101){
                mUseFts = data?.getSerializableExtra("data") as ChooseGetBean

                textView6.text =mUseFts.name

                for (meta in mUseFts.metas) {
                    if("symbol-icon".equals(meta.key)){
                        if(meta.value.isEmpty())return
                        var decodedByte: Bitmap? = Base64Utils.base64ToBitmap(meta.value)
                        if(decodedByte == null)return
                        iv_img.setImageBitmap(decodedByte)
                    }
                }
            }
        }
    }

}
