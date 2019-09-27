package com.smallcat.shenhai.mvpbase.extension

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.smallcat.shenhai.mvpbase.App
import com.smallcat.shenhai.mvpbase.model.bean.ResultBean
import com.smallcat.shenhai.mvpbase.utils.LogUtil
import org.json.JSONObject
import java.lang.Exception

/**
 * @author hui
 * @date 2018/4/27
 */
fun String.logD() {
    LogUtil.d(this)
}

fun String.logE() {
    LogUtil.e(this)
}

private var toast: Toast? = null

@SuppressLint("ShowToast")
fun String.toast() {
    try {
        if (toast == null) {
            toast = Toast.makeText(App.getInstance(), this, Toast.LENGTH_SHORT)
        } else {
            toast!!.setText(this)
        }
        toast!!.show()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.start(activity: Class<*>) {
    Intent(applicationContext, activity).apply {
        startActivity(this)
    }
}

fun String.hide4(): String {
    val subString = this.substring(3, 7)
    return this.replace(subString, "****")
}

fun String.toResultBean(): ResultBean {
    var resultBean: ResultBean?
    try {
        val json = JSONObject(this)
        resultBean = ResultBean()
        resultBean.code = json.getInt("code")
        resultBean.message = json.getString("message")
        resultBean.data = json.get("data")
    } catch (e: Exception) {
        resultBean = ResultBean()
        resultBean.code = 0
        resultBean.message = "data is invalid !"
    }
    return resultBean!!
}



