package com.jama.kenyablooddonationsystem.repository.firebase.firebaseDatabase

import androidx.lifecycle.MutableLiveData
import com.firebase.geofire.GeoFire
import com.firebase.geofire.GeoLocation
import com.firebase.geofire.GeoQuery
import com.firebase.geofire.GeoQueryEventListener
import com.google.firebase.database.*
import com.jama.kenyablooddonationsystem.models.RequestModel
import com.jama.kenyablooddonationsystem.repository.firebase.firebaseAuth.AuthenticationRepository
import com.jama.kenyablooddonationsystem.utils.RegexUtil
import kotlinx.coroutines.*

class RequestRepository {

    private val authRepository = AuthenticationRepository()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseRequestRef = database.getReference("requests")
    private val regexUtil = RegexUtil()
    private lateinit var geoFire: GeoFire
    private lateinit var geoQuery: GeoQuery
    var requestModelList: MutableLiveData<MutableList<RequestModel>> =
        MutableLiveData(mutableListOf())
    var showSnackbar: MutableLiveData<String> = MutableLiveData("")
    var showProgressbar: MutableLiveData<Boolean> = MutableLiveData(false)

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

    private fun requestRef(): DatabaseReference {
        val bloodType =
            regexUtil.getUserBloodType(authRepository.getFirebaseUser()?.displayName.toString())
        return database.getReference("geofire/request/${bloodType}")
    }

    fun getRequest(key: String) {
        val requestRef = databaseRequestRef.child(key)
        requestRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                val requestModel = p0.getValue(RequestModel::class.java)
                if (requestModel!!.active) {
                    requestModelList.value!!.add(requestModel)
                    requestModelList.notifyObserver()
                }
            }
        })
    }

    fun listenToRequests(latlang: Map<String, Double>) {
        geoFire = GeoFire(requestRef())
        geoQuery = geoFire.queryAtLocation(GeoLocation(latlang["lng"]!!, latlang["lat"]!!), 10.0)
        geoQuery.addGeoQueryEventListener(object : GeoQueryEventListener {
            override fun onKeyEntered(key: String, location: GeoLocation) {
                getRequest(key)
            }

            override fun onKeyExited(key: String) {}
            override fun onKeyMoved(key: String, location: GeoLocation) {}
            override fun onGeoQueryReady() {
                println("Geofire All initial data has been loaded and events have been fired!")
            }

            override fun onGeoQueryError(error: DatabaseError) {
                System.err.println("Geofire There was an error with this query: $error")
            }
        })
    }

    fun viewedRequest(key: String) {
        databaseRequestRef.child(key).runTransaction(object : Transaction.Handler {
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
        acceptedRequestRef().child(key).addListenerForSingleValueEvent(object : ValueEventListener {
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

    fun incrementAcceptedRequest(key: String) {
        databaseRequestRef.child(key).runTransaction(object : Transaction.Handler {
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