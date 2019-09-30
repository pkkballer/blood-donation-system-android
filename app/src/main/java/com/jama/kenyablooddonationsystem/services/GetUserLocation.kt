package com.jama.kenyablooddonationsystem.services

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await

class GetUserLocation(private val fragmentActivity: FragmentActivity? = null, private val context: Context? = null) {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    suspend fun getLastLocation(): Map<String, Double> {
        fusedLocationClient = if (fragmentActivity != null ) {
            LocationServices.getFusedLocationProviderClient(fragmentActivity)
        } else {
            LocationServices.getFusedLocationProviderClient(context!!)
        }
        val location = fusedLocationClient.lastLocation.await()
        return mapOf(
            "lat" to location.latitude,
            "lng" to location.longitude
        )
    }
}