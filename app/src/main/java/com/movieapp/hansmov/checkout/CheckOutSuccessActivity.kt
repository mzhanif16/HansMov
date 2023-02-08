package com.movieapp.hansmov.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.movieapp.hansmov.databinding.ActivityCheckOutSuccessBinding
import com.movieapp.hansmov.home.HomeActivity

class CheckOutSuccessActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckOutSuccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckOutSuccessBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnHome.setOnClickListener {
            finishAffinity()
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}