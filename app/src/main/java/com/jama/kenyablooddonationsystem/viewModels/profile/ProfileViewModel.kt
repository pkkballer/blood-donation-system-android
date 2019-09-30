package com.jama.kenyablooddonationsystem.viewModels.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jama.kenyablooddonationsystem.models.DonationDetailsModel
import com.jama.kenyablooddonationsystem.models.UserModel
import com.jama.kenyablooddonationsystem.repository.firebase.firebaseDatabase.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel: ViewModel() {

    private val profileRepository = ProfileRepository()
    val userModel: LiveData<UserModel> = Transformations.map(profileRepository.userModel) {
        it
    }
    val donationDetailsModel: LiveData<DonationDetailsModel> = Transformations.map(profileRepository.donationDetailsModel) {
        it
    }

    init {
        getUserProfile()
    }

    private fun getUserProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            profileRepository.getUserProfile()
        }
    }

}