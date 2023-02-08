package com.movieapp.hansmov.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.movieapp.hansmov.R
import com.movieapp.hansmov.sign.signin.SignInActivity

lateinit var button1: Button
class OnBoardingThreeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding_three)

        button1 = findViewById(R.id.btn_mulai)
        button1.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this@OnBoardingThreeActivity, SignInActivity::class.java))
        }
    }
}