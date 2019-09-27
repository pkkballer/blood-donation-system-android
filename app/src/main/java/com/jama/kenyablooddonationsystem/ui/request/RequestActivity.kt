package com.jama.kenyablooddonationsystem.ui.request

import android.annotation.SuppressLint
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jama.kenyablooddonationsystem.R
import com.jama.kenyablooddonationsystem.models.RequestModel
import com.jama.kenyablooddonationsystem.services.GetUserLocation
import com.jama.kenyablooddonationsystem.utils.DateTimeUtil
import com.jama.kenyablooddonationsystem.utils.LeafletWebviewClient
import kotlinx.android.synthetic.main.activity_request.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jama.kenyablooddonationsystem.viewModels.home.RequestsFragmentViewModel
import com.jama.kenyablooddonationsystem.viewModels.home.RequestsViewModel
import kotlinx.coroutines.*
import kotlin.math.roundToInt


class RequestActivity : AppCompatActivity() {

    lateinit var requestModel: RequestModel
    private lateinit var results: FloatArray
    private lateinit var requestsViewModel: RequestsViewModel

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)

        requestModel = intent.getSerializableExtra("request") as RequestModel
        supportActionBar!!.title = requestModel.hname

        requestsViewModel = run {
            ViewModelProviders.of(this)[RequestsViewModel::class.java]
        }

        webview.settings.javaScriptEnabled = true
        this.applicationContext
        webview.loadUrl("file:///android_asset/leaflet/leaflet.html")
        webview.webViewClient = LeafletWebviewClient(webview, requestModel.lat, requestModel.lng)

        requestsViewModel.getUserLocation(applicationContext)
        
        requestsViewModel.latLng.observe(this, Observer {
            if (it.isNotEmpty()) {
                println("Geofire ${it["lat"]}")
                val loc1 = Location("").apply {
                    latitude = it["lat"]!!
                    longitude = it["lng"]!!
                }

                val loc2 = Location("").apply {
                    latitude = requestModel.lat.toDouble()
                    longitude = requestModel.lng.toDouble()
                }

                val distanceInMeters = loc1.distanceTo(loc2)
                textViewDistance.text = "Distance of about ${distanceInMeters.roundToInt()} meters"
            }
        })

        textViewBloodType.text = requestModel.bloodType
        textViewFullName.text = requestModel.recepientName
        val dateTimeUtil = DateTimeUtil()
        textViewDate.text = dateTimeUtil.getTime(requestModel.timestamp.toLong())
        textViewGender.text = requestModel.gender
        textViewReason.text = requestModel.requestReason
        textViewHname.text = requestModel.hname
        textViewPlace.text = requestModel.place


    }
}
