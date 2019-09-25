package com.jama.kenyablooddonationsystem.repository.firebase.firebaseDatabase

import androidx.lifecycle.MutableLiveData
import com.jama.kenyablooddonationsystem.repository.firebase.firebaseAuth.AuthenticationRepository
import com.firebase.geofire.GeoFire
import com.firebase.geofire.GeoLocation
import com.firebase.geofire.GeoQuery
import com.firebase.geofire.GeoQueryEventListener
import com.google.firebase.database.*
import com.jama.kenyablooddonationsystem.models.RequestModel


class GeofireRepository {

    private val authRepository = AuthenticationRepository()
    private val database = FirebaseDatabase.getInstance()
    private lateinit var geoFire: GeoFire
    private lateinit var geoQuery: GeoQuery
    var requestModelList: MutableLiveData<MutableList<RequestModel>> = MutableLiveData(mutableListOf())

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

    private fun requestRef(): DatabaseReference {
        val bloodType = authRepository.getFirebaseUser()?.displayName
        return database.getReference("geofire/request/$bloodType")
    }

    fun getRequest(key: String) {
        val requestRef = database.getReference("requests/$key")
        requestRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                val requestModel = p0.getValue(RequestModel::class.java)
                if (requestModel!!.active) {
                    requestModelList.value!!.add(requestModel!!)
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


}