package com.jama.kenyablooddonationsystem.viewModels.event

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jama.kenyablooddonationsystem.models.EventModel
import com.jama.kenyablooddonationsystem.repository.firebase.firebaseDatabase.EventsRepository
import com.jama.kenyablooddonationsystem.services.GetUserLocation
import kotlinx.coroutines.launch

class EventViewModel: ViewModel() {

    private val eventRepository = EventsRepository()
    val eventModelList: LiveData<MutableList<EventModel>> = Transformations.map(eventRepository.eventModelList) {
        it.asReversed()
    }
    private var callListenEvent = true

    fun listenToEvents(fragmentActivity: FragmentActivity) {
        viewModelScope.launch {
            if (callListenEvent) {
                val latlng = GetUserLocation(fragmentActivity).getLastLocation()
                eventRepository.listenToEvents(latlng)
                callListenEvent = false
            }
        }
    }

    fun viewedRequest(key: String) {
        eventRepository.viewedRequest(key)
    }
}