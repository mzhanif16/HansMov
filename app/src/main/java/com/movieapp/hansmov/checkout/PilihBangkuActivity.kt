package com.movieapp.hansmov.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.movieapp.hansmov.R
import com.movieapp.hansmov.databinding.ActivityPilihBangkuBinding
import com.movieapp.hansmov.model.Checkout
import com.movieapp.hansmov.model.Film

class PilihBangkuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPilihBangkuBinding
    var statusA3:Boolean =false
    var statusA4:Boolean =false
    var total:Int =0

    private var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPilihBangkuBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val data = intent.getParcelableExtra<Film>("data")
        binding.tvTitle.text = data!!.judul
        binding.a3.setOnClickListener{
            if(statusA3) {
                binding.a3.setImageResource(R.drawable.rectangle_5)
                statusA3 = false
                total -= 1
                beliTiket(total)
            } else{
                binding.a3.setImageResource(R.drawable.rectangle_selected)
                statusA3 = true
                total += 1
                beliTiket(total)

                val data = Checkout("A3","70000")
                dataList.add(data)
            }
        }
        binding.a4.setOnClickListener{
            if(statusA4) {
                binding.a4.setImageResource(R.drawable.rectangle_5)
                statusA4 = false
                total -= 1
                beliTiket(total)
            } else{
                binding.a4.setImageResource(R.drawable.rectangle_selected)
                statusA4 = true
                total += 1
                beliTiket(total)

                val data = Checkout("A4","70000")
                dataList.add(data)
            }
        }

        binding.btnBelitiket.setOnClickListener {
            var intent = Intent(this@PilihBangkuActivity, CheckOutActivity::class.java).putExtra("data",dataList)
            startActivity(intent)
        }
    }

    private fun beliTiket(total: Int) {
        if(total == 0){
            binding.btnBelitiket.setText("Beli Tiket")
            binding.btnBelitiket.visibility = View.INVISIBLE
        } else{
            binding.btnBelitiket.setText("Beli Tiket ("+total+")")
            binding.btnBelitiket.visibility = View.VISIBLE
        }
    }
}