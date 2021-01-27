package com.doctorblue.thehiveshop.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import com.doctorblue.thehiveshop.R
import com.doctorblue.thehiveshop.base.BaseActivity
import com.doctorblue.thehiveshop.databinding.ActivitySplashScreenBinding
import com.doctorblue.thehiveshop.ui.authentication.login.LoginActivity

class SplashScreenActivity : BaseActivity() {

    private val binding: ActivitySplashScreenBinding
        get() = (getViewBinding() as ActivitySplashScreenBinding)

    override fun getLayoutId(): Int = R.layout.activity_splash_screen

    override fun initControls(savedInstanceState: Bundle?) {
        val intent = Intent(this, LoginActivity::class.java)
        val thread: Thread = object : Thread() {
            override fun run() {
                super.run()
                try {
                    sleep(2000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {
                    startActivity(intent)
                    finish()
                }
            }
        }
        thread.start()
    }

    override fun initEvents() {

    }

}