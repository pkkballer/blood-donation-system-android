package com.jama.kenyablooddonationsystem.repository.firebase.firebaseDatabase

import androidx.lifecycle.MutableLiveData
import com.firebase.geofire.GeoFire
import com.firebase.geofire.GeoLocation
import com.firebase.geofire.GeoQuery
import com.firebase.geofire.GeoQueryEventListener
import com.google.firebase.FirebaseException
import com.google.firebase.database.*
import com.jama.kenyablooddonationsystem.models.DonationDetailsModel
import com.jama.kenyablooddonationsystem.models.RequestModel
import com.jama.kenyablooddonationsystem.repository.firebase.firebaseAuth.AuthenticationRepository
import com.jama.kenyablooddonationsystem.utils.RegexUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class RequestRepository {

    private val authRepository = AuthenticationRepository()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseRequestRef = database.getReference("requests")
    private val databaseAcceptedRequestRef = database.getReference("acceptedRequests")
    private val databaseDonationDetiailsRef = database.getReference("donationDetails")
    private val regexUtil = RegexUtil()
    private lateinit var geoFire: GeoFire
    private lateinit var geoQuery: GeoQuery
    var requestModelList: MutableLiveData<MutableList<RequestModel>> =
        MutableLiveData(mutableListOf())
    var acceptedRequestModelList: MutableLiveData<MutableList<RequestModel>> =
        MutableLiveData(mutableListOf())
    var showSnackbar: MutableLiveData<String> = MutableLiveData("")
    var showProgressbar: MutableLiveData<Boolean> = MutableLiveData(false)
    val closeQrCode: MutableLiveData<Boolean> = MutableLiveData(false)

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

    private fun requestRef(): DatabaseReference {
        val bloodType =
            regexUtil.getUserBloodType(authRepository.getFirebaseUser()?.displayName.toString())
        return database.getReference("geofire/request/${bloodType}")
    }

    private fun acceptedReqRef(): DatabaseReference {
        val uid = authRepository.getFirebaseUser()?.uid
        return databaseAcceptedRequestRef.child(uid!!)
    }

    private fun donationDetailsRef(): DatabaseReference {
        val uid = authRepository.getFirebaseUser()?.uid
        return databaseDonationDetiailsRef.child(uid!!)
    }

    fun getRequest(key: String, isRequest: Boolean) {
        val requestRef = databaseRequestRef.child(key)
        requestRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("Accepted ${p0.message}")
            }
            override fun onDataChange(p0: DataSnapshot) {
                val requestModel = p0.getValue(RequestModel::class.java)
                if (requestModel!!.active) {
                    if (isRequest) {
                        requestModelList.value!!.add(requestModel)
                        requestModelList.notifyObserver()
                    } else {
                        acceptedRequestModelList.value!!.add(requestModel)
                        acceptedRequestModelList.notifyObserver()
                    }
                }
            }
        })
    }

    fun listenToRequests(latlng: Map<String, Double>) {
        geoFire = GeoFire(requestRef())
        geoQuery = geoFire.queryAtLocation(GeoLocation(latlng["lat"]!!, latlng["lng"]!!), 10.0)
        geoQuery.addGeoQueryEventListener(object : GeoQueryEventListener {
            override fun onKeyEntered(key: String, location: GeoLocation) {
                getRequest(key, true)
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

    fun getAcceptedRequests() {
        acceptedRequestModelList.value = mutableListOf()
        acceptedReqRef().addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach {
                    getRequest(it.key!!, false)
                }
            }

        })
    }

    suspend fun registerDonation(requestModel: RequestModel) {
        try {
            donationDetailsRef().updateChildren(requestModel.registerDonation()).await()
            acceptedReqRef().child(requestModel.key).removeValue().await()
            incrementDonationCount()
        } catch (e: FirebaseException) {
            println("FIREBASE error -> ${e.message}")
        }

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

    fun acceptRequest(key: String) {
        showProgressbar(true)
        acceptedReqRef().child(key).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    showSnackbar("You have already accepted this request, please visit the hospital to donate blood")
                    showProgressbar(false)
                } else {
                    acceptedReqRef().child(key).setValue(true).addOnCompleteListener {
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

    private fun incrementDonationCount() {
        donationDetailsRef().runTransaction(object : Transaction.Handler {
            override fun onComplete(p0: DatabaseError?, p1: Boolean, p2: DataSnapshot?) {}

            override fun doTransaction(mutableData: MutableData): Transaction.Result {
                val donationDetail = mutableData.getValue(DonationDetailsModel::class.java)
                    ?: return Transaction.success(mutableData)
                donationDetail.noOfDonations++
                mutableData.value = donationDetail
                closeQrCode(true)

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

    fun closeQrCode(close: Boolean) {
        CoroutineScope(Dispatchers.Main).launch {
            closeQrCode.value = close
        }
    }

}
