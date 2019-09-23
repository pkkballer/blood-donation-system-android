package com.jama.kenyablooddonationsystem.services

import android.app.Activity
import android.content.Context
import android.location.Location
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await

class GetUserLocation(val context: FragmentActivity) {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    suspend fun getLastLocation(): Location? {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        return fusedLocationClient.lastLocation.await()
    }

}