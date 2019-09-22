package com.jama.kenyablooddonationsystem.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jama.kenyablooddonationsystem.R
import com.jama.kenyablooddonationsystem.ui.home.adapters.HomeActivityAdapter
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.elevation = 0F

        viewPager.adapter = HomeActivityAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_home_red_24dp)
        tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_event_red_24dp)
        tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_notifications_red_24dp)
        tabLayout.getTabAt(3)?.setIcon(R.drawable.ic_person_red_24dp)
    }
}
