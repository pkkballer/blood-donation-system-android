package com.jama.kenyablooddonationsystem.repository.firebase.firebaseDatabase

import com.google.firebase.database.FirebaseDatabase
import com.jama.kenyablooddonationsystem.models.UserModel
import kotlinx.coroutines.tasks.await

class AuthRepository(private val userModel: UserModel) {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseUserRef = database.getReference("users")
    private val databaseDonorDetailsRef = database.getReference("donationDetails")

    suspend fun createUserRef() {
        databaseUserRef.child(userModel.uid).setValue(userModel.toMap()).await()
    }

    suspend fun createDonorDetails() {
        databaseDonorDetailsRef.child(userModel.uid).setValue(userModel.toDonorDetails()).await()
    }

}