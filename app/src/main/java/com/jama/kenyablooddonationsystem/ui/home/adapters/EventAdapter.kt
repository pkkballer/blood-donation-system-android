package com.jama.kenyablooddonationsystem.ui.home.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jama.kenyablooddonationsystem.R
import com.jama.kenyablooddonationsystem.models.EventModel
import com.jama.kenyablooddonationsystem.ui.event.EventActivity
import com.jama.kenyablooddonationsystem.utils.DateTimeUtil
import com.jama.kenyablooddonationsystem.utils.EventsDiffUtillCallback
import kotlinx.android.synthetic.main.events_item.view.*

class EventAdapter(private var events: MutableList<EventModel>, private val context: Context): RecyclerView.Adapter<EventAdapter.EventViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.events_item, parent, false)
        return EventViewHolder(layoutInflater)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.itemView.textViewEventName.text = events[position].eventName
        holder.itemView.textViewPlace.text = events[position].place
        val dateTimeUtil = DateTimeUtil()
        holder.itemView.textViewDate.text = dateTimeUtil.getRelativeTime(events[position].timestamp.toLong())
        Glide.with(context).load(events[position].imageUrl).into(holder.itemView.imageViewEvent)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, EventActivity::class.java)
            intent.putExtra("event", events[position])
            context.startActivity(intent)
        }
    }

    fun insertData(newEvents: MutableList<EventModel>) {
        val requestDiffCallback = EventsDiffUtillCallback(events, newEvents)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(requestDiffCallback)
        events.clear()
        events.addAll(newEvents)
        diffResult.dispatchUpdatesTo(this)
    }

    class EventViewHolder(view: View): RecyclerView.ViewHolder(view)
}