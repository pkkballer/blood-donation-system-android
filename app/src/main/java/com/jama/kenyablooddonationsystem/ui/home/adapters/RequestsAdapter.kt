package com.jama.kenyablooddonationsystem.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jama.kenyablooddonationsystem.R
import com.jama.kenyablooddonationsystem.models.RequestModel
import com.jama.kenyablooddonationsystem.utils.RequestsDiffUtilCallback
import kotlinx.android.synthetic.main.requests_item.view.*

class RequestsAdapter(private var requests: MutableList<RequestModel>): RecyclerView.Adapter<RequestsAdapter.RequestViewHolder>() {
    override fun getItemCount(): Int {
        return requests.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.requests_item, parent, false)
        return RequestViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        holder.itemView.textViewFullName.text = requests[position].recepientName
    }

    fun insertData(newRequests: MutableList<RequestModel>) {
        val requestDiffCallback = RequestsDiffUtilCallback(requests, newRequests)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(requestDiffCallback)
        requests.clear()
        requests.addAll(newRequests)
        diffResult.dispatchUpdatesTo(this)
    }

    class RequestViewHolder(view: View): RecyclerView.ViewHolder(view)
}
