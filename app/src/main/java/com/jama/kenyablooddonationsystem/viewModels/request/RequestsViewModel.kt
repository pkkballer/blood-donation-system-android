package com.jama.kenyablooddonationsystem.viewModels.request

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.jama.kenyablooddonationsystem.models.RequestModel
import com.jama.kenyablooddonationsystem.repository.firebase.firebaseDatabase.RequestRepository
import com.jama.kenyablooddonationsystem.services.GetUserLocation
import kotlinx.coroutines.launch

class RequestsViewModel: ViewModel() {

    var latLng: MutableLiveData<Map<String, Double>> = MutableLiveData(mutableMapOf())
    private val requestRepository = RequestRepository()
    val requestModelList: LiveData<MutableList<RequestModel>> = Transformations.map(requestRepository.requestModelList) {
        it.asReversed()
    }
    val acceptedRequestModelList: LiveData<MutableList<RequestModel>> = Transformations.map(requestRepository.acceptedRequestModelList) {
        it.asReversed()
    }
    val showSnackbar: LiveData<String> = Transformations.map(requestRepository.showSnackbar) {
        it
    }
    val showProgressbar: LiveData<Boolean> = Transformations.map(requestRepository.showProgressbar) {
        it
    }
    val closeQrCode: LiveData<Boolean> = Transformations.map(requestRepository.closeQrCode) {
        it
    }
    private var callListenRequests = true

    fun listenToRequests(fragmentActivity: FragmentActivity) {
        viewModelScope.launch {
            if (callListenRequests) {
                val latlng = GetUserLocation(fragmentActivity).getLastLocation()
                requestRepository.listenToRequests(latlng)
                callListenRequests = false
            }
        }
    }

    fun getAcceptedRequests() {
        requestRepository.getAcceptedRequests()
    }

    fun registerDonation(requestModel: RequestModel) {
        viewModelScope.launch {
            requestRepository.registerDonation(requestModel)
        }
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