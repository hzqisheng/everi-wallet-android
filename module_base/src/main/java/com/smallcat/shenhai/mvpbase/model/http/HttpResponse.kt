package com.smallcat.shenhai.mvpbase.model.http

/**
 * @author hui
 * @date 2018/4/27
 */
class HttpResponse<T> {
    var code: Int = 0
    var data: T? = null
    var message: String = ""
}
