package com.movieapp.hansmov.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.movieapp.hansmov.R
import com.movieapp.hansmov.databinding.ActivityCheckOutBinding
import com.movieapp.hansmov.model.Checkout
import com.movieapp.hansmov.utils.Preferences

class CheckOutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckOutBinding
    private var dataList = ArrayList<Checkout>()
    private var total:Int = 0
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckOutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        preferences = Preferences(this)
        dataList = intent.getSerializableExtra("data") as ArrayList<Checkout>

        for (a in dataList.indices){
            total += dataList[a].harga!!.toInt()
        }

        dataList.add(Checkout("Total harus dibayar", total.toString()))

        binding.rcCheckout.layoutManager = LinearLayoutManager(this)
        binding.rcCheckout.adapter = CheckoutAdapter(dataList){
        }

        binding.btnTiket.setOnClickListener {
            var intent = Intent(this, CheckOutSuccessActivity::class.java)
            startActivity(intent)
        }
    }
}