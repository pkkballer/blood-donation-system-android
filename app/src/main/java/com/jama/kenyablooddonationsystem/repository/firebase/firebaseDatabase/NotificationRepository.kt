package com.jama.kenyablooddonationsystem.repository.firebase.firebaseDatabase

import com.google.firebase.database.FirebaseDatabase
import com.jama.kenyablooddonationsystem.repository.firebase.firebaseMessaging.RegistrationTokenRepository
import kotlinx.coroutines.tasks.await

class NotificationRepository {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val registrationTokenRepository = RegistrationTokenRepository()
    private val databaseNotificationsRef = database.getReference("notifications")

    suspend fun signUpForNotifications() {
        val token = registrationTokenRepository.getRegistrationToken()
        databaseNotificationsRef.child(token).setValue(true).await()
    }

    suspend fun signOutOfNotifications() {
        val token = registrationTokenRepository.getRegistrationToken()
        databaseNotificationsRef.child(token).removeValue().await()
    }

}