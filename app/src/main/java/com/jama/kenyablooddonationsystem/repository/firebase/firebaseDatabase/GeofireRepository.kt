package com.jama.kenyablooddonationsystem.repository.firebase.firebaseDatabase

import com.google.firebase.database.*
import com.jama.kenyablooddonationsystem.models.RequestModel
import com.jama.kenyablooddonationsystem.repository.firebase.firebaseAuth.AuthenticationRepository
import java.util.concurrent.CountDownLatch

class GeofireRepository: ValueEventListener {

    private lateinit var snapshot: DataSnapshot
    lateinit var countDownLatch: CountDownLatch

    override fun onCancelled(error: DatabaseError) {}

    override fun onDataChange(dataSnapshot: DataSnapshot) {
        snapshot = dataSnapshot
        countDownLatch.countDown()
    }

    private val authRepository = AuthenticationRepository()
    private val database = FirebaseDatabase.getInstance()

    fun requestRef(): DatabaseReference {
        val bloodType = authRepository.getFirebaseUser()?.displayName
        return database.getReference("geofire/request/$bloodType")
    }

    fun getRequest(key: String): RequestModel? {
        countDownLatch = CountDownLatch(1)
        val requestRef = database.getReference("requests/$key")
        requestRef.addListenerForSingleValueEvent(this)
        try {
            countDownLatch.await()
            return snapshot.getValue(RequestModel::class.java)
        } finally {

        }
    }

}