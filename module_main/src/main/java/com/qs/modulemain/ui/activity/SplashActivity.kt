package com.qs.modulemain.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.smallcat.shenhai.mvpbase.extension.start

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({
            start(MainActivity::class.java)
            finish()
        }, 1000)
    }
}
