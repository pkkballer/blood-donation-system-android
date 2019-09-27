package com.jama.kenyablooddonationsystem.viewModels.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jama.kenyablooddonationsystem.repository.firebase.firebaseDatabase.RequestRepository
import com.jama.kenyablooddonationsystem.services.GetUserLocation
import kotlinx.coroutines.launch

class RequestsViewModel: ViewModel() {

    var latLng: MutableLiveData<Map<String, Double>> = MutableLiveData(mutableMapOf())
    private val requestRepository = RequestRepository()

    fun viewedRequest(key: String) {
        requestRepository.viewedRequest(key)
    }

    fun getUserLocation(context: Context) {
        viewModelScope.launch {
           latLng.value = GetUserLocation(context = context).getLastLocation()
        }
    }
}