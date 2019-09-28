package com.jama.kenyablooddonationsystem.viewModels.home

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.jama.kenyablooddonationsystem.models.RequestModel
import com.jama.kenyablooddonationsystem.repository.firebase.firebaseDatabase.RequestRepository
import com.jama.kenyablooddonationsystem.services.GetUserLocation
import kotlinx.coroutines.launch


class RequestsFragmentViewModel: ViewModel() {

    private val requestRepository = RequestRepository()
    val requestModelList: LiveData<MutableList<RequestModel>> = Transformations.map(requestRepository.requestModelList) {
        it.asReversed()
    }
    private var callListenRequests = true

    fun listenToRequests(fragmentActivity: FragmentActivity) {
        viewModelScope.launch {
            if (callListenRequests) {
                val latlang = GetUserLocation(fragmentActivity).getLastLocation()
                requestRepository.listenToRequests(latlang)
                callListenRequests = false
            }
        }
    }
}