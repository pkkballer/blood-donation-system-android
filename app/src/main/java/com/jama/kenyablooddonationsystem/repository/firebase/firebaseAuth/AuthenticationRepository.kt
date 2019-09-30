package com.jama.kenyablooddonationsystem.repository.firebase.firebaseAuth

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.jama.kenyablooddonationsystem.models.UserModel
import com.jama.kenyablooddonationsystem.repository.firebase.firebaseDatabase.AuthRepository
import kotlinx.coroutines.tasks.await

class AuthenticationRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun signIn(email: String, password: String): AuthResult? {
        return auth.signInWithEmailAndPassword(email, password).await()
    }

    suspend fun signUp(userModel: UserModel) {
        val firebaseUser = auth.createUserWithEmailAndPassword(userModel.email, userModel.password).await()
        val profileUpdate = UserProfileChangeRequest.Builder().setDisplayName("${userModel.fullName} (${userModel.bloodType})").build()
        firebaseUser.user!!.updateProfile(profileUpdate).await()
        userModel.uid = firebaseUser.user!!.uid
        val userRepository = AuthRepository(userModel)
        userRepository.createUserRef()
        userRepository.createDonorDetails()
    }

    fun getFirebaseUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun checkIfUserExists(): Boolean {
        return auth.currentUser != null
    }

    fun signOut() {
        auth.signOut()
    }
}