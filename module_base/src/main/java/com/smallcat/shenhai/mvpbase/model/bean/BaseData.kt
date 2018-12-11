package com.smallcat.shenhai.mvpbase.model.bean

import org.litepal.crud.DataSupport

data class BaseData(
    var id:Int = 0,
    val mnemoinc: String,
    val password: String,
    val privateKey: String,
    val publicKey: String,
    val type: String
):DataSupport()
