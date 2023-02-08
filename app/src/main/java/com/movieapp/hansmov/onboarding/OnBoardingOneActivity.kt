package com.movieapp.hansmov.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.movieapp.hansmov.R
import com.movieapp.hansmov.sign.signin.SignInActivity
import com.movieapp.hansmov.utils.Preferences

class OnBoardingOneActivity : AppCompatActivity() {

    lateinit var preferences: Preferences
    lateinit var button1:Button
    lateinit var button2:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding_one)

        preferences = Preferences(this)
        if(preferences.getValues("onboarding").equals("1")){
            finishAffinity()
            var intent = Intent(this@OnBoardingOneActivity, SignInActivity::class.java)
            startActivity(intent)
        }

        button1 = findViewById(R.id.btn_lanjutnowplaying)
        button1.setOnClickListener {
            var intent = Intent(this@OnBoardingOneActivity, OnBoardingTwoActivity::class.java)
            startActivity(intent)
        }
        button2 = findViewById(R.id.btn_lewatiperkenalannowplaying)
        button2.setOnClickListener {
            preferences.setValues("onboarding","1")
            finishAffinity()
            var intent = Intent(this@OnBoardingOneActivity, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}