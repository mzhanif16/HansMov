package com.movieapp.hansmov.home.tiket

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.movieapp.hansmov.R
import com.movieapp.hansmov.databinding.FragmentTiketBinding
import com.movieapp.hansmov.home.dashboard.ComingSoonAdapter
import com.movieapp.hansmov.model.Film
import com.movieapp.hansmov.utils.Preferences

class TiketFragment : Fragment() {

    private var _binding: FragmentTiketBinding? = null
    private val binding get()= _binding!!
    private lateinit var preferences: Preferences
    private lateinit var mDatabase: DatabaseReference
    private var dataList = ArrayList<Film>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTiketBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        preferences = Preferences(requireContext())
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")

        binding.rvTiket.layoutManager = LinearLayoutManager(context)
        getData()


    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (getdataSnapshot in snapshot.children){
                    val film = getdataSnapshot.getValue(Film::class.java)
                    dataList.add(film!!)
                }
                binding.rvTiket.adapter = ComingSoonAdapter(dataList){
                    var intent = Intent(context, TiketActivity::class.java).putExtra("data",it)
                    startActivity(intent)
                }

                binding.tvTotal.setText("${dataList.size}Movies")
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,""+error.message,Toast.LENGTH_LONG).show()
            }

        })
    }
}