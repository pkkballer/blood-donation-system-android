package com.jama.kenyablooddonationsystem.viewModels.home

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.jama.kenyablooddonationsystem.models.RequestModel
import com.jama.kenyablooddonationsystem.repository.firebase.firebaseDatabase.GeofireRepository
import com.jama.kenyablooddonationsystem.services.GetUserLocation
import kotlinx.coroutines.launch


class RequestsFragmentViewModel: ViewModel() {

    private var repo: GeofireRepository = GeofireRepository()
    val requestModelList: LiveData<MutableList<RequestModel>> = Transformations.map(repo.requestModelList) {
        it.asReversed()
    }
    private var callListenRequests = true

    fun listenToRequests(fragmentActivity: FragmentActivity) {
        viewModelScope.launch {
            if (callListenRequests) {
                val latlang = GetUserLocation(fragmentActivity).getLastLocation()
                repo.listenToRequests(latlang)
                callListenRequests = false
            }
        }
    }
}