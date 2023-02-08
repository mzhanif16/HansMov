package com.movieapp.hansmov.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.movieapp.hansmov.R

    lateinit var button: Button
class OnBoardingTwoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding_two)

        button = findViewById(R.id.btn_lanjutkanpresale)
        button.setOnClickListener {
            var intent = Intent(this@OnBoardingTwoActivity,OnBoardingThreeActivity::class.java)
            startActivity(intent)
        }
    }
}