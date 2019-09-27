package com.jama.kenyablooddonationsystem.repository.firebase.firebaseDatabase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.jama.kenyablooddonationsystem.models.RequestModel
import com.jama.kenyablooddonationsystem.repository.firebase.firebaseAuth.AuthenticationRepository
import kotlinx.coroutines.*

class RequestRepository {

    private val authRepository = AuthenticationRepository()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseRequestRef = database.getReference("requests")
    var showSnackbar: MutableLiveData<String> = MutableLiveData("")
    var showProgressbar: MutableLiveData<Boolean> = MutableLiveData(false)

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

    private fun acceptedRequestRef(): DatabaseReference {
        val uid = authRepository.getFirebaseUser()?.uid
        return database.getReference("acceptedRequests/$uid")
    }

    fun acceptRequest(key: String) {
        showProgressbar(true)
        acceptedRequestRef().child(key).addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    showSnackbar("You have already accepted this request, please visit the hospital to donate blood")
                    showProgressbar(false)
                } else {
                    acceptedRequestRef().child(key).setValue(true).addOnCompleteListener {
                        if (it.isSuccessful) {
                            incrementAcceptedRequest(key)
                        }
                    }
                }
            }

        })
    }

    fun incrementAcceptedRequest(key:String) {
        databaseRequestRef.child(key).runTransaction(object: Transaction.Handler {
            override fun onComplete(p0: DatabaseError?, p1: Boolean, p2: DataSnapshot?) {}

            override fun doTransaction(mutableData: MutableData): Transaction.Result {
                val request = mutableData.getValue(RequestModel::class.java)
                    ?: return Transaction.success(mutableData)
                request.accepted++
                mutableData.value = request
                showSnackbar("Request successfully accepted")
                showProgressbar(false)
                return Transaction.success(mutableData)
            }

        })
    }

    fun showSnackbar(text: String) {
        CoroutineScope(Dispatchers.Main).launch {
            showSnackbar.value = text
        }
    }

    fun showProgressbar(show: Boolean) {
        CoroutineScope(Dispatchers.Main).launch {
            showProgressbar.value = show
        }
    }

}