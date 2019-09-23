package com.jama.kenyablooddonationsystem.services

import android.app.Activity
import android.content.Context
import android.location.Location
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await

class GetUserLocation(private val context: FragmentActivity) {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    suspend fun getLastLocation(): Map<String, Double> {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        val location = fusedLocationClient.lastLocation.await()
        return mapOf(
            "lat" to location.latitude,
            "lng" to location.longitude
        )
    }

}