package com.movieapp.hansmov.home.tiket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.movieapp.hansmov.databinding.ActivityDetail1Binding
import com.movieapp.hansmov.databinding.ActivityTiketBinding
import com.movieapp.hansmov.model.Checkout
import com.movieapp.hansmov.model.Film

class TiketActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTiketBinding
    private var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTiketBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var data = intent.getParcelableExtra<Film>("data")
        binding.tvTitle1.text = data!!.judul
        binding.tvGenre1.text = data!!.genre
        binding.tvRate1.text = data!!.rating

        Glide.with(this)
            .load(data.poster)
            .into((binding.ivPosterImage))

        binding.rvCheckout.layoutManager = LinearLayoutManager(this)
        dataList.add(Checkout("C1",""))
        dataList.add(Checkout("C2",""))

        binding.rvCheckout.adapter = TiketAdapter(dataList){

        }


    }
}