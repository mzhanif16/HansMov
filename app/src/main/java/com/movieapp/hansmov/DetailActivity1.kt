package com.movieapp.hansmov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.movieapp.hansmov.checkout.PilihBangkuActivity
import com.movieapp.hansmov.databinding.ActivityDetail1Binding
import com.movieapp.hansmov.home.dashboard.PlaysAdapter
import com.movieapp.hansmov.model.Film
import com.movieapp.hansmov.model.Plays


class DetailActivity1 : AppCompatActivity() {

    private lateinit var binding: ActivityDetail1Binding
    private lateinit var mDatabase: DatabaseReference
    private var dataList: ArrayList<Plays>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetail1Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val data = intent.getParcelableExtra<Film>("data")
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")
            .child(data!!.judul.toString())
            .child("play")

            binding.tvKursi.text = data.judul
            binding.tvGenre.text = data.genre
            binding.tvDescstoryboard.text = data.desc
            binding.tvRate.text = data.rating

            Glide.with(this)
                .load(data.poster)
                .into(binding.ivPoster1)

        binding.rvWhoplay.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        binding.btnPilihBangku.setOnClickListener {
            var intent = Intent(this@DetailActivity1, PilihBangkuActivity::class.java).putExtra("data",data)
            startActivity(intent)
        }

    }
    private fun getData(){
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList!!.clear()

                for (getdataSnapshot in snapshot.children){
                    var Film = getdataSnapshot.getValue(Plays::class.java)
                    dataList!!.add(Film!!)
                }
                binding.rvWhoplay.adapter = PlaysAdapter(dataList!!){

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DetailActivity1,""+error.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}