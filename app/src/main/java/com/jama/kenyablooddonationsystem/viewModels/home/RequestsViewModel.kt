package com.jama.kenyablooddonationsystem.viewModels.home

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jama.kenyablooddonationsystem.services.GetUserLocation
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import com.firebase.geofire.GeoFire
import com.jama.kenyablooddonationsystem.repository.firebase.firebaseDatabase.GeofireRepository
import com.firebase.geofire.GeoLocation
import com.google.firebase.database.DatabaseError
import com.firebase.geofire.GeoQueryEventListener
import android.icu.lang.UCharacter.GraphemeClusterBreak.T






class RequestsViewModel: ViewModel() {

    private val geofireRepository = GeofireRepository()
    private val geofire = GeoFire(geofireRepository.requestRef())

    fun setupGoe(context: FragmentActivity) {
        viewModelScope.launch(IO) {
            val latlang = GetUserLocation(context).getLastLocation()
            val geoQuery = geofire.queryAtLocation(GeoLocation(latlang["lng"]!!, latlang["lat"]!!), 10.0)
            geoQuery.addGeoQueryEventListener(object : GeoQueryEventListener {
                override fun onKeyEntered(key: String, location: GeoLocation) {
                    println(
                        String.format(
                            "Geofire Key %s entered the search area at [%f,%f]",
                            key,
                            location.latitude,
                            location.longitude
                        )
                    )
                }
                override fun onKeyExited(key: String) {
                    println(String.format("Geofire Key %s is no longer in the search area", key))
                }
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

}