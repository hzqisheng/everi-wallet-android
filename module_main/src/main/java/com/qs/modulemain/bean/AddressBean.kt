package com.qs.modulemain.bean

import org.litepal.crud.DataSupport
/**
 * 作者： MirsFang on 2018/12/15 15:54
 * 邮箱： mirsfang@163.com
 * 类描述：
 */
data class AddressBean (
    var type:String,
    var address:String,
    var groupName:String,
    var name:String,
    var phone:String,
    var memo:String
):DataSupport(){
    override fun toString(): String {
        return type+","+address+","+groupName+","+name+","+phone+","+memo+"\n"
    }
}