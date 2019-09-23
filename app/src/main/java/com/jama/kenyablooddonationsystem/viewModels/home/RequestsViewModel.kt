package com.jama.kenyablooddonationsystem.viewModels.home

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jama.kenyablooddonationsystem.repository.geofire.GeofireRepository
import com.jama.kenyablooddonationsystem.services.GetUserLocation
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class RequestsViewModel: ViewModel() {

    private val geofireRepository = GeofireRepository()

    fun setupGoe(context: FragmentActivity) {
        viewModelScope.launch(IO) {
            val latlang = GetUserLocation(context).getLastLocation()
        }
    }

}