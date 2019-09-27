package com.jama.kenyablooddonationsystem.repository.firebase.firebaseDatabase

import com.google.firebase.database.*
import com.jama.kenyablooddonationsystem.models.RequestModel

class RequestRepository {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseRequestRef = database.getReference("requests")

    fun viewedRequest(key: String) {
        databaseRequestRef.child(key).runTransaction(object: Transaction.Handler {
            override fun onComplete(p0: DatabaseError?, p1: Boolean, p2: DataSnapshot?) {}

            override fun doTransaction(mutableData: MutableData): Transaction.Result {
                val request = mutableData.getValue(RequestModel::class.java)
                    ?: return Transaction.success(mutableData)
                request.viewed++
                mutableData.value = request
                return Transaction.success(mutableData)
            }

        })
    }

}