package com.smallcat.shenhai.mvpbase.model

import com.smallcat.shenhai.mvpbase.model.http.HttpResponse
import io.reactivex.Flowable
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * @author hui
 * @date 2018/4/27
 */
interface Api{

    /**
     * Test
     */
    @POST("user/test")
    fun test(@Query("test1") test: String): Flowable<HttpResponse<Any>>

}