package com.jama.kenyablooddonationsystem.repository.firebase.firebaseAuth

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import com.jama.kenyablooddonationsystem.models.SignUpModel
import com.jama.kenyablooddonationsystem.repository.firebase.firebaseDatabase.UserRepository
import kotlinx.coroutines.tasks.await

class AuthenticationRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun signIn(email: String, password: String): AuthResult? {
        return auth.signInWithEmailAndPassword(email, password).await()
    }

    suspend fun signUp(signUpModel: SignUpModel) {
        val firebaseUser = auth.createUserWithEmailAndPassword(signUpModel.email, signUpModel.password).await()
        val profileUpdate = UserProfileChangeRequest.Builder().setDisplayName(signUpModel.bloodType).build()
        firebaseUser.user!!.updateProfile(profileUpdate).await()
        signUpModel.uid = firebaseUser.user!!.uid
        val userRepository = UserRepository(signUpModel)
        userRepository.createUserRef()
        userRepository.createDonorDetails()
    }

    fun checkIfUserExists(): Boolean {
        return auth.currentUser != null
    }

    fun signOut() {
        auth.signOut()
    }
}