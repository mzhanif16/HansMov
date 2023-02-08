package com.movieapp.hansmov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.movieapp.hansmov.onboarding.OnBoardingOneActivity
import com.movieapp.hansmov.sign.signin.SignInActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        var handler = Handler()
        handler.postDelayed({
            var intent = Intent(this@SplashScreenActivity,OnBoardingOneActivity::class.java)
            startActivity(intent)
            finish()
        },5000)
    }
}