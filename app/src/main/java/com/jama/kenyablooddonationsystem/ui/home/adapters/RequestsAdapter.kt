package com.jama.kenyablooddonationsystem.ui.home.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jama.kenyablooddonationsystem.R
import com.jama.kenyablooddonationsystem.models.RequestModel
import com.jama.kenyablooddonationsystem.ui.request.RequestActivity
import com.jama.kenyablooddonationsystem.utils.DateTimeUtil
import com.jama.kenyablooddonationsystem.utils.RequestsDiffUtilCallback
import kotlinx.android.synthetic.main.requests_item.view.*


class RequestsAdapter(private var requests: MutableList<RequestModel>, private val context: Context): RecyclerView.Adapter<RequestsAdapter.RequestViewHolder>() {
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
        holder.itemView.textViewBloodType.text = requests[position].bloodType
        val dateTimeUtil = DateTimeUtil()
        holder.itemView.textViewDate.text = dateTimeUtil.getTime(requests[position].timestamp.toLong())
        holder.itemView.textViewHname.text = requests[position].hname
        holder.itemView.textViewPlace.text = requests[position].place
        holder.itemView.textViewGender.text = requests[position].gender
        holder.itemView.setOnClickListener {
            val intent = Intent(context, RequestActivity::class.java)
            intent.putExtra("request", requests[position])
            context.startActivity(intent)
        }
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
