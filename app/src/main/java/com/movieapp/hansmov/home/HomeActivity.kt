package com.movieapp.hansmov.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.movieapp.hansmov.R
import com.movieapp.hansmov.home.dashboard.DashboardFragment
import com.movieapp.hansmov.home.setting.ProfileFragment
import com.movieapp.hansmov.home.tiket.TiketFragment

lateinit var iv_menu1: ImageView
lateinit var iv_menu2: ImageView
lateinit var iv_menu3: ImageView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val fragmentHome = DashboardFragment()
        val fragmentTiket = TiketFragment()
        val fragmentProfile = ProfileFragment()

        setFragment(fragmentHome)

        iv_menu1 = findViewById(R.id.iv_menu1)
        iv_menu2 = findViewById(R.id.iv_menu2)
        iv_menu3 = findViewById(R.id.iv_menu3)

        iv_menu1.setOnClickListener {
            setFragment(fragmentHome)
            changeIcon(iv_menu1, R.drawable.ic_baseline_space_dashboard_active_24)
            changeIcon(iv_menu2, R.drawable.ic_baseline_local_movies_24)
            changeIcon(iv_menu3, R.drawable.ic_baseline_account_circle_24)
        }
        iv_menu2.setOnClickListener {
            setFragment(fragmentTiket)
            changeIcon(iv_menu1, R.drawable.ic_baseline_space_dashboard_24)
            changeIcon(iv_menu2, R.drawable.ic_baseline_local_movies_active_24)
            changeIcon(iv_menu3, R.drawable.ic_baseline_account_circle_24)
        }
        iv_menu3.setOnClickListener {
            setFragment(fragmentProfile)
            changeIcon(iv_menu1, R.drawable.ic_baseline_space_dashboard_24)
            changeIcon(iv_menu2, R.drawable.ic_baseline_local_movies_24)
            changeIcon(iv_menu3, R.drawable.ic_baseline_account_circle__active_24)
        }
    }

    private fun setFragment(fragment: Fragment){
        val fragmentManager= supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layout_frame, fragment)
        fragmentTransaction.commit()
    }

    private fun changeIcon(imageView: ImageView, int : Int){
        imageView.setImageResource(int)
    }
}