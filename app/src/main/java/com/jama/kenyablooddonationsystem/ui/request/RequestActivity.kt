package com.jama.kenyablooddonationsystem.ui.request

import android.annotation.SuppressLint
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jama.kenyablooddonationsystem.R
import com.jama.kenyablooddonationsystem.models.RequestModel
import com.jama.kenyablooddonationsystem.utils.DateTimeUtil
import com.jama.kenyablooddonationsystem.utils.LeafletWebviewClient
import kotlinx.android.synthetic.main.activity_request.*
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.jama.kenyablooddonationsystem.viewModels.request.RequestsViewModel
import kotlin.math.roundToInt

class RequestActivity : AppCompatActivity() {

    lateinit var requestModel: RequestModel
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
        webview.loadUrl("file:///android_asset/leaflet/leaflet.html")
        webview.webViewClient = LeafletWebviewClient(webview, requestModel.lat, requestModel.lng)

        requestsViewModel.getUserLocation(applicationContext)
        requestsViewModel.viewedRequest(requestModel.key)
        
        requestsViewModel.latLng.observe(this, Observer {
            if (it.isNotEmpty()) {
                val loc1 = Location("").apply {
                    latitude = it["lat"]!!
                    longitude = it["lng"]!!
                }
                val loc2 = Location("").run {
                    latitude = requestModel.lat.toDouble()
                    longitude = requestModel.lng.toDouble()
                    distanceTo(loc1)
                }
                textViewDistance.text = "Distance of about ${loc2.roundToInt()} meters"
            }
        })

        requestsViewModel.showSnackbar.observe(this, Observer {
            if (it.isNotBlank()) Snackbar.make(buttonAcceptRequest.rootView, it, Snackbar.LENGTH_SHORT).show()
        })

        requestsViewModel.showProgressbar.observe(this, Observer {
            when(it) {
                true -> {
                    progressBar.visibility = View.VISIBLE
                    buttonAcceptRequest.isEnabled = !it
                }
                else -> {
                    progressBar.visibility = View.GONE
                    buttonAcceptRequest.isEnabled = !it
                }
            }
        })

        buttonAcceptRequest.setOnClickListener {
            requestsViewModel.acceptRequest(requestModel.key)
        }

        textViewBloodType.text = requestModel.bloodType
        textViewFullName.text = requestModel.recepientName
        val dateTimeUtil = DateTimeUtil()
        textViewDate.text = dateTimeUtil.getRelativeTime(requestModel.timestamp.toLong())
        textViewGender.text = requestModel.gender
        textViewReason.text = requestModel.requestReason
        textViewHname.text = requestModel.hname
        textViewPlace.text = requestModel.place
    }
}
