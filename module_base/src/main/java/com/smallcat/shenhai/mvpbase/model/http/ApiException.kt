package com.smallcat.shenhai.mvpbase.model.http

/**
 * @author hui
 * @date 2018/4/27
 */
class ApiException : Exception {

    var code: Int = 0

    constructor(msg: String) : super(msg) {}
    constructor(msg: String, code: Int) : super(msg) {
        this.code = code
    }

}
