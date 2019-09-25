package com.jama.kenyablooddonationsystem.services

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class GetUserLocation(val fragmentActivity: FragmentActivity) {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    suspend fun getLastLocation(): Map<String, Double> {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(fragmentActivity)
        val location = fusedLocationClient.lastLocation.await()
        return mapOf(
            "lat" to location.latitude,
            "lng" to location.longitude
        )
    }
}