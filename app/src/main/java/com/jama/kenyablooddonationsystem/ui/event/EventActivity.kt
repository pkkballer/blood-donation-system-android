package com.jama.kenyablooddonationsystem.ui.event

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.jama.kenyablooddonationsystem.R
import com.jama.kenyablooddonationsystem.models.EventModel
import com.jama.kenyablooddonationsystem.utils.DateTimeUtil
import com.jama.kenyablooddonationsystem.utils.LeafletWebviewClient
import com.jama.kenyablooddonationsystem.viewModels.event.EventViewModel
import kotlinx.android.synthetic.main.activity_event.*

class EventActivity : AppCompatActivity() {

    private lateinit var eventModel: EventModel
    private lateinit var eventViewModel: EventViewModel

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        eventModel = intent.getSerializableExtra("event") as EventModel
        supportActionBar!!.title = eventModel.hname

        eventViewModel = run {
            ViewModelProviders.of(this)[EventViewModel::class.java]
        }

        eventViewModel.viewedRequest(eventModel.key)

        webView.settings.javaScriptEnabled = true
        webView.loadUrl("file:///android_asset/leaflet/leaflet.html")
        webView.webViewClient = LeafletWebviewClient(webView, eventModel.lat, eventModel.lng)

        Glide.with(this).load(eventModel.imageUrl).into(imageViewEvent)

        textViewEventName.text = eventModel.eventName
        textViewPlace.text = eventModel.place
        val dateTimeUtil = DateTimeUtil()
        textViewDate.text = dateTimeUtil.getDate(eventModel.date.toLong())
        textViewEventName.text = eventModel.eventName
        textViewStartTime.text = eventModel.startTime
        textViewEndTime.text = eventModel.endTime
        textViewDescription.text = eventModel.eventDescription

    }
}
