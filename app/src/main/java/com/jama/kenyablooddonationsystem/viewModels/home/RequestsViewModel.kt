package com.jama.kenyablooddonationsystem.viewModels.home

import android.content.Context
import android.view.animation.Transformation
import androidx.lifecycle.*
import com.jama.kenyablooddonationsystem.repository.firebase.firebaseDatabase.RequestRepository
import com.jama.kenyablooddonationsystem.services.GetUserLocation
import kotlinx.coroutines.launch

class RequestsViewModel: ViewModel() {

    var latLng: MutableLiveData<Map<String, Double>> = MutableLiveData(mutableMapOf())
    private val requestRepository = RequestRepository()
    val showSnackbar: LiveData<String> = Transformations.map(requestRepository.showSnackbar) {
        it
    }
    val showProgressbar: LiveData<Boolean> = Transformations.map(requestRepository.showProgressbar) {
        it
    }

    fun viewedRequest(key: String) {
        requestRepository.viewedRequest(key)
    }

    fun acceptRequest(key: String) {
        requestRepository.acceptRequest(key)
    }

    fun getUserLocation(context: Context) {
        viewModelScope.launch {
           latLng.value = GetUserLocation(context = context).getLastLocation()
        }
    }
}