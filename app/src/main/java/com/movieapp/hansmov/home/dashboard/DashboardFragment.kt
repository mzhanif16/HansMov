package com.movieapp.hansmov.home.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.*
import com.movieapp.hansmov.DetailActivity1
import com.movieapp.hansmov.databinding.FragmentDashboardBinding
import com.movieapp.hansmov.model.Film
import com.movieapp.hansmov.utils.Preferences
import java.text.NumberFormat
import java.util.Locale

class DashboardFragment : Fragment() {

    private lateinit var preferences: Preferences
    private lateinit var mDatabase: DatabaseReference
    private var dataList = ArrayList<Film>()
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        preferences = Preferences(activity!!.applicationContext)
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")

        binding.tvUser.setText(preferences.getValues("nama"))
        if(preferences.getValues("saldo").equals("")){
            currency(preferences.getValues("saldo")!!.toDouble(), binding.tvBalance)
        }

        Glide.with(this)
            .load(preferences.getValues("url"))
            .apply(RequestOptions.circleCropTransform())
            .into(binding.ivPhotoprofiledash)

        binding.rvNowplaying.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        binding.rvComingsoon.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false)

        getData()

    }
    private fun getData(){
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                dataList.clear()
                for(getdataSnapshot in datasnapshot.children){
                    var film = getdataSnapshot.getValue(Film::class.java)
                    dataList.add(film!!)
                }

                binding.rvNowplaying.adapter = NowPlayingAdapter(dataList){
                    var intent = Intent(context, DetailActivity1::class.java).putExtra("data", it)
                    startActivity(intent)
                }
                binding.rvComingsoon.adapter = ComingSoonAdapter(dataList){
                    var intent = Intent(context, DetailActivity1::class.java).putExtra("data", it)
                    startActivity(intent)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context,""+databaseError.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun currency(harga : Double, textview : TextView){
        val localID = Locale("in","ID")
        val format = NumberFormat.getCurrencyInstance(localID)
        textview.setText(format.format(harga))
    }
}