package com.smallcat.shenhai.mvpbase.model.bean

import org.litepal.crud.DataSupport
import java.io.Serializable

//钱包
class BaseData : DataSupport(), Serializable {
    var id: Int = 0
    var name: String = ""
    var mnemoinc: String = ""
    var password: String = ""
    var privateKey: String = ""
    var publicKey: String = ""
    var type: String = ""

    var isSelect: Int = 0
    var isCreate: Int = 0
    var isFinger: Int = 0

}