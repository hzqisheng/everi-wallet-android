package com.qs.modulemain.ui.activity.index

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.qs.modulemain.R
import com.qs.modulemain.bean.ChooseGetBean
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.toast
import kotlinx.android.synthetic.main.activity_add_meta.*

/** 元数据 **/
class AddMetaActivity : SimpleActivity() {
    private lateinit var mMetaBean:ChooseGetBean.MetasBean

    override val layoutId: Int
        get() = R.layout.activity_add_meta

    override fun initData() {

        tvSave.setOnClickListener {

            if(edKey.text.toString().isEmpty()){
                "Invalid Key".toast()
                return@setOnClickListener
            }

            if(edValue.text.toString().isEmpty()){
                "Invalid Value".toast()
                return@setOnClickListener
            }

            mMetaBean = ChooseGetBean.MetasBean()
            mMetaBean.key = edKey.text.toString()
            mMetaBean.value = edValue.text.toString()
            mMetaBean.creator = "[A] " +sharedPref.publicKey
            var intent = Intent()
            intent.putExtra("result",mMetaBean)
            setResult(1,intent)
            finish()

        }

    }


}
