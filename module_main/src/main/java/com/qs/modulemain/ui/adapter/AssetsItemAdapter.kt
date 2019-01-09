package com.qs.modulemain.ui.adapter

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qs.modulemain.R
import com.qs.modulemain.bean.ChooseGetBean
import com.qs.modulemain.ui.activity.index.PayActivity
import com.smallcat.shenhai.mvpbase.extension.getResourceString
import com.smallcat.shenhai.mvpbase.extension.sharedPref
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.utils.Base64Utils

/**
 * Created by hui on 2018/7/19.
 */
class AssetsItemAdapter(data: MutableList<ChooseGetBean>?) : BaseQuickAdapter<ChooseGetBean, BaseViewHolder>(R.layout.item_assets_item, data) {
    private var pwdDialog:Dialog? = null

    override fun convert(viewHolder: BaseViewHolder, item: ChooseGetBean) {
        viewHolder.addOnClickListener(R.id.content)
        viewHolder.setText(R.id.tv_name, item.sym_name+"("+item.asset.split("S")[1]+")")

        viewHolder.setText(R.id.tv_number,item.asset.split(" ")[0])

        viewHolder.getView<ImageView>(R.id.iv_pay).setOnClickListener {
            showSetUpDialog(item)
        }

        val bg = viewHolder.getView<ImageView>(R.id.iv_img)

        bg.setImageResource(R.drawable.icon_fukuan_evt)

        for (meta in item.metas) {
            if("symbol-icon" == meta.key){
                if(meta.value.isEmpty())return
                val decodedByte: Bitmap = Base64Utils.base64ToBitmap(meta.value) ?: return
                bg.setImageBitmap(decodedByte)
            }
        }
    }

    private fun showSetUpDialog(item: ChooseGetBean){
        if (pwdDialog == null) {
            pwdDialog = Dialog(mContext, R.style.CustomDialog)
        }
        val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_set_up_sign, null)
        val etNumber = view.findViewById<EditText>(R.id.et_number)
        val tvSure = view.findViewById<TextView>(R.id.tv_sure)
        val cbCheck = view.findViewById<CheckBox>(R.id.cb_check)
        val tvCancel = view.findViewById<TextView>(R.id.tv_cancel)
        tvSure.setOnClickListener {
            if(mContext.sharedPref.password == etNumber.text.toString()){
                val intent = Intent(mContext, PayActivity::class.java)
                intent.putExtra("data",item)
                mContext.startActivity(intent)
            }else{
                mContext.getString(R.string.password_error).toast()
            }
            pwdDialog!!.dismiss()
        }
        tvCancel.setOnClickListener{ pwdDialog!!.dismiss() }
        pwdDialog!!.setContentView(view)
        pwdDialog!!.setCanceledOnTouchOutside(false)
        pwdDialog!!.setCancelable(true)
        pwdDialog!!.show()
    }
}