package com.jama.kenyablooddonationsystem.repository.firebase.firebaseAuth

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.jama.kenyablooddonationsystem.models.UserModel
import com.jama.kenyablooddonationsystem.repository.firebase.firebaseDatabase.AuthRepository
import com.jama.kenyablooddonationsystem.repository.firebase.firebaseDatabase.NotificationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthenticationRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val notificationRepository = NotificationRepository()

    suspend fun signIn(email: String, password: String): AuthResult? {
        notificationRepository.signUpForNotifications()
        return auth.signInWithEmailAndPassword(email, password).await()
    }

    suspend fun signUp(userModel: UserModel) {
        val firebaseUser = auth.createUserWithEmailAndPassword(userModel.email, userModel.password).await()
        val profileUpdate = UserProfileChangeRequest.Builder().setDisplayName("${userModel.fullName} (${userModel.bloodType})").build()
        firebaseUser.user!!.updateProfile(profileUpdate).await()
        userModel.uid = firebaseUser.user!!.uid
        val userRepository = AuthRepository(userModel)
        notificationRepository.signUpForNotifications()
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
        CoroutineScope(Dispatchers.IO).launch {
            notificationRepository.signOutOfNotifications()
        }
        auth.signOut()
    }
}