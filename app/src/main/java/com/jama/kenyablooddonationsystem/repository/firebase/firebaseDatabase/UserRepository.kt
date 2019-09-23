package com.jama.kenyablooddonationsystem.repository.firebase.firebaseDatabase

import com.google.firebase.database.FirebaseDatabase
import com.jama.kenyablooddonationsystem.models.SignUpModel
import kotlinx.coroutines.tasks.await

class UserRepository(private val signUpModel: SignUpModel) {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseUserRef = database.getReference("users")
    private val databaseDonorDetailsRef = database.getReference("donationDetails")

    suspend fun createUserRef() {
        databaseUserRef.child(signUpModel.uid).setValue(signUpModel.toMap()).await()
    }

    suspend fun createDonorDetails() {
        databaseDonorDetailsRef.child(signUpModel.uid).setValue(signUpModel.toDonorDetails()).await()
    }

}