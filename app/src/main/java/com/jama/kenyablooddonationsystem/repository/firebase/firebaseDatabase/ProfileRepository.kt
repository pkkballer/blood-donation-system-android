package com.jama.kenyablooddonationsystem.repository.firebase.firebaseDatabase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.jama.kenyablooddonationsystem.models.DonationDetailsModel
import com.jama.kenyablooddonationsystem.models.UserModel
import com.jama.kenyablooddonationsystem.repository.firebase.firebaseAuth.AuthenticationRepository

class ProfileRepository {

    private val authRepository = AuthenticationRepository()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseUserRef = database.getReference("users")
    private val databaseDonorDetailsRef = database.getReference("donationDetails")
    var userModel: MutableLiveData<UserModel> =
        MutableLiveData(UserModel())
    var donationDetailsModel: MutableLiveData<DonationDetailsModel> =
        MutableLiveData(DonationDetailsModel())

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

    private fun userRef(): DatabaseReference {
        val uid = authRepository.getFirebaseUser()?.uid
        return databaseUserRef.child(uid!!)
    }

    private fun donationDetailsRef(): DatabaseReference {
        val uid = authRepository.getFirebaseUser()?.uid
        return databaseDonorDetailsRef.child(uid!!)
    }

    fun getUserProfile() {
        userRef().addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                val user = p0.getValue(UserModel::class.java)
                userModel.value = user
                userModel.notifyObserver()
                getDonationDetails()
            }
        })
    }

    fun getDonationDetails() {
        donationDetailsRef().addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                val donationDetails = p0.getValue(DonationDetailsModel::class.java)
                donationDetailsModel.value = donationDetails
                donationDetailsModel.notifyObserver()
            }
        })
    }
}