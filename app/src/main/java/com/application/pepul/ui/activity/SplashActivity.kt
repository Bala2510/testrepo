package com.application.pepul.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.application.pepul.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val w = window
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContentView(R.layout.activity_splash)
        initView()
    }

    private fun initView() {
        Thread(Runnable {
            Thread.sleep(4000)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }).start()
    }
}