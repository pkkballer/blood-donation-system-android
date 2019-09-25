package com.jama.kenyablooddonationsystem.utils


import androidx.recyclerview.widget.DiffUtil
import com.jama.kenyablooddonationsystem.models.RequestModel

class RequestsDiffUtilCallback(
    private var oldList: MutableList<RequestModel>,
    private var newList: MutableList<RequestModel>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].key == newList[newItemPosition].key
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}