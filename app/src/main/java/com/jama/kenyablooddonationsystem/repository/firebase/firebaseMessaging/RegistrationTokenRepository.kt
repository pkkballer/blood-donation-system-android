package com.jama.kenyablooddonationsystem.repository.firebase.firebaseMessaging

import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class RegistrationTokenRepository {

    private val firebaseInstance = FirebaseInstanceId.getInstance()

    suspend fun getRegistrationToken(): String {
        return try {
            val task = firebaseInstance.instanceId.await()
            task.token
        } catch (e: Exception) {
            "Error ${e.message}"
        }
    }

}