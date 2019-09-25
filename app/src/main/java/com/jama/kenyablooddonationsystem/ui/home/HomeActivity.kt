package com.jama.kenyablooddonationsystem.ui.home

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.jama.kenyablooddonationsystem.R
import com.jama.kenyablooddonationsystem.ui.home.adapters.HomeActivityAdapter
import kotlinx.android.synthetic.main.activity_home.*

open class HomeActivity : AppCompatActivity() {

    private val PERMISSIONS_REQUEST_LOCATION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        checkIfHasPermissions()

        supportActionBar?.elevation = 0F

        viewPager.adapter = HomeActivityAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_home_red_24dp)
        tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_event_red_24dp)
        tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_notifications_red_24dp)
        tabLayout.getTabAt(3)?.setIcon(R.drawable.ic_person_red_24dp)
    }

    fun checkIfHasPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                PERMISSIONS_REQUEST_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_LOCATION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay!
                } else {
                    checkIfHasPermissions()
                }
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }
}
