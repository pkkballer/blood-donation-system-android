package com.jama.kenyablooddonationsystem.repository.firebase.firebaseDatabase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.jama.kenyablooddonationsystem.repository.firebase.firebaseAuth.AuthenticationRepository

class GeofireRepository {

    private val authRepository = AuthenticationRepository()

    fun requestRef(): DatabaseReference {
        val bloodType = authRepository.getFirebaseUser()?.displayName
        println("Geofire $bloodType")
        return FirebaseDatabase.getInstance().getReference("geofire/request/$bloodType")
    }

}