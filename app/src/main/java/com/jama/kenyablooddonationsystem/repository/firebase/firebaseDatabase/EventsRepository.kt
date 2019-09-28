package com.jama.kenyablooddonationsystem.repository.firebase.firebaseDatabase

import androidx.lifecycle.MutableLiveData
import com.firebase.geofire.GeoFire
import com.firebase.geofire.GeoLocation
import com.firebase.geofire.GeoQuery
import com.firebase.geofire.GeoQueryEventListener
import com.google.firebase.database.*
import com.jama.kenyablooddonationsystem.models.EventModel
import com.jama.kenyablooddonationsystem.repository.firebase.firebaseAuth.AuthenticationRepository

class EventsRepository {

    private val authRepository = AuthenticationRepository()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseEventsRef = database.getReference("events")
    private val eventGeoFireRef = database.getReference("geofire/events")
    private lateinit var geoFire: GeoFire
    private lateinit var geoQuery: GeoQuery
    var eventModelList: MutableLiveData<MutableList<EventModel>> =
        MutableLiveData(mutableListOf())

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

    fun getEvent(key: String) {
        val requestRef = databaseEventsRef.child(key)
        requestRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                val eventModel = p0.getValue(EventModel::class.java)
                if (eventModel!!.active) {
                    eventModelList.value!!.add(eventModel)
                    eventModelList.notifyObserver()
                }
            }
        })
    }

    fun listenToEvents(latlng: Map<String, Double>) {
        geoFire = GeoFire(eventGeoFireRef)
        geoQuery = geoFire.queryAtLocation(GeoLocation(latlng["lng"]!!, latlng["lat"]!!), 10.0)
        geoQuery.addGeoQueryEventListener(object : GeoQueryEventListener {
            override fun onKeyEntered(key: String, location: GeoLocation) {
                getEvent(key)
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