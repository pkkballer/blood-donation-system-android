package com.jama.kenyablooddonationsystem.repository.geofire

import androidx.fragment.app.FragmentActivity
import com.firebase.geofire.GeoQuery
import com.firebase.geofire.GeoFire
import com.google.firebase.database.FirebaseDatabase
import com.jama.kenyablooddonationsystem.services.GetUserLocation
import com.firebase.geofire.GeoLocation
import com.firebase.geofire.GeoQueryDataEventListener
import com.google.firebase.database.DatabaseError
import com.firebase.geofire.GeoQueryEventListener




class GeofireRepository {
    private lateinit var geoQuery: GeoQuery
    private lateinit var geoFire: GeoFire
    private val databaseRef = FirebaseDatabase.getInstance().getReference("geofire/request/AB+")

    suspend fun setUpGeoQuery() {
        geoFire = GeoFire(databaseRef)
//        geoQuery = geoFire.queryAtLocation(GeoLocation(lat, lng), 10.0)
//        geoQuery.addGeoQueryEventListener(object : GeoQueryEventListener {
//            override fun onKeyEntered(key: String, location: GeoLocation) {
//                println(
//                    String.format(
//                        "Geofire Key %s entered the search area at [%f,%f]",
//                        key,
//                        location.latitude,
//                        location.longitude
//                    )
//                )
//            }
//
//            override fun onKeyExited(key: String) {
//                println(String.format("Geofire Key %s is no longer in the search area", key))
//            }
//
//            override fun onKeyMoved(key: String, location: GeoLocation) {
//                println(
//                    String.format(
//                        "Geofire Key %s moved within the search area to [%f,%f]",
//                        key,
//                        location.latitude,
//                        location.longitude
//                    )
//                )
//            }
//
//            override fun onGeoQueryReady() {
//                println("Geofire All initial data has been loaded and events have been fired!")
//            }
//
//            override fun onGeoQueryError(error: DatabaseError) {
//                System.err.println("Geofire There was an error with this query: $error")
//            }
//        })
    }

}