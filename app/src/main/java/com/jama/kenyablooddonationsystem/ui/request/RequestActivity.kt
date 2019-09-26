package com.jama.kenyablooddonationsystem.ui.request

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jama.kenyablooddonationsystem.R
import com.jama.kenyablooddonationsystem.models.RequestModel
import com.jama.kenyablooddonationsystem.utils.LeafletWebviewClient
import kotlinx.android.synthetic.main.activity_request.*

class RequestActivity : AppCompatActivity() {

    lateinit var requestModel: RequestModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)

        requestModel = intent.getSerializableExtra("request") as RequestModel
        supportActionBar!!.title = requestModel.hname

        webview.settings.javaScriptEnabled = true
        webview.loadUrl("file:///android_asset/leaflet/leaflet.html")
        webview.webViewClient = LeafletWebviewClient(webview, requestModel.lat, requestModel.lng)

        textViewBloodType.text = requestModel.bloodType
        textViewFullName.text = requestModel.recepientName
        textViewDate.text = requestModel.timestamp.toString()
        textViewGender.text = requestModel.gender
        textViewReason.text = requestModel.requestReason
        textViewHname.text = requestModel.hname
        textViewPlace.text = requestModel.place


    }
}
