package com.smallcat.shenhai.mvpbase.model.http

import android.annotation.SuppressLint
import com.google.gson.GsonBuilder
import com.smallcat.shenhai.mvpbase.model.Api
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author hui
 * @date 2018/4/27
 */
class HttpModule private constructor() {
    private val client = createOkHttp()
    private val factory = GsonConverterFactory.create(GsonBuilder().create())
    private var mRetrofit: Retrofit? = null

    val api: Api
        get() = mRetrofit!!.create(Api::class.java)

    init {
        init()
    }

    private fun createOkHttp(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    }

    private fun init() {
        resetApp()
    }

    private fun resetApp() {
        mRetrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    companion object {
        private const val BASE_URL = ApiConfig.BASE_URL
        @SuppressLint("StaticFieldLeak")
        private var instance: HttpModule? = null
        fun getInstance(): HttpModule {
            if (instance == null) {
                instance = HttpModule()
            }
            return instance as HttpModule
        }
    }
}