package com.jama.kenyablooddonationsystem.viewModels.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jama.kenyablooddonationsystem.repository.geofire.GeofireRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class RequestsViewModel: ViewModel() {

    private val geofireRepository = GeofireRepository()

    fun setupGoe() {
        viewModelScope.launch(IO) {
            geofireRepository.setUpGeoQuery()
        }
    }

}