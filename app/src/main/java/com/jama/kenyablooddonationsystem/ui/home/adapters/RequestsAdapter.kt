package com.jama.kenyablooddonationsystem.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jama.kenyablooddonationsystem.R
import kotlinx.android.synthetic.main.requests_item.view.*

class RequestsAdapter(private val dataSet: Array<String>): RecyclerView.Adapter<RequestsAdapter.RequestViewHolder>() {
    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.requests_item, parent, false)
        return RequestViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        holder.itemView.textViewFullName.text = dataSet[position]
    }

    class RequestViewHolder(view: View): RecyclerView.ViewHolder(view)


}
